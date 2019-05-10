package edu.arobs.meetingsapi.meeting;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class Meeting {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;
    private String title;
    private String location;
    private LocalDateTime time;
}
