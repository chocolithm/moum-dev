// 공통

function openOverlay() {
  fadeIn(document.getElementsByClassName("overlay")[0]);
}

function closePopup() {
  fadeOut(document.getElementsByClassName("overlay")[0]);
  for (i = 0; i < document.getElementsByClassName("layer").length; i++) {
    fadeOut(document.getElementsByClassName("layer")[i]);
  }
}

function fadeIn(element) {
  element.style.display = "block";
  setTimeout(function () {
    element.style.opacity = 1;
  }, 10);
}

function fadeOut(element) {
  element.style.opacity = 0;
  setTimeout(function () {
    element.style.display = "none";
  }, 500);
}

// 로그인

var modal = document.getElementById("loginModal");
var btn = document.getElementById("openModalBtn");

function openLoginModal() {
    var modal = document.getElementById("loginModal");
    modal.style.display = "block";
}

window.onclick = function(event) {
    var modal = document.getElementById("loginModal");
    if (event.target === modal) {
        closeLoginModal();
    }
}


function closeLoginModal() {
    var modal = document.getElementById("loginModal");
    modal.style.display = "none";
}


function openLoginPopup() {
  openOverlay();
  fadeIn(document.getElementsByClassName("login-layer")[0]);
}

function openSignupModal() {
    var modal = document.getElementById("signupModal");
    modal.style.display = "block";
}

function closeSignupModal() {
    var modal = document.getElementById("signupModal");
    modal.style.display = "none";
}

window.onclick = function(event) {
    var loginModal = document.getElementById("loginModal");
    var signupModal = document.getElementById("signupModal");
    if (event.target === loginModal) {
        closeLoginModal();
    }
    if (event.target === signupModal) {
        closeSignupModal();
    }
}

// 마이홈

  // 수집품 등록 화면 열기
function openCollectionFormPopup() {
    fetchCollectionForm();
    openOverlay();
    fadeIn(document.querySelector(".collection-form-layer"));
}

  // 수집품 조회 화면 열기
function openCollectionViewPopup(no) {
    fetchCollectionView(no);
    openOverlay();
    fadeIn(document.getElementsByClassName("collection-view-layer")[0]);
}

  // 수집품 등록 화면 내용 가져오기
function fetchCollectionForm() {
    fetch(`/collection/form`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');

            document.querySelector('.collection-form-layer').innerHTML =
                doc.querySelector('.collection-form-layer').innerHTML;
        })
        .catch(error => {
            console.error("Error fetching collection form:", error);
        });
}

  // 수집품 조회 화면 내용 가져오기
function fetchCollectionView(no) {
    fetch(`/collection/view?no=${no}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');

            document.querySelector('.collection-view-layer').innerHTML =
                doc.querySelector('.collection-view-layer').innerHTML;
        })
        .catch(error => {
            console.error("Error fetching collection view:", error);
        });
}

  // 소분류 데이터 가져오기
function fetchSubcategories(maincategoryNo) {
    const subcategorySelect = document.getElementById("subcategoryNo");
    subcategorySelect.innerHTML = "";

    if (maincategoryNo == 0) {
        subcategorySelect.disabled = true;
    } else if (maincategoryNo > 0) {
        subcategorySelect.disabled = false;
        fetch(`/subcategory/list?maincategoryNo=${maincategoryNo}`)
                .then(response => response.json())
                .then(data => {
                    data.forEach(subcategory => {
                        const option = document.createElement("option");
                        option.value = subcategory.no;
                        option.text = subcategory.name;
                        subcategorySelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error("Error fetching subcategories:", error);
                });
    }
}


function triggerFileInput() {
    document.getElementById('files').click();
}

// 선택한 이미지 미리보기
function previewImage(event) {
    const files = event.target.files;
    const empty = document.getElementById("empty-image");
    const preview = document.getElementById("preview");
    const filenames = document.getElementById('filenames');

    if (files && files.length > 0) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            empty.style.display = "none";
            preview.style.display = "block";
        }
        reader.readAsDataURL(files[0]);

        filenames.innerHTML = "";
        Array.from(files).forEach(file => {
            const element = document.createElement("p");
            element.textContent = file.name;
            filenames.appendChild(element);
        });
    } else {
        empty.style.display = "block";
        preview.style.display = "none";
        filenames.innerHTML = "";
    }
}
