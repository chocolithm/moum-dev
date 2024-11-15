// 로딩 시 메뉴에 onclick 부여
const adminSelects = document.querySelectorAll(".sidemenu input");
adminSelects.forEach(input => {
  input.onclick = function () {
    selectAdminMenu(this);
  }
})

// 게시글 관리 기본선택
document.querySelector("#board-admin").click();

// 뒤로가기 시 이전페이지 동작 추가
window.addEventListener("popstate", function (event) {
  if (event.state) {
    const type = event.state.type;

    if (type == "list") {
      const menu = event.state.menu;
      const pageNo = event.state.pageNo;
      const pageCount = event.state.pageCount;

      toggleAdminMenu(menu, pageNo, pageCount, true);
    }

    if (type == "view") {
      const menu = event.state.menu;
      const no = event.state.no;
      fetchAdminDetail(menu, no, true);
    }

  }
})

// 메뉴 선택 시 항목별 동작
function selectAdminMenu(element) {
  const id = element.id;

  switch (id) {
    case "board-admin": toggleAdminMenu("board", 1, 20); break;
    case "category-admin": toggleAdminMenu("category", 1, 20); break;
    case "achievement-admin": toggleAdminMenu("achievement", 1, 20); break;
    case "user-admin": toggleAdminMenu("user", 1, 5); break;
    case "report-admin": alert("신고/유해콘텐츠 관리"); break;
    case "statistics-admin": alert("통계 조회"); break;
    default: alert("잘못된 접근입니다.");
  }
}

// 메뉴별 화면 생성
function toggleAdminMenu(menu, pageNo, pageCount, fromPopState = false) {
  createAdminTableHead(menu);
  fetchAdminData(menu, pageNo, pageCount, fromPopState);
  createAdminPagination(menu, pageCount);
}

// 목록 테이블 생성
function createAdminTableHead(menu) {
  const title = document.querySelector("h1");
  const content_section = document.querySelector(".content-section");
  const table = document.createElement("table");
  table.className = "list-table";
  const thead = document.createElement("thead");

  content_section.innerHTML = "";

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

  if (menu == "category") {
    title.innerHTML = "수집품 분류 관리";
    thead.innerHTML = `
        <tr>
          <th>번호</th>
          <th>대분류</th>
          <th>보유자 수</th>
        </tr>
    `;
  }

  if (menu == "achievement") {
    title.innerHTML = "업적 관리";
    thead.innerHTML = `
        <tr>
          <th>업적ID</th>
          <th>업적명</th>
          <th>설명</th>
          <th>점수</th>
          <th>취득자수</th>
        </tr>
    `;
  }

  table.append(thead);
  content_section.append(table);
}

// 목록 데이터 조회
function fetchAdminData(menu, pageNo, pageCount, fromPopState) {
  const table = document.querySelector(".content-section table");

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
            <tr onclick="fetchAdminDetail('user', ${user.no})">
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
            <tr onclick="fetchAdminDetail('board', ${board.no})">
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

      if (menu == "category") {
        data.forEach(category => {
          tbody.innerHTML += `
            <tr onclick="fetchAdminDetail('category', ${category.no})">
              <td>${category.no}</td>
              <td>${category.name}</td>
              <td>${category.count}</td>
            </tr>
          `;
        });
      }

      if (menu == "achievement") {
        data.forEach(achievement => {
          tbody.innerHTML += `
            <tr onclick="fetchAdminDetail('achievement', '${achievement.id}')">
              <td>${achievement.id}</td>
              <td>${achievement.name}</td>
              <td>${achievement.content}</td>
              <td>${achievement.score}</td>
              <td>${achievement.completeCount}</td>
            </tr>
          `;
        });
      }

      table.append(tbody);

      // 조회 시마다 url 변경
      if (!fromPopState) {
        const newUrl = `/admin/management?menu=${menu}&pageNo=${pageNo}&pageCount=${pageCount}`;
        history.pushState({ menu, pageNo, pageCount, type: "list" }, null, newUrl);
      }

    })
    .catch(error => {
      console.error(`error fetching ${menu} list: `, error);
    })
}

// 페이징 생성
function createAdminPagination(menu, pageCount) {
  fetch(`/admin/${menu}/count`)
    .then(response => response.text())
    .then(length => {
      const totalItems = parseInt(length, 10);
      const totalPages = Math.ceil(totalItems / pageCount);

      const paginationContainer = document.querySelector('.pagination');
      paginationContainer.innerHTML = '';  // 초기화

      let currentPage = 1;

      // "Previous" 버튼
      const prevButton = document.createElement('li');
      prevButton.classList.add('page-item', 'disabled');
      const prevLink = document.createElement('a');
      prevLink.classList.add('page-link');
      prevLink.href = '#';
      prevLink.textContent = 'Previous';
      prevButton.appendChild(prevLink);
      paginationContainer.appendChild(prevButton);

      // 페이지 버튼들
      for (let i = 1; i <= totalPages; i++) {
        const pageItem = document.createElement('li');
        pageItem.classList.add('page-item');

        const pageLink = document.createElement('a');
        pageLink.classList.add('page-link');
        pageLink.href = '#';
        pageLink.textContent = i;

        pageLink.addEventListener('click', (event) => {
          event.preventDefault();  // 페이지 리로딩 방지
          currentPage = i;
          fetchAdminData(menu, i, pageCount);
          updateActivePage(i, totalPages);
        });

        pageItem.appendChild(pageLink);
        paginationContainer.appendChild(pageItem);
      }

      // "Next" 버튼
      const nextButton = document.createElement('li');
      nextButton.classList.add('page-item', 'disabled');
      const nextLink = document.createElement('a');
      nextLink.classList.add('page-link');
      nextLink.href = '#';
      nextLink.textContent = 'Next';
      nextButton.appendChild(nextLink);
      paginationContainer.appendChild(nextButton);

      // "Previous" 버튼 클릭 시
      prevLink.addEventListener('click', (event) => {
        if (currentPage > 1) {
          currentPage--;
          fetchAdminData(menu, currentPage, pageCount);
          updateActivePage(currentPage, totalPages);
        }
      });

      // "Next" 버튼 클릭 시
      nextLink.addEventListener('click', (event) => {
        if (currentPage < totalPages) {
          currentPage++;
          fetchAdminData(menu, currentPage, pageCount);
          updateActivePage(currentPage, totalPages);
        }
      });

      // 첫 페이지 활성화
      updateActivePage(1, totalPages);
    })
    .catch(error => {
      console.error('Error creating pagination:', error);
    });
}

// 활성화된 페이지 업데이트
function updateActivePage(currentPage, totalPages) {
  const paginationContainer = document.querySelector('.pagination');
  const pageItems = paginationContainer.querySelectorAll('.page-item');

  pageItems.forEach(item => {
    const link = item.querySelector('.page-link');
    if (link) {
      if (parseInt(link.textContent, 10) === currentPage) {
        item.classList.add('active');
      } else {
        item.classList.remove('active');
      }
    }
  });

  // "Previous" 버튼 및 "Next" 버튼의 활성화 상태 업데이트
  const prevButton = paginationContainer.querySelector('.page-item:first-child');
  const nextButton = paginationContainer.querySelector('.page-item:last-child');

  if (currentPage === 1) {
    prevButton.classList.add('disabled');
  } else {
    prevButton.classList.remove('disabled');
  }

  if (currentPage === totalPages) {
    nextButton.classList.add('disabled');
  } else {
    nextButton.classList.remove('disabled');
  }
}


// 상세조회 화면
function fetchAdminDetail(menu, no, fromPopState = false) {
  const content_section = document.querySelector(".content-section");
  const paginationContainer = document.querySelector('.pagination');
  console.log("fetchAdminDetail");

  // 목록화면 삭제
  content_section.innerHTML = "";
  paginationContainer.innerHTML = "";

  fetch(`/admin/${menu}?no=${no}`)
    .then(response => response.json())
    .then(data => {
      if (menu == "user") {
        const user = data;
        content_section.innerHTML = `
          <div>
            <img src="${user.photo != "" && user.photo != null
            ? 'https://kr.object.ncloudstorage.com/bitcamp-moum/user/profile/' + user.photo
            : '/images/common2/profile.png'}" alt="프로필 사진" class="user-profile-img">
          </div>
          <table>
            <tbody>
              <tr>
                <td>회원번호</td>
                <td>${user.no}</td>
              </tr>
              <tr>
                <td>이메일</td>
                <td>${user.email}</td>
              </tr>
              <tr>
                <td>닉네임</td>
                <td>${user.nickname}</td>
              </tr>
              <tr>
                <td>가입일자</td>
                <td>${formatDate(user.startDate)}</td>
              </tr>
              <tr>
                <td>최근접속</td>
                <td>${formatDate(user.lastLogin)}</td>
              </tr>
              <tr>
                <td>관리자권한</td>
                <td>
                  <select onchange="toggleAdminUser(this, ${user.no})">
                    <option value="0" ${user.admin === false ? "selected" : ""}>미부여</option>
                    <option value="1" ${user.admin === true ? "selected" : ""}>부여</option>
                  </select>
                </td>
              </tr>
              <tr>
                <td>SNS 연동</td>
                <td>${user.snsId == null ? "미연동" : "연동"}</td>
              </tr>
            </tbody>
          </table>
        `;
      }

      if (menu == "board") {

      }

      if (menu == "category") {
        const category = data;

        let content = `
          <table class="maincategory-table">
            <tbody>
              <tr>
                <td>분류번호</td>
                <td>${category[0].maincategory.no}</td>
              </tr>
              <tr>
                <td>분류명</td>
                <td>${category[0].maincategory.name}</td>
              </tr>
              <tr>
                <td>보유자수</td>
                <td>${category[0].count}</td>
              </tr>
            </tbody>
          </table>
          <table class="subcategory-table">
            <thead>
              <tr>
                <th>분류번호</th>
                <th>소분류</th>
                <th>취득자수</th>
              </tr>
            </thead>
            <tbody>
        `;

        for (i = 1; i < category.length; i++) {
          content += `
            <tr>
              <td>${category[i].no}</td>
              <td>${category[i].name}</td>
              <td>${category[i].count}</td>
            </tr>
          `;
        }

        content += `
            </tbody>
          </table>
        `;

        content_section.innerHTML = content;
      }

      if (menu == "achievement") {
        const achievement = data;
        content_section.innerHTML = `
          <div>
            <img src="${achievement.photo != "" && achievement.photo != null
            ? 'https://kr.object.ncloudstorage.com/bitcamp-moum/achievement/' + achievement.photo
            : '/images/common2/profile.png'}" alt="업적 사진" class="achievement-img">
          </div>
          <table>
            <tbody>
              <tr>
                <td>업적 ID</td>
                <td>${achievement.id}</td>
              </tr>
              <tr>
                <td>엄적명</td>
                <td>
                  <input name="name" type="text" value="${achievement.name}">
                </td>
              </tr>
              <tr>
                <td>설명</td>
                <td>
                  <input name="content" type="text" value="${achievement.content}">
                </td>
              </tr>
              <tr>
                <td>취득조건</td>
                <td>
                  <input name="content" type="text" value="${achievement.condition}">
                </td>
              </tr>
              <tr>
                <td>점수</td>
                <td>
                  <input name="content" type="text" value="${achievement.score}" onchange="formatNumber(this);">
                </td>
              </tr>
            </tbody>
          </table>
        `;
      }

      // 조회 시마다 url 변경
      if (!fromPopState) {
        const newUrl = `/admin/management?menu=${menu}&no=${no}`;
        history.pushState({ menu, no, type: "view" }, null, newUrl);
      }
    })
    .catch(error => {
      console.error('error creating detail:', error);
    });
}

function toggleAdminUser(element, userNo) {
  if (element.value == 0) {
    if (confirm("관리자 권한을 해제하시겠습니까?")) {
      fetch(`/admin/updateAdmin?admin=0&userNo=${userNo}`)
        .then(response => response.text())
        .then(response => {
          if (response == "success") {
            alert("관리자 권한 해제 완료");
          } else if (response == "failure") {
            element.value = 1;
            alert("오류 발생");
          } else if (response == "inhibited") {
            alert("본인 권한 수정 불가");
          }
        })
        .catch(error => {
          console.error("error setting admin: ", error);
        })
    }
  } else if (element.value == 1) {
    if (confirm("관리자 권한을 부여하시겠습니까?")) {
      fetch(`/admin/updateAdmin?admin=1&userNo=${userNo}`)
        .then(response => response.text())
        .then(response => {
          if (response == "success") {
            alert("관리자 권한 설정 완료");
          } else if (response == "failure") {
            element.value = 0;
            alert("오류 발생");
          } else if (response == "inhibited") {
            alert("본인 권한 수정 불가");
          }
        })
        .catch(error => {
          console.error("error setting admin: ", error);
        })
    }
  }

}
