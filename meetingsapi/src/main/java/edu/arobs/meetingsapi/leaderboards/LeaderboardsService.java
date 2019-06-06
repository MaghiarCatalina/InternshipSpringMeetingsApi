package edu.arobs.meetingsapi.leaderboards;

import edu.arobs.meetingsapi.user.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderboardsService {

    private final LeaderboardsRepository leaderboardsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LeaderboardsService(LeaderboardsRepository leaderboardsRepository, ModelMapper modelMapper) {
        this.leaderboardsRepository = leaderboardsRepository;
        this.modelMapper = modelMapper;
    }

    public List<LeaderboardsDTO> findAll() {
        List<LeaderboardsDTO> leaderboardsDTOS = new ArrayList<>();
        List<Leaderboards> leaderboards = (List<Leaderboards>) leaderboardsRepository.findAll();
        for (Leaderboards leaderboard : leaderboards) {
            LeaderboardsDTO dto = new LeaderboardsDTO();
            dto.setId(leaderboard.getUser().getId());
            dto.setUserId(leaderboard.getUser().getId());
            UserDTO userDTO = new UserDTO();
            modelMapper.map(leaderboard.getUser(), userDTO);
            dto.setUsers(userDTO);

            leaderboardsDTOS.add(dto);
        }
        return leaderboardsDTOS;
    }
}
