package edu.arobs.meetingsapi.feedback;


import edu.arobs.meetingsapi.event.Event;
import edu.arobs.meetingsapi.time.TimeSetter;
import edu.arobs.meetingsapi.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "feedback")
public class Feedback extends TimeSetter {

    @Id
    @GeneratedValue
    @NotNull
    private Integer id;
    private String clarity;
    private String originality;
    private String complexity;
    private String engagement;
    private String cursive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
}