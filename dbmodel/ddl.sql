-- 회원
DROP TABLE IF EXISTS user RESTRICT;

-- 게시글
DROP TABLE IF EXISTS board RESTRICT;

-- 댓글
DROP TABLE IF EXISTS comment RESTRICT;

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

-- 신고항목
DROP TABLE IF EXISTS report_category RESTRICT;

-- 수집품 첨부파일
DROP TABLE IF EXISTS collection_photo RESTRICT;

-- 게시글 첨부파일
DROP TABLE IF EXISTS board_photo RESTRICT;

-- 수집품 상태
DROP TABLE IF EXISTS collection_status RESTRICT;

-- 채팅방
DROP TABLE IF EXISTS chatroom RESTRICT;

-- 정지회원
DROP TABLE IF EXISTS user_restricted RESTRICT;

-- 유저SNS
DROP TABLE IF EXISTS user_sns RESTRICT;

-- 회원
CREATE TABLE user (
    user_id    INTEGER      NOT NULL COMMENT '회원 번호', -- 회원 번호
    photo      VARCHAR(255) NULL     COMMENT '프로필 사진', -- 프로필 사진
    email      VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
    nickname   VARCHAR(50)  NOT NULL COMMENT '닉네임', -- 닉네임
    password   VARCHAR(255) NOT NULL COMMENT '비밀번호', -- 비밀번호
    start_date DATETIME     NOT NULL DEFAULT now() COMMENT '가입일자', -- 가입일자
    end_date   DATE         NULL     COMMENT '탈퇴일자', -- 탈퇴일자
    last_login DATETIME     NULL     COMMENT '마지막로그인', -- 마지막로그인
    admin      TINYINT      NOT NULL DEFAULT 0 COMMENT '관리자 여부' -- 관리자 여부
)
COMMENT '회원';

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
    MODIFY COLUMN user_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원 번호';

-- 게시글
CREATE TABLE board (
    board_id   INTEGER      NOT NULL COMMENT '게시글 번호', -- 게시글 번호
    title      VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
    content    TEXT         NOT NULL COMMENT '내용', -- 내용
    user_id    INTEGER      NOT NULL COMMENT '작성자 번호', -- 작성자 번호
    post_date  DATETIME     NOT NULL DEFAULT now() COMMENT '작성일자', -- 작성일자
    view_count INTEGER      NOT NULL DEFAULT 0 COMMENT '조회수', -- 조회수
    is_public  TINYINT      NOT NULL DEFAULT 1 COMMENT '공개', -- 공개
    is_deleted TINYINT      NOT NULL DEFAULT 0 COMMENT '삭제', -- 삭제
    board_type VARCHAR(255) NOT NULL COMMENT '구분' -- 구분
)
COMMENT '게시글';

-- 게시글
ALTER TABLE board
    ADD CONSTRAINT PK_board -- 게시글 기본키
    PRIMARY KEY (
    board_id -- 게시글 번호
    );

ALTER TABLE board
    MODIFY COLUMN board_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시글 번호';

-- 댓글
CREATE TABLE comment (
    comment_id          INTEGER  NOT NULL COMMENT '댓글 번호', -- 댓글 번호
    user_id             INTEGER  NOT NULL COMMENT '작성자 번호', -- 작성자 번호
    board_id            INTEGER  NOT NULL COMMENT '게시글 번호', -- 게시글 번호
    content             TEXT     NOT NULL COMMENT '작성내용', -- 작성내용
    date                DATETIME NOT NULL DEFAULT now() COMMENT '작성일시', -- 작성일시
    original_comment_id INTEGER  NULL     COMMENT '상위 댓글 번호', -- 상위 댓글 번호
    is_public           TINYINT  NOT NULL DEFAULT 1 COMMENT '공개' -- 공개
)
COMMENT '댓글';

-- 댓글
ALTER TABLE comment
    ADD CONSTRAINT PK_comment -- 댓글 기본키
    PRIMARY KEY (
    comment_id -- 댓글 번호
    );

ALTER TABLE comment
    MODIFY COLUMN comment_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '댓글 번호';

-- 업적
CREATE TABLE achievement (
    achievement_id        VARCHAR(50)  NOT NULL COMMENT '업적ID', -- 업적ID
    name                  VARCHAR(50)  NOT NULL COMMENT '업적명', -- 업적명
    content               TEXT         NOT NULL COMMENT '업적 설명', -- 업적 설명
    photo                 VARCHAR(255) NOT NULL COMMENT '업적 이미지', -- 업적 이미지
    acquisition_condition TEXT         NOT NULL COMMENT '취득 조건', -- 취득 조건
    location              TEXT         NULL     COMMENT '취득 장소', -- 취득 장소
    max_count             INTEGER      NULL     COMMENT '총횟수', -- 총횟수
    score                 INTEGER      NOT NULL COMMENT '업적 점수' -- 업적 점수
)
COMMENT '업적';

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
    chat_id     INTEGER  NOT NULL COMMENT '채팅번호', -- 채팅번호
    chatroom_id INTEGER  NOT NULL COMMENT '채팅방번호', -- 채팅방번호
    user_id     INTEGER  NOT NULL COMMENT '발신자 번호', -- 발신자 번호
    message     TEXT     NOT NULL COMMENT '대화내용', -- 대화내용
    chat_date   DATETIME NOT NULL DEFAULT now() COMMENT '작성일', -- 작성일
    chat_read   TINYINT  NOT NULL DEFAULT 0 COMMENT '읽기여부' -- 읽기여부
)
COMMENT '채팅';

-- 채팅
ALTER TABLE chat
    ADD CONSTRAINT PK_chat -- 채팅 기본키
    PRIMARY KEY (
    chat_id -- 채팅번호
    );

ALTER TABLE chat
    MODIFY COLUMN chat_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '채팅번호';

-- 회원 업적
CREATE TABLE user_achievement (
    user_id        INTEGER     NOT NULL COMMENT '회원 번호', -- 회원 번호
    achievement_id VARCHAR(50) NOT NULL COMMENT '업적ID', -- 업적ID
    progress       INTEGER     NOT NULL DEFAULT 0 COMMENT '진행도', -- 진행도
    get_date       DATETIME    NULL     COMMENT '취득일자', -- 취득일자
    is_primary     TINYINT     NULL     DEFAULT 0 COMMENT '대표업적', -- 대표업적
    current_count  INTEGER     NOT NULL DEFAULT 0 COMMENT '현재횟수' -- 현재횟수
)
COMMENT '회원 업적';

-- 회원 업적
ALTER TABLE user_achievement
    ADD CONSTRAINT PK_user_achievement -- 회원 업적 기본키
    PRIMARY KEY (
    user_id,        -- 회원 번호
    achievement_id  -- 업적ID
    );

-- 수집품
CREATE TABLE collection (
    collection_id    INTEGER      NOT NULL COMMENT '수집품 번호', -- 수집품 번호
    user_id          INTEGER      NOT NULL COMMENT '소유자 번호', -- 소유자 번호
    subcategory_id   INTEGER      NOT NULL COMMENT '소분류번호', -- 소분류번호
    other_category   VARCHAR(50)  NULL     COMMENT '기타 분류명', -- 기타 분류명
    name             VARCHAR(50)  NOT NULL COMMENT '제품명', -- 제품명
    status_id        INTEGER      NULL     COMMENT '수집품상태번호', -- 수집품상태번호
    purchase_date    DATE         NULL     COMMENT '구매일자', -- 구매일자
    purchase_place   VARCHAR(255) NULL     COMMENT '구매처', -- 구매처
    price            INTEGER      NULL     DEFAULT 0 COMMENT '가격', -- 가격
    storage_location VARCHAR(255) NULL     COMMENT '보관장소', -- 보관장소
    post_date        DATETIME     NOT NULL DEFAULT now() COMMENT '등록일시' -- 등록일시
)
COMMENT '수집품';

-- 수집품
ALTER TABLE collection
    ADD CONSTRAINT PK_collection -- 수집품 기본키
    PRIMARY KEY (
    collection_id -- 수집품 번호
    );

ALTER TABLE collection
    MODIFY COLUMN collection_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '수집품 번호';

-- 신고
CREATE TABLE report (
    report_id          INTEGER  NOT NULL COMMENT '신고 번호', -- 신고 번호
    user_id            INTEGER  NOT NULL COMMENT '신고자 번호', -- 신고자 번호
    report_category_id INTEGER  NOT NULL COMMENT '신고항목번호', -- 신고항목번호
    report_content     TEXT     NOT NULL COMMENT '신고내용', -- 신고내용
    report_date        DATETIME NOT NULL DEFAULT now() COMMENT '신고일시', -- 신고일시
    report_result_id   INTEGER  NULL     COMMENT '신고처리유형번호', -- 신고처리유형번호
    result_content     TEXT     NULL     COMMENT '처리내용' -- 처리내용
)
COMMENT '신고';

-- 신고
ALTER TABLE report
    ADD CONSTRAINT PK_report -- 신고 기본키
    PRIMARY KEY (
    report_id -- 신고 번호
    );

ALTER TABLE report
    MODIFY COLUMN report_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '신고 번호';

-- 알림
CREATE TABLE alert (
    alert_id          INTEGER      NOT NULL COMMENT '알림 번호', -- 알림 번호
    user_id           INTEGER      NOT NULL COMMENT '수신자 번호', -- 수신자 번호
    alert_content     TEXT         NOT NULL COMMENT '알림 내용', -- 알림 내용
    alert_date        DATETIME     NOT NULL DEFAULT now() COMMENT '알림 날짜', -- 알림 날짜
    alert_read        TINYINT      NOT NULL DEFAULT 0 COMMENT '읽기여부', -- 읽기여부
    alert_category    VARCHAR(255) NULL     COMMENT '구분', -- 구분
    alert_category_id VARCHAR(50)  NULL     COMMENT '구분번호' -- 구분번호
)
COMMENT '알림';

-- 알림
ALTER TABLE alert
    ADD CONSTRAINT PK_alert -- 알림 기본키
    PRIMARY KEY (
    alert_id -- 알림 번호
    );

ALTER TABLE alert
    MODIFY COLUMN alert_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '알림 번호';

-- 추천
CREATE TABLE likes (
    board_id INTEGER NOT NULL COMMENT '게시글 번호', -- 게시글 번호
    user_id  INTEGER NOT NULL COMMENT '회원 번호' -- 회원 번호
)
COMMENT '추천';

-- 추천
ALTER TABLE likes
    ADD CONSTRAINT PK_likes -- 추천 기본키
    PRIMARY KEY (
    board_id, -- 게시글 번호
    user_id   -- 회원 번호
    );

-- 게시글 태그
CREATE TABLE board_tag (
    tag_id   INTEGER      NOT NULL COMMENT '게시글태그번호', -- 게시글태그번호
    board_id INTEGER      NOT NULL COMMENT '게시글 번호', -- 게시글 번호
    tag_name VARCHAR(255) NULL     COMMENT '태그명' -- 태그명
)
COMMENT '게시글 태그';

-- 게시글 태그
ALTER TABLE board_tag
    ADD CONSTRAINT PK_board_tag -- 게시글 태그 기본키
    PRIMARY KEY (
    tag_id -- 게시글태그번호
    );

ALTER TABLE board_tag
    MODIFY COLUMN tag_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시글태그번호';

-- 금지어
CREATE TABLE forbidden_word (
    word VARCHAR(255) NOT NULL COMMENT '단어' -- 단어
)
COMMENT '금지어';

-- 금지어
ALTER TABLE forbidden_word
    ADD CONSTRAINT PK_forbidden_word -- 금지어 기본키
    PRIMARY KEY (
    word -- 단어
    );

-- 유형 대분류
CREATE TABLE maincategory (
    maincategory_id INTEGER     NOT NULL COMMENT '대분류 번호', -- 대분류 번호
    category_name   VARCHAR(50) NOT NULL COMMENT '분류명' -- 분류명
)
COMMENT '유형 대분류';

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
    MODIFY COLUMN maincategory_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '대분류 번호';

-- 유형 소분류
CREATE TABLE subcategory (
    subcategory_id  INTEGER     NOT NULL COMMENT '소분류번호', -- 소분류번호
    maincategory_id INTEGER     NOT NULL COMMENT '대분류 번호', -- 대분류 번호
    category_name   VARCHAR(50) NOT NULL COMMENT '분류명' -- 분류명
)
COMMENT '유형 소분류';

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
    MODIFY COLUMN subcategory_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '소분류번호';

-- 수집품게시글
CREATE TABLE collection_board (
    board_id      INTEGER NOT NULL COMMENT '게시글 번호', -- 게시글 번호
    collection_id INTEGER NOT NULL COMMENT '수집품 번호' -- 수집품 번호
)
COMMENT '수집품게시글';

-- 수집품게시글
ALTER TABLE collection_board
    ADD CONSTRAINT PK_collection_board -- 수집품게시글 기본키
    PRIMARY KEY (
    board_id -- 게시글 번호
    );

ALTER TABLE collection_board
    MODIFY COLUMN board_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시글 번호';

-- 수집품거래게시글
CREATE TABLE trade_board (
    board_id      INTEGER      NOT NULL COMMENT '게시글 번호', -- 게시글 번호
    collection_id INTEGER      NULL     COMMENT '수집품 번호', -- 수집품 번호
    price         INTEGER      NOT NULL COMMENT '거래 가격', -- 거래 가격
    status        TINYINT      NOT NULL DEFAULT 0 COMMENT '거래 상태', -- 거래 상태
    trade_type    VARCHAR(50)  NOT NULL COMMENT '거래 종류' -- 거래 종류
)
COMMENT '수집품거래게시글';

-- 수집품거래게시글
ALTER TABLE trade_board
    ADD CONSTRAINT PK_trade_board -- 수집품거래게시글 기본키
    PRIMARY KEY (
    board_id -- 게시글 번호
    );

ALTER TABLE trade_board
    MODIFY COLUMN board_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시글 번호';

-- 신고처리유형
CREATE TABLE report_result_category (
    report_result_id INTEGER      NOT NULL COMMENT '신고처리유형번호', -- 신고처리유형번호
    result_name      VARCHAR(255) NOT NULL COMMENT '신고처리유형명' -- 신고처리유형명
)
COMMENT '신고처리유형';

-- 신고처리유형
ALTER TABLE report_result_category
    ADD CONSTRAINT PK_report_result_category -- 신고처리유형 기본키
    PRIMARY KEY (
    report_result_id -- 신고처리유형번호
    );

ALTER TABLE report_result_category
    MODIFY COLUMN report_result_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '신고처리유형번호';

-- 신고항목
CREATE TABLE report_category (
    report_category_id INTEGER      NOT NULL COMMENT '신고항목번호', -- 신고항목번호
    category_name      VARCHAR(255) NOT NULL COMMENT '신고항목' -- 신고항목
)
COMMENT '신고항목';

-- 신고항목
ALTER TABLE report_category
    ADD CONSTRAINT PK_report_category -- 신고항목 기본키
    PRIMARY KEY (
    report_category_id -- 신고항목번호
    );

ALTER TABLE report_category
    MODIFY COLUMN report_category_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '신고항목번호';

-- 수집품 첨부파일
CREATE TABLE collection_photo (
    photo_id        INTEGER      NOT NULL COMMENT '수집품 첨부파일 번호', -- 수집품 첨부파일 번호
    collection_id   INTEGER      NOT NULL COMMENT '수집품 번호', -- 수집품 번호
    filename        VARCHAR(255) NOT NULL COMMENT '파일명', -- 파일명
    origin_filename VARCHAR(255) NOT NULL COMMENT '원본파일명' -- 원본파일명
)
COMMENT '수집품 첨부파일';

-- 수집품 첨부파일
ALTER TABLE collection_photo
    ADD CONSTRAINT PK_collection_photo -- 수집품 첨부파일 기본키
    PRIMARY KEY (
    photo_id -- 수집품 첨부파일 번호
    );

ALTER TABLE collection_photo
    MODIFY COLUMN photo_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '수집품 첨부파일 번호';

-- 게시글 첨부파일
CREATE TABLE board_photo (
    photo_id        INTEGER      NOT NULL COMMENT '게시글첨부파일 번호', -- 게시글첨부파일 번호
    board_id        INTEGER      NOT NULL COMMENT '게시글 번호', -- 게시글 번호
    filename        VARCHAR(255) NOT NULL COMMENT '파일명', -- 파일명
    origin_filename VARCHAR(255) NOT NULL COMMENT '원본파일명' -- 원본파일명
)
COMMENT '게시글 첨부파일';

-- 게시글 첨부파일
ALTER TABLE board_photo
    ADD CONSTRAINT PK_board_photo -- 게시글 첨부파일 기본키
    PRIMARY KEY (
    photo_id -- 게시글첨부파일 번호
    );

ALTER TABLE board_photo
    MODIFY COLUMN photo_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시글첨부파일 번호';

-- 수집품 상태
CREATE TABLE collection_status (
    status_id   INTEGER      NOT NULL COMMENT '수집품상태번호', -- 수집품상태번호
    status_name VARCHAR(255) NOT NULL COMMENT '상태명' -- 상태명
)
COMMENT '수집품 상태';

-- 수집품 상태
ALTER TABLE collection_status
    ADD CONSTRAINT PK_collection_status -- 수집품 상태 기본키
    PRIMARY KEY (
    status_id -- 수집품상태번호
    );

ALTER TABLE collection_status
    MODIFY COLUMN status_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '수집품상태번호';

-- 채팅방
CREATE TABLE chatroom (
    chatroom_id INTEGER NOT NULL COMMENT '채팅방번호', -- 채팅방번호
    board_id    INTEGER NOT NULL COMMENT '게시글 번호', -- 게시글 번호
    user_id     INTEGER NOT NULL COMMENT '참여자 번호' -- 참여자 번호
)
COMMENT '채팅방';

-- 채팅방
ALTER TABLE chatroom
    ADD CONSTRAINT PK_chatroom -- 채팅방 기본키
    PRIMARY KEY (
    chatroom_id -- 채팅방번호
    );

-- 채팅방 유니크 인덱스
CREATE UNIQUE INDEX UIX_chatroom
    ON chatroom ( -- 채팅방
        board_id ASC, -- 게시글 번호
        user_id ASC   -- 참여자 번호
    );

ALTER TABLE chatroom
    MODIFY COLUMN chatroom_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '채팅방번호';

-- 정지회원
CREATE TABLE user_restricted (
    user_id    INTEGER  NOT NULL COMMENT '회원 번호', -- 회원 번호
    start_date DATETIME NOT NULL COMMENT '시작일자', -- 시작일자
    end_date   DATETIME NOT NULL COMMENT '종료일자', -- 종료일자
    comment    TEXT     NOT NULL COMMENT '제재사유'  -- 제재사유
)
COMMENT '정지회원';

-- 유저SNS
CREATE TABLE user_sns (
    sns_account_id   INTEGER     NOT NULL COMMENT '회원 SNS 번호', -- 회원 SNS 번호
    provider         VARCHAR(50) NOT NULL COMMENT '연동된 SNS', -- 연동된 SNS
    provider_user_id VARCHAR(50) NOT NULL COMMENT '제공된 사용자ID', -- 제공된 사용자ID
    user_id          INTEGER     NOT NULL COMMENT '회원 번호' -- 회원 번호
)
COMMENT '유저SNS';

-- 유저SNS
ALTER TABLE user_sns
    ADD CONSTRAINT PK_user_sns -- 유저SNS 기본키
    PRIMARY KEY (
    sns_account_id -- 회원 SNS 번호
    );

-- 유저SNS 유니크 인덱스
CREATE UNIQUE INDEX UIX_user_sns
    ON user_sns ( -- 유저SNS
        provider ASC,         -- 연동된 SNS
        provider_user_id ASC  -- 제공된 사용자ID
    );

ALTER TABLE user_sns
    MODIFY COLUMN sns_account_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원 SNS 번호';

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
ALTER TABLE comment
    ADD CONSTRAINT FK_board_TO_comment -- 게시글 -> 댓글
    FOREIGN KEY (
    board_id -- 게시글 번호
    )
    REFERENCES board ( -- 게시글
    board_id -- 게시글 번호
    );

-- 댓글
ALTER TABLE comment
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
    ADD CONSTRAINT FK_report_category_TO_report -- 신고항목 -> 신고
    FOREIGN KEY (
    report_category_id -- 신고항목번호
    )
    REFERENCES report_category ( -- 신고항목
    report_category_id -- 신고항목번호
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

-- 정지회원
ALTER TABLE user_restricted
    ADD CONSTRAINT FK_user_TO_user_restricted -- 회원 -> 정지회원
    FOREIGN KEY (
    user_id -- 회원 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );

-- 유저SNS
ALTER TABLE user_sns
    ADD CONSTRAINT FK_user_TO_user_sns -- 회원 -> 유저SNS
    FOREIGN KEY (
    user_id -- 회원 번호
    )
    REFERENCES user ( -- 회원
    user_id -- 회원 번호
    );