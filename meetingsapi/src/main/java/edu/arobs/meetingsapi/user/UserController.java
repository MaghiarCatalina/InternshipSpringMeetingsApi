package edu.arobs.meetingsapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getUser(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Integer id, @RequestBody UserDTO user) {
        return userService.update(id, user);
    }

    @GetMapping
    @ResponseBody
    public List<User> get(@RequestParam(value = "q") String q, @RequestParam(value = "password") String password) {
        return userService.findByEmailAndPassword(q, password);
    }

    @DeleteMapping("/{id}")
    public UserDTO delete(@PathVariable Integer id) {
        return userService.delete(id);
    }

    @PatchMapping("/{id}")
    public UserDTO tokenUpdate(@PathVariable Integer id) {
        UserDTO userDTO = userService.getById(id);
        userDTO.setToken("usrABC" + id);
        userService.update(id, userDTO);
        return userDTO;
    }

}
