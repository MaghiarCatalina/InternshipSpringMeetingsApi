package edu.arobs.meetingsapi.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
//@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;
    private String firstName;
    private String lastName;
}
