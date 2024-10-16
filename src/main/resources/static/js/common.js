function openOverlay() {
  fadeIn(document.getElementsByClassName("overlay")[0]);
}

function openLoginPopup() {
  openOverlay();
  fadeIn(document.getElementsByClassName("login_layer")[0]);
}

function closePopup() {
  fadeOut(document.getElementsByClassName("overlay")[0]);
  fadeOut(document.getElementsByClassName("layer")[0]);
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