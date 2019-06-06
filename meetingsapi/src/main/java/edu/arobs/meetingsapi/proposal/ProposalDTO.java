package edu.arobs.meetingsapi.proposal;

import edu.arobs.meetingsapi.enumerations.Difficulty;
import edu.arobs.meetingsapi.enumerations.Type;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ProposalDTO {

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

}
