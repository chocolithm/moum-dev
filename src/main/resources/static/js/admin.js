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
    case "board-admin": alert("게시글 관리"); break;
    case "category-admin": fetchCategoryTable(); break;
    case "achievement-admin": alert("업적 관리"); break;
    case "user-admin": alert("회원 관리"); break;
    case "report-admin": alert("신고/유해콘텐츠 관리"); break;
    case "statistics-admin": alert("통계 조회"); break;
    default: alert("잘못된 접근입니다.");
  }
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