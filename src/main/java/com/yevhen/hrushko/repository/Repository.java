package com.yevhen.hrushko.repository;

import java.util.List;

import com.yevhen.hrushko.model.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<EventModel, Long> {
  List<EventModel> findByEventName(String eventName);

  List<EventModel> findByAssetId(String start);
  List<EventModel> findByEventNameContainingIgnoreCase(String eventName);
}
