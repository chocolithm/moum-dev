let achievementSlideIndex = 0;
let filesArray = [];

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

      this.document.getElementById(menu + "-admin").checked = true;
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
  const pageNo = 1;
  const pageCount = 20;

  switch (id) {
    case "board-admin": toggleAdminMenu("board", pageNo, pageCount); break;
    case "category-admin": toggleAdminMenu("category", pageNo, pageCount); break;
    case "achievement-admin": toggleAdminMenu("achievement", pageNo, pageCount); break;
    case "user-admin": toggleAdminMenu("user", pageNo, pageCount); break;
    case "report-admin": toggleAdminMenu("report", pageNo, pageCount); break;
    default: 
      Swal.fire({
        icon: "error",
        text: "잘못된 접근입니다."
      })
  }
}

// 메뉴별 화면 생성
function toggleAdminMenu(menu, pageNo, pageCount, fromPopState = false) {
  createAdminTableHead(menu, pageNo, pageCount);
  fetchAdminData(menu, pageNo, pageCount, fromPopState);
  createAdminPagination(menu, pageCount);
  createAddBtn(menu);
}

// 목록 테이블 생성
function createAdminTableHead(menu, pageNo, pageCount) {
  const title = document.querySelector("h2");
  const content_section = document.querySelector(".content-section");
  const table = document.createElement("table");
  table.className = "list-table";
  const thead = document.createElement("thead");

  content_section.innerHTML = "";


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
        <tr class="tr_search">
          <th><input name="no" type="text" placeholder="search"</th>
          <th><input name="title" type="text"></th>
          <th><input name="user.nickname" type="text"></th>
          <th><input name="postDateString" type="text"></th>
          <th><input name="viewCountString" type="text"></th>
          <th><input name="likeString" type="text"></th>
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
        <tr class="tr_search">
          <th><input name="no" type="text" placeholder="search"></th>
          <th><input name="name" type="text"></th>
          <th><input name="countString" type="text"></th>
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
        <tr class="tr_search">
          <th><input name="id" type="text" placeholder="search"></th>
          <th><input name="name" type="text"></th>
          <th><input name="content" type="text"></th>
          <th><input name="score" type="text"></th>
          <th><input name="completeCountString" type="text"></th>
        </tr>
    `;
  }

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
      <tr class="tr_search">
        <th><input name="no" type="text" placeholder="search"></th>
        <th><input name="email" type="text"></th>
        <th><input name="nickname" type="text"></th>
        <th><input name="startDateString" type="text"></th>
        <th><input name="lastLoginString" type="text"></th>
        <th><input name="isAdmin" type="text"></th>
        <th><input name="userSns.provider" type="text"></th>
      </tr>
    `;
  }


  if (menu == "report") {
    title.innerHTML = "신고 관리";
    thead.innerHTML = `
        <tr>
          <th>신고일시</th>
          <th>신고자</th>
          <th>신고항목</th>
          <th>처리결과</th>
        </tr>
        <tr class="tr_search">
          <th><input name="reportDate" type="text" placeholder="search"></th>
          <th><input name="user.nickname" type="text"></th>
          <th><input name="reportCategory.name" type="text"></th>
          <th><input name="resultCategory.name" type="text"></th>
        </tr>
    `;
  }

  table.append(thead);
  content_section.append(table);

  const searchParams = document.querySelectorAll(".tr_search input");

  for (let i = 0; i < searchParams.length; i++) {
    searchParams[i].addEventListener('keydown', (event) => {
      if (event.key === "Enter") {
        searchAdminData(menu, pageNo, pageCount);
      }
    });
  }
}

function searchAdminData(menu, pageNo, pageCount) {
  fetchAdminData(menu, pageNo, pageCount);
  createAdminPagination(menu, pageCount);
}

// 목록 데이터 조회
function fetchAdminData(menu, pageNo, pageCount, fromPopState) {
  const table = document.querySelector(".content-section table");

  if (table.querySelector("tbody")) {
    table.querySelector("tbody").remove();
  }

  let url = `/admin/${menu}/list?pageNo=${pageNo}&pageCount=${pageCount}`;
  const searchParams = document.querySelectorAll(".tr_search input");


  for (let i = 0; i < searchParams.length; i++) {
    if (searchParams[i].value !== "") {
      url += `&${searchParams[i].name}=${searchParams[i].value}`;
    }
  }

  fetch(url)
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
              <td>${user.provider == null ? "" : user.provider}</td>
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

      if (menu == "report") {
        data.forEach(report => {
          tbody.innerHTML += `
            <tr onclick="fetchAdminDetail('report', '${report.no}')">
              <td>${formatDate(report.reportDate)}</td>
              <td>${report.user.nickname}</td>
              <td>${report.reportCategory.name}</td>
              <td>${report.resultCategory == null ? "-" : report.resultCategory.name}</td>
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

  let url = `/admin/${menu}/count`;
  const searchParams = document.querySelectorAll(".tr_search input");
  let count = 0;

  for (let i = 0; i < searchParams.length; i++) {
    if (searchParams[i].value !== "" && count == 0) {
      url += `?${searchParams[i].name}=${searchParams[i].value}`;
      count++;
      continue;
    }

    if (searchParams[i].value !== "") {
      url += `&${searchParams[i].name}=${searchParams[i].value}`;
    }
  }

  fetch(url)
    .then(response => response.text())
    .then(length => {
      const totalItems = parseInt(length, 10);
      const totalPages = Math.ceil(totalItems / pageCount);

      const paginationContainer = document.querySelector('.pagination');
      paginationContainer.innerHTML = '';  // 초기화

      let currentPage = 1;

      // "Previous" 버튼 (하얀 배경, 검정 글씨)
      const prevButton = document.createElement('li');
      prevButton.classList.add('page-item', 'disabled');
      const prevLink = document.createElement('a');
      prevLink.classList.add('page-link', 'bg-white', 'text-dark');
      prevLink.href = '#';
      prevLink.textContent = 'Previous';
      prevButton.appendChild(prevLink);
      paginationContainer.appendChild(prevButton);

      // 페이지 버튼들
      for (let i = 1; i <= totalPages; i++) {
        const pageItem = document.createElement('li');
        pageItem.classList.add('page-item');

        const pageLink = document.createElement('a');
        pageLink.classList.add('page-link', 'bg-dark', 'text-white');
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

      // "Next" 버튼 (하얀 배경, 검정 글씨)
      const nextButton = document.createElement('li');
      nextButton.classList.add('page-item', 'disabled');
      const nextLink = document.createElement('a');
      nextLink.classList.add('page-link', 'bg-white', 'text-dark');
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

// 등록 버튼 생성
function createAddBtn(menu) {
  const btn_section = document.querySelector(".btn-section");
  btn_section.innerHTML = "";

  if (menu == "category" || menu == "achievement") {
    const btn = document.createElement("button");
    btn.className = `${menu}-add-btn btn`;
    btn.innerHTML = "등록";
    btn.onclick = () => openAddPage(menu);
    btn_section.append(btn);
  }
  
}

function openAddPage(menu) {
  const content_section = document.querySelector(".content-section");
  const paginationContainer = document.querySelector('.pagination');
  const btn_section = document.querySelector(".btn-section");
  content_section.innerHTML = "";
  paginationContainer.innerHTML = "";
  btn_section.innerHTML = "";

  if (menu == "category") {
    let content = `
          <table class="maincategory-table">
            <tbody>
              <tr>
                <td>분류명</td>
                <td><input name="maincategory-name" id="maincategory-name" type="text"></td>
              </tr>
              <tr>
                <td>색상</td>
                <td><input name="maincategory-color" id="maincategory-color" type="text"></td>
              </tr>
              <tr>
                <td colspan="2">
                  <button class="btn" onclick="addMaincategory()">등록</button>
                </td>
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
            </tbody>
          </table>
        `;

        content_section.innerHTML = content;
  }

  // <img alt="업적 사진 등록" class="achievement-img empty-image" onClick="triggerFileInput();"
  //            src="/images/collections/empty-collection.png">
  //       <div class="slider" style="display: none;">
  //         <div class="slides"></div>
  //         <a class="prev" onClick="changeSlide(-1)">&#10094;</a>
  //         <a class="next" onClick="changeSlide(1)">&#10095;</a>
  //       </div>

  if (menu == "achievement") {
    content_section.innerHTML = `
    
      <label for="files" style="cursor: pointer; position: relative; display: block;" id="files-label">
        <img alt="수집품 이미지 등록" class="collection-image empty-image"
              src="/images/collections/empty-collection-image.png" style="opacity: 0.5;">
        <div class="edit-overlay">
            <i class="fas fa-camera"></i>
        </div>
      </label>

      <div class="img-div add-achievement" style="display: none";>
        <div class="main-image">
          <img alt="Achievement Image"
                id="mainAchievementImage"
                src="/images/collections/empty-collection-image.png">
        </div>

        <div class="thumbnail-images">
          <span class="new-image-btn">
              <img alt="Thumbnail Image"
                  onclick="triggerFileInput()"
                  src="/images/collections/empty-collection-image.png">
          </span>
        </div>
        <input id="files" multiple name="files" onChange="addTempFiles(event);" style="display: none;" type="file">
      </div>
      <table class="view-table">
        <tbody>
          <tr>
            <td>업적 ID</td>
            <td><input name="id" id="id" type="text"></td>
          </tr>
          <tr>
            <td>엄적명</td>
            <td><input name="name" id="name" type="text"></td>
          </tr>
          <tr>
            <td>설명</td>
            <td><input name="content" id="content" type="text"></td>
          </tr>
          <tr>
            <td>취득조건</td>
            <td><input name="condition" id="condition" type="text"></td>
          </tr>
          <tr>
            <td>조건횟수</td>
            <td><input name="maxCount" id="maxCount" type="text" onchange="formatNumber(this);"></td>
          </tr>
          <tr>
            <td>점수</td>
            <td><input name="score" id="score" type="text" onchange="formatNumber(this);"></td>
          </tr>
          <tr>
            <td colspan="2"><button class="btn" onclick="addAchievement()">등록</button></td>
          </tr>
        </tbody>
      </table>
    `;
  }
}

function triggerFileInput() {
  Swal.fire({
    icon: "info",
    text: "업적 사진은 미취득 1장, 취득 1장 순으로 총 2장이 필요합니다."
  }).then(() => {
    document.getElementById('files').click();
  });
}

// 선택한 이미지 미리보기
function previewImage(event) {
    const files = event.target.files;
    const empty = document.querySelector(".empty-image");
    const slider = document.querySelector(".slider");
    const slides = document.querySelector(".slides");
    const newImages = document.getElementsByClassName("new-image");
    const currentImages = document.getElementsByClassName("current-image");

    for (i = newImages.length - 1; i >= 0; i--) {
        newImages[i].remove();
    }

    if (files && files.length > 0) {

        for (let i = 0; i < files.length; i++) {
            const reader = new FileReader();

            reader.onload = function (e) {
                const img = document.createElement("img");
                img.src = e.target.result;
                img.alt = "Uploaded Image";
                img.className = "achievement-img slide new-image";
                img.onclick = function () {
                    triggerFileInput();
                };
                slides.appendChild(img);
            }

            reader.readAsDataURL(files[i]);
        }

        if (empty) {
            empty.style.display = "none";
        }

        slider.style.display = "block";
        if (slides.children.length + files.length > 1) {
            document.querySelector(".prev").style.display = "block";
            document.querySelector(".next").style.display = "block";
        } else {
            document.querySelector(".prev").style.display = "none";
            document.querySelector(".next").style.display = "none";
        }

    } else if (currentImages && currentImages.length > 0) {
      initAchievementSlideIndex();
        showSlides(achievementSlideIndex);
    } else {
        if (empty) {
            empty.style.display = "block";
        }
        slider.style.display = "none";
    }
}

// 사진 인덱스 초기화
function initAchievementSlideIndex() {
  achievementSlideIndex = 0;
}

// 슬라이드 display 처리
function showSlides(index) {
  const slides = document.querySelectorAll('.slide');
  if (index >= slides.length) { achievementSlideIndex = 0; }
  if (index < 0) { achievementSlideIndex = slides.length - 1; }

  slides.forEach((slide, i) => {
    slide.style.display = (i === achievementSlideIndex) ? 'block' : 'none';
  });
}

// 사진 슬라이드 처리
function changeSlide(n) {
  showSlides(achievementSlideIndex += n);
}

function addAchievement() {

  Swal.fire({
    text: "등록하시겠습니까?",
    showCancelButton: true,
    confirmButtonText: "확인"
  }).then((result) => {

    if (result.isConfirmed) {
      const formData = new FormData();

      const filesInput = document.getElementById('files');
      for (let i = 0; i < filesInput.files.length; i++) {
        formData.append("files", filesInput.files[i]);
      }
      
      const inputs = document.querySelectorAll('table.view-table input');
      inputs.forEach(input => {
        formData.append(input.name, input.value);
      });

      if (validateAchievement(formData, "add")) {
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        fetch(`/achievement/add`, {
          method: "POST",
          body: formData,
          headers: {
              [csrfHeader]: csrfToken
          }
        })
          .then(response => response.text())
          .then(response => {
              switch (response) {
                  case "success":
                      Swal.fire({
                        icon: "success",
                        text: "등록했습니다."
                      });
                      document.querySelector("#achievement-admin").click();
                      break;
                  case "failure":
                      Swal.fire({
                        icon: "error",
                        text: "등록 중 오류 발생"
                      });
                      break;
              }
          })
          .catch(error => {
              console.error("Error adding achievement: ", error);
          });
      }
    }
  })
}

function updateAchievement() {

  Swal.fire({
    text: "수정하시겠습니까?",
    showCancelButton: true,
    confirmButtonText: "확인"
  }).then((result) => {

    if (result.isConfirmed) {
      const formData = new FormData();

      formData.append("id", document.querySelector(".achievement-id-td").innerHTML);
      
      const inputs = document.querySelectorAll('table.view-table input');
      inputs.forEach(input => {
        formData.append(input.name, input.value);
      });

      if (validateAchievement(formData, "update")) {
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        fetch(`/achievement/update`, {
          method: "PUT",
          body: formData,
          headers: {
              [csrfHeader]: csrfToken
          }
        })
          .then(response => response.text())
          .then(response => {
              switch (response) {
                  case "success":
                      Swal.fire({
                        icon: "success",
                        text: "수정했습니다."
                      });
                      document.querySelector("#achievement-admin").click();
                      break;
                  case "failure":
                      Swal.fire({
                        icon: "error",
                        text: "수정 중 오류 발생"
                      });
                      break;
              }
          })
          .catch(error => {
              console.error("Error updating achievement: ", error);
          });
      }
    }
  })
}

function deleteAchievement(id) {

  Swal.fire({
    text: "삭제하시겠습니까?",
    showCancelButton: true,
    confirmButtonText: "확인"
  }).then((result) => {

    if (result.isConfirmed) {
      const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

    fetch(`/achievement/delete?id=${id}`, {
        method: "DELETE",
        headers: {
            [csrfHeader]: csrfToken
        }
      })
        .then(response => response.text())
        .then(response => {
            switch (response) {
                case "success":
                    Swal.fire({
                      icon: "success",
                      text: "삭제했습니다."
                    });
                    document.querySelector("#achievement-admin").click();
                    break;
                case "failure":
                    Swal.fire({
                      icon: "error",
                      text: "삭제 중 오류 발생"
                    });
                    break;
            }
        })
        .catch(error => {
            console.error("Error deleting achievement: ", error);
        });
    }
  })
}

function validateAchievement(formData, type) {
  const name = document.querySelector(".view-table #name");
  const content = document.querySelector(".view-table #content");
  const condition = document.querySelector(".view-table #condition");
  const maxCount = document.querySelector(".view-table #maxCount");
  const score = document.querySelector(".view-table #score");

  name.style="border-color: #ccc";
  content.style="border-color: #ccc";
  condition.style="border-color: #ccc";
  maxCount.style="border-color: #ccc";
  score.style="border-color: #ccc";

  if (type == "add") {
    const id = document.querySelector(".view-table #id");
    id.style="border-color: #ccc";

    if (formData.getAll("files").length != 2) {
      Swal.fire({
        icon: "warning",
        text: "이미지를 2장 등록하세요."
      });
      return false;
    }
    if (formData.get("id").trim() == "") {id.style="border-color: red"; return false;}
  }

  if (formData.get("name").trim() == "") {name.style="border-color: red"; return false;}
  if (formData.get("content").trim() == "") {content.style="border-color: red"; return false;}
  if (formData.get("condition").trim() == "") {condition.style="border-color: red"; return false;}
  if (formData.get("maxCount").trim() == "") {maxCount.style="border-color: red"; return false;}
  if (formData.get("score").trim() == "") {score.style="border-color: red"; return false;}
  return true;
}

// 상세조회 화면
function fetchAdminDetail(menu, no, fromPopState = false) {
  if (menu == "board") {
    location.href = `/board/boardView?no=${no}`;
  }

  const content_section = document.querySelector(".content-section");
  const paginationContainer = document.querySelector('.pagination');
  const btn_section = document.querySelector(".btn-section");
  content_section.innerHTML = "";
  paginationContainer.innerHTML = "";
  btn_section.innerHTML = "";

  fetch(`/admin/${menu}?no=${no}`)
    .then(response => response.json())
    .then(data => {
      if (menu == "user") {
        const user = data;
        content_section.innerHTML = `
          <div class="img-div">
            <img src="${user.photo != "" && user.photo != null
            ? 'https://kr.object.ncloudstorage.com/bitcamp-moum/user/profile/' + user.photo
            : '/images/common2/profile.png'}" alt="프로필 사진" class="user-profile-img">
          </div>
          <table class="view-table">
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
              <tr>
                <td>색상</td>
                <td>${category[0].maincategory.color}</td>
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
              <tr class="subcategory-add-tr">
                <td colspan="2"><input name="subcategory-name" id="subcategory-name" type="text"></td>
                <td><button onclick="addSubcategory(${category[0].maincategory.no})">등록</button></td>
              </tr>
            </tbody>
          </table>
        `;

        content_section.innerHTML = content;
      }

      if (menu == "achievement") {
        const achievement = data;
        content_section.innerHTML = `
          <div class="img-div">

            <div class="main-image">
                <img alt="Achievement Image"
                    id="mainAchievementImage"
                    src="https://kr.object.ncloudstorage.com/bitcamp-moum/achievement/${achievement.photo}">
            </div>

            <div class="thumbnail-images">
                <span>
                    <img alt="Thumbnail Image"
                        src="https://kr.object.ncloudstorage.com/bitcamp-moum/achievement/${achievement.photo}"
                        onclick="changeAchievementMainImage(this)">
                </span>
                <span>
                    <img alt="Thumbnail Image"
                        src="https://kr.object.ncloudstorage.com/bitcamp-moum/achievement/complete/${achievement.photo}"
                        onclick="changeAchievementMainImage(this)">
                </span>
            </div>

          </div>
          <table class="view-table">
            <tbody>
              <tr>
                <td>업적 ID</td>
                <td class="achievement-id-td">${achievement.id}</td>
              </tr>
              <tr>
                <td>엄적명</td>
                <td>
                  <input name="name" id="name" type="text" value="${achievement.name}">
                </td>
              </tr>
              <tr>
                <td>설명</td>
                <td>
                  <input name="content" id="content" type="text" value="${achievement.content}">
                </td>
              </tr>
              <tr>
                <td>취득조건</td>
                <td>
                  <input name="condition" id="condition" type="text" value="${achievement.condition}">
                </td>
              </tr>
              <tr>
                <td>조건횟수</td>
                <td>
                  <input name="maxCount" type="text" id="maxCount" value="${achievement.maxCount}" onchange="formatNumber(this);">
                </td>
              </tr>
              <tr>
                <td>점수</td>
                <td>
                  <input name="score" type="text" id="score" value="${achievement.score}" onchange="formatNumber(this);">
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <button class="btn" onclick="updateAchievement()">수정</button>
                  <button class="btn" onclick="deleteAchievement('${achievement.id}')">삭제</button>
                </td>
              </tr>
            </tbody>
          </table>
        `;
      }

      if (menu == "report") {
        const report = data;

        fetch(`/report/listResultCategories`)
          .then(response => response.json())
          .then(categories => {
            let htmlContent = "";
            htmlContent = `
              <table class="view-table">
                <tbody>
                  <tr>
                    <td>번호</td>
                    <td>${report.no}</td>
                  </tr>
                  <tr>
                    <td>신고일시</td>
                    <td>${formatDate(report.reportDate)}</td>
                  </tr>
                  <tr>
                    <td>신고자</td>
                    <td>${report.user.nickname}</td>
                  </tr>
                  <tr>
                    <td>신고항목</td>
                    <td>${report.reportCategory.name}</td>
                  </tr>
                  <tr>
                    <td>내용</td>
                    <td>${report.reportContent}</td>
                  </tr>
                  <tr>
                    <td>처리결과</td>
                    <td>
                      <select name="resultCategory.no" id="resultCategory.no">
                        <option value="0">미처리</option>
            `;

            categories.forEach(category => {
              htmlContent += `
                        <option value="${category.no}">${category.name}</option>
              `;
            })

            htmlContent += `
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td>처리사항</td>
                    <td>
                      <input name="resultContent" id="resultContent" type="text" value="${report.resultContent == null ? "" : report.resultContent}">
                    </td>
                  </tr>
            `;

            if (report.resultCategory == null) {
              htmlContent += `
                <tr>
                  <td colspan="2"><button class="btn report-btn" onclick="handleReport(${report.no});">처리</button></td>
                </tr>
              `;
            }

            htmlContent += `
                </tbody>
              </table>
            `;

            content_section.innerHTML = htmlContent;

            if (report.resultCategory != null) {
              document.getElementById("resultCategory.no").value = report.resultCategory.no;
            }
          })
          .catch(error => {
            console.error("error fetching report categories: ", error);
          })
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

    Swal.fire({
      text: "관리자 권한을 해제하시겠습니까?",
      showCancelButton: true,
      confirmButtonText: "확인"
    }).then((result) => {
  
      if (result.isConfirmed) {
        fetch(`/admin/updateAdmin?admin=0&userNo=${userNo}`)
          .then(response => response.text())
          .then(response => {
            if (response == "success") {
              Swal.fire({
                icon: "success",
                text: "해제 완료"
              });
            } else if (response == "failure") {
              element.value = 1;
              Swal.fire({
                icon: "error",
                text: "설정 중 오류 발생"
              });
            } else if (response == "inhibited") {
              Swal.fire({
                icon: "warning",
                text: "관리자 권한은 상위관리자만 설정 가능합니다."
              });
            } else if (response == "self-inhibited") {
              Swal.fire({
                icon: "warning",
                text: "본인 권한 수정 불가"
              });
            }
          })
          .catch(error => {
            console.error("error setting admin: ", error);
          })
      } else {
        element.value = 1;
      }
    })

  } else if (element.value == 1) {

    Swal.fire({
      text: "관리자 권한을 부여하시겠습니까?",
      showCancelButton: true,
      confirmButtonText: "확인"
    }).then((result) => {
  
      if (result.isConfirmed) {
        fetch(`/admin/updateAdmin?admin=1&userNo=${userNo}`)
          .then(response => response.text())
          .then(response => {
            if (response == "success") {
              Swal.fire({
                icon: "success",
                text: "설정 완료"
              });
            } else if (response == "failure") {
              element.value = 0;
              Swal.fire({
                icon: "error",
                text: "설정 중 오류 발생"
              });
            } else if (response == "inhibited") {
              Swal.fire({
                icon: "warning",
                text: "관리자 권한은 상위관리자만 설정 가능합니다."
              });
            } else if (response == "self-inhibited") {
              Swal.fire({
                icon: "warning",
                text: "본인 권한 수정 불가"
              });
            }
          })
          .catch(error => {
            console.error("error setting admin: ", error);
          })
      } else {
        element.value = 0;
      }
    })
  }

}

function handleReport(reportNo) {

  Swal.fire({
    text: "처리하시겠습니까?",
    showCancelButton: true,
    confirmButtonText: "확인"
  }).then((result) => {

    if (result.isConfirmed) {
      const formData = new FormData();
      const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
      const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

      document.getElementById("resultCategory.no").style = "border-color: #ccc";
      document.getElementById("resultContent").style = "border-color: #ccc";

      formData.set("no", reportNo);
      formData.set("resultCategory.no", document.getElementById("resultCategory.no").value);
      formData.set("resultContent", document.getElementById("resultContent").value.trim());

      if (formData.get("resultCategory.no") == 0) {
        document.getElementById("resultCategory.no").style = "border-color: red";
        return;
      }
      if (formData.get("resultContent") == "") {
        document.getElementById("resultContent").style = "border-color: red";
        return;
      }

      fetch(`/report/updateResult`, {
        method: "PUT",
        body: formData,
        headers: {
          [csrfHeader]: csrfToken
        }
      })
        .then(response => response.text())
        .then(response => {
          switch (response) {
            case "success":
              Swal.fire({
                icon: "success",
                text: "처리했습니다."
              });
              break;
            case "failure":
              Swal.fire({
                icon: "error",
                text: "처리 중 오류 발생"
              });
              break;
          }
        })
        .catch(error => {
          console.error("Error handling report result: ", error);
        });
    }

  })
}

function addMaincategory() {
  Swal.fire({
    text: "등록하시겠습니까?",
    showCancelButton: true,
    confirmButtonText: "확인"
  }).then((result) => {

    if (result.isConfirmed) {
      const nameInput = document.querySelector("#maincategory-name");
      const colorInput = document.querySelector("#maincategory-color");
      const formData = new FormData();

      // input.style = "border-color: #ccc";
      formData.append("name", nameInput.value.trim());
      formData.append("color", colorInput.value.trim());

      if (formData.get("name") == "") {
        Swal.fire({
          icon: "warning",
          text: "분류명을 입력해주세요."
        });
        return;
      }

      if (formData.get("name").length > 7) {
        Swal.fire({
          icon: "warning",
          text: "분류명은 최대 7자까지 가능합니다."
        });
        return;
      }
      
      if (formData.get("color") == "") {
        Swal.fire({
          icon: "warning",
          text: "색상을 입력해주세요."
        });
        return;
      }
      
      const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
      const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

      fetch(`/collection/maincategory/add`, {
        method: "POST",
        body: formData,
        headers: {
            [csrfHeader]: csrfToken
        }
      })
        .then(response => response.text())
        .then(response => {
            switch (response) {
                case "success":
                  Swal.fire({
                    icon: "success",
                    text: "등록했습니다."
                  });
                  document.querySelector("#category-admin").click();
                  break;
                case "exist":
                  Swal.fire({
                    icon: "warning",
                    text: "이미 등록된 이름입니다."
                  });
                  break;
                case "overSeven":
                  Swal.fire({
                    icon: "warning",
                    text: "분류명은 최대 7자까지 가능합니다."
                  });
                  break;
                case "failure":
                  Swal.fire({
                    icon: "error",
                    text: "등록 중 오류 발생"
                  });
                  break;
            }
        })
        .catch(error => {
            console.error("Error adding achievement: ", error);
        });
    }
  })
  
}

function addSubcategory(maincategoryNo) {

  Swal.fire({
    text: "등록하시겠습니까?",
    showCancelButton: true,
    confirmButtonText: "확인"
  }).then((result) => {

    if (result.isConfirmed) {
      const input = document.querySelector("#subcategory-name");
      const formData = new FormData();

      formData.append("name", input.value.trim());
      formData.append("maincategory.no", maincategoryNo);

      if (formData.get("name") != "") {
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        fetch(`/collection/subcategory/add`, {
          method: "POST",
          body: formData,
          headers: {
              [csrfHeader]: csrfToken
          }
        })
          .then(response => response.text())
          .then(response => {
              switch (response) {
                case "success":
                  fetchAdminDetail("category", maincategoryNo);
                  break;
                case "exist":
                  Swal.fire({
                    icon: "warning",
                    text: "이미 등록된 이름입니다."
                  });
                  break;
                case "failure":
                  Swal.fire({
                    icon: "error",
                    text: "등록 중 오류 발생"
                  });
                  break;
            }
        })
        .catch(error => {
            console.error("Error adding achievement: ", error);
        });
    }
    }
  })
}

function changeAchievementMainImage(element) {
    document.querySelector('#mainAchievementImage').src = element.src;
}


function addTempFiles(event) {
  const newFiles = Array.from(event.target.files);
  filesArray = [...filesArray, ...newFiles];
  previewImage(event);
}

function previewImage(event) {
  const files = event.target.files;

  if (files && files.length > 0) {

      if (document.querySelector(".collection-images").style.display == "none") {
          document.querySelector(".collection-images").style.display = "block";
          document.querySelector("#files-label").style.display = "none";
      }

      for (let i = 0; i < files.length; i++) {
          const reader = new FileReader();

          reader.onload = function (e) {
              const span = document.createElement("span");
              span.className = "new-image-span new-image-span-" + filesCounter;
              span.id = filesCounter;

              // <button class="collection-image-btn btn"
              //         data-th-onclick="'deleteFile(' + ${file.no} + ')'">⊗</button>

              const img = document.createElement("img");
              img.src = e.target.result;
              img.alt = "새 이미지";
              img.className = "new-image";
              img.onclick = () => changeAchievementMainImage(img.src);

              span.append(img);

              filesCounter++;
              document.querySelector(".thumbnail-images").insertBefore(span, document.querySelector(".new-image-btn"));
              document.querySelector(".new-image-btn").style.display = "none";
          }

          reader.readAsDataURL(files[i]);
      }

      

  } else {
      if (document.querySelector("#files-label")) {
          document.querySelector(".collection-images").style.display = "none";
          document.querySelector("#files-label").style.display = "block";
      }
  }
}