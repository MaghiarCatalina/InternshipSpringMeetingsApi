package edu.arobs.meetingsapi.feedback;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

    @Query(value = "select * from feedback where event_id=:idEvent", nativeQuery = true)
    Set<Feedback> findByEvent(@Param("idEvent") Integer idEvent);

}
