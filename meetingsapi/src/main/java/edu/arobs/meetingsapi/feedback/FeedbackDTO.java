package edu.arobs.meetingsapi.feedback;

import lombok.Data;

@Data
public class FeedbackDTO {
    private String clarity;
    private String originality;
    private String complexity;
    private String engagement;
    private String cursive;
    private Integer usersId;
}
