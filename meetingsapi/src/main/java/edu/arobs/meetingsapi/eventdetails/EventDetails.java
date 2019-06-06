package edu.arobs.meetingsapi.eventdetails;

import edu.arobs.meetingsapi.enumerations.Difficulty;
import edu.arobs.meetingsapi.enumerations.Type;
import edu.arobs.meetingsapi.event.Event;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@Entity
public class EventDetails {
    @Id
    private Integer id;

    private String title;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    private String language;
    private String duration;
    private Integer maxPersons;
    private String shortDescription;
    private Date date;
    private Time time;
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Event event;

    public EventDetails() {
        super();
    }
}
