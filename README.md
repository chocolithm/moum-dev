![moum](https://github.com/user-attachments/assets/b22aa708-e791-4c17-8151-27edb7f4dd58)
# 나만의 컬렉션 북 수집품 저장소, 모음

### 소개
**개발기간** : 2024-09-06(금) ~ 2024-12-02(월)  
**캐치프레이즈** : 모으는 즐거움, 모음으로부터  
**발표자료** : https://1drv.ms/p/s!Asad0zibIBicikUbIypvONh4avO7?e=poq9vJ  
**발표영상** : https://www.youtube.com/watch?v=eKirps0OGmg&t=1607s


### 배경 및 목적
- 중고거래 업체의 분석에 따르면, 적극적으로 취미를 즐기고 소위 덕질을 하는 "디깅족"들이 중고거래 시장을 이끌고 있다고 한다.
- 실제로 취미/키덜트 상품의 거래건수가 상위 3건 안에 포함되는 등 상승세를 보이고 있지만, 해외와 달리 국내에는 수집품만을 대상으로 하는 온라인 플랫폼이 부재한 상태이다.
- 수집가들은 온라인 카페나 블로그, 중고거래 플랫폼 등에서 소규모로 활동하여, 수집가들만을 위한 커뮤니티의 규모가 작고 정보가 산발적으로 공유되고 있다.
- 따라 이름 그대로 “모으다”라는 뜻을 담아, 오프라인에 흩어져 있는 나만의 소중한 수집품을 한 데 모아서 관리하고 공유할 수 있는 플랫폼을 제공하고자 한다.



### 인원 🧑‍🎓

|강윤상|권기윤|김재우|양지윤|이우성|
|:---:|:---:|:---:|:---:|:---:|
|![강윤상](https://github.com/user-attachments/assets/6545033e-b5ca-49e3-ac4e-bd46232b8feb)|![권기윤](https://github.com/user-attachments/assets/44cb38ac-a51e-4c7c-89be-1798e21a0fa0)|![김재우](https://github.com/user-attachments/assets/e6de6fba-b6fa-4890-9e36-06464fd350c1)|![양지윤](https://github.com/user-attachments/assets/1becc7c8-be23-40cd-8479-0d2b9d37af42)|![이우성](https://github.com/user-attachments/assets/c34adb34-6b89-4a62-8299-ecb5b352fa93)|
|강윤상 [<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/>](https://github.com/chocolithm)|권기윤 [<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/>](https://github.com/Homez96)|김재우 [<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/>](https://github.com/stereokim123)|양지윤 [<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/>](https://github.com/j1yunn)|이우성 [<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/>](https://github.com/Nari-Lee)
|마이홈 및 수집품<br>알림 및 채팅<br>관리자 및 신고<br> 인프라 구성|업적<br>하비위키|전시하기 게시판<br>거래 게시판 <br>카테고리 필터 및 검색 기능|화면 기획<br>디자인 & 이미지 제작<br>업적 취득조건|프론트엔드<br>게임<br>회원 가입 및 로그인<br>간편 로그인 회원<br>수정/삭제|


<br>

## 목차
1. [기획 및 설계](#1-기획-및-설계)
2. [기술 스택](#2-기술-스택)
3. [프로젝트 구조](#3-프로젝트-구조)
4. [주요 기능](#4-주요-기능)
5. [화면구성](#5-화면-구성)
6. [배포 및 시스템 구조](#6-배포-및-시스템-구조)
7. [트러블슈팅](#7-트러블슈팅)

  
<br>

## 1. 기획 및 설계
#### 화면 
<img src="README_images/ui_prototype.png" style="width: 50%">

Figma를 통해 UI Prototype을 작성함으로써 기능 흐름을 시각적으로 확인하고 팀원간 디자인 방향 공유

#### Use Case 작성
<p>
  <img src="README_images/actor.png" style="width: 45%; vertical-align: middle;">
  <img src="README_images/use_case.png" style="width: 45%; vertical-align: middle;">
</p>


Figma 기반으로 UseCase를 작성하여 Actor를 식별하고, 사용자의 요구사항 및 시스템과의 상호작용을 확인

#### DB Modeling
<img src="README_images/erd.png" style="width: 50%;">

Exerd 기반으로 DB Modeling 진행하여 데이터 구조 및 관계 설정

#### 커뮤니케이션
<p>
  <img src="README_images/notion.png" style="width: 45%;">
  <img src="README_images/slack.png" style="width: 43%;">
</p>

Notion을 통해 팀원 각자의 작업사항 및 회의록 공유<br>
github - slack 연동으로 push 알림 구성


<br>

## 2. 기술 스택

### Environment
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)

### Design
![Adobe Photoshop](https://img.shields.io/badge/adobe%20photoshop-%2331A8FF.svg?style=for-the-badge&logo=adobe%20photoshop&logoColor=white)
![Canva](https://img.shields.io/badge/Canva-%2300C4CC.svg?style=for-the-badge&logo=Canva&logoColor=white)
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)

### Development
#### Frontend
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![jQuery](https://img.shields.io/badge/jquery-%230769AD.svg?style=for-the-badge&logo=jquery&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)

#### Backend
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Python](https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white)
![OAuth2](https://img.shields.io/badge/OAuth2-%230092FE.svg?style=for-the-badge&logo=oauth&logoColor=white)
![MyBatis](https://img.shields.io/badge/mybatis-ED8B00?style=for-the-badge&logo=mybatis&logoColor=white)

#### Database
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Exerd](https://img.shields.io/badge/Exerd-%230055CC.svg?style=for-the-badge&logoColor=white)

### DevOps
![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white)
![Naver Cloud](https://img.shields.io/badge/Naver%20Cloud-%2300C73C.svg?style=for-the-badge&logo=naver&logoColor=white)
![Application Load Balancer (ALB)](https://img.shields.io/badge/ALB-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)

### Collaboration
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)


<br>

## 3. 프로젝트 구조
<details>
  <summary>프로젝트 구조</summary>
  <pre>
  📦main
  ┣ 📂java
  ┃ ┣ 📂moum
  ┃ ┃ ┣ 📂project
  ┃ ┃ ┃ ┣ 📂config
  ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetails.java
  ┃ ┃ ┃ ┃ ┣ 📜MailConfig.java
  ┃ ┃ ┃ ┃ ┣ 📜MyBatisConfig.java
  ┃ ┃ ┃ ┃ ┣ 📜SecurityConfig.java
  ┃ ┃ ┃ ┃ ┗ 📜WebSocketConfig.java
  ┃ ┃ ┃ ┣ 📂controller
  ┃ ┃ ┃ ┃ ┣ 📜AchievementController.java
  ┃ ┃ ┃ ┃ ┣ 📜AdminController.java
  ┃ ┃ ┃ ┃ ┣ 📜AlertController.java
  ┃ ┃ ┃ ┃ ┣ 📜AuthController.java
  ┃ ┃ ┃ ┃ ┣ 📜BoardController.java
  ┃ ┃ ┃ ┃ ┣ 📜ChatController.java
  ┃ ┃ ┃ ┃ ┣ 📜CollectionController.java
  ┃ ┃ ┃ ┃ ┣ 📜CommentController.java
  ┃ ┃ ┃ ┃ ┣ 📜ErrorController.java
  ┃ ┃ ┃ ┃ ┣ 📜HomeController.java
  ┃ ┃ ┃ ┃ ┣ 📜LikesController.java
  ┃ ┃ ┃ ┃ ┣ 📜MailController.java
  ┃ ┃ ┃ ┃ ┣ 📜ReportController.java
  ┃ ┃ ┃ ┃ ┣ 📜SocketController.java
  ┃ ┃ ┃ ┃ ┗ 📜UserController.java
  ┃ ┃ ┃ ┣ 📂dao
  ┃ ┃ ┃ ┃ ┣ 📜AchievementDao.java
  ┃ ┃ ┃ ┃ ┣ 📜AlertDao.java
  ┃ ┃ ┃ ┃ ┣ 📜BoardDao.java
  ┃ ┃ ┃ ┃ ┣ 📜ChatDao.java
  ┃ ┃ ┃ ┃ ┣ 📜CollectionCategoryDao.java
  ┃ ┃ ┃ ┃ ┣ 📜CollectionDao.java
  ┃ ┃ ┃ ┃ ┣ 📜CollectionStatusDao.java
  ┃ ┃ ┃ ┃ ┣ 📜CommentDao.java
  ┃ ┃ ┃ ┃ ┣ 📜LikesDao.java
  ┃ ┃ ┃ ┃ ┣ 📜ReportDao.java
  ┃ ┃ ┃ ┃ ┣ 📜UserDao.java
  ┃ ┃ ┃ ┃ ┗ 📜UserSnsDao.java
  ┃ ┃ ┃ ┣ 📂dto
  ┃ ┃ ┃ ┃ ┗ 📜MailDTO.java
  ┃ ┃ ┃ ┣ 📂paging
  ┃ ┃ ┃ ┃ ┗ 📜RequestList.java
  ┃ ┃ ┃ ┣ 📂service
  ┃ ┃ ┃ ┃ ┣ 📜AchievementService.java
  ┃ ┃ ┃ ┃ ┣ 📜AlertService.java
  ┃ ┃ ┃ ┃ ┣ 📜BoardService.java
  ┃ ┃ ┃ ┃ ┣ 📜ChatKafkaConsumer.java
  ┃ ┃ ┃ ┃ ┣ 📜ChatKafkaProducer.java
  ┃ ┃ ┃ ┃ ┣ 📜ChatService.java
  ┃ ┃ ┃ ┃ ┣ 📜CollectionCategoryService.java
  ┃ ┃ ┃ ┃ ┣ 📜CollectionService.java
  ┃ ┃ ┃ ┃ ┣ 📜CollectionStatusService.java
  ┃ ┃ ┃ ┃ ┣ 📜CommentService.java
  ┃ ┃ ┃ ┃ ┣ 📜CustomOAuth2UserService.java
  ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetailsService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultAchievementService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultAlertService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultBoardService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultChatService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultCollectionCategoryService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultCollectionService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultCollectionStatusService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultLikesService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultReportService.java
  ┃ ┃ ┃ ┃ ┣ 📜DefaultUserService.java
  ┃ ┃ ┃ ┃ ┣ 📜KafkaToWebSocketService.java
  ┃ ┃ ┃ ┃ ┣ 📜LikesService.java
  ┃ ┃ ┃ ┃ ┣ 📜MailService.java
  ┃ ┃ ┃ ┃ ┣ 📜NcpObjectStorageService.java
  ┃ ┃ ┃ ┃ ┣ 📜OAuth2Service.java
  ┃ ┃ ┃ ┃ ┣ 📜ReportService.java
  ┃ ┃ ┃ ┃ ┣ 📜StorageService.java
  ┃ ┃ ┃ ┃ ┗ 📜UserService.java
  ┃ ┃ ┃ ┣ 📂vo
  ┃ ┃ ┃ ┃ ┣ 📜Achievement.java
  ┃ ┃ ┃ ┃ ┣ 📜Alert.java
  ┃ ┃ ┃ ┃ ┣ 📜AttachedFile.java
  ┃ ┃ ┃ ┃ ┣ 📜Board.java
  ┃ ┃ ┃ ┃ ┣ 📜Chat.java
  ┃ ┃ ┃ ┃ ┣ 📜Chatroom.java
  ┃ ┃ ┃ ┃ ┣ 📜Collection.java
  ┃ ┃ ┃ ┃ ┣ 📜CollectionStatus.java
  ┃ ┃ ┃ ┃ ┣ 📜CommentRequest.java
  ┃ ┃ ┃ ┃ ┣ 📜CommentResponse.java
  ┃ ┃ ┃ ┃ ┣ 📜Likes.java
  ┃ ┃ ┃ ┃ ┣ 📜Maincategory.java
  ┃ ┃ ┃ ┃ ┣ 📜Report.java
  ┃ ┃ ┃ ┃ ┣ 📜ReportCategory.java
  ┃ ┃ ┃ ┃ ┣ 📜ReportResultCategory.java
  ┃ ┃ ┃ ┃ ┣ 📜Subcategory.java
  ┃ ┃ ┃ ┃ ┣ 📜User.java
  ┃ ┃ ┃ ┃ ┗ 📜User_SNS.java
  ┃ ┃ ┃ ┗ 📜MoumApplication.java
  ┣ 📂resources
  ┃ ┣ 📂config
  ┃ ┃ ┣ 📜mail.properties
  ┃ ┃ ┣ 📜ncp.properties
  ┃ ┣ 📂moum
  ┃ ┃ ┣ 📂project
  ┃ ┃ ┃ ┣ 📂dao
  ┃ ┃ ┃ ┃ ┣ 📜AchievementDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜AlertDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜BoardDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜ChatDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜CollectionCategoryDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜CollectionDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜CollectionStatusDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜CommentDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜LikesDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜ReportDao.xml
  ┃ ┃ ┃ ┃ ┣ 📜UserDao.xml
  ┃ ┃ ┃ ┃ ┗ 📜UserSnsDao.xml
  ┃ ┣ 📂static
  ┃ ┃ ┣ 📂css
  ┃ ┃ ┣ 📂images
  ┃ ┃ ┃ ┣ 📂achievement
  ┃ ┃ ┃ ┣ 📂board
  ┃ ┃ ┃ ┣ 📂collections
  ┃ ┃ ┃ ┣ 📂common
  ┃ ┃ ┃ ┣ 📂common2
  ┃ ┃ ┃ ┣ 📂main
  ┃ ┃ ┃ ┣ 📂post
  ┃ ┃ ┃ ┣ 📂user
  ┃ ┃ ┣ 📂js
  ┃ ┃ ┣ 📜favicon.ico
  ┃ ┃ ┗ 📜index.html
  ┃ ┣ 📂templates
  ┃ ┃ ┣ 📂achievement
  ┃ ┃ ┣ 📂admin
  ┃ ┃ ┣ 📂auth
  ┃ ┃ ┣ 📂board
  ┃ ┃ ┣ 📂collection
  ┃ ┃ ┣ 📂report
  ┃ ┃ ┣ 📂user
  ┃ ┃ ┣ 📜error.html
  ┃ ┃ ┣ 📜footer.html
  ┃ ┃ ┣ 📜header.html
  ┃ ┃ ┣ 📜home.html
  ┃ ┃ ┣ 📜index.html
  ┃ ┃ ┣ 📜myhome.html
  ┃ ┃ ┣ 📜ranking.html
  ┃ ┃ ┗ 📜test.html
  ┃ ┣ 📜application-dev.yml
  ┃ ┣ 📜application-local.yml
  ┃ ┣ 📜application-prod.yml
  ┃ ┗ 📜application.yml
  </pre>
</details>


<br>

## 4. 주요 기능

**사용자 인증 및 권한 관리**  
Spring Security를 활용한 로그인 및 접근 권한 설정  
OAuth2에 기반한 간편로그인 (네이버, 카카오, 구글 제공)

**수집품 관리 및 공유**  
마이홈이라는 나만의 공간에 항목별로 수집품 등록 및 관리  
게시글과 수집품을 연동하여 등록한 수집품 공유 및 거래 지원

**다양한 게시글**  
사용자의 다양한 목적 충족을 위해 일반, 전시, 거래 등 다양한 목적의 게시글 제공

**업적 시스템**  
사용자의 활동 고취를 목적으로 플랫폼 내 활동별 업적 취득  
게시글 목록 화면에서 전체사용자의 업적 랭킹 제공

**실시간 채팅**  
수집품 거래 지원을 위해 WebSocket과 Kafka을 이용한 1:1 채팅 기능

**관리자 및 신고**  
관리자 권한 소유자에게는 별도의 관리페이지로 플랫폼 관리 지원  
불건전한 게시글 제재를 위해 신고 기능 지원

**하비위키**  
OpenNamu를 활용하여 수집가들만을 위한 위키페이지 제공

**파일 업로드 및 관리**  
클라우드 스토리지를 활용한 이미지 및 파일 업로드


<br>

## 5. 화면 구성
발표자료 및 영상 참고  
**발표자료** : https://1drv.ms/p/s!Asad0zibIBicikUbIypvONh4avO7?e=poq9vJ  
**발표영상** : https://www.youtube.com/watch?v=eKirps0OGmg&t=1607s

<details>
  <summary>회원가입/로그인</summary>
  
### 회원가입/로그인
<img src="README_images/user/login_main.png">

- 메인화면 우측 상단 [시작하기] 버튼으로 로그인 화면 표출
- 자체회원 및 SNS 로그인 제공

<br>
<p>
  <img src="README_images/user/join.png" style="width: 45%; vertical-align: middle;">
  <img src="README_images/user/login.png" style="width: 45%; vertical-align: middle;">
</p>

- 회원가입 시 닉네임 중복검사 및 이메일 인증 진행
- 비밀번호 규칙 설정: 최소 8자 / 영문, 숫자, 특수문자 포함

<br>
마이페이지
<img src="README_images/user/mypage.png">

- 프로필사진 변경, 닉네임 및 비밀번호 변경, SNS 계정 연동 지원
</details>

<details>
  <summary>수집품/마이홈</summary>

### 수집품/마이홈
마이홈
<img src="README_images/collection/myhome.png">

- 로그인 시 마이홈 페이지로 연결
- 수집품 등록 및 관리 지원
- 수집품 카테고리에 따른 필터 지원

<br>
수집품 등록
<img src="README_images/collection/collection.png">

- 사진 및 필요정보 입력 후 수집품
</details>

<details>
  <summary>게시글</summary>

### 게시글
전시게시글
<img src="README_images/board/exhibition.png">
일반게시글
<img src="README_images/board/general.png">
거래게시글
<img src="README_images/board/trade.png">

- 게시글을 전시, 일반, 거래게시글로 구분하여 독립적 목적 부여
- 전시게시글은 특정 기간 내 특정 추천 수 만족 시 자동 게재
- 거래게시글은 사용자간 수집품 거래 지원

<br>
게시글 상세
<img src="README_images/board/board_detail.png">
<img src="README_images/board/comment.png">

- 제목, 내용, 댓글 등 게시글 정보 게시
- 하단 프로필 박스에서 작성자 정보 확인 및 신고, 추천 지원
- 거래게시글의 경우 [채팅하기] 버튼 표시

<br>
게시글 등록
<img src="README_images/board/board_add.png">

- Summernote.js 활용
</details>

<details>
  <summary>업적</summary>

### 업적

<img src="README_images/achievement/mypage.png">

- 사용자의 플랫폼 활동 고취를 위해 업적시스템 적용
- 마이페이지 우측에서 현재 진행중인 업적 확인 가능

<br>
<img src="README_images/achievement/detail_1.png">
<img src="README_images/achievement/detail_2.png">

- 더보기 버튼 클릭 시 미획득 업적 목록을 진행도 순으로 출력
- 전체 / 획득 / 미획득 업적 목록 조회 가능

<br>
<img src="README_images/achievement/myhome.png">

- 회원가입 후 첫 로그인 시, 알림을 통해 첫 업적 취득을 알려줌으로써 업적 시스템 소개
</details>

<details>
  <summary>채팅</summary>

### 채팅

<img src="README_images/chat/main.png">

- 우측 상단 채팅 버튼을 통해 채팅 UI 접근

<br>
<p>
  <img src="README_images/chat/chatrooms.png" style="width: 45%">
  <img src="README_images/chat/chat.png" style="width: 45%">
</p>

- 채팅방 목록에서 상대방 정보, 최근메시지, 관련게시글 사진 확인
- 특정 방 클릭 시 해당 채팅창으로 이동
- 새로운 메시지 발행 시 상대방에게 알림 전송

<br>
<img src="README_images/chat/livechat.png">

- 참가자가 모두 채팅방을 보고 있다면 별도 알림 발행하지 않음
</details>

<details>
  <summary>알림</summary>

### 알림

<img src="README_images/alert/main.png">

- 우측 상단 알림 버튼을 통해 알림 UI 접근
- 새로운 알림이 있을 시 종 색깔 변경하여 표시

<br>
<p>
  <img src="README_images/alert/list.png" style="width: 45%">
  <img src="README_images/alert/all_read.png" style="width: 44%">
</p>

- 특정 알림 클릭 시 유형에 따라 화면 이동 (채팅: 채팅방 / 댓글: 게시글 / 업적: 마이페이지 등)
- 알림창 상단 [모두 읽기] 버튼으로 알림 읽음처리 가능
</details>

<details>
  <summary>관리자</summary>

### 관리자

<img src="README_images/admin/main.png">

- 관리자 계정으로 로그인 시 별도 관리자 화면으로 이동

<br>
<img src="README_images/admin/search.png">

- 컬럼별 검색 기능 제공

<br>
<img src="README_images/admin/category.png">
<img src="README_images/admin/achievement.png">
<img src="README_images/admin/user.png">

- 게시글, 수집품 분류, 업적, 회원, 신고 관리 지원
- 회원 관리 시 슈퍼어드민 계정에 한하여 관리자권한 부여/회수 가능
</details>

<details>
  <summary>신고</summary>

### 신고

<img src="README_images/report/main.png">
<img src="README_images/report/report_page.png">

- 게시글 프로필 영역에서 부적절한 게시글 신고 지원

<br>
<img src="README_images/report/admin.png">
<p>
  <img src="README_images/report/result_1.png" style="width: 60%; vertical-align: middle;">
  <img src="README_images/report/result_2.png" style="width: 35%; vertical-align: middle;">
</p>

- 신고된 게시글은 관리자 화면에서 확인 가능
- 관리자는 해당 게시글 경고/삭제 조치 및 기록
- 필요에 따라 기각 처리

<br>
<img src="README_images/report/alert.png" style="width: 50%">

- 경고/삭제 시 대상자에게 알림 발행
</details>

<details>
  <summary>하비위키</summary>

### 하비위키

<img src="README_images/wiki/main.png">

- 수집가들만의 정보 공유를 위해 자체 위키 제공
- OPENNamu 활용

<br>
<img src="README_images/wiki/page.png">
<img src="README_images/wiki/edit.png">

- 나무위키와 유사한 UI로 정보 제공
- NamuMark 언어를 활용하여 편집 가능
</details>


<br>

## 6. 배포 및 시스템 구조
#### 배포
<img src="README_images/devops/jenkins.png">

- Github의 webhook, Jenkins 및 Docker를 활용하여 자동 배포 환경 구축
- 개발서버는 Git Push 발생 시 자동으로 Jenkins Build 진행
- 운영서버는 개발자 검토 후 수동 Build 진행 및 이중화 배포

#### 시스템 구조
<img src="README_images/devops/architecture.png">

- 클라이언트 요청 시 Application Load Balancer가 Round Robin으로 분배
- 서버는 단일 Storage, 단일 Database를 사용하며 Standby Database로 장애 대비
- 다중서버간 메시지 전송을 위해 Apache Kafka 활용
- NCP Certification Manager로 SSL 적용
- NCP Image Optimizer로 이미지 리사이즈 및 크롭
- Moum 도메인과 Wiki 도메인 요청 구분을 위해 Nginx 리버스 프록시 적용


<br>

## 7. 트러블슈팅

<details>
  <summary>프로필 사진 변경 오류</summary>

### 현상
운영 서버 이중화 이후, 프로필 사진 변경 시 서버 오류 발생

### 해결시도 1
**원인** : Spring 용량 설정, 또는 NCP Storage 용량 설정 문제로 추측

**시도** : Spring 및 Storage 용량 설정 재확인

**결과** : Local 및 Dev에서 프로필 사진 변경 가능함을 확인하였으나, 운영서버에서 동일한 오류 발생

### 해결시도 2
**원인** : 다중 도메인 및 SSL 적용을 위해 운영서버에 nginx를 도입하였는데, 설정 용량 기본값이 1M인 것을 확인

**시도** : 클라이언트 최대 바디 사이즈를 100M으로 변경

**결과** : 운영 서버에서 프로필 사진 변경 가능함을 확인

</details>

<details>
  <summary>게시글 사진 등록 오류</summary>

### 현상
게시글 사진 첨부 시, 게시글 등록여부와 관계없이 첨부 즉시 Storage에 저장되는 문제 확인

### 해결시도
**원인** : Summernote Editor에서 이미지 첨부 후처리 시 바로 Storage에 업로드하는 것으로 확인

**시도**
- 이미지 첨부 시 Base64 데이터로 Summernote content에 저장
- 서버에 등록 요청 시 content 내 Base64를 탐지
- 탐지된 데이터를 Storage에 업로드한 후 URL을 반환받아 content 치환

**결과** : 게시글 사진 첨부 정상 작동 확인    

</details>

<details>
  <summary>Opennamu Docker container 오류</summary>

### 현상
오픈소스인 Opennamu 활용을 시도하였으나, 제공된 Dockerfile이 정상 동작하지 않아 container 실행 불가

### 해결시도
**원인** : Opennamu 소스 내 Dockerfile은 존재하나, 공식적으로는 Docker를 지원하지 않는 것으로 확인

**시도** : 별도 Ubuntu container 내부에서 Opennamu를 내려받아 직접 실행

**결과** : Docker container 실행 확인

</details>

<details>
  <summary>채팅 Socket 통신 불가</summary>

### 현상
운영서버 이중화 이후, 채팅방 입장 시 403 에러 발생으로 Socket 통신 연결 불가

### 해결시도 1
**원인** : 기존 Socket통신이 단일서버 내 이루어지도록 구성되어 있어, ALB 및 이중화 구조에서 오류 발생하는 것으로 추측

**시도** : 메시지 중개자로서 Kafka를 도입하여 다중서버 간 통신이 가능할 것을 기대

**결과** : Local 및 Dev에서 Kafka를 통해 통신이 가능함을 확인했으나, 운영서버에서는 동일한 오류 발생

### 해결시도 2
**원인** : 다중 도메인 및 SSL 적용을 위해 운영서버에 nginx를 도입하였는데, Websocket 경로를 처리하지 않는 것으로 확인

**시도** : /ws 경로로 들어오는 요청을 WebSocket 프로토콜로 업그레이드하도록 설정 추가

**결과** : 운영 서버에서의 Socket 통신 성공 확인

</details>