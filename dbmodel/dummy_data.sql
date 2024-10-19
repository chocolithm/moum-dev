-- 유형 대분류 데이터
INSERT INTO maincategory (category_name) VALUES
('건담프라모델'),
('레고'),
('신발');

-- 유형 소분류 데이터
INSERT INTO subcategory (maincategory_id, category_name) VALUES
(1, 'PG'),
(1, 'RG'),
(1, 'MG'),
(1, 'HG'),
(1, 'SD'),
(1, 'EG'),
(1, '1/60'),
(1, '1/100'),
(1, '1/144'),
(1, 'RE/100'),
(2, '아키텍처'),
(2, '배트맨'),
(2, '보태니컬 컬렉션'),
(2, '브릭헤즈'),
(2, '시티'),
(2, '클래식'),
(2, '크리에이터 3in1'),
(2, '크리에이터 엑스퍼트'),
(2, 'DC'),
(2, '슈퍼 배드 4'),
(2, '디즈니'),
(2, '도트'),
(2, '듀플로'),
(2, '프렌즈'),
(2, '해리포터'),
(2, '아이디어'),
(2, '쥬라기월드'),
(2, '레고 동물의 숲'),
(2, '레고 아트'),
(2, '레고 아바타'),
(2, '레고 Braile Bricks'),
(2, '레고 드림즈'),
(2, '레고 듀플로 페파 피그'),
(2, '레고 에듀케이션'),
(2, '레고 Fortnite'),
(2, '레고 개비의 매직 하우스'),
(2, '레고 Icons'),
(2, '레고 인디애나 존스'),
(2, '레고 슈퍼 마리오'),
(2, '레고 젤다의 전설'),
(2, '레고 웬즈데이'),
(2, '레고 위키드'),
(2, 'Lord of the Rings'),
(2, '마블'),
(2, '마인크래프트'),
(2, '미니 피겨'),
(2, '몽키 키드'),
(2, '닌자고'),
(2, '오버워치'),
(2, '파워드업'),
(2, '파워퍼프걸'),
(2, '소닉 더 헤지혹'),
(2, '스피드 챔피언'),
(2, '스파이더맨'),
(2, '스타워즈'),
(2, '테크닉'),
(3, '나이키'),
(3, '뉴발란스'),
(3, '컨버스'),
(3, '아디다스'),
(3, '팀버랜드'),
(3, '내셔널지오그래픽'),
(3, '아식스'),
(3, '휠라'),
(3, '폴로'),
(3, '리복'),
(3, '반스'),
(3, '프로스펙스'),
(3, '야스히로'),
(3, '살로몬'),
(3, '퓨마');

-- 회원 데이터
INSERT INTO user (user_id, email, nickname, password, admin) VALUES
(1, 'user1@example.com', '레고매니아', '1111', 0),
(2, 'user2@example.com', '신발', '1111', 0),
(3, 'user3@example.com', '건담수집가', '1111', 0),
(4, 'admin@example.com', '관리자', '1111', 1);


-- 상태 데이터
INSERT INTO collection_status (status_id, status_name) VALUES
(1, '미개봉' ),
(2, '사용감 없음'),
(3, '사용감 있음');


-- 수집품 데이터
INSERT INTO collection (user_id, subcategory_id, name, en_name, status_id, purchase_date, purchase_place, price, storage_location) VALUES
(2, 15, '레고 시티 경찰서', 'Lego City Police', 1, '2023-01-15', '레고 공식 스토어', 200000, '선반'),
(2, 4, '나이키 에어맥스', 'Nike Airmax', 3, '2023-02-20', '나이키 매장', 150000, '신발장'),
(3, 7, 'HG 건담 바바토스', 'HG Babatos', 2, '2023-03-10', '건담베이스', 20000, '진열장'),
(1, 2, '레고 밀레니엄 팔콘', 'Lego Millenium Falcon', 1, '2023-04-05', '레고 공식 스토어', 300000, '책상');


-- 게시글 데이터
INSERT INTO board (board_id, title, content, user_id, view_count) VALUES
(1, '레고 시티 경찰서 조립기', '새로 산 레고 시티 경찰서 조립 과정과 후기입니다...', 1, 100),
(2, '나이키 에어맥스 리뷰', '편안한 나이키 에어맥스 사용 후기입니다...', 2, 50),
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

INSERT INTO collection_photo (photo_id, collection_id, filepath) VALUES
(1, 1, "/images/collections/collection-image-1.png"),
(2, 2, "/images/collections/collection-image-2.png"),
(3, 3, "/images/collections/collection-image-3.png"),
(4, 4, "/images/collections/collection-image-3.png");

-- 댓글 데이터
INSERT INTO comment (comment_id, user_id, board_id, content) VALUES
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
