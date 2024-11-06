// 알림창 열기
function openAlertModal() {
  closeChatroomModal();
  countAlert();

  const alert_btn = document.querySelector(".alert-btn");
  const alert_layer = document.querySelector(".alert-layer");

  fetchAlertContent(1);

  fadeIn(alert_layer);
  alert_btn.onclick = () => closeAlertModal();

  document.querySelector("main").addEventListener("click", function () {
    closeAlertModal();
  }, { once: true });
}

// 알림창 닫기
function closeAlertModal() {
  countAlert();
  const alert_btn = document.querySelector(".alert-btn");
  const alert_layer = document.querySelector(".alert-layer");

  alert_btn.onclick = () => openAlertModal();
  fadeOut(alert_layer);
  setTimeout(() => {
    alert_layer.innerHTML = "";
  }, 500);
}

// 알림 목록 로딩
function fetchAlertContent(pageNo) {
  fetch(`/alert/listByUser?pageNo=${pageNo}`)
    .then(response => response.json())
    .then(alerts => {
      if (alerts && alerts.length > 0) {
        const alert_layer = document.querySelector(".alert-layer");

        alerts.forEach(alert => {
          const box = document.createElement("div");

          createAlertBoxContent(alert, box);

          alert_layer.append(box);
          alert_layer.setAttribute('onscroll', `fetchMoreAlertContent(2)`);
        })
      }
    })
    .catch(error => {
      console.error("error fetching alerts", error);
    })
}

// 알림 목록 추가로딩
function fetchMoreAlertContent(pageNo) {
  const alert_layer = document.querySelector(".alert-layer");

  if (alert_layer.scrollTop > alert_layer.scrollHeight - alert_layer.offsetHeight - 50) {
    console.log("데이터 추가로딩");
    fetch(`/alert/listByUser?pageNo=${pageNo}`)
      .then(response => response.json())
      .then(alerts => {
        if (alerts && alerts.length > 0) {
          const alert_layer = document.querySelector(".alert-layer");

          alerts.forEach(alert => {
            const box = document.createElement("div");

            createAlertBoxContent(alert, box);

            alert_layer.append(box);
          })

          if (alerts.length == 20) {
            alert_layer.setAttribute('onscroll', `fetchMoreAlertContent(${pageNo + 1})`);
          } else {
            alert_layer.removeAttribute('onscroll');
          }
        }
      })
      .catch(error => {
        console.error("error fetching alerts", error);
      })
  }
}

// 알림 목록 구조 생성
function createAlertBoxContent(alert, box) {
  box.className = alert.read == 1 ? "alert-box read" : "alert-box unread";
  box.id = `alert-${alert.no}`;

  const content = document.createElement("span");
  content.className = "alert-content";
  content.innerHTML = alert.content;

  const time = document.createElement("span");
  time.className = "alert-time";
  time.innerHTML = calcTime(alert.date);

  content.addEventListener('mouseenter', () => {
    box.classList.add('active');
  });

  content.addEventListener('mouseleave', () => {
    box.classList.remove('active');
  });

  time.addEventListener('mouseenter', () => {
    box.classList.add('active');
  });

  time.addEventListener('mouseleave', () => {
    box.classList.remove('active');
  });

  const delete_btn = document.createElement("a");
  delete_btn.className = "alert-delete-btn";
  delete_btn.innerText = "x";
  delete_btn.onclick = () => deleteAlert(event, alert.no);

  box.append(content, time, delete_btn);
  box.onclick = () => {
    console.log(alert);
    if (alert.read == 1) {
      executeAlert(alert);

    } else {
      updateAlertRead(alert);
    }
  }
}

// 읽음 처리
function updateAlertRead(alert) {
  fetch(`/alert/read?no=${alert.no}`)
    .then((result) => {
      if (result.ok) {
        executeAlert(alert);
      }
    });
}

// 알림 링크 실행
function executeAlert(alert) {
  if (alert.category == "chatroom") {
    openChatroomModal();
    openChat(alert.categoryNo);
  } else if (alert.category == "board") {
    location.href = `/board/boardView?no=${alert.categoryNo}`;
  }
}

// 알림 삭제
function deleteAlert(event, alertNo) {
  event.stopPropagation();
  fetch(`/alert/delete?no=${alertNo}`)
    .then(response => response.text())
    .then(response => {
      if (response == "success") {
        const box = document.querySelector(`#alert-${alertNo}`);
        box.remove();
        countAlert();
      } else {
        alert("삭제가 불가합니다. 다시 시도해 주세요.");
      }
    })
    .catch(error => {
      console.error("error deleting alert: ", error);
    })
}

// 알림 개수
function countAlert() {
  fetch(`/alert/count`)
    .then(response => response.text())
    .then(count => {
      if (count > 0) {
        document.querySelector(".alert-count").innerHTML = count;
      } else {
        document.querySelector(".alert-count").innerHTML = "";
      }
    })
    .catch(error => {
      console.error("error fetching alert counts: ", error);
      return 0;
    })
}

// 화면 로딩 시 알림 개수 카운트
window.onload = async () => {
  countAlert();
}