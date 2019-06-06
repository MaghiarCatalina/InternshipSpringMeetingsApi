package edu.arobs.meetingsapi.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "select * from user where email=:email and password=:password", nativeQuery = true)
    List<User> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
