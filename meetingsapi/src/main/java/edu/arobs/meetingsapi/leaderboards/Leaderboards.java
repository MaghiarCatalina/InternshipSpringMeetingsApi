package edu.arobs.meetingsapi.leaderboards;

import edu.arobs.meetingsapi.time.TimeSetter;
import edu.arobs.meetingsapi.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Leaderboards extends TimeSetter {
    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
    private Integer points;
}
