package com.example.leapit.application.bookmark;

import com.example.leapit.application.Application;
import com.example.leapit.application.ApplicationRepository;
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
import com.example.leapit.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationBookmarkService {
    private final ApplicationBookmarkRepository applicationBookmarkRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;


    @Transactional
    public ApplicationBookmarkResponse.SaveDTO saveBookmark(ApplicationBookmarkRequest.SaveDTO reqDTO, Integer sessionUserId) {

        User companyUser = userRepository.findById(sessionUserId);
        if (companyUser == null) throw new RuntimeException("유저가 존재하지 않습니다");

        Application application = applicationRepository.findByApplicationId(reqDTO.getApplicationId());
        if (application == null) throw new RuntimeException("지원 정보가 존재하지 않습니다");

        ApplicationBookmark bookmark = ApplicationBookmark.builder()
                .user(companyUser)
                .application(application)
                .build();

        applicationBookmarkRepository.save(bookmark);
        return new ApplicationBookmarkResponse.SaveDTO(bookmark.getId());
    }

    @Transactional
    public void deleteBookmark(Integer applicationId, Integer sessionUserId) {
        ApplicationBookmark bookmark = applicationBookmarkRepository.findByUserIdAndApplicationId(sessionUserId, applicationId);

        if (bookmark == null) throw new RuntimeException("해당 스크랩이 존재하지 않습니다.");
        if (!bookmark.getUser().getId().equals(sessionUserId)) throw new RuntimeException("권한이 없습니다.");

        applicationBookmarkRepository.delete(bookmark);
    }

}
