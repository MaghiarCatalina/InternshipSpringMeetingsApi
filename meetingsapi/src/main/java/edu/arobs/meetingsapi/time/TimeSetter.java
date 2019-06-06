package edu.arobs.meetingsapi.time;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class TimeSetter {

    private Timestamp created;
    private Timestamp lastUpdated;

    public TimeSetter() {
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.lastUpdated = Timestamp.valueOf(LocalDateTime.now());
    }
}
