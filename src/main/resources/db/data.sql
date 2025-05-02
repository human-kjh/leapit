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
INSERT INTO position_type_tb (code)
VALUES ('백엔드');
INSERT INTO position_type_tb (code)
VALUES ('프론트엔드');
INSERT INTO position_type_tb (code)
VALUES ('풀스택');
INSERT INTO position_type_tb (code)
VALUES ('데이터 엔지니어');
INSERT INTO position_type_tb (code)
VALUES ('모바일 앱 개발자');
INSERT INTO position_type_tb (code)
VALUES ('AI 엔지니어');

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
VALUES (1, '쌀의 이력서', NULL, '자바 개발자입니다', '백엔드', '적극적이고 성실합니다', false, now());
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (1, '쌀의 이력서2', '이력서 사진4.png', '프론트엔드 자신 있습니다', '프론트엔드', '디자인 감각도 좋아요', true, now());
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (2, '파이썬 이력서', '이력서 사진5.png', 'Django와 FastAPI 경험 있음', '백엔드', '데이터 파이프라인 경험', false, now());
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (3, '풀스택 도전기', NULL, '다양한 프로젝트 수행 경험 있음', '풀스택', '매일 꾸준히 성장 중', true, now());
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (4, '코틀린 마스터', NULL, '안드로이드 개발 경험 풍부', '모바일 앱 개발자', '성능 최적화에 관심 많습니다', true, now());
INSERT INTO resume_tb (user_id, title, photo_url, summary, position_type, self_introduction, is_public, created_at)
VALUES (5, '데이터 분석가', NULL, 'SQL과 데이터 시각화 강점', 'AI 엔지니어', '통계에 자신 있습니다', false, now());

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
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level, school_name, major, gpa, gpa_scale,
                          created_at)
VALUES (1, '2025-02-28', FALSE, '전문학사', '가나다대학교', '컴퓨터공학과', 4.2, 4.5, NOW());
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level, school_name, major, gpa, gpa_scale,
                          created_at)
VALUES (2, '2025-02-28', TRUE, '전문학사', '가나다대학교', '컴퓨터공학과', 2.2, 4.5, NOW());
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level, school_name, major, gpa, gpa_scale,
                          created_at)
VALUES (3, '2024-08-31', TRUE, '고등학교', '라마바대학교', '정보처리학과', 3.5, 4.5, NOW());
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level, school_name, major, gpa, gpa_scale,
                          created_at)
VALUES (4, '2023-02-28', FALSE, '학사', '사아자차카대학교', '인공지능학과', 3.9, 4.3, NOW());
INSERT INTO education_tb (resume_id, graduation_date, is_dropout, education_level, school_name, major, gpa, gpa_scale,
                          created_at)
VALUES (5, '2024-02-28', FALSE, '전문학사', '부산대학교', '통계학과', 4.25, 4.3, NOW());

-- 2.6 experience_tb
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position,
                           responsibility, created_at)
VALUES (1, '2022-01-01', '2023-12-31', false, '랩핏', '구직 플랫폼 회사입니다.', '플랫폼팀 / 팀장',
        '팀장으로 플랫폼 전반 운영과 프로젝트 관리, 팀 빌딩 등을 주도했습니다.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position,
                           responsibility, created_at)
VALUES (1, '2024-01-01', NULL, true, '망고소프트', '소프트웨어 솔루션 기업입니다.', '프론트엔드 엔지니어 / 사원',
        'React 기반 웹 프론트 개발 및 유지보수, 디자인 시스템 구축에 기여했습니다.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position,
                           responsibility, created_at)
VALUES (2, '2020-06-01', '2022-06-30', false, '코코넷', 'B2B 통신 솔루션 회사입니다.', '백엔드 개발자 / 주임',
        'Spring Boot와 PostgreSQL 기반의 API 서버 개발 및 배포 자동화 작업 수행.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position,
                           responsibility, created_at)
VALUES (2, '2023-02-01', NULL, true, '엔젤헬스', '헬스케어 플랫폼 스타트업입니다.', '풀스택 개발자 / 대리',
        'MVP 서비스 구축, AWS 인프라 구성, 사용자 피드백 기반 기능 개선 주도.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position,
                           responsibility, created_at)
VALUES (4, '2020-03-01', '2022-05-31', false, '에듀플랜', '교육 콘텐츠 제작 스타트업입니다.', '콘텐츠 기획자',
        '학습자 맞춤형 온라인 강의 콘텐츠 기획 및 제작을 담당했습니다.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position,
                           responsibility, created_at)
VALUES (5, '2021-01-01', '2023-01-15', false, '에이텍솔루션', '전자부품 제조 솔루션 회사입니다.', 'IT 인프라 엔지니어',
        '사내 네트워크 및 서버 인프라 관리, 정기 백업 및 보안 정책 적용을 수행했습니다.', '2025-04-18 12:05:23');
INSERT INTO experience_tb (resume_id, start_date, end_date, is_employed, company_name, summary, position,
                           responsibility, created_at)
VALUES (5, '2023-04-01', NULL, true, '에버핏', '피트니스 플랫폼 운영 회사입니다.', '백엔드 개발자',
        '회원관리, 트레이너 매칭, 결제 시스템 관련 API 개발 및 유지보수를 담당 중입니다.', '2025-04-18 12:05:23');

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
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url,
                        created_at)
VALUES (1, '2024-01-01', '2024-03-01', false, '랩핏', '구직 사이트 프로젝트입니다.',
        '구직 사이트 프로젝트입니다. Spring Boot, MySQL, JPA를 이용해 개발했습니다.', 'https://github.com/example1', '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url,
                        created_at)
VALUES (1, '2023-09-01', NULL, true, '타임로그', '근태 기록 웹 앱', '근무 시간을 기록하고 확인할 수 있는 사내 근태 관리 시스템입니다.', NULL,
        '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url,
                        created_at)
VALUES (2, '2024-05-01', '2024-08-01', false, '스터디메이트', '스터디 매칭 서비스', '스터디 모집과 매칭을 돕는 웹 애플리케이션입니다. Vue.js와 Spring 사용.',
        'https://github.com/example3', '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url,
                        created_at)
VALUES (2, '2024-02-01', NULL, true, '포트폴리오 웹', '개인 포트폴리오 사이트입니다.', 'HTML/CSS/JS 기반 정적 웹사이트로 자기소개, 프로젝트, 연락처를 제공합니다.',
        NULL, '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url,
                        created_at)
VALUES (3, '2023-06-01', '2023-12-15', false, '쇼핑몰 프로젝트', '온라인 쇼핑몰 구축',
        'Spring Boot + Thymeleaf 기반 쇼핑몰 프로젝트입니다. 장바구니, 결제 연동 기능 포함.', 'https://github.com/example5',
        '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url,
                        created_at)
VALUES (4, '2023-08-01', '2023-12-15', false, '이력서 자동 생성기', '입사지원용 이력서 생성 프로젝트입니다.',
        'PDF 변환 기능이 포함된 이력서 자동 생성 웹 애플리케이션입니다. HTML/CSS 기반 템플릿과 Java Spring을 사용했습니다.', 'https://github.com/example6',
        '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url,
                        created_at)
VALUES (4, '2024-06-01', NULL, true, '채용관리 시스템', '기업용 채용공고 및 지원자 관리',
        '관리자 페이지에서 채용공고 등록 및 지원자 목록을 관리할 수 있는 시스템입니다. Spring Boot + Thymeleaf로 개발 중입니다.', NULL, '2025-04-18 12:05:23');
INSERT INTO project_tb (resume_id, start_date, end_date, is_ongoing, title, summary, description, repository_url,
                        created_at)
VALUES (5, '2022-11-01', '2023-03-01', false, '포인트 적립 앱', '간단한 사용자 리워드 앱',
        'QR 코드 스캔 시 포인트를 적립하고 사용 가능한 모바일 리워드 애플리케이션입니다. Firebase와 React Native 기반으로 제작.', 'https://github.com/example7',
        '2025-04-18 12:05:23');

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
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description,
                         created_at)
VALUES (1, '2024-01-01', '2024-06-30', false, '자바 백엔드 개발자 과정', '그린컴퓨터', 'JPA와 MVC 패턴 학습', '2025-04-18 12:00:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description,
                         created_at)
VALUES (1, '2024-07-01', NULL, true, 'Spring Boot 심화과정', '멀티캠퍼스', 'Spring Security, OAuth 학습', '2025-04-18 12:05:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description,
                         created_at)
VALUES (2, '2023-09-01', '2024-02-28', false, '프론트엔드 심화반', '코드스쿼드', 'React, 상태관리 학습', '2025-04-18 12:10:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description,
                         created_at)
VALUES (3, '2024-03-01', '2024-08-31', false, '데이터 분석 입문', '패스트캠퍼스', 'Python과 데이터 시각화', '2025-04-18 12:15:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description,
                         created_at)
VALUES (4, '2024-05-01', NULL, true, 'Kotlin 안드로이드 앱 개발', '그린컴퓨터', '모바일 앱 UI 구현', '2025-04-18 12:20:00');
INSERT INTO training_tb (resume_id, start_date, end_date, is_ongoing, course_name, institution_name, description,
                         created_at)
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
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description,
                    created_at)
VALUES (1, '2024-01-01', '2024-03-01', true, '토익', '어학', 'YBM', '850점', '2025-04-18 12:00:00');
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description,
                    created_at)
VALUES (2, '2023-06-01', NULL, false, '정보처리기사', '자격증', 'HRD', '필기합격', '2025-04-18 12:05:00');
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description,
                    created_at)
VALUES (3, '2023-10-01', '2023-12-01', true, '오픈소스 컨트리뷰션', '대외활동', 'OSS Korea', '3건 PR 기여', '2025-04-18 12:10:00');
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description,
                    created_at)
VALUES (4, '2023-01-01', NULL, false, '멋쟁이사자처럼', '대외활동', 'LIKELION', '프론트엔드 팀장', '2025-04-18 12:15:00');
INSERT INTO etc_tb (resume_id, start_date, end_date, has_end_date, title, etc_type, institution_name, description,
                    created_at)
VALUES (5, '2023-03-01', '2023-05-01', true, '교내 알고리즘 대회', '수상이력', 'OO대학교', '3위 수상', '2025-04-18 12:20:00');

-- 4-1. region_tb
INSERT INTO region_tb (name)
VALUES ('서울특별시');
INSERT INTO region_tb (name)
VALUES ('부산광역시');

-- 4-2. sub_region_tb
INSERT INTO sub_region_tb (name, region_id)
VALUES ('강남구', 1);
INSERT INTO sub_region_tb (name, region_id)
VALUES ('서초구', 1);
INSERT INTO sub_region_tb (name, region_id)
VALUES ('부산진구', 2);
INSERT INTO sub_region_tb (name, region_id)
VALUES ('해운대구', 2);

-- 3. job_posting_tb
INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (6, '시니어 백엔드 개발자 채용', '백엔드', 5, 10,
        '전문학사', 1, 1, '강남대로 123',
        '대용량 트래픽 처리 기반 백엔드 플랫폼 개발',
        '2025-06-30', '마이크로서비스 아키텍처 기반 시스템 설계 및 운영',
        'Java, Spring 기반 개발 경험 필수',
        'AWS 경험자 우대',
        '탄력 근무제, 점심 제공',
        NULL, 3, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (7, '프론트엔드 개발자 모집', '프론트엔드', 0, 2,
        NULL, 1, 2, '서초대로 77',
        'B2B SaaS 웹서비스 구축 중인 스타트업입니다.',
        '2025-05-20', 'React 기반 웹 프론트엔드 개발 및 유지보수',
        'React, TypeScript 기반 개발 경험',
        'Figma 연동 경험자 우대',
        '재택 가능, 장비 지원',
        NULL, 13, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (7, '데이터 엔지니어 채용', '데이터 엔지니어', 0, 2,
        NULL, 1, 2, '서초대로 77',
        'B2B SaaS 플랫폼의 데이터 인프라를 담당할 인재를 찾습니다.',
        '2025-07-20', 'ETL 파이프라인 구축 및 데이터 웨어하우스 운영',
        'Python, SQL 기반 데이터 처리 경험',
        'AWS Glue, Redshift 경험자 우대',
        '재택 근무 가능, 최신 장비 지원',
        NULL, 13, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (7, '주니어 데이터 엔지니어 모집', '데이터 엔지니어', 0, 2,
        NULL, 1, 2, '서초대로 77',
        'B2B SaaS 스타트업의 데이터 기반 서비스 구축에 참여할 인재를 찾습니다.',
        '2025-05-20', '데이터 수집 및 정제 자동화 시스템 구축',
        'Python, SQL 기본 지식 필수',
        '빅데이터 분석 또는 Hadoop 경험자 우대',
        '유연 근무제, 성장 지원 프로그램 운영',
        NULL, 13, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (7, '마감된 개발자 모집', '프론트엔드', 0, 2,
        NULL, 1, 2, '서초대로 77',
        'B2B SaaS 웹서비스 구축 중인 스타트업입니다.',
        '2025-03-20', 'React 기반 웹 프론트엔드 개발 및 유지보수',
        'React, TypeScript 기반 개발 경험',
        'Figma 연동 경험자 우대',
        '재택 가능, 장비 지원',
        NULL, 13, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (8, '데이터 엔지니어 채용', '데이터 엔지니어', 3, 5,
        '학사', 2, 3, '해운대로 456',
        'AI 데이터 파이프라인 구축 기업입니다.',
        '2025-04-15', 'ETL 파이프라인 설계 및 데이터 웨어하우스 운영',
        'Python, SQL, AWS Redshift 경험',
        '빅데이터 처리 경험 우대',
        '성과급, 복지포인트',
        '해외 컨퍼런스 참가 지원', 23, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (6, '프론트엔드 웹 개발자 채용', '프론트엔드', 2, 5,
        '학사', 1, 2, '테헤란로 456',
        '대규모 플랫폼 웹 프론트엔드 개발',
        '2025-06-30', 'React 기반 SPA 아키텍처 설계 및 개발',
        'React, TypeScript 실무 경험자',
        'Next.js, GraphQL 경험자 우대',
        '유연 근무제, 점심 식대 지원',
        NULL, 5, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (6, '모바일 프론트엔드 앱 개발자', '모바일 앱 개발자', 2, 5,
        '학사', 1, 1, '정자일로 123',
        'React Native 기반 모바일 앱 개발',
        '2025-06-30', '모바일 최적화 및 퍼포먼스 개선 작업',
        'React Native 개발 경험 필수',
        'Flutter 경험자 우대',
        '사내 카페 무료 이용, 워크샵 지원',
        NULL, 5, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (7, 'iOS 앱 개발자 구인', '모바일 앱 개발자', 1, 4,
        '학사', 1, 2, '잠실로 321',
        'iOS 기반 모바일 앱 개발',
        '2025-07-31', 'Swift 기반 앱 아키텍처 설계 및 구현',
        'Swift, UIKit 경험자 필수',
        'RxSwift 경험자 우대',
        '탄력근무제, 자기계발비 지원',
        NULL, 4, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (8, 'AI 연구원 채용', 'AI 엔지니어', 0, 2,
        '석사', 2, 4, '777',
        '머신러닝/딥러닝 모델 연구 및 개발',
        '2025-07-01', '최신 AI 모델 논문 분석 및 모델 개선',
        'TensorFlow, PyTorch 활용 경험',
        '논문 작성 및 발표 경험자 우대',
        '연구장비 지원, 탄력 근무',
        NULL, 8, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (8, '풀스택 개발자 채용', '풀스택', 4, 8,
        '전문학사', 1, 2, '디지털로 888',
        '웹 및 서버 통합 풀스택 개발을 담당할 인재를 찾습니다.',
        '2025-08-10', 'React 및 Spring Boot 기반 풀스택 아키텍처 설계 및 개발',
        'Java, Spring Boot, React 개발 경험',
        'AWS 클라우드 서비스 활용 경험 우대',
        '재택근무 일부 가능, 복지 포인트 제공',
        NULL, 6, NOW());

INSERT INTO job_posting_tb (user_id, title, position_type, min_career_level, max_career_level,
                            education_level, address_region_id, address_sub_region_id, address_detail,
                            service_intro, deadline, responsibility, qualification, preference,
                            benefit, additional_info, view_count, created_at)
VALUES (7, 'QA 엔지니어 모집', '백엔드', 1, 5,
        '학사', 2, 1, '999',
        '품질 보증 및 테스트 자동화 구축',
        '2025-07-20', '테스트 케이스 작성 및 품질 관리',
        '테스트 자동화 경험 필수 (Selenium 등)',
        '애자일 환경 테스트 경험 우대',
        '식사 지원, 경조사 지원',
        NULL, 1, NOW());


-- 3-1. job_posting_tech_stack_tb
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (1, 'Python');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (1, 'Java');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (1, 'React');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (2, 'Django');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (2, 'Kotlin');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (2, 'Spring Boot');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (3, 'SQL');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (3, 'Node.js');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (3, 'React');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (4, 'React');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (4, 'Node.js');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (4, 'CSS');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (5, 'Python');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (5, 'Django');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (5, 'SQL');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (6, 'Java');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (6, 'Spring Boot');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (6, 'SQL');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (7, 'React');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (7, 'HTML');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (7, 'CSS');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (8, 'Node.js');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (8, 'SQL');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (8, 'Java');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (9, 'Python');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (9, 'React');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (9, 'SQL');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (10, 'Java');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (10, 'Spring Boot');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (10, 'HTML');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (11, 'Python');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (11, 'Django');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (11, 'CSS');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (12, 'Java');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (12, 'Kotlin');
INSERT INTO job_posting_tech_stack_tb (job_posting_id, tech_stack_code)
VALUES (12, 'Spring Boot');


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
VALUES (2, 2, '2025-04-18', null, false);
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
INSERT INTO application_bookmark_tb (user_id, application_id, created_at)
VALUES (6, 3, NOW());
INSERT INTO application_bookmark_tb (user_id, application_id, created_at)
VALUES (7, 1, NOW());
INSERT INTO application_bookmark_tb (user_id, application_id, created_at)
VALUES (7, 7, NOW());
INSERT INTO application_bookmark_tb (user_id, application_id, created_at)
VALUES (8, 6, NOW());
INSERT INTO application_bookmark_tb (user_id, application_id, created_at)
VALUES (8, 5, NOW());


-- 6. company_info_tb
INSERT INTO company_info_tb (user_id, logo_image, company_name, establishment_date, address, main_service, introduction,
                             image, benefit)
VALUES (6, '점핏주식회사로고이미지.png', '점핏 주식회사', '2017-07-01', '서울특별시 강남구 테헤란로 1길 10',
        'https://www.google.co.kr/', '우리는 혁신적인 구직 플랫폼입니다.', '점핏주식회사대표이미지.png',
        '유연근무제, 점심 제공, 워케이션 제도');
INSERT INTO company_info_tb (user_id, logo_image, company_name, establishment_date, address, main_service, introduction,
                             image, benefit)
VALUES (7, '랩핏테크로고이미지.png', '랩핏테크', '2019-03-15', '서울시 마포구 백범로 12길 22', 'https://www.nate.com/',
        '랩핏테크는 기업 전용 커뮤니케이션 도구를 개발합니다.', '랩핏테크대표이미지.png', '자유복장, 재택근무, 자율출퇴근');
INSERT INTO company_info_tb (user_id, logo_image, company_name, establishment_date, address, main_service, introduction,
                             image, benefit)
VALUES (8, '코드몽키로고이미지.png', '코드몽키', '2018-04-20', '경기도 성남시 분당구 판교로 235', 'https://www.daum.net/',
        '우리는 개발자를 위한 온라인 실습 기반 교육 플랫폼입니다.', '코드몽키대표이미지.png', '식대 제공, 헬스비 지원, 사내 도서관');

-- 7. board_tb
INSERT INTO board_tb (user_id, title, content, created_at)
VALUES (1, '취업 준비중인데 조언 부탁드립니다', '개발자로 취업 준비 중입니다. 혹시 면접에서 자주 나오는 질문이나, 포트폴리오에 꼭 들어가야 하는 내용 있을까요?', NOW());

INSERT INTO board_tb (user_id, title, content, created_at)
VALUES (3, '랩핏 회사 어떤가요?', '코드몽키에서 랩핏이라는 회사 채용공고 봤는데 분위기 어떤가요? 실제로 다녀보신 분 있으시면 후기 좀 공유해주세요.', NOW());

INSERT INTO board_tb (user_id, title, content, created_at)
VALUES (4, '펌펌테크 지원하신 분 계신가요?', '이번에 펌펌테크 개발자 포지션에 지원했는데 기술 스택이 저랑 잘 맞는 것 같더라고요. 근데 복지나 연봉 괜찮은지 궁금합니다.', NOW());

INSERT INTO board_tb (user_id, title, content, created_at)
VALUES (5, '면접 후기 공유합니다', '며칠 전에 점핏 주식회사 면접 보고 왔습니다. 분위기는 부드러웠는데 코딩 테스트가 꽤 어려웠어요. 준비하실 분들 참고하세요!', NOW());

INSERT INTO board_tb (user_id, title, content, created_at)
VALUES (2, '사내 분위기 어떤지 궁금해요', '최근에 코드몽키 채용을 봤는데 실무자 인터뷰가 없더라고요. 혹시 다녀본 분 계시면 조직 문화나 워라밸 어떤지 알려주실 수 있나요?', NOW());

INSERT INTO board_tb (user_id, title, content, created_at)
VALUES (4, '첫 구직 성공했어요!', '드디어 첫 회사에 합격했습니다. 아직 입사 전이지만 기분이 너무 좋네요. 랩핏 덕분에 많은 정보 얻었어요. 다들 화이팅입니다!', NOW());

INSERT INTO board_tb (user_id, title, content, created_at)
VALUES (3, '펌펌테크... 다시는 가고 싶지 않네요',
        '예전에 펌펌테크에서 잠깐 일했었는데요, 야근은 기본이고 주말에도 연락 오는 경우 많았습니다.
        윗사람 눈치 너무 많이 봐야 했고, 회식 강요도 심했어요.
        기술적으로도 구체적인 방향 없이 일만 시키는 구조라 많이 지쳤습니다.
        지원 고민 중이신 분들, 신중하게 결정하세요.',
        NOW());

-- 8. reply_tb
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (1, 1, '좋아요 부탁드려요 ~', NOW());
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (1, 2, '질문이 하나 있습니다.', NOW());
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (1, 3, '좋아요 누르고 갑니다.', NOW());
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (2, 1, '감사합니다. 도움됐어요.', NOW());
INSERT INTO reply_tb (user_id, board_id, content, created_at)
VALUES (2, 2, '오늘도 화이팅!', NOW());
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