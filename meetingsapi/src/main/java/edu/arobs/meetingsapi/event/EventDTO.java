package edu.arobs.meetingsapi.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.arobs.meetingsapi.enumerations.Difficulty;
import edu.arobs.meetingsapi.enumerations.Type;
import edu.arobs.meetingsapi.feedback.Feedback;
import edu.arobs.meetingsapi.feedback.FeedbackDTO;
import edu.arobs.meetingsapi.user.User;
import edu.arobs.meetingsapi.user.UserDTO;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventDTO {

    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String lang;
    private Integer usersId;
    private String duration;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time time;
    private List<Integer> attendanceIds = new ArrayList<>();
    private Integer maxPeople;
    private List<Integer> waitingListIds = new ArrayList<>();
    private Long timestamp;
    private List<FeedbackDTO> feedback = new ArrayList<>();
    private UserDTO users;
    private String description;
}
