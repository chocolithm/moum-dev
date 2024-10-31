


function openAlertModal() {
  const alert_btn = document.querySelector(".alert-btn");
  const alert_layer = document.querySelector(".alert-layer");

  fadeIn(alert_layer);
  alert_btn.onclick = () => closeAlertModal();
}

function closeAlertModal() {
  const alert_btn = document.querySelector(".alert-btn");
  const alert_layer = document.querySelector(".alert-layer");

  fadeOut(alert_layer);
  alert_btn.onclick = () => openAlertModal();
}