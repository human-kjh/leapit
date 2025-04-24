package com.example.leapit.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User login(UserRequest.LoginDTO loginDTO) {

        User user = userRepository.findByUsernameAndRole(loginDTO.getUsername(), loginDTO.getRole());

        if (user == null) {
            throw new RuntimeException("유저네임 혹은 비밀번호가 틀렸습니다");
        }

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("유저네임 혹은 비밀번호가 틀렸습니다");
        }return user;
    }

    @Transactional
    public void join(UserRequest.PersonalJoinDTO personalJoinDTO) {
        userRepository.save(personalJoinDTO.toEntity());
    }

    @Transactional
    public void join(UserRequest.CompanyJoinDTO companyJoinDTO) {
        userRepository.save(companyJoinDTO.toEntity());
    }

    public Map<String, Object> checkUsernameDuplicate(String username) {
        User user = userRepository.findByUsername(username);
        Map<String, Object> dto = new HashMap<>();

        if (user == null) {
            dto.put("available", true);
        } else {
            dto.put("available", false);
        }
        return dto;
    }


    @Transactional
    public User update(UserRequest.PersonalUpdateDTO reqDTO, Integer userId) {
        User userPS = userRepository.findById(userId);

        if (userPS == null) throw new RuntimeException("자원을 찾을 수 없습니다");
        userPS.PersonalUpdate(reqDTO.getName(),reqDTO.getNewPassword(), reqDTO.getEmail(),reqDTO.getContactNumber()); // 영속화된 객체의 상태변경
        return userPS;
    }

    @Transactional
    public User update(UserRequest.CompanyUpdateDTO reqDTO, Integer userId) {
        User userPS = userRepository.findById(userId);

        if (userPS == null) throw new RuntimeException("자원을 찾을 수 없습니다");
        userPS.CompanyUpdate(reqDTO.getNewPassword(),reqDTO.getContactNumber()); // 영속화된 객체의 상태변경
        return userPS;
    }


}
