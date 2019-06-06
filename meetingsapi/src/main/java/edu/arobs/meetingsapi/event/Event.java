package edu.arobs.meetingsapi.event;

import edu.arobs.meetingsapi.time.TimeSetter;
import edu.arobs.meetingsapi.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
public class Event extends TimeSetter {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "event_user",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.getEvents().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getEvents().remove(this);
    }

    public Event() {
        super();
    }
}
