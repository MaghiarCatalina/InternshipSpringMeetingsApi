package edu.arobs.meetingsapi.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper ;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO create(UserDTO userDTO){
        User user = new User();
        user.setId(null);
        modelMapper.map(userDTO,user);
        User savedUser = userRepository.save(user);
        UserDTO userCreatedDTO = new UserDTO();
        modelMapper.map(savedUser,userCreatedDTO);
        return userCreatedDTO;
    }

    public UserDTO update(Integer id, UserDTO user){
        UserDTO existingUser = getById(id);
        UserDTO updatedUserDTO = new UserDTO();
        User updatedUser = new User();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setId(id);
        userRepository.save(updatedUser);
        modelMapper.map(updatedUser,updatedUserDTO);
        return updatedUserDTO;
    //modelMapper.map();
//        User existingUser = getById(id);
//        existingUser.setFirstName(user.getFirstName());
//        existingUser.setLastName(user.getLastName());
//        return userRepository.save(existingUser);
    }

    public UserDTO getById(Integer id) {
        UserDTO userDTO = new UserDTO();
        User existingUser = userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException(String.format("Person id=%d does not exist", id)));
        modelMapper.map(existingUser,userDTO);
        return userDTO;
    }

    public UserDTO delete(Integer id) {
        UserDTO existingUserDTO = getById(id);
        userRepository.deleteById(id);
        return existingUserDTO;
    }
}
