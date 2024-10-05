-- member

INSERT INTO tbl_member
(name, email, password, phone_number, height, weight, main_foot, area, position, stamina, defend, speed, pass, shoot,
 dribble, total_match, total_score, total_assist, total_mom, birth, introduction, is_pro, gender, authority,
 social_type)
VALUES ('홍길동', 'hong@example.com', 'password123', '010-1111-1111', 180.0, 75.0, '왼발', '서울', 'ST', 90, 85, 88, 82, 80,
        78, 150, 50, 30, 15, '1985-03-15', '열정적인 공격수입니다.', false, 'MALE', 'USER', 'NONE'),
       ('김철수', 'kim@example.com', 'password123', '010-2222-2222', 175.0, 70.0, '오른발', '부산', 'GK', 85, 80, 87, 78, 75,
        82, 180, 45, 40, 20, '1988-06-12', '경기 집중력이 뛰어납니다.', false, 'MALE', 'USER', 'NONE'),
       ('박영희', 'park@example.com', 'password123', '010-3333-3333', 165.0, 55.0, '양발', '경기', 'RW', 78, 72, 80, 75, 72,
        70, 120, 35, 25, 10, '1992-11-20', '빠른 윙어입니다.', false, 'FEMALE', 'USER', 'NONE'),
       ('이민호', 'lee@example.com', 'password123', '010-4444-4444', 170.0, 68.0, '오른발', '대전', 'CM', 82, 78, 85, 80, 78,
        75, 180, 20, 50, 18, '1990-01-05', '경기를 조율하는 미드필더.', true, 'MALE', 'USER', 'NONE'),
       ('최지우', 'choi@example.com', 'password123', '010-5555-5555', 185.0, 78.0, '왼발', '광주', 'CB', 88, 85, 90, 85, 88,
        82, 190, 55, 35, 22, '1987-08-22', '단단한 수비수입니다.', true, 'MALE', 'USER', 'NONE'),
       ('장혜진', 'jang@example.com', 'password123', '010-6666-6666', 160.0, 52.0, '오른발', '대구', 'LW', 75, 70, 78, 72, 75,
        70, 140, 25, 30, 10, '1994-05-17', '빠르고 기술적인 선수입니다.', false, 'FEMALE', 'USER', 'NONE'),
       ('윤석현', 'yoon@example.com', 'password123', '010-7777-7777', 178.0, 72.0, '왼발', '울산', 'LB', 85, 80, 87, 82, 80,
        78, 165, 30, 20, 12, '1991-12-03', '확실한 수비수입니다.', true, 'MALE', 'USER', 'NONE'),
       ('김하늘', 'kimhaneul@example.com', 'password123', '010-8888-8888', 182.0, 75.0, '양발', '제주', 'ST', 90, 88, 92, 85,
        90, 85, 170, 50, 25, 10, '1996-02-14', '양발을 잘 쓰는 공격수입니다.', false, 'MALE', 'USER', 'NONE'),
       ('오준영', 'oh@example.com', 'password123', '010-9999-9999', 175.0, 70.0, '오른발', '세종', 'RM', 78, 75, 80, 78, 75, 72,
        120, 28, 32, 11, '1993-07-01', '기술이 뛰어난 미드필더입니다.', true, 'MALE', 'USER', 'NONE'),
       ('정수민', 'jung@example.com', 'password123', '010-1010-1010', 165.0, 60.0, '왼발', '전남', 'CB', 85, 82, 87, 80, 85,
        80, 190, 55, 40, 22, '1995-11-11', '경험이 많은 수비수입니다.', false, 'FEMALE', 'USER', 'NONE');


-- tbl_club 테이블에 데이터 삽입
INSERT INTO tbl_club (club_owner_id, club_name, club_introduction, club_code, eroll_date, member_count, skill_level, stadium_name, city, region, age_group, gender)
VALUES
    (1, 'Seoul FC', '서울 최고의 구단입니다.', 'SEO123', '2024-01-01 10:00:00', 0,'프로', '서울 월드컵 경기장', '서울', '서울특별시', '20대', '남성'),
    (2, 'Busan United', '부산 최고의 구단입니다.', 'BUS456', '2024-01-02 11:00:00', 0,'아마추어', '부산 아시아드 경기장', '부산', '부산광역시', '30대', '남성'),
    (3, 'Incheon City', '인천에서 최고의 실력을 자랑하는 구단입니다.', 'INC789', '2024-01-03 12:00:00',0, '세미프로', '인천 송도 경기장', '인천', '인천광역시', '20대', '여성'),
    (4, 'Daegu FC', '대구에서 활동하는 구단입니다.', 'DAE321', '2024-01-04 13:00:00', 0,'입문자', '대구 스타디움', '대구', '대구광역시', '40대', '남성'),
    (5, 'Jeonju FC', '전주에서 활동하는 구단입니다.', 'JEO654', '2024-01-05 14:00:00',0, '아마추어', '전주월드컵 경기장', '전주', '전라북도', '30대', '여성'),
    (6, 'Gwangju United', '광주의 자랑스러운 구단입니다.', 'GWA987', '2024-01-06 15:00:00', 0,'프로', '광주월드컵 경기장', '광주', '광주광역시', '20대', '남성'),
    (7, 'Ulsan FC', '울산의 강력한 구단입니다.', 'ULS258', '2024-01-07 16:00:00',0, '세미프로', '울산문수축구경기장', '울산', '울산광역시', '30대', '여성'),
    (8, 'Daejeon Citizen', '대전의 대표 구단입니다.', 'DAE369', '2024-01-08 17:00:00',0, '프로', '대전 월드컵 경기장', '대전', '대전광역시', '40대', '남성'),
    (9, 'Jeju United', '제주의 강력한 구단입니다.', 'JEJ147', '2024-01-09 18:00:00',0, '아마추어', '제주월드컵경기장', '제주', '제주특별자치도', '20대', '여성'),
    (10, 'Suwon Bluewings', '수원의 자랑스러운 구단입니다.', 'SUW369', '2024-01-10 19:00:00',0,'프로', '수원월드컵경기장', '수원', '경기도', '30대', '남성');

-- tbl_match 테이블에 데이터 삽입
INSERT INTO tbl_match
(match_enroll_user_id, match_apply_user_id, my_club_id, enemy_club_id, match_introduce,
 match_date, match_start_time, match_end_time, match_time, match_player_quantity,
 quarter_quantity, field_location, match_cost, is_pro, pro_quantity, club_level,
 match_gender, match_status)
VALUES
    -- 서울 FC와 부산 United의 매치
    (1, 2, 1, 2, '서울과 부산 간의 열띤 대결입니다.',
     '2024-10-01', '10:00:00', '12:00:00', TIME_TO_SEC(TIMEDIFF('12:00:00', '10:00:00')),
     'ELEVEN', 'FOUR', '서울 월드컵 경기장', 100000, false, 0, '프로', 'MALE', 'WAITING'),

    -- 인천 City와 대구 FC의 매치
    (3, 4, 3, 4, '인천과 대구의 매치! 경기장이 뜨거워질 것입니다.',
     '2024-10-02', '09:00:00', '11:00:00', TIME_TO_SEC(TIMEDIFF('11:00:00', '09:00:00')),
     'NINE', 'FOUR', '대구 스타디움', 80000, true, 2, '세미프로', 'FEMALE', 'WAITING'),

    -- 광주 United와 제주 United의 매치
    (6, 9, 6, 9, '광주와 제주, 섬과 도시의 대결!',
     '2024-10-03', '14:00:00', '16:30:00', TIME_TO_SEC(TIMEDIFF('16:30:00', '14:00:00')),
     'FIVE', 'TWO', '광주월드컵 경기장', 120000, false, 0, '프로', 'MIX', 'WAITING'),

    -- 대전 Citizen과 전주 FC의 매치
    (8, 5, 8, 5, '대전과 전주의 도심 라이벌전.',
     '2024-10-04', '07:00:00', '09:00:00', TIME_TO_SEC(TIMEDIFF('09:00:00', '07:00:00')),
     'SEVEN', 'THREE', '대전 월드컵 경기장', 90000, true, 1, '세미프로', 'MALE', 'WAITING'),

    -- 울산 FC와 수원 Bluewings의 매치
    (7, 10, 7, 10, '울산과 수원, 한판 승부가 벌어집니다.',
     '2024-10-05', '08:30:00', '10:30:00', TIME_TO_SEC(TIMEDIFF('10:30:00', '08:30:00')),
     'ELEVEN', 'FOUR', '울산문수축구경기장', 150000, true, 3, '프로', 'MIX', 'WAITING'),

    -- 서울 FC와 인천 City의 매치
    (5, 3, 1, 3, '서울과 인천, 수도권 라이벌 매치!',
     '2024-10-06', '18:00:00', '20:00:00', TIME_TO_SEC(TIMEDIFF('20:00:00', '18:00:00')),
     'ELEVEN', 'FOUR', '서울 월드컵 경기장', 130000, false, 0, '프로', 'MALE', 'WAITING'),

    -- 부산 United와 대전 Citizen의 매치
    (2, 8, 2, 8, '부산과 대전의 치열한 승부.',
     '2024-10-07', '06:00:00', '08:00:00', TIME_TO_SEC(TIMEDIFF('08:00:00', '06:00:00')),
     'NINE', 'THREE', '부산 아시아드 경기장', 85000, true, 1, '아마추어', 'FEMALE', 'WAITING'),

    -- 제주 United와 전주 FC의 매치
    (9, 5, 9, 5, '제주와 전주, 남부의 자존심을 건 매치!',
     '2024-10-08', '13:30:00', '15:30:00', TIME_TO_SEC(TIMEDIFF('15:30:00', '13:30:00')),
     'SEVEN', 'TWO', '제주월드컵경기장', 70000, false, 0, '아마추어', 'MIX', 'WAITING'),

    -- 수원 Bluewings와 광주 United의 매치
    (10, 6, 10, 6, '수원과 광주의 뜨거운 대결!',
     '2024-10-09', '11:00:00', '13:00:00', TIME_TO_SEC(TIMEDIFF('13:00:00', '11:00:00')),
     'ELEVEN', 'FOUR', '수원월드컵경기장', 110000, true, 2, '프로', 'MALE', 'WAITING'),

    -- 대구 FC와 울산 FC의 매치
    (4, 7, 4, 7, '대구와 울산의 동해안 더비!',
     '2024-10-10', '17:30:00', '19:30:00', TIME_TO_SEC(TIMEDIFF('19:30:00', '17:30:00')),
     'SEVEN', 'THREE', '대구 스타디움', 95000, false, 0, '세미프로', 'MIX', 'WAITING');
