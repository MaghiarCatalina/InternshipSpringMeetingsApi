package edu.arobs.meetingsapi.proposal;

import edu.arobs.meetingsapi.enumerations.Difficulty;
import edu.arobs.meetingsapi.enumerations.Type;
import edu.arobs.meetingsapi.time.TimeSetter;
import edu.arobs.meetingsapi.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@Setter
public class Proposal extends TimeSetter {

    @Id
    @GeneratedValue
    @NotNull
    private Integer id;
    private String title;
    private String author;
    private Integer maxPersons;
    private String description;
    private String language;
    private String duration;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Proposal() {
        super();
    }
}
