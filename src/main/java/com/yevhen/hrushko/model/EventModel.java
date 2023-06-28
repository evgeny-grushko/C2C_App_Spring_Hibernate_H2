package com.yevhen.hrushko.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "events")
public class EventModel {

    // {{eventName}},
    // {{ownerId}},
    // {{assetId}},
    // {{value}},
    // {{start}},
    // {{end}},
    // {{createdOn}},
    // {{confidence}}

    public EventModel(String eventName, String ownerId, String assetId, String value, Long start, Long end, Long createdOn, String confidence, String evn) {
        this.eventName = eventName;
        this.ownerId = ownerId;
        this.assetId = assetId;
        this.value = value;
        this.start = start;
        this.end = end;
        this.createdOn = createdOn;
        this.confidence = confidence;
        this.time = Instant.now();
        this.env = evn;
    }

    public EventModel() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "eventName")
    private String eventName;

    @Column(name = "ownerId")
    private String ownerId;

    @Column(name = "assetId")
    private String assetId;

    @Column(name = "_value")
    private String value;

    @Column(name = "_start")
    private Long start;

    @Column(name = "_end")
    private Long end;

    @Column(name = "createdOn")
    private Long createdOn;

    @Column(name = "confidence")
    private String confidence;

    @Column(name = "time")
    private Instant time;

    @Column(name = "env")
    private String env;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", assetId='" + assetId + '\'' +
                ", value='" + value + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", createdOn=" + createdOn +
                ", confidence='" + confidence + '\'' +
                ", time=" + time +
                ", env='" + env + '\'' +
                '}';
    }
}
