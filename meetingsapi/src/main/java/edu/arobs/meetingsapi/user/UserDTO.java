package edu.arobs.meetingsapi.user;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String password;
    private Integer points;
    private String token;
}
