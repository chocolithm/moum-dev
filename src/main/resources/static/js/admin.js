// 로딩 시 실행

const adminSelects = document.querySelectorAll(".sidemenu input");
adminSelects.forEach(input => {
  input.onclick = function () {
    selectAdminMenu(this);
  }
})
document.querySelector("#board-admin").click();


function selectAdminMenu(element) {
  const id = element.id;

  switch (id) {
    case "board-admin": toggleMenu("board", 1, 20); break;
    case "category-admin": createCategoryTable(); break;
    case "achievement-admin": createAchievementTable(); break;
    case "user-admin": toggleMenu("user", 1, 20); break;
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

function toggleMenu(menu, pageNo, pageCount) {
  createTableHead(menu);
  fetchData(menu, pageNo, pageCount);
  createPagination(menu, pageCount);
}

function createTableHead(menu) {
  const title = document.querySelector("h1");
  const table = document.querySelector(".table-section table");
  const thead = document.createElement("thead");

  table.innerHTML = "";

  if (menu == "user") {
    title.innerHTML = "회원 관리";
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
  }

  if (menu == "board") {
    title.innerHTML = "게시글 관리";
    thead.innerHTML = `
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>조회수</th>
          <th>추천수</th>
        </tr>
    `;
  }

  table.append(thead);
}

function fetchData(menu, pageNo, pageCount) {
  const table = document.querySelector(".table-section table");
  if (table.querySelector("tbody")) {
    table.querySelector("tbody").remove();
  }

  fetch(`/admin/${menu}/list?pageNo=${pageNo}&pageCount=${pageCount}`)
    .then(response => response.json())
    .then(data => {

      const tbody = document.createElement("tbody");

      if (menu == "user") {
        data.forEach(user => {
          tbody.innerHTML += `
            <tr>
              <td>${user.no}</td>
              <td>${user.email}</td>
              <td>${user.nickname}</td>
              <td>${formatDate(user.startDate).substring(0, 10)}</td>
              <td>${formatDate(user.lastLogin).substring(0, 10)}</td>
              <td>${user.admin === false ? "" : "관리자"}</td>
              <td>${user.snsId == null ? "" : ""}</td>
            </tr>
          `;
        });
      }

      if (menu == "board") {
        data.forEach(board => {
          tbody.innerHTML += `
            <tr>
              <td>${board.no}</td>
              <td>${board.title}</td>
              <td>${board.user.endDate == null ? board.user.nickname : "탈퇴한 회원"}</td>
              <td>${formatDate(board.postDate).substring(0, 10)}</td>
              <td>${board.viewCount}</td>
              <td>${board.likeCount}</td>
            </tr>
          `;
        });
      }

      table.append(tbody);
    })
    .catch(error => {
      console.error(`error fetching ${menu} list: `, error);
    })
}

function createPagination(menu, pageCount) {
  fetch(`/admin/${menu}/count`)
    .then(response => response.text())
    .then(length => {
      const totalItems = parseInt(length, 10);
      const totalPages = Math.ceil(totalItems / pageCount);

      const paginationContainer = document.querySelector('.page-section');
      paginationContainer.innerHTML = '';

      for (let i = 1; i <= totalPages; i++) {
        const pageButton = document.createElement('button');
        pageButton.textContent = i;
        pageButton.classList.add('page-button');

        pageButton.addEventListener('click', () => {
          fetchData(menu, i, pageCount);
        });

        paginationContainer.appendChild(pageButton);
      }
    })
    .catch(error => {
      console.error('error creating pagination:', error);
    });
}