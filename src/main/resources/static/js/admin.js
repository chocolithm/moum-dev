// 로딩 시 실행

const adminSelects = document.querySelectorAll(".sidemenu input");
adminSelects.forEach(input => {
  input.onclick = function () {
    selectAdminMenu(this);
  }
})


function selectAdminMenu(element) {
  const id = element.id;

  switch (id) {
    case "board-admin": createBoardTable(); break;
    case "category-admin": createCategoryTable(); break;
    case "achievement-admin": createAchievementTable(); break;
    case "user-admin": createUserTable(); break;
    case "report-admin": alert("신고/유해콘텐츠 관리"); break;
    case "statistics-admin": alert("통계 조회"); break;
    default: alert("잘못된 접근입니다.");
  }
}

function createBoardTable() {
  const title = document.querySelector("h1");
  const table = document.querySelector(".table-section table");

  title.innerHTML = "게시글 관리";

  table.innerHTML = `
    <thead>
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
        <th>조회수</th>
        <th>추천수</th>
      </tr>
    </thead>
  `;
}

function createCategoryTable() {
  const title = document.querySelector("h1");
  const table = document.querySelector(".table-section table");

  title.innerHTML = "수집품 분류 관리";

  table.innerHTML = `
    <thead>
      <tr>
        <th>번호</th>
        <th>대분류</th>
        <th>소분류</th>
        <th>보유자 수</th>
      </tr>
    </thead>
  `;
}

function createAchievementTable() {
  const title = document.querySelector("h1");
  const table = document.querySelector(".table-section table");

  title.innerHTML = "업적 관리";

  table.innerHTML = `
    <thead>
      <tr>
        <th>업적ID</th>
        <th>업적명</th>
        <th>설명</th>
        <th>점수</th>
        <th>취득자수</th>
      </tr>
    </thead>
  `;
}

function createUserTable() {
  const title = document.querySelector("h1");
  const table = document.querySelector(".table-section table");
  const thead = document.createElement("thead");

  title.innerHTML = "회원 관리";
  table.innerHTML = "";

  thead.innerHTML = `
    <tr>
      <th>회원번호</th>
      <th>ID</th>
      <th>닉네임</th>
      <th>가입일</th>
      <th>최근접속일자</th>
      <th>관리자여부</th>
      <th>SNS연동</th>
    </tr>
  `;

  table.append(thead);

  fetchUserData(1, 20);
}

function fetchUserData(pageNo, pageCount) {
  const table = document.querySelector(".table-section table");

  fetch(`/admin/user/list?pageNo=${pageNo}&pageCount=${pageCount}`)
    .then(response => response.json())
    .then(users => {

      const tbody = document.createElement("tbody");

      users.forEach(user => {
        tbody.innerHTML += `
          <tr>
            <td>${user.no}</td>
            <td>${user.email}</td>
            <td>${user.nickname}</td>
            <td>${formatDate(user.startDate)}</td>
            <td>${formatDate(user.lastLogin)}</td>
            <td>${user.admin === false ? "" : "관리자"}</td>
            <td>${user.snsId == null ? "" : ""}</td>
          </tr>
        `;
      });

      table.append(tbody);
    })
    .catch(error => {
      console.error("error fetching user list: ", error);
    })
}