package edu.arobs.meetingsapi.user;

import edu.arobs.meetingsapi.event.Event;
import edu.arobs.meetingsapi.time.TimeSetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends TimeSetter {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;
    private String fullName;
    private String email;
    private String password;
    private Integer points;
    private String token;

    @ManyToMany(mappedBy = "users")
    private Set<Event> events = new HashSet<>();

    public User() {
        super();
    }
}
