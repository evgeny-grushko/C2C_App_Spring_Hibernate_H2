package com.yevhen.hrushko.repository;

import java.time.Instant;
import java.util.List;

import com.yevhen.hrushko.model.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface Repository extends JpaRepository<EventModel, Long> {
  List<EventModel> findByEventName(String eventName);

  List<EventModel> findByAssetId(String start);
  List<EventModel> findByEventNameContainingIgnoreCase(String eventName);

  // Custom query to delete events by time before the given cutoff time
  @Transactional
  @Modifying
  @Query("DELETE FROM EventModel e WHERE e.time < :cutoffTime")
  void deleteByTimeBefore(@Param("cutoffTime") Instant cutoffTime);

}
