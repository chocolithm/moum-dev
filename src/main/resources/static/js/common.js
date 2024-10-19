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

// 마이홈
function openCollectionFormPopup() {
    fetchCollectionForm();

    openOverlay();
    fadeIn(document.querySelector(".collection-form-layer"));
}

function openCollectionViewPopup(no) {
    openOverlay();
    fadeIn(document.getElementsByClassName("collection-view-layer")[0]);
}

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

