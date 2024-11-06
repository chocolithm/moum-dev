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
    case "board-admin": fetchBoardTable(); break;
    case "category-admin": fetchCategoryTable(); break;
    case "achievement-admin": fetchAchievementTable(); break;
    case "user-admin": fetchUserTable(); break;
    case "report-admin": alert("신고/유해콘텐츠 관리"); break;
    case "statistics-admin": alert("통계 조회"); break;
    default: alert("잘못된 접근입니다.");
  }
}

function fetchBoardTable() {
  const table = document.querySelector(".table-section table");

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

function fetchCategoryTable() {
  const table = document.querySelector(".table-section table");

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

function fetchAchievementTable() {
  const table = document.querySelector(".table-section table");

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

function fetchUserTable() {
  const table = document.querySelector(".table-section table");

  table.innerHTML = `
    <thead>
      <tr>
        <th>회원번호</th>
        <th>ID</th>
        <th>닉네임</th>
        <th>가입일</th>
        <th>최근접속일자</th>
        <th>관리자여부</th>
        <th>SNS연동</th>
      </tr>
    </thead>
  `;

  fetch(`/admin/user/list`)
    .then(response => response.json())
    .then(users => {
      users.forEach(user => {
        table.innerHTML += `
          <tbody>
            <tr>
              <td>${user.no}</td>
              <td>${user.email}</td>
              <td>${user.nickname}</td>
              <td>${user.startDate}</td>
              <td>${user.lastLogin}</td>
              <td>${user.admin}</td>
              <td>${user.snsId}</td>
            </tr>
          </tbody>
        `;
      })
    })
    .catch(error => {
      console.error("error fetching user list: ", error);
    })
}