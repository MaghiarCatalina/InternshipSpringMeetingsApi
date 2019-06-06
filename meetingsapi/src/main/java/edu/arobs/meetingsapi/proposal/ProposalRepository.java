package edu.arobs.meetingsapi.proposal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProposalRepository extends CrudRepository<Proposal, Integer> {

    @Query(value = "select * from proposal where user_id=:idUser", nativeQuery = true)
    List<Proposal> findByUser(@Param("idUser") Integer idUser);
}
