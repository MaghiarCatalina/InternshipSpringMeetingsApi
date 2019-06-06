package edu.arobs.meetingsapi.user;

import edu.arobs.meetingsapi.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserDTO create(UserDTO userDTO) {
        User user = new User();
        user.setId(null);
        modelMapper.map(userDTO, user);
        User savedUser = userRepository.save(user);
        UserDTO userCreatedDTO = new UserDTO();
        modelMapper.map(savedUser, userCreatedDTO);
        return userCreatedDTO;
    }

    @Transactional
    public UserDTO update(Integer id, UserDTO user) {
        UserDTO updatedUserDTO = new UserDTO();
        User updatedUser = new User();
        updatedUser.setFullName(user.getFullName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setPoints(user.getPoints());
        updatedUser.setToken(user.getToken());
        updatedUser.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
        updatedUser.setId(id);
        userRepository.save(updatedUser);
        modelMapper.map(updatedUser, updatedUserDTO);
        return updatedUserDTO;
    }

    @Transactional
    public UserDTO getById(Integer id) {
        UserDTO userDTO = new UserDTO();
        User existingUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        modelMapper.map(existingUser, userDTO);
        return userDTO;
    }

    @Transactional
    public UserDTO delete(Integer id) {
        UserDTO existingUserDTO = getById(id);
        userRepository.deleteById(id);
        return existingUserDTO;
    }

    @Transactional
    public List<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
