package edu.arobs.meetingsapi.meeting;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingDTO {
    private String title;
    private String location;
    private LocalDateTime time;
}
