package edu.arobs.meetingsapi.meeting;

import org.springframework.data.repository.CrudRepository;

public interface MeetingRepository extends CrudRepository<Meeting,Integer> {
}
