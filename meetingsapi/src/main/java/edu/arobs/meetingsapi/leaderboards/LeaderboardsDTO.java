package edu.arobs.meetingsapi.leaderboards;

import edu.arobs.meetingsapi.user.UserDTO;
import lombok.Data;

@Data
public class LeaderboardsDTO {

    private Integer id;
    private Integer userId;
    private UserDTO users;
}
