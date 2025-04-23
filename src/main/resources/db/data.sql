-- 1. user_tb
INSERT INTO user_tb (username, password, email, contact_number, role, created_at, name, birth_date)
VALUES ('ssar', '1234', 'ssar@nate.com', '010-1234-5678', 'PERSONAL', NOW(), '쌀', '2000-01-01');
INSERT INTO user_tb (username, password, email, contact_number, role, created_at, name, birth_date)
VALUES ('cos', '1234', 'cos@nate.com', '010-2345-6789', 'PERSONAL', NOW(), '코스', '1999-12-31');
INSERT INTO user_tb (username, password, email, contact_number, role, created_at, name, birth_date)
VALUES ('love', '1234', 'love@nate.com', '010-3456-6709', 'PERSONAL', NOW(), '러브', '1999-10-25');
INSERT INTO user_tb (username, password, email, contact_number, role, created_at, name, birth_date)
VALUES ('hana', '1234', 'hana@nate.com', '010-4567-7890', 'PERSONAL', NOW(), '김하나', '2001-03-14');
INSERT INTO user_tb (username, password, email, contact_number, role, created_at, name, birth_date)
VALUES ('minsu', '1234', 'minsu@nate.com', '010-5678-8901', 'PERSONAL', NOW(), '박민수', '1998-07-22');
INSERT INTO user_tb (username, password, email, contact_number, role, created_at)
VALUES ('company01', '1234', 'company01@nate.com', '02-1234-5678', 'COMPANY', NOW());
INSERT INTO user_tb (username, password, email, contact_number, role, created_at)
VALUES ('company02', '1234', 'company02@nate.com', '02-2345-6789', 'COMPANY', NOW());
INSERT INTO user_tb (username, password, email, contact_number, role, created_at)
VALUES ('company03', '1234', 'company03@nate.com', '02-3456-7890', 'COMPANY', NOW());

-- 2.1 position_type_tb
INSERT INTO position_type_tb (code, label) VALUES ('backend', '백엔드');
INSERT INTO position_type_tb (code, label) VALUES ('frontend', '프론트엔드');
INSERT INTO position_type_tb (code, label) VALUES ('fullstack', '풀스택');
INSERT INTO position_type_tb (code, label) VALUES ('data_engineer', '데이터 엔지니어');
INSERT INTO position_type_tb (code, label) VALUES ('mobile_app', '모바일 앱 개발자');
INSERT INTO position_type_tb (code, label) VALUES ('ai_engineer', 'AI 엔지니어');

-- 2.2 tech_stack_tb
INSERT INTO tech_stack_tb (code)
VALUES ('Python');
INSERT INTO tech_stack_tb (code)
VALUES ('Java');
INSERT INTO tech_stack_tb (code)
VALUES ('React');
INSERT INTO tech_stack_tb (code)
VALUES ('Spring Boot');
INSERT INTO tech_stack_tb (code)
VALUES ('Kotlin');
INSERT INTO tech_stack_tb (code)
VALUES ('SQL');
INSERT INTO tech_stack_tb (code)
VALUES ('Node.js');
INSERT INTO tech_stack_tb (code)
VALUES ('CSS');
INSERT INTO tech_stack_tb (code)
VALUES ('HTML');
INSERT INTO tech_stack_tb (code)
VALUES ('Django');

-- 2. resume_tb
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (1, '쌀의 이력서', NULL, '자바 개발자입니다', 'backend', '적극적이고 성실합니다', false, '2025-04-18 12:00:00');
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (1, '쌀님의 이력서2', 'file://localhost/c:/image1.png', '프론트엔드 자신 있습니다', 'frontend', '디자인 감각도 좋아요', true, '2025-04-18 12:05:00');
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (2, '파이썬 이력서', NULL, 'Django와 FastAPI 경험 있음', 'backend', '데이터 파이프라인 경험', false, '2025-04-18 12:10:00');
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (3, '풀스택 도전기', NULL, '다양한 프로젝트 수행 경험 있음', 'fullstack', '매일 꾸준히 성장 중', true, '2025-04-18 12:15:00');
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (4, '코틀린 마스터', NULL, '안드로이드 개발 경험 풍부', 'mobile_app', '성능 최적화에 관심 많습니다', true, '2025-04-18 12:20:00');
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (5, '데이터 분석가', NULL, 'SQL과 데이터 시각화 강점', 'data_engineer', '통계에 자신 있습니다', false, '2025-04-18 12:25:00');

-- 2.3 resume_tech_stack_tb
INSERT INTO resume_tech_stack_tb (resume_id, tech_stack)
VALUES (1, 'Java');
INSERT INTO resume_tech_stack_tb (resume_id, tech_stack)
VALUES (1, 'Spring Boot');
INSERT INTO resume_tech_stack_tb (resume_id, tech_stack)
VALUES (2, 'React');
INSERT INTO resume_tech_stack_tb (resume_id, tech_stack)
VALUES (3, 'Python');
INSERT INTO resume_tech_stack_tb (resume_id, tech_stack)
VALUES (4, 'Java');
INSERT INTO resume_tech_stack_tb (resume_id, tech_stack)
VALUES (4, 'Spring Boot');
INSERT INTO resume_tech_stack_tb (resume_id, tech_stack)
VALUES (4, 'Kotlin');
INSERT INTO resume_tech_stack_tb (resume_id, tech_stack)
VALUES (5, 'SQL');

-- 2.4 link_tb
INSERT INTO link_tb (resume_id, title, url)
VALUES (1, 'GitHub', 'https://github.com/username');
INSERT INTO link_tb (resume_id, title, url)
VALUES (1, 'Notion', 'https://notion.so/my-resume');
INSERT INTO link_tb (resume_id, title, url)
VALUES (2, '개인 블로그', 'https://velog.io/@username');

-- 2.5 education_tb
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level,school_name, major, gpa, gpa_scale, created_at)
VALUES (1, '2025-02-28', FALSE, 2, '가나다대학교', '컴퓨터공학과', 4.2, 4.5, NOW());
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level,school_name, major, gpa, gpa_scale, created_at)
VALUES (2, '2025-02-28', TRUE, 2, '가나다대학교', '컴퓨터공학과', 2.2, 4.5, NOW());
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level,school_name, major, gpa, gpa_scale, created_at)
VALUES (3, '2024-08-31', TRUE, 1, '라마바대학교', '정보처리학과', 3.5, 4.5, NOW());
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level, school_name, major, gpa, gpa_scale, created_at)
VALUES (4, '2023-02-28', FALSE, 3, '사아자차카대학교', '인공지능학과', 3.9, 4.3, NOW());
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level, school_name, major, gpa, gpa_scale, created_at)
VALUES (5, '2024-02-28', FALSE, 2, '부산대학교', '통계학과', 4.25, 4.3, NOW());

-- 2.6 experience_tb
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position, responsibility, created_at)
VALUES (1, '2022-01-01', '2023-12-31', false, '랩핏', '구직 플랫폼 회사입니다.', '플랫폼팀 / 팀장', '팀장으로 플랫폼 전반 운영과 프로젝트 관리, 팀 빌딩 등을 주도했습니다.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position, responsibility, created_at)
VALUES (1, '2024-01-01', NULL, true, '망고소프트', '소프트웨어 솔루션 기업입니다.', '프론트엔드 엔지니어 / 사원', 'React 기반 웹 프론트 개발 및 유지보수, 디자인 시스템 구축에 기여했습니다.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position, responsibility, created_at)
VALUES (2, '2020-06-01', '2022-06-30', false, '코코넷', 'B2B 통신 솔루션 회사입니다.', '백엔드 개발자 / 주임', 'Spring Boot와 PostgreSQL 기반의 API 서버 개발 및 배포 자동화 작업 수행.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position, responsibility, created_at)
VALUES (2, '2023-02-01', NULL, true, '엔젤헬스', '헬스케어 플랫폼 스타트업입니다.', '풀스택 개발자 / 대리', 'MVP 서비스 구축, AWS 인프라 구성, 사용자 피드백 기반 기능 개선 주도.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position, responsibility, created_at)
VALUES (4, '2020-03-01', '2022-05-31', false, '에듀플랜', '교육 콘텐츠 제작 스타트업입니다.', '콘텐츠 기획자', '학습자 맞춤형 온라인 강의 콘텐츠 기획 및 제작을 담당했습니다.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position, responsibility, created_at)
VALUES (5, '2021-01-01', '2023-01-15', false, '에이텍솔루션', '전자부품 제조 솔루션 회사입니다.', 'IT 인프라 엔지니어', '사내 네트워크 및 서버 인프라 관리, 정기 백업 및 보안 정책 적용을 수행했습니다.', '2025-04-18 12:05:23');
INSERT INTO experience_tb  (resume_id, start_date, end_date, is_employed, company_name, summary, position, responsibility, created_at)
VALUES (5, '2023-04-01', NULL, true, '에버핏', '피트니스 플랫폼 운영 회사입니다.', '백엔드 개발자', '회원관리, 트레이너 매칭, 결제 시스템 관련 API 개발 및 유지보수를 담당 중입니다.', '2025-04-18 12:05:23');

-- 2.7 experience_tech_stack_tb
INSERT INTO experience_tech_stack_tb (experience_id, tech_stack)
VALUES (1, 'Java');
INSERT INTO experience_tech_stack_tb (experience_id, tech_stack)
VALUES (1, 'Spring Boot');
INSERT INTO experience_tech_stack_tb (experience_id, tech_stack)
VALUES (2, 'React');
INSERT INTO experience_tech_stack_tb (experience_id, tech_stack)
VALUES (3, 'Node.js');
INSERT INTO experience_tech_stack_tb (experience_id, tech_stack)
VALUES (4, 'Python');
INSERT INTO experience_tech_stack_tb (experience_id, tech_stack)
VALUES (5, 'Django');
INSERT INTO experience_tech_stack_tb (experience_id, tech_stack)
VALUES (6, 'Node.js');
INSERT INTO experience_tech_stack_tb (experience_id, tech_stack)
VALUES (7, 'Spring Boot');


-- 2.8 project_tb
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url, created_at)
VALUES (1, '2024-01-01', '2024-03-01', false, '랩핏', '구직 사이트 프로젝트입니다.', '구직 사이트 프로젝트입니다. Spring Boot, MySQL, JPA를 이용해 개발했습니다.', 'https://github.com/example1', '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url, created_at)
VALUES (1, '2023-09-01', NULL, true, '타임로그', '근태 기록 웹 앱', '근무 시간을 기록하고 확인할 수 있는 사내 근태 관리 시스템입니다.', NULL, '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url, created_at)
VALUES (2, '2024-05-01', '2024-08-01', false, '스터디메이트', '스터디 매칭 서비스', '스터디 모집과 매칭을 돕는 웹 애플리케이션입니다. Vue.js와 Spring 사용.', 'https://github.com/example3', '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url, created_at)
VALUES (2, '2024-02-01', NULL, true, '포트폴리오 웹', '개인 포트폴리오 사이트입니다.', 'HTML/CSS/JS 기반 정적 웹사이트로 자기소개, 프로젝트, 연락처를 제공합니다.', NULL, '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url, created_at)
VALUES (3, '2023-06-01', '2023-12-15', false, '쇼핑몰 프로젝트', '온라인 쇼핑몰 구축', 'Spring Boot + Thymeleaf 기반 쇼핑몰 프로젝트입니다. 장바구니, 결제 연동 기능 포함.', 'https://github.com/example5', '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url, created_at)
VALUES (4, '2023-08-01', '2023-12-15', false, '이력서 자동 생성기', '입사지원용 이력서 생성 프로젝트입니다.', 'PDF 변환 기능이 포함된 이력서 자동 생성 웹 애플리케이션입니다. HTML/CSS 기반 템플릿과 Java Spring을 사용했습니다.', 'https://github.com/example6', '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url, created_at)
VALUES (4, '2024-06-01', NULL, true, '채용관리 시스템', '기업용 채용공고 및 지원자 관리', '관리자 페이지에서 채용공고 등록 및 지원자 목록을 관리할 수 있는 시스템입니다. Spring Boot + Thymeleaf로 개발 중입니다.', NULL, '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url, created_at)
VALUES (5, '2022-11-01', '2023-03-01', false, '포인트 적립 앱', '간단한 사용자 리워드 앱', 'QR 코드 스캔 시 포인트를 적립하고 사용 가능한 모바일 리워드 애플리케이션입니다. Firebase와 React Native 기반으로 제작.', 'https://github.com/example7', '2025-04-18 12:05:23');

-- 2.9 project_tech_stack_tb
INSERT INTO project_tech_stack_tb (project_id, tech_stack)
VALUES (1, 'Java');
INSERT INTO project_tech_stack_tb (project_id, tech_stack)
VALUES (2, 'Spring Boot');
INSERT INTO project_tech_stack_tb (project_id, tech_stack)
VALUES (3, 'React');
INSERT INTO project_tech_stack_tb (project_id, tech_stack)
VALUES (4, 'Python');
INSERT INTO project_tech_stack_tb (project_id, tech_stack)
VALUES (5, 'Kotlin');
INSERT INTO project_tech_stack_tb (project_id, tech_stack)
VALUES (6, 'Django');
INSERT INTO project_tech_stack_tb (project_id, tech_stack)
VALUES (7, 'Django');
INSERT INTO project_tech_stack_tb (project_id, tech_stack)
VALUES (8, 'Django');

-- 2.9 training_tb
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description, created_at)
VALUES (1, '2024-01-01', '2024-06-30', false, '자바 백엔드 개발자 과정', '그린컴퓨터', 'JPA와 MVC 패턴 학습', '2025-04-18 12:00:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description, created_at)
VALUES (1, '2024-07-01', NULL, true, 'Spring Boot 심화과정', '멀티캠퍼스', 'Spring Security, OAuth 학습', '2025-04-18 12:05:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description, created_at)
VALUES (2, '2023-09-01', '2024-02-28', false, '프론트엔드 심화반', '코드스쿼드', 'React, 상태관리 학습', '2025-04-18 12:10:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description, created_at)
VALUES (3, '2024-03-01', '2024-08-31', false, '데이터 분석 입문', '패스트캠퍼스', 'Python과 데이터 시각화', '2025-04-18 12:15:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description, created_at)
VALUES (4, '2024-05-01', NULL, true, 'Kotlin 안드로이드 앱 개발', '그린컴퓨터', '모바일 앱 UI 구현', '2025-04-18 12:20:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description, created_at)
VALUES (5, '2023-01-01', '2023-06-30', false, 'SQL 고급 과정', '멀티캠퍼스', '복잡한 쿼리 작성 연습', '2025-04-18 12:25:00');

-- 2.10 training_tech_stack_tb
INSERT INTO training_tech_stack_tb (training_id, tech_stack)
VALUES (1, 'Java');
INSERT INTO training_tech_stack_tb (training_id, tech_stack)
VALUES (2, 'Spring Boot');
INSERT INTO training_tech_stack_tb (training_id, tech_stack)
VALUES (3, 'HTML');
INSERT INTO training_tech_stack_tb (training_id, tech_stack)
VALUES (4, 'CSS');
INSERT INTO training_tech_stack_tb (training_id, tech_stack)
VALUES (5, 'Python');
INSERT INTO training_tech_stack_tb (training_id, tech_stack)
VALUES (5, 'Node.js');

-- 2.12 etc_tb
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description, created_at)
VALUES (1, '2024-01-01', '2024-03-01', true, '토익', 0, 'YBM', '850점', '2025-04-18 12:00:00');
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description, created_at)
VALUES (1, '2023-06-01', NULL, false, '정보처리기사', 1, 'HRD', '필기합격', '2025-04-18 12:05:00');
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description, created_at)
VALUES (3, '2023-10-01', '2023-12-01', true, '오픈소스 컨트리뷰션', 2, 'OSS Korea', '3건 PR 기여', '2025-04-18 12:10:00');
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description, created_at)
VALUES (4, '2023-01-01', NULL, false, '멋쟁이사자처럼', 2, 'LIKELION', '프론트엔드 팀장', '2025-04-18 12:15:00');
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description, created_at)
VALUES (5, '2023-03-01', '2023-05-01', true, '교내 알고리즘 대회', 3, 'OO대학교', '3위 수상', '2025-04-18 12:20:00');

-- 4-1. region_tb
INSERT INTO region_tb (name) VALUES ('서울특별시');
INSERT INTO region_tb (name) VALUES ('부산광역시');

-- 4-2. sub_region_tb
INSERT INTO sub_region_tb (name, region_id) VALUES ('강남구', 1);
INSERT INTO sub_region_tb (name, region_id) VALUES ('서초구', 1);
INSERT INTO sub_region_tb (name, region_id) VALUES ('부산진구', 2);
INSERT INTO sub_region_tb (name, region_id) VALUES ('해운대구', 2);

-- 3. job_posting_tb
INSERT INTO job_posting_tb (
    user_id, title, position_type, min_career_level, max_career_level,
    education_level, address_region_id, address_sub_region_id, address_detail,
    service_intro, deadline, responsibility, qualification, preference,
    benefit, additional_info, view_count, created_at
) VALUES (
             6, '시니어 백엔드 개발자 채용', 'backend', 5, 10,
             2, 1, 1, '강남대로 123',
             '대용량 트래픽 처리 기반 백엔드 플랫폼 개발',
             '2025-06-30', '마이크로서비스 아키텍처 기반 시스템 설계 및 운영',
             'Java, Spring 기반 개발 경험 필수',
             'AWS 경험자 우대',
             '탄력 근무제, 점심 제공',
             NULL, 3, NOW()
         );

INSERT INTO job_posting_tb (
    user_id, title, position_type, min_career_level, max_career_level,
    education_level, address_region_id, address_sub_region_id, address_detail,
    service_intro, deadline, responsibility, qualification, preference,
    benefit, additional_info, view_count, created_at
) VALUES (
             7, '프론트엔드 개발자 모집', 'frontend', 0, 2,
             NULL, 1, 2, '서초대로 77',
             'B2B SaaS 웹서비스 구축 중인 스타트업입니다.',
             '2025-05-20', 'React 기반 웹 프론트엔드 개발 및 유지보수',
             'React, TypeScript 기반 개발 경험',
             'Figma 연동 경험자 우대',
             '재택 가능, 장비 지원',
             NULL, 13, NOW()
         );

INSERT INTO job_posting_tb (
    user_id, title, position_type, min_career_level, max_career_level,
    education_level, address_region_id, address_sub_region_id, address_detail,
    service_intro, deadline, responsibility, qualification, preference,
    benefit, additional_info, view_count, created_at
) VALUES (
             8, '데이터 엔지니어 채용', 'data_engineer', 3, 5,
             3, 2, 3, '해운대로 456',
             'AI 데이터 파이프라인 구축 기업입니다.',
             '2025-04-15', 'ETL 파이프라인 설계 및 데이터 웨어하우스 운영',
             'Python, SQL, AWS Redshift 경험',
             '빅데이터 처리 경험 우대',
             '성과급, 복지포인트',
             '해외 컨퍼런스 참가 지원', 23, NOW()
         );

-- 3-1. job_posting_tech_stack_tb
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack) VALUES (1, 'Python');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack) VALUES (1, 'Java');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack) VALUES (1, 'React');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack) VALUES (2, 'Django');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack) VALUES (2, 'Kotlin');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack) VALUES (2, 'Spring Boot');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack) VALUES (3, 'SQL');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack) VALUES (3, 'Node.js');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack) VALUES (3, 'React');

-- 3-2. job_posting_bookmark_tb
INSERT INTO job_posting_bookmark_tb (user_id, job_posting_id, created_at)
VALUES (1, 1, NOW());
INSERT INTO job_posting_bookmark_tb (user_id, job_posting_id, created_at)
VALUES (1, 2, NOW());
INSERT INTO job_posting_bookmark_tb (user_id, job_posting_id, created_at)
VALUES (1, 3, NOW());
INSERT INTO job_posting_bookmark_tb (user_id, job_posting_id, created_at)
VALUES (2, 1, NOW());
INSERT INTO job_posting_bookmark_tb (user_id, job_posting_id, created_at)
VALUES (3, 3, NOW());

-- 5. application_tb
INSERT INTO application_tb (resume_id, job_posting_id, applied_date, is_passed, is_viewed)
VALUES (1, 2, '2025-04-18', null, false);
INSERT INTO application_tb (resume_id, job_posting_id, applied_date, is_passed, is_viewed)
VALUES (2, 2, '2025-04-19', true, true);
INSERT INTO application_tb (resume_id, job_posting_id, applied_date, is_passed, is_viewed)
VALUES (2, 1, '2025-04-19', null, true);
INSERT INTO application_tb (resume_id, job_posting_id, applied_date, is_passed, is_viewed)
VALUES (3, 1, '2025-04-20', false, true);
INSERT INTO application_tb (resume_id, job_posting_id, applied_date, is_passed, is_viewed)
VALUES (3, 3, '2025-04-21', true, true);
INSERT INTO application_tb (resume_id, job_posting_id, applied_date, is_passed, is_viewed)
VALUES (4, 3, '2025-04-21', null, true);
INSERT INTO application_tb (resume_id, job_posting_id, applied_date, is_passed, is_viewed)
VALUES (4, 2, '2025-04-21', true, true);
INSERT INTO application_tb (resume_id, job_posting_id, applied_date, is_passed, is_viewed)
VALUES (5, 3, '2025-04-21', false, true);

-- 5-1. application_bookmark_tb
INSERT INTO application_bookmark_tb (user_id,application_id,created_at)
VALUES (6, 3, NOW());
INSERT INTO application_bookmark_tb (user_id,application_id,created_at)
VALUES (7, 1, NOW());
INSERT INTO application_bookmark_tb (user_id,application_id,created_at)
VALUES (7, 7, NOW());
INSERT INTO application_bookmark_tb (user_id,application_id,created_at)
VALUES (8, 6, NOW());
INSERT INTO application_bookmark_tb (user_id,application_id,created_at)
VALUES (8, 5, NOW());


-- 6. company_info_tb
INSERT INTO company_info_tb (user_id, logo_image, company_name, establishment_date, address, main_service, introduction, image, benefit)
VALUES (6, '/images/company/logo1.png', '점핏 주식회사', '2017-07-01', '서울특별시 강남구 테헤란로 1길 10', '채용 플랫폼, 이력서 관리 등', '우리는 혁신적인 구직 플랫폼입니다.', '/images/company/office.jpg', '유연근무제, 점심 제공, 워케이션 제도');
INSERT INTO company_info_tb (user_id, logo_image, company_name, establishment_date, address, main_service, introduction, image, benefit)
VALUES (7, '/images/company/logo2.png', '랩핏테크', '2019-03-15', '서울시 마포구 백범로 12길 22', 'B2B 솔루션, SaaS 서비스', '랩핏테크는 기업 전용 커뮤니케이션 도구를 개발합니다.', '/images/company/labpit.jpg', '자유복장, 재택근무, 자율출퇴근');
INSERT INTO company_info_tb (user_id, logo_image, company_name, establishment_date, address, main_service, introduction, image, benefit)
VALUES (8, '/images/company/logo3.png', '코드몽키', '2018-04-20', '경기도 성남시 분당구 판교로 235', 'IT 교육, 개발자 훈련 프로그램', '우리는 개발자를 위한 온라인 실습 기반 교육 플랫폼입니다.', '/images/company/codeoffice.jpg', '식대 제공, 헬스비 지원, 사내 도서관');

-- 7. board_tb
INSERT INTO board_tb (user_id,title,content,created_at)
VALUES (1,'반갑습니다','안녕하세요 김랩핏입니다.', NOW());
INSERT INTO board_tb (user_id,title,content,created_at)
VALUES (2,'안녕하세요','반갑습니다 김망고입니다.', NOW());
INSERT INTO board_tb (user_id,title,content,created_at)
VALUES (1,'화이팅','반갑습니다 오늘 날씨가 좋네요! 오늘 하루도 화이팅 입니다.', NOW());
INSERT INTO board_tb (user_id,title,content,created_at)
VALUES (3,'구직사이트 추천','저는 랩핏이 제일 좋은 것 같아요!', NOW());

-- 8. reply_tb
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (1, 1, '정말 유익한 글입니다.', NOW());
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (1, 2, '질문이 하나 있습니다.', NOW());
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (1, 3, '좋아요 누르고 갑니다.', NOW());
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (2, 1, '감사합니다. 도움됐어요.', NOW());
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (2, 2, '정리 감사합니다!', NOW());
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (3, 1, '재밌게 잘 읽었습니다.', NOW());

-- 9. like_tb
INSERT INTO like_tb (user_id, board_id, created_at)
VALUES (1, 1, NOW());
INSERT INTO like_tb (user_id, board_id, created_at)
VALUES (1, 2, NOW());
INSERT INTO like_tb (user_id, board_id, created_at)
VALUES (2, 3, NOW());
INSERT INTO like_tb (user_id, board_id, created_at)
VALUES (3, 3, NOW());