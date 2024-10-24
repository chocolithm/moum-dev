-- 회원
DROP TABLE IF EXISTS user RESTRICT;

-- 게시글
DROP TABLE IF EXISTS board RESTRICT;

-- 댓글
DROP TABLE IF EXISTS commentRequest RESTRICT;

-- 업적
DROP TABLE IF EXISTS achievement RESTRICT;

-- 채팅
DROP TABLE IF EXISTS chat RESTRICT;

-- 회원 업적
DROP TABLE IF EXISTS user_achievement RESTRICT;

-- 수집품
DROP TABLE IF EXISTS collection RESTRICT;

-- 신고
DROP TABLE IF EXISTS report RESTRICT;

-- 알림
DROP TABLE IF EXISTS alert RESTRICT;

-- 추천
DROP TABLE IF EXISTS likes RESTRICT;

-- 게시글 태그
DROP TABLE IF EXISTS board_tag RESTRICT;

-- 금지어
DROP TABLE IF EXISTS forbidden_word RESTRICT;

-- 구글
DROP TABLE IF EXISTS google RESTRICT;

-- 유형 대분류
DROP TABLE IF EXISTS maincategory RESTRICT;

-- 유형 소분류
DROP TABLE IF EXISTS subcategory RESTRICT;

-- 수집품게시글
DROP TABLE IF EXISTS collection_board RESTRICT;

-- 수집품거래게시글
DROP TABLE IF EXISTS trade_board RESTRICT;

-- 신고처리유형
DROP TABLE IF EXISTS report_result_category RESTRICT;

-- 신고대상
DROP TABLE IF EXISTS report_category RESTRICT;

-- 네이버
DROP TABLE IF EXISTS naver RESTRICT;

-- 카카오
DROP TABLE IF EXISTS kakao RESTRICT;

-- 수집품 첨부파일
DROP TABLE IF EXISTS collection_photo RESTRICT;

-- 게시글 첨부파일
DROP TABLE IF EXISTS board_photo RESTRICT;

-- 수집품 상태
DROP TABLE IF EXISTS collection_status RESTRICT;

-- 채팅방
DROP TABLE IF EXISTS chatroom RESTRICT;

-- 회원
CREATE TABLE user (
    user_id    INTEGER      NOT NULL, -- 회원 번호
    photo      VARCHAR(255) NULL,     -- 프로필 사진
    email      VARCHAR(40)  NOT NULL, -- 이메일
    nickname   VARCHAR(50)  NOT NULL, -- 닉네임
    password   VARCHAR(255) NOT NULL, -- 비밀번호
    start_date DATETIME     NOT NULL DEFAULT now(), -- 가입일자
    end_date   DATE         NULL,     -- 탈퇴일자
    admin      TINYINT      NOT NULL DEFAULT 0, -- 관리자 여부
    sns_id     INTEGER      NULL      -- SNS 번호
);

-- 회원
ALTER TABLE user
    ADD CONSTRAINT PK_user -- 회원 기본키
    PRIMARY KEY (
    user_id -- 회원 번호
    );

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_user
    ON user ( -- 회원
        nickname ASC -- 닉네임
    );

-- 회원 유니크 인덱스2
CREATE UNIQUE INDEX UIX_user2
    ON user ( -- 회원
        email ASC -- 이메일
    );

ALTER TABLE user
    MODIFY COLUMN user_id INTEGER NOT NULL AUTO_INCREMENT;

-- 게시글
CREATE TABLE board (
    board_id   INTEGER      NOT NULL, -- 게시글 번호
    title      VARCHAR(255) NOT NULL, -- 제목
    content    TEXT         NOT NULL, -- 내용
    user_id    INTEGER      NOT NULL, -- 작성자 번호
    post_date  DATETIME     NOT NULL DEFAULT now(), -- 작성일자
    view_count INTEGER      NOT NULL DEFAULT 0, -- 조회수
    is_public  TINYINT      NOT NULL DEFAULT 1, -- 공개
    is_deleted TINYINT      NOT NULL DEFAULT 0 -- 삭제
);

-- 게시글
ALTER TABLE board
    ADD CONSTRAINT PK_board -- 게시글 기본키
    PRIMARY KEY (
    board_id -- 게시글 번호
    );

ALTER TABLE board
    MODIFY COLUMN board_id INTEGER NOT NULL AUTO_INCREMENT;

-- 댓글
CREATE TABLE commentRequest (
    comment_id          INTEGER  NOT NULL, -- 댓글 번호
    user_id             INTEGER  NOT NULL, -- 작성자 번호
    board_id            INTEGER  NOT NULL, -- 게시글 번호
    content             TEXT     NOT NULL, -- 작성내용
    date                DATETIME NOT NULL DEFAULT now(), -- 작성일시
    original_comment_id INTEGER  NULL,     -- 상위 댓글 번호
    is_public           TINYINT  NOT NULL DEFAULT 1 -- 공개
);

-- 댓글
ALTER TABLE commentRequest
    ADD CONSTRAINT PK_comment -- 댓글 기본키
    PRIMARY KEY (
    comment_id -- 댓글 번호
    );

ALTER TABLE commentRequest
    MODIFY COLUMN comment_id INTEGER NOT NULL AUTO_INCREMENT;

-- 업적
CREATE TABLE achievement (
    achievement_id        VARCHAR(50)  NOT NULL, -- 업적ID
    name                  VARCHAR(50)  NOT NULL, -- 업적명
    content               TEXT         NOT NULL, -- 업적 설명
    photo                 VARCHAR(255) NOT NULL, -- 업적 이미지
    acquisition_condition TEXT         NOT NULL, -- 취득 조건
    location              TEXT         NOT NULL  -- 취득 장소
);

-- 업적
ALTER TABLE achievement
    ADD CONSTRAINT PK_achievement -- 업적 기본키
    PRIMARY KEY (
    achievement_id -- 업적ID
    );

-- 업적 유니크 인덱스
CREATE UNIQUE INDEX UIX_achievement
    ON achievement ( -- 업적
        name ASC -- 업적명
    );

-- 채팅
CREATE TABLE chat (
    chat_id     INTEGER  NOT NULL, -- 채팅번호
    chatroom_id INTEGER  NOT NULL, -- 채팅방번호
    user_id     INTEGER  NOT NULL, -- 발신자 번호
    message     TEXT     NOT NULL, -- 대화내용
    chat_date   DATETIME NOT NULL DEFAULT now() -- 작성일
);

-- 채팅
ALTER TABLE chat
    ADD CONSTRAINT PK_chat -- 채팅 기본키
    PRIMARY KEY (
    chat_id -- 채팅번호
    );

ALTER TABLE chat
    MODIFY COLUMN chat_id INTEGER NOT NULL AUTO_INCREMENT;

-- 회원 업적
CREATE TABLE user_achievement (
    user_id        INTEGER     NOT NULL, -- 회원 번호
    achievement_id VARCHAR(50) NOT NULL, -- 업적ID
    get_date       DATETIME    NOT NULL DEFAULT now() -- 취득일자
);

-- 회원 업적
ALTER TABLE user_achievement
    ADD CONSTRAINT PK_user_achievement -- 회원 업적 기본키
    PRIMARY KEY (
    user_id,        -- 회원 번호
    achievement_id  -- 업적ID
    );

-- 수집품
CREATE TABLE collection (
    collection_id    INTEGER      NOT NULL, -- 수집품 번호
    user_id          INTEGER      NOT NULL, -- 소유자 번호
    subcategory_id   INTEGER      NOT NULL, -- 소분류번호
    name             VARCHAR(50)  NOT NULL, -- 제품명
    en_name          VARCHAR(50)  NULL,     -- 제품명(영문)
    status_id        INTEGER      NULL,     -- 수집품상태번호
    purchase_date    DATE         NULL,     -- 구매일자
    purchase_place   VARCHAR(255) NULL,     -- 구매처
    price            INTEGER      NULL,     -- 가격
    storage_location VARCHAR(255) NULL,     -- 보관장소
    post_date        DATETIME     NOT NULL DEFAULT now() -- 등록일시
);

-- 수집품
ALTER TABLE collection
    ADD CONSTRAINT PK_collection -- 수집품 기본키
    PRIMARY KEY (
    collection_id -- 수집품 번호
    );

ALTER TABLE collection
    MODIFY COLUMN collection_id INTEGER NOT NULL AUTO_INCREMENT;

-- 신고
CREATE TABLE report (
    report_id          INTEGER  NOT NULL, -- 신고 번호
    user_id            INTEGER  NOT NULL, -- 신고자 번호
    report_content     TEXT     NOT NULL, -- 신고내용
    report_date        DATETIME NOT NULL DEFAULT now(), -- 신고일시
    report_result_id   INTEGER  NULL,     -- 신고처리유형번호
    result_content     TEXT     NULL,     -- 처리내용
    report_category_id INTEGER  NOT NULL  -- 신고대상번호
);

-- 신고
ALTER TABLE report
    ADD CONSTRAINT PK_report -- 신고 기본키
    PRIMARY KEY (
    report_id -- 신고 번호
    );

ALTER TABLE report
    MODIFY COLUMN report_id INTEGER NOT NULL AUTO_INCREMENT;

-- 알림
CREATE TABLE alert (
    alert_id      INTEGER  NOT NULL, -- 알림 번호
    user_id       INTEGER  NOT NULL, -- 수신자 번호
    alert_content TEXT     NOT NULL, -- 알림 내용
    alert_date    DATETIME NOT NULL DEFAULT now(), -- 알림 날짜
    alert_read    TINYINT  NOT NULL DEFAULT 0 -- 읽기여부
);

-- 알림
ALTER TABLE alert
    ADD CONSTRAINT PK_alert -- 알림 기본키
    PRIMARY KEY (
    alert_id -- 알림 번호
    );

ALTER TABLE alert
    MODIFY COLUMN alert_id INTEGER NOT NULL AUTO_INCREMENT;

-- 추천
CREATE TABLE likes (
    board_id INTEGER NOT NULL, -- 게시글 번호
    user_id  INTEGER NOT NULL  -- 회원 번호
);

-- 추천
ALTER TABLE likes
    ADD CONSTRAINT PK_likes -- 추천 기본키
    PRIMARY KEY (
    board_id, -- 게시글 번호
    user_id   -- 회원 번호
    );

-- 게시글 태그
CREATE TABLE board_tag (
    tag_id   INTEGER      NOT NULL, -- 게시글태그번호
    board_id INTEGER      NOT NULL, -- 게시글 번호
    tag_name VARCHAR(255) NULL      -- 태그명
);

-- 게시글 태그
ALTER TABLE board_tag
    ADD CONSTRAINT PK_board_tag -- 게시글 태그 기본키
    PRIMARY KEY (
    tag_id -- 게시글태그번호
    );

ALTER TABLE board_tag
    MODIFY COLUMN tag_id INTEGER NOT NULL AUTO_INCREMENT;

-- 금지어
CREATE TABLE forbidden_word (
    word VARCHAR(255) NOT NULL -- 단어
);

-- 금지어
ALTER TABLE forbidden_word
    ADD CONSTRAINT PK_forbidden_word -- 금지어 기본키
    PRIMARY KEY (
    word -- 단어
    );

-- 구글
CREATE TABLE google (
    google_id   INTEGER     NOT NULL, -- SNS 번호
    google_name VARCHAR(50) NOT NULL  -- SNS 이름
);

-- 구글
ALTER TABLE google
    ADD CONSTRAINT PK_google -- 구글 기본키
    PRIMARY KEY (
    google_id -- SNS 번호
    );

-- 유형 대분류
CREATE TABLE maincategory (
    maincategory_id INTEGER     NOT NULL, -- 대분류 번호
    category_name   VARCHAR(50) NOT NULL  -- 분류명
);

-- 유형 대분류
ALTER TABLE maincategory
    ADD CONSTRAINT PK_maincategory -- 유형 대분류 기본키
    PRIMARY KEY (
    maincategory_id -- 대분류 번호
    );

-- 유형 대분류 유니크 인덱스
CREATE UNIQUE INDEX UIX_maincategory
    ON maincategory ( -- 유형 대분류
        category_name ASC -- 분류명
    );

ALTER TABLE maincategory
    MODIFY COLUMN maincategory_id INTEGER NOT NULL AUTO_INCREMENT;

-- 유형 소분류
CREATE TABLE subcategory (
    subcategory_id  INTEGER     NOT NULL, -- 소분류번호
    maincategory_id INTEGER     NOT NULL, -- 대분류 번호
    category_name   VARCHAR(50) NOT NULL  -- 분류명
);

-- 유형 소분류
ALTER TABLE subcategory
    ADD CONSTRAINT PK_subcategory -- 유형 소분류 기본키
    PRIMARY KEY (
    subcategory_id -- 소분류번호
    );

-- 유형 소분류 유니크 인덱스
CREATE UNIQUE INDEX UIX_subcategory
    ON subcategory ( -- 유형 소분류
        maincategory_id ASC, -- 대분류 번호
        category_name ASC    -- 분류명
    );

ALTER TABLE subcategory
    MODIFY COLUMN subcategory_id INTEGER NOT NULL AUTO_INCREMENT;

-- 수집품게시글
CREATE TABLE collection_board (
    board_id      INTEGER NOT NULL, -- 게시글 번호
    collection_id INTEGER NOT NULL  -- 수집품 번호
);

-- 수집품게시글
ALTER TABLE collection_board
    ADD CONSTRAINT PK_collection_board -- 수집품게시글 기본키
    PRIMARY KEY (
    board_id -- 게시글 번호
    );

ALTER TABLE collection_board
    MODIFY COLUMN board_id INTEGER NOT NULL AUTO_INCREMENT;

-- 수집품거래게시글
CREATE TABLE trade_board (
    board_id      INTEGER NOT NULL, -- 게시글 번호
    collection_id INTEGER NULL,     -- 수집품 번호
    price         INTEGER NOT NULL, -- 거래 가격
    status        TINYINT NOT NULL  -- 거래 상태
);

-- 수집품거래게시글
ALTER TABLE trade_board
    ADD CONSTRAINT PK_trade_board -- 수집품거래게시글 기본키
    PRIMARY KEY (
    board_id -- 게시글 번호
    );

ALTER TABLE trade_board
    MODIFY COLUMN board_id INTEGER NOT NULL AUTO_INCREMENT;

-- 신고처리유형
CREATE TABLE report_result_category (
    report_result_id INTEGER      NOT NULL, -- 신고처리유형번호
    result_name      VARCHAR(255) NOT NULL  -- 신고처리유형명
);

-- 신고처리유형
ALTER TABLE report_result_category
    ADD CONSTRAINT PK_report_result_category -- 신고처리유형 기본키
    PRIMARY KEY (
    report_result_id -- 신고처리유형번호
    );

ALTER TABLE report_result_category
    MODIFY COLUMN report_result_id INTEGER NOT NULL AUTO_INCREMENT;

-- 신고대상
CREATE TABLE report_category (
    report_category_id INTEGER      NOT NULL, -- 신고대상번호
    category_name      VARCHAR(255) NOT NULL  -- 신고대상
);

-- 신고대상
ALTER TABLE report_category
    ADD CONSTRAINT PK_report_category -- 신고대상 기본키
    PRIMARY KEY (
    report_category_id -- 신고대상번호
    );

ALTER TABLE report_category
    MODIFY COLUMN report_category_id INTEGER NOT NULL AUTO_INCREMENT;

-- 네이버
CREATE TABLE naver (
    naver_id   INTEGER     NOT NULL, -- SNS 번호
    naver_name VARCHAR(50) NOT NULL  -- SNS 이름
);

-- 네이버
ALTER TABLE naver
    ADD CONSTRAINT PK_naver -- 네이버 기본키
    PRIMARY KEY (
    naver_id -- SNS 번호
    );

-- 카카오
CREATE TABLE kakao (
    kakao_id   INTEGER     NOT NULL, -- SNS 번호
    kakao_name VARCHAR(50) NOT NULL  -- SNS 이름
);

-- 카카오
ALTER TABLE kakao
    ADD CONSTRAINT PK_kakao -- 카카오 기본키
    PRIMARY KEY (
    kakao_id -- SNS 번호
    );

-- 수집품 첨부파일
CREATE TABLE collection_photo (
    photo_id        INTEGER      NOT NULL, -- 수집품 첨부파일 번호
    collection_id   INTEGER      NOT NULL, -- 수집품 번호
    filename        VARCHAR(255) NOT NULL, -- 파일명
    origin_filename VARCHAR(255) NOT NULL  -- 원본파일명
);

-- 수집품 첨부파일
ALTER TABLE collection_photo
    ADD CONSTRAINT PK_collection_photo -- 수집품 첨부파일 기본키
    PRIMARY KEY (
    photo_id -- 수집품 첨부파일 번호
    );

ALTER TABLE collection_photo
    MODIFY COLUMN photo_id INTEGER NOT NULL AUTO_INCREMENT;

-- 게시글 첨부파일
CREATE TABLE board_photo (
    photo_id        INTEGER      NOT NULL, -- 게시글첨부파일 번호
    board_id        INTEGER      NOT NULL, -- 게시글 번호
    filename        VARCHAR(255) NOT NULL, -- 파일명
    origin_filename VARCHAR(255) NOT NULL  -- 원본파일명
);

-- 게시글 첨부파일
ALTER TABLE board_photo
    ADD CONSTRAINT PK_board_photo -- 게시글 첨부파일 기본키
    PRIMARY KEY (
    photo_id -- 게시글첨부파일 번호
    );

ALTER TABLE board_photo
    MODIFY COLUMN photo_id INTEGER NOT NULL AUTO_INCREMENT;

-- 수집품 상태
CREATE TABLE collection_status (
    status_id   INTEGER      NOT NULL, -- 수집품상태번호
    status_name VARCHAR(255) NOT NULL  -- 상태명
);

-- 수집품 상태
ALTER TABLE collection_status
    ADD CONSTRAINT PK_collection_status -- 수집품 상태 기본키
    PRIMARY KEY (
    status_id -- 수집품상태번호
    );

ALTER TABLE collection_status
    MODIFY COLUMN status_id INTEGER NOT NULL AUTO_INCREMENT;

-- 채팅방
CREATE TABLE chatroom (
    chatroom_id INTEGER NOT NULL, -- 채팅방번호
    board_id    INTEGER NOT NULL, -- 게시글 번호
    user_id     INTEGER NOT NULL  -- 참여자 번호
);

-- 채팅방
ALTER TABLE chatroom
    ADD CONSTRAINT PK_chatroom -- 채팅방 기본키
    PRIMARY KEY (
    chatroom_id -- 채팅방번호
    );

ALTER TABLE chatroom
    MODIFY COLUMN chatroom_id INTEGER NOT NULL AUTO_INCREMENT;

-- 회원
ALTER TABLE user
    ADD CONSTRAINT FK_google_TO_user -- 구글 -> 회원
    FOREIGN KEY (
    sns_id -- SNS 번호
    )
    REFERENCES google ( -- 구글
    google_id -- SNS 번호
    );

-- 회원
ALTER TABLE user
    ADD CONSTRAINT FK_naver_TO_user -- 네이버 -> 회원
    FOREIGN KEY (
    sns_id -- SNS 번호
    )
    REFERENCES naver ( -- 네이버
    naver_id -- SNS 번호
    );

-- 회원
ALTER TABLE user
    ADD CONSTRAINT FK_kakao_TO_user -- 카카오 -> 회원
    FOREIGN KEY (
    sns_id -- SNS 번호
    )
    REFERENCES kakao ( -- 카카오
    kakao_id -- SNS 번호
    );

-- 게시글
ALTER TABLE board
    ADD CONSTRAINT FK_user_TO_board -- 회원 -> 게시글
    FOREIGN KEY (
    user_id -- 작성자 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );

-- 댓글
ALTER TABLE commentRequest
    ADD CONSTRAINT FK_board_TO_comment -- 게시글 -> 댓글
    FOREIGN KEY (
    board_id -- 게시글 번호
    )
    REFERENCES board ( -- 게시글
    board_id -- 게시글 번호
    );

-- 댓글
ALTER TABLE commentRequest
    ADD CONSTRAINT FK_user_TO_comment -- 회원 -> 댓글
    FOREIGN KEY (
    user_id -- 작성자 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );

-- 채팅
ALTER TABLE chat
    ADD CONSTRAINT FK_user_TO_chat -- 회원 -> 채팅
    FOREIGN KEY (
    user_id -- 발신자 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );

-- 채팅
ALTER TABLE chat
    ADD CONSTRAINT FK_chatroom_TO_chat -- 채팅방 -> 채팅
    FOREIGN KEY (
    chatroom_id -- 채팅방번호
    )
    REFERENCES chatroom ( -- 채팅방
    chatroom_id -- 채팅방번호
    );

-- 회원 업적
ALTER TABLE user_achievement
    ADD CONSTRAINT FK_user_TO_user_achievement -- 회원 -> 회원 업적
    FOREIGN KEY (
    user_id -- 회원 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );

-- 회원 업적
ALTER TABLE user_achievement
    ADD CONSTRAINT FK_achievement_TO_user_achievement -- 업적 -> 회원 업적
    FOREIGN KEY (
    achievement_id -- 업적ID
    )
    REFERENCES achievement ( -- 업적
    achievement_id -- 업적ID
    );

-- 수집품
ALTER TABLE collection
    ADD CONSTRAINT FK_user_TO_collection -- 회원 -> 수집품
    FOREIGN KEY (
    user_id -- 소유자 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );

-- 수집품
ALTER TABLE collection
    ADD CONSTRAINT FK_subcategory_TO_collection -- 유형 소분류 -> 수집품
    FOREIGN KEY (
    subcategory_id -- 소분류번호
    )
    REFERENCES subcategory ( -- 유형 소분류
    subcategory_id -- 소분류번호
    );

-- 수집품
ALTER TABLE collection
    ADD CONSTRAINT FK_collection_status_TO_collection -- 수집품 상태 -> 수집품
    FOREIGN KEY (
    status_id -- 수집품상태번호
    )
    REFERENCES collection_status ( -- 수집품 상태
    status_id -- 수집품상태번호
    );

-- 신고
ALTER TABLE report
    ADD CONSTRAINT FK_user_TO_report -- 회원 -> 신고
    FOREIGN KEY (
    user_id -- 신고자 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );

-- 신고
ALTER TABLE report
    ADD CONSTRAINT FK_report_result_category_TO_report -- 신고처리유형 -> 신고
    FOREIGN KEY (
    report_result_id -- 신고처리유형번호
    )
    REFERENCES report_result_category ( -- 신고처리유형
    report_result_id -- 신고처리유형번호
    );

-- 신고
ALTER TABLE report
    ADD CONSTRAINT FK_report_category_TO_report -- 신고대상 -> 신고
    FOREIGN KEY (
    report_category_id -- 신고대상번호
    )
    REFERENCES report_category ( -- 신고대상
    report_category_id -- 신고대상번호
    );

-- 알림
ALTER TABLE alert
    ADD CONSTRAINT FK_user_TO_alert -- 회원 -> 알림
    FOREIGN KEY (
    user_id -- 수신자 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );

-- 추천
ALTER TABLE likes
    ADD CONSTRAINT FK_board_TO_likes -- 게시글 -> 추천
    FOREIGN KEY (
    board_id -- 게시글 번호
    )
    REFERENCES board ( -- 게시글
    board_id -- 게시글 번호
    );

-- 추천
ALTER TABLE likes
    ADD CONSTRAINT FK_user_TO_likes -- 회원 -> 추천
    FOREIGN KEY (
    user_id -- 회원 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );

-- 게시글 태그
ALTER TABLE board_tag
    ADD CONSTRAINT FK_board_TO_board_tag -- 게시글 -> 게시글 태그
    FOREIGN KEY (
    board_id -- 게시글 번호
    )
    REFERENCES board ( -- 게시글
    board_id -- 게시글 번호
    );

-- 유형 소분류
ALTER TABLE subcategory
    ADD CONSTRAINT FK_maincategory_TO_subcategory -- 유형 대분류 -> 유형 소분류
    FOREIGN KEY (
    maincategory_id -- 대분류 번호
    )
    REFERENCES maincategory ( -- 유형 대분류
    maincategory_id -- 대분류 번호
    );

-- 수집품게시글
ALTER TABLE collection_board
    ADD CONSTRAINT FK_collection_TO_collection_board -- 수집품 -> 수집품게시글
    FOREIGN KEY (
    collection_id -- 수집품 번호
    )
    REFERENCES collection ( -- 수집품
    collection_id -- 수집품 번호
    );

-- 수집품게시글
ALTER TABLE collection_board
    ADD CONSTRAINT FK_board_TO_collection_board -- 게시글 -> 수집품게시글
    FOREIGN KEY (
    board_id -- 게시글 번호
    )
    REFERENCES board ( -- 게시글
    board_id -- 게시글 번호
    );

-- 수집품거래게시글
ALTER TABLE trade_board
    ADD CONSTRAINT FK_board_TO_trade_board -- 게시글 -> 수집품거래게시글
    FOREIGN KEY (
    board_id -- 게시글 번호
    )
    REFERENCES board ( -- 게시글
    board_id -- 게시글 번호
    );

-- 수집품거래게시글
ALTER TABLE trade_board
    ADD CONSTRAINT FK_collection_TO_trade_board -- 수집품 -> 수집품거래게시글
    FOREIGN KEY (
    collection_id -- 수집품 번호
    )
    REFERENCES collection ( -- 수집품
    collection_id -- 수집품 번호
    );

-- 수집품 첨부파일
ALTER TABLE collection_photo
    ADD CONSTRAINT FK_collection_TO_collection_photo -- 수집품 -> 수집품 첨부파일
    FOREIGN KEY (
    collection_id -- 수집품 번호
    )
    REFERENCES collection ( -- 수집품
    collection_id -- 수집품 번호
    );

-- 게시글 첨부파일
ALTER TABLE board_photo
    ADD CONSTRAINT FK_board_TO_board_photo -- 게시글 -> 게시글 첨부파일
    FOREIGN KEY (
    board_id -- 게시글 번호
    )
    REFERENCES board ( -- 게시글
    board_id -- 게시글 번호
    );

-- 채팅방
ALTER TABLE chatroom
    ADD CONSTRAINT FK_board_TO_chatroom -- 게시글 -> 채팅방
    FOREIGN KEY (
    board_id -- 게시글 번호
    )
    REFERENCES board ( -- 게시글
    board_id -- 게시글 번호
    );

-- 채팅방
ALTER TABLE chatroom
    ADD CONSTRAINT FK_user_TO_chatroom -- 회원 -> 채팅방
    FOREIGN KEY (
    user_id -- 참여자 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );