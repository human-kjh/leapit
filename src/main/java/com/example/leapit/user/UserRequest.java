package com.example.leapit.user;

import com.example.leapit.common.enums.Role;
import lombok.Data;

import java.time.LocalDate;

public class UserRequest {


    @Data
    public static class PersonalUpdateDTO {
        private String name;
        private String newPassword;
        private String confirmPassword;
        private String email;
        private String contactNumber;
    }

    @Data
    public static class CompanyUpdateDTO {
        private String newPassword;
        private String confirmPassword;
        private String contactNumber;
    }

    @Data
    public static class PersonalJoinDTO {
        private String name;
        private String username;
        private String password;
        private String email;
        private LocalDate birthDate;
        private String contactNumber;
        private Role role;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .contactNumber(contactNumber)
                    .role(Role.personal)
                    .name(name)
                    .birthDate(birthDate)
                    .build();
        }

    }

    @Data
    public static class CompanyJoinDTO {
        private String username;
        private String password;
        private String email;
        private String contactNumber;
        private Role role;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .contactNumber(contactNumber)
                    .role(Role.company)
                    .build();
        }


    }

    @Data
    public static class LoginDTO{
        private String username;
        private String password;
        private Role role;
        private String rememberMe;
    }


}
