// 공통


function openOverlay() {
  fadeIn(document.getElementsByClassName("overlay")[0]);
}

function closePopup() {
  fadeOut(document.getElementsByClassName("overlay")[0]);
  for (i = 0; i < document.getElementsByClassName("layer").length; i++) {
    document.getElementsByClassName("layer")[i].innerHTML = "";
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

function submitSignupForm() {
    document.getElementById('signupForm').submit();
}

// 페이지 로드 시 실행되는 함수
document.addEventListener('DOMContentLoaded', function() {
    // 회원가입 성공 후 로그인 모달 열기
    const urlParams = new URLSearchParams(window.location.search);
    const openLoginModal = urlParams.get('openLoginModal');
    if (openLoginModal === 'true') {
        openLoginModal();
    }
});

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

            initCollectionSlideIndex();
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

            document.addEventListener("DOMContentLoaded", function() {
                initCollectionSlideIndex();
                showSlides(collectionSlideIndex);
            });
        })
        .catch(error => {
            console.error("Error fetching collection view:", error);
        });
}

function initCollectionSlideIndex() {
    collectionSlideIndex = 0;
}

function showSlides(index) {
    const slides = document.querySelectorAll('.slide');
    if (index >= slides.length) { collectionSlideIndex = 0; }
    if (index < 0) { collectionSlideIndex = slides.length - 1; }

    slides.forEach((slide, i) => {
        slide.style.display = (i === collectionSlideIndex) ? 'block' : 'none';
    });
}

function changeSlide(n) {
    showSlides(collectionSlideIndex += n);
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
    const slider = document.querySelector(".slider");
    const slides = document.querySelector(".slides");
    const filenames = document.getElementById('filenames');

    if (files && files.length > 0) {
        slides.innerHTML = "";

        for (let i = 0; i < files.length; i++) {
            const reader = new FileReader();

            reader.onload = function(e) {
                const img = document.createElement('img');
                img.src = e.target.result;
                img.alt = "Uploaded Image";
                img.className = "collection-image slide";
                img.onclick = function() {
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
        if (files.length > 1) {
            document.querySelector(".prev").style.display = "block";
            document.querySelector(".next").style.display = "block";
        } else {
            document.querySelector(".prev").style.display = "none";
            document.querySelector(".next").style.display = "none";
        }

        Array.from(files).forEach(file => {
            const element = document.createElement("p");
            element.textContent = file.name;
            filenames.appendChild(element);
        });
    } else {
        if (empty) {
            empty.style.display = "block";
        }
        slider.style.display = "none";
        filenames.innerHTML = "";
    }
}

function filterCategories(element) {
    const items = document.getElementsByClassName(element.value);

    if (element.checked) {
        for(i = 0; i < items.length; i++) {
            fadeIn(items[i]);
        }
    } else {
        for(i = 0; i < items.length; i++) {
            fadeOut(items[i]);
        }
    }
}

function submitLoginForm() {
    document.getElementById('loginForm').submit();
}

function submitSignupForm() {
    document.getElementById('signupForm').submit();
}
