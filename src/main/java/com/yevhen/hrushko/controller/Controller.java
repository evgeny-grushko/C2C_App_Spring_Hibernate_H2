package com.yevhen.hrushko.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.yevhen.hrushko.model.EventModel;
import com.yevhen.hrushko.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8091")
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    Repository repository;

    @GetMapping("/events")
    public ResponseEntity<List<EventModel>> getAllEvents(@RequestParam(required = false) String eventName) {
        try {
            List<EventModel> events = new ArrayList<>();

            if (eventName == null) {
                repository.findAll().stream().limit(1000).forEach(events::add);
            } else {
                repository.findByEventNameContainingIgnoreCase(eventName).stream().limit(1000).forEach(events::add);
            }

            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/events/{id}")
    public ResponseEntity<EventModel> getEventById(@PathVariable("id") long id) {
        Optional<EventModel> eventData = repository.findById(id);

        if (eventData.isPresent()) {
            return new ResponseEntity<>(eventData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/events")
    public ResponseEntity<EventModel> putEvent(@RequestBody EventModel eventModel) {

        try {
            // Delete old data, calculate the cutoff time as 30 days ago
            Instant cutoffTime = Instant.now().minus(Duration.ofDays(30));
            repository.deleteByTimeBefore(cutoffTime);
        } catch (Exception e) {
            System.out.println("Eception: " + e.getMessage());
            System.out.println("Eception: " + e.getCause());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            EventModel controller = repository.save(new EventModel(eventModel.getEventName(), eventModel.getOwnerId(), eventModel.getAssetId(),
                    eventModel.getValue(), eventModel.getStart(), eventModel.getEnd(), eventModel.getCreatedOn(), eventModel.getConfidence(),
                    eventModel.getEnv()));
            return new ResponseEntity<>(controller, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Eception: " + e.getMessage());
            System.out.println("Eception: " + e.getCause());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventModel> updateEventById(@PathVariable("id") long id, @RequestBody EventModel events) {
        Optional<EventModel> eventsData = repository.findById(id);

        // TODO add check for all required fileds

        if (eventsData.isPresent()) {
            EventModel event = eventsData.get();
            event.setEventName(events.getEventName());
            event.setOwnerId(events.getOwnerId());
            event.setAssetId(events.getAssetId());
            event.setValue(events.getValue());
            event.setStart(events.getStart());
            event.setEnd(events.getEnd());
            event.setCreatedOn(events.getCreatedOn());
            event.setConfidence(events.getConfidence());
            event.setEnv(events.getEnv());
            return new ResponseEntity<>(repository.save(event), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<HttpStatus> deleteEventById(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Eception: " + e.getMessage());
            System.out.println("Eception: " + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/events")
    public ResponseEntity<HttpStatus> deleteAllEvents() {
        try {
            repository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Eception: " + e.getMessage());
            System.out.println("Eception: " + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/events/byeventname")
    public ResponseEntity<List<EventModel>> findByEventType(@RequestParam(required = false) String eventName) {
        try {
            List<EventModel> events = repository.findByEventName(eventName);
            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            // Limit the number of items to 1000
            int limit = 1000;
            if (events.size() > limit) {
                events = events.subList(0, limit);
            }
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Eception: " + e.getMessage());
            System.out.println("Eception: " + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/events/byasetid")
    public ResponseEntity<List<EventModel>> findByAssetId(@RequestParam(required = false) String assetId) {
        try {
            List<EventModel> events = repository.findByAssetId(assetId);
            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            // Limit the number of items to 1000
            int limit = 1000;
            if (events.size() > limit) {
                events = events.subList(0, limit);
            }
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Eception: " + e.getMessage());
            System.out.println("Eception: " + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
