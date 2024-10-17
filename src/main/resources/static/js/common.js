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

function openLoginPopup() {
  openOverlay();
  fadeIn(document.getElementsByClassName("login-layer")[0]);
}

// 마이홈
function openCollectionAddPopup() {
    openOverlay();
    fadeIn(document.getElementsByClassName("collection-add-layer")[0]);
}

function openCollectionViewPopup(no) {
    openOverlay();
    fadeIn(document.getElementsByClassName("collection-view-layer")[0]);
}

