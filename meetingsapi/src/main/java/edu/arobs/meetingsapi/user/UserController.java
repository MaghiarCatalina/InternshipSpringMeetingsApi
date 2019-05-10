package edu.arobs.meetingsapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Integer id, @RequestBody UserDTO user) {
        return userService.update(id, user);
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public UserDTO delete(@PathVariable Integer id) {
        return userService.delete(id);
    }
}
