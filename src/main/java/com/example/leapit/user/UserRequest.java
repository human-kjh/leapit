package com.example.leapit.user;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import com.example.leapit.common.enums.Role;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

public class UserRequest {


    @Data
    public static class PersonalUpdateDTO {
        @Size(min = 2, max = 20, message = "이름은 2~20자 이내여야 합니다.")
        @NotBlank(message = "이름은 필수입니다.")
        private String name;

        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,16}$",
                message = "비밀번호는 8~16자, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다."
        )
        private String newPassword;

        private String confirmPassword;

        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 010-1234-5678 형식으로 입력해주세요.")
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
        @NotBlank(message = "이름은 필수입니다.")
        @Size(min = 2, max = 20, message = "이름은 2~20자 이내여야 합니다.")
        private String name;

        @NotEmpty(message = "아이디는 필수입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9*_]{4,20}$", message = "아이디는 4~20자, 영문/숫자/*/_만 가능합니다.")
        private String username;

        @NotEmpty(message = "비밀번호는 필수입니다.")
        @Size(min = 4, max = 20)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,16}$",
                message = "비밀번호는 8~16자, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다."
        )
        private String password;

        @NotEmpty(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        private LocalDate birthDate;

        @NotEmpty(message = "전화번호는 필수입니다.")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 010-1234-5678 형식으로 입력해주세요.")
        private String contactNumber;

        @NotNull(message = "회원 유형은 필수입니다.")
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
        @NotEmpty(message = "아이디는 필수입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9*_]{4,20}$", message = "아이디는 4~20자, 영문/숫자/*/_만 가능합니다.")
        private String username;

        @NotEmpty(message = "비밀번호는 필수입니다.")
        @Size(min = 4, max = 20)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,16}$",
                message = "비밀번호는 8~16자, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다."
        )
        private String password;

        @NotEmpty(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @NotEmpty(message = "전화번호는 필수입니다.")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 010-1234-5678 형식으로 입력해주세요.")
        private String contactNumber;

        @NotNull(message = "회원 유형은 필수입니다.")
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
        @NotEmpty(message = "아이디를 입력해주세요.")
        private String username;
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
        private Role role;
        private String rememberMe;
    }


}
