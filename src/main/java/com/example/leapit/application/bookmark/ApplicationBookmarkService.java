package com.example.leapit.application.bookmark;

import com.example.leapit._core.error.ex.Exception404;
import com.example.leapit._core.error.ex.ExceptionApi400;
import com.example.leapit._core.error.ex.ExceptionApi403;
import com.example.leapit._core.error.ex.ExceptionApi404;
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
    public ApplicationBookmarkResponse.SaveDTO saveApplicantBookmarkByUserId(ApplicationBookmarkRequest.SaveDTO reqDTO, Integer sessionUserId) {

        User companyUser = userRepository.findById(sessionUserId);
        if (companyUser == null) throw new ExceptionApi404("회원정보가 존재하지 않습니다.");

        Application application = applicationRepository.findByApplicationId(reqDTO.getApplicationId());
        if (application == null) throw new ExceptionApi404("지원 정보가 존재하지 않습니다");

        ApplicationBookmark applicationBookmark = applicationBookmarkRepository.findByUserIdAndApplicationId(sessionUserId, application.getId());
        if (applicationBookmark != null) throw new ExceptionApi400("이미 스크랩된 지원서 입니다.");

        ApplicationBookmark bookmark = ApplicationBookmark.builder()
                .user(companyUser)
                .application(application)
                .build();

        applicationBookmarkRepository.save(bookmark);
        return new ApplicationBookmarkResponse.SaveDTO(bookmark.getId());
    }

    @Transactional
    public void deleteApplicationBookmarkByApplicationId(Integer applicationId, Integer sessionUserId) {
        ApplicationBookmark bookmark = applicationBookmarkRepository.findByUserIdAndApplicationId(sessionUserId, applicationId);
        if (sessionUserId == null) throw new ExceptionApi404("회원정보가 존재하지 않습니다.");

        if (bookmark == null) throw new ExceptionApi404("해당 스크랩이 존재하지 않습니다.");
        if (!bookmark.getUser().getId().equals(sessionUserId)) throw new ExceptionApi403("권한이 없습니다.");

        applicationBookmarkRepository.delete(bookmark);
    }


}
