package edu.arobs.meetingsapi.leaderboards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboards")
public class LeaderboardsController {
    public final LeaderboardsService leaderboardsService;

    @Autowired
    public LeaderboardsController(LeaderboardsService leaderboardsService) {
        this.leaderboardsService = leaderboardsService;
    }

    @GetMapping
    public List<LeaderboardsDTO> getAll() {
        return leaderboardsService.findAll();
    }
}
