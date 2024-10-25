-- 유형 대분류 데이터
INSERT INTO maincategory (maincategory_id, category_name) VALUES
(1, '건담프라모델'),
(2, '레고'),
(3, '신발');

-- 유형 소분류 데이터
INSERT INTO subcategory (subcategory_id, maincategory_id, category_name) VALUES
(1, 1, 'PG'),
(2, 1, 'RG'),
(3, 1, 'MG'),
(4, 1, 'HG'),
(5, 1, 'SD'),
(6, 1, 'EG'),
(7, 1, '1/60'),
(8, 1, '1/100'),
(9, 1, '1/144'),
(10, 1, 'RE/100'),
(11, 1, 'B-CLUB'),
(12, 1, 'EX 모델'),
(13, 1, 'SD 건담 BB 전사'),
(14, 1, 'SD 건담 EX 스탠다드'),
(15, 1, 'SD 건담 크로스 실루엣'),
(16, 1, '풀 메카닉스'),
(17, 2, '아키텍처'),
(18, 2, '배트맨'),
(19, 2, '보태니컬 컬렉션'),
(20, 2, '브릭헤즈'),
(21, 2, '시티'),
(22, 2, '클래식'),
(23, 2, '크리에이터 3in1'),
(24, 2, '크리에이터 엑스퍼트'),
(25, 2, 'DC'),
(26, 2, '슈퍼 배드 4'),
(27, 2, '디즈니'),
(28, 2, '도트'),
(29, 2, '듀플로'),
(30, 2, '프렌즈'),
(31, 2, '해리포터'),
(32, 2, '아이디어'),
(33, 2, '쥬라기월드'),
(34, 2, '레고 동물의 숲'),
(35, 2, '레고 아트'),
(36, 2, '레고 아바타'),
(37, 2, '레고 Braile Bricks'),
(38, 2, '레고 드림즈'),
(39, 2, '레고 듀플로 페파 피그'),
(40, 2, '레고 에듀케이션'),
(41, 2, '레고 Fortnite'),
(42, 2, '레고 개비의 매직 하우스'),
(43, 2, '레고 Icons'),
(44, 2, '레고 인디애나 존스'),
(45, 2, '레고 슈퍼 마리오'),
(46, 2, '레고 젤다의 전설'),
(47, 2, '레고 웬즈데이'),
(48, 2, '레고 위키드'),
(49, 2, 'Lord of the Rings'),
(50, 2, '마블'),
(51, 2, '마인크래프트'),
(52, 2, '미니 피겨'),
(53, 2, '몽키 키드'),
(54, 2, '닌자고'),
(55, 2, '오버워치'),
(56, 2, '파워드업'),
(57, 2, '파워퍼프걸'),
(58, 2, '소닉 더 헤지혹'),
(59, 2, '스피드 챔피언'),
(60, 2, '스파이더맨'),
(61, 2, '스타워즈'),
(62, 2, '테크닉'),
(63, 2, 'Agents'),
(64, 2, '캐슬'),
(65, 2, 'Orient Expedition'),
(66, 2, 'Pharaohs Quest'),
(67, 2, '마인드스톰'),
(68, 2, '믹셀'),
(69, 2, '엘프'),
(70, 2, '킹덤'),
(71, 2, '히든사이드'),
(72, 2, '바이오니클'),
(73, 2, '부스트'),
(74, 2, '엑소 포스'),
(75, 2, '히어로 팩토리'),
(76, 2, '키마의 전설'),
(77, 2, '넥소 나이츠'),
(78, 3, '나이키'),
(79, 3, '뉴발란스'),
(80, 3, '컨버스'),
(81, 3, '아디다스'),
(82, 3, '팀버랜드'),
(83, 3, '디스커버리'),
(84, 3, '아식스'),
(85, 3, '휠라'),
(86, 3, '구찌'),
(87, 3, '리복'),
(88, 3, '반스'),
(89, 3, '프로스펙스'),
(90, 3, 'RAF SIMONS'),
(91, 3, '살로몬'),
(92, 3, '써코니'),
(93, 3, '내셔널지오그래픽'),
(94, 3, '발렌시아가'),
(95, 3, '루이비통'),
(96, 3, 'SACAI'),
(97, 3, '아크네'),
(98, 3, 'A.P.C.'),
(99, 3, '미즈노'),
(100, 3, 'HOKA ONEONE'),
(101, 3, '발렌티노'),
(102, 3, '알렉산더 맥퀸'),
(103, 3, 'MASION MAGIELA'),
(104, 3, 'MLB'),
(105, 3, '베트멍'),
(106, 3, '퓨마'),
(107, 1, '프리미엄 반다이'),
(108, 1, '하이 레졸루션 모델');

-- 회원 데이터
INSERT INTO user (user_id, email, nickname, password, admin) VALUES
(1, 'admin@test.com', '관리자', '$2a$10$v3qFJAESrWNRNOgkbMBvUu.GfcPVxvw8Sc9dZtL3YVdGop549CvKe', 1);


-- 상태 데이터
INSERT INTO collection_status (status_id, status_name) VALUES
(1, '미개봉' ),
(2, '사용감 없음'),
(3, '사용감 있음');


-- 수집품 데이터
INSERT INTO collection (collection_id, user_id, subcategory_id, name, en_name, status_id, purchase_date, purchase_place, price, storage_location) VALUES
(1, 2, 15, '레고 시티 경찰서', 'Lego City Police', 1, '2023-01-15', '레고 공식 스토어', 200000, '선반'),
(2, 2, 15, '레고 시티 소방서', 'Lego City Fire Station', 2, '2023-01-16', '레고 공식 스토어', 165000, '집'),
(3, 2, 15, '레고 시티 딜리버리트럭', 'Lego City Delivery Truck', 1, '2023-02-09', '레고 중고 스토어', 200000, '창고'),
(4, 2, 15, '레고 시티 굴착기', 'Lego City Gulchak', 3, '2023-08-11', '레고 공식 스토어', 140000, '선반'),
(5, 2, 4, '나이키 에어맥스', 'Nike Airmax', 3, '2023-02-20', '나이키 매장', 150000, '신발장'),
(6, 3, 7, 'HG 건담 바바토스', 'HG Babatos', 2, '2023-03-10', '건담베이스', 20000, '진열장'),
(7, 1, 2, '레고 밀레니엄 팔콘', 'Lego Millenium Falcon', 1, '2023-04-05', '레고 공식 스토어', 300000, '책상'),
(8, 2, 4, '나이키 에어맥스2', 'Nike Airmax2', 1, '2023-04-16', '당근', 180000, '신발장'),
(9, 2, 4, '나이키 에어맥스3', 'Nike Airmax3', 2, '2023-04-20', '나이키 매장', 150000, '신발장');


-- 게시글 데이터
INSERT INTO board (board_id, title, content, user_id, view_count) VALUES
(2, '레고 시티 경찰서 조립기', '새로 산 레고 시티 경찰서 조립 과정과 후기입니다...', 1, 100),
(1, '나이키 에어맥스 리뷰', '편안한 나이키 에어맥스 사용 후기입니다...', 2, 50),
(3, 'HG 건담 바바토스 조립 팁', 'HG 건담 바바토스 조립 시 주의할 점과 팁 공유...', 3, 200),
(4, '레고 테크닉 그것이 알고 싶다', '레고 테크닉 정보 공유...', 1, 150),
(5, '레고 스타워즈', '레고 스타워즈 제품 공유...', 1, 150),
(6, '레고 시티 소방서', '레고 시티 소방서 정보 공유...', 1, 150),
(7, '레고 마블', '레고 마블 신제품 공유...', 1, 150),
(8, '레고 dc', '레고 dc 정보 공유...', 1, 150);


-- 거래 게시글 데이터
INSERT INTO trade_board (board_id, collection_id, price, status) VALUES
(1, 1, 20000, 1),
(2, 2, 40000, 0),
(3, null, 50000, 0),
(4, 3, 200000, 1);


-- 수집품게시글 데이터
INSERT INTO collection_board (board_id, collection_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO chatroom(chatroom_id, board_id, user_id) VALUES
(1, 7, 3),
(2, 7, 4),
(3, 8, 2),
(4, 8, 3),
(5, 8, 4);

INSERT INTO chat(chat_id, chatroom_id, user_id, message, chat_date) VALUES
(1, 1, 1, "안녕하세요.", "2024-08-23 23:23:42"),
(2, 1, 3, "안녕하세요!!", "2024-08-23 23:23:56"),
(3, 1, 1, "구매하시나요?", "2024-08-23 23:24:41"),
(4, 1, 1, "참고로 싸게 올렸어요", "2024-08-23 23:25:16"),
(5, 1, 3, "구매할게요!", "2024-08-24 11:16:55"),
(6, 1, 1, "감사합니다", "2024-08-24 12:08:11"),
(7, 1, 3, "수고하세요", "2024-08-24 12:12:41"),
(8, 2, 1, "안녕하세요.", "2024-10-11 12:12:56"),
(9, 2, 4, "안녕하세요!!", "2024-10-11 12:13:16"),
(10, 2, 1, "구매하시나요?", "2024-10-11 12:13:23"),
(11, 2, 1, "참고로 싸게 올렸어요", "2024-10-11 12:13:47"),
(12, 2, 4, "좀만 싸게 해주세요!", "2024-10-11 12:14:08"),
(13, 2, 1, "에누리 안되는데", "2024-10-11 12:14:21"),
(14, 2, 4, "수고하세요", "2024-10-11 12:14:36"),
(15, 2, 1, "아 잠시", "2024-10-11 12:14:52"),
(16, 2, 1, "깎아드릴게요", "2024-10-11 12:15:02"),
(17, 2, 4, "고맙습니다~~~", "2024-10-11 12:15:08");

INSERT INTO collection_photo (photo_id, collection_id, filename, origin_filename) VALUES
(1, 1, "collection-image-1.png", "collection-image-1.png"),
(2, 2, "collection-image-2.png", "collection-image-2.png"),
(3, 3, "collection-image-3.png", "collection-image-3.png"),
(4, 4, "collection-image-4.png", "collection-image-4.png"),
(5, 2, "collection-image-5.png", "collection-image-5.png"),
(6, 2, "collection-image-6.png", "collection-image-6.png"),
(7, 7, "collection-image-7.png", "collection-image-7.png"),
(8, 7, "collection-image-8.png", "collection-image-8.png"),
(9, 8, "collection-image-9.png", "collection-image-9.png"),
(10, 1, "collection-image-10.png", "collection-image-10.png"),
(11, 5, "collection-image-11.png", "collection-image-11.png");

-- 댓글 데이터
INSERT INTO commentRequest (comment_id, user_id, board_id, content) VALUES
(1, 2, 1, '멋진 경찰서네요! 저도 갖고 싶어요.'),
(2, 3, 2, '에어맥스 편안해 보이네요. 저도 구매 고민 중이에요.'),
(3, 1, 3, '바바토스 조립 팁 감사합니다. 많은 도움이 될 것 같아요.'),
(4, 2, 4, '멋진 디스플레이네요! 공간 활용이 인상적입니다.');

-- 추천 데이터
INSERT INTO likes (board_id, user_id) VALUES
(1, 2),
(1, 3),
(2, 1),
(2, 3),
(3, 1),
(3, 2),
(4, 2),
(4, 3);

-- 업적 데이터
INSERT INTO achievement (achievement_id, name, content, photo, acquisition_condition, location) VALUES
('FIRST_POST', '첫 게시글', '첫 번째 게시글을 작성하세요', 'first_post.png', '게시글 1개 작성', '커뮤니티'),
('COLLECTOR_NOVICE', '초보 수집가', '첫 번째 수집품을 등록하세요', 'collector_novice.png', '수집품 1개 등록', '수집품 관리'),
('LEGO_MASTER', '레고 마스터', '레고 수집품 10개를 등록하세요', 'lego_master.png', '레고 수집품 10개 등록', '수집품 관리'),
('SHOE_ENTHUSIAST', '신발 애호가', '신발 수집품 10개를 등록하세요', 'shoe_enthusiast.png', '신발 수집품 10개 등록', '수집품 관리'),
('GUNDAM_PRO', '건담 프로', '건담 프라모델 10개를 등록하세요', 'gundam_pro.png', '건담 프라모델 10개 등록', '수집품 관리');

-- 회원 업적 데이터
INSERT INTO user_achievement (user_id, achievement_id) VALUES
(1, 'FIRST_POST'),
(1, 'COLLECTOR_NOVICE'),
(2, 'FIRST_POST'),
(2, 'COLLECTOR_NOVICE'),
(3, 'FIRST_POST'),
(3, 'COLLECTOR_NOVICE');

-- 게시글 태그 데이터
INSERT INTO board_tag (board_id, tag_name) VALUES
(1, '레고'),
(1, '시티'),
(1, '조립'),
(2, '나이키'),
(2, '에어맥스'),
(2, '운동화'),
(3, '건담'),
(3, 'HG'),
(3, '조립'),
(4, '레고'),
(4, '스타워즈'),
(4, '디스플레이');

-- 신고 카테고리 데이터
INSERT INTO report_category (report_category_id, category_name) VALUES
(1, '스팸'),
(2, '부적절한 콘텐츠'),
(3, '저작권 침해'),
(4, '혐오 발언');

-- 신고 처리 유형 데이터
INSERT INTO report_result_category (report_result_id, result_name) VALUES
(1, '경고'),
(2, '게시물 삭제'),
(3, '계정 정지'),
(4, '신고 기각');

-- 금지어 유형 데이터
INSERT INTO forbidden_word (word) VALUES
('시바'),
('병신'),
('버러지'),
('운지');
