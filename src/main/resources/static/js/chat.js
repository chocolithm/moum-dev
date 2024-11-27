// 채팅
let stompChatClient = null;
let cachedSender = null;

// 소켓 통신 연결
function connect(chatroomNo) {
  return new Promise((resolve, reject) => {
    const socket = new SockJS("/ws");
    stompChatClient = StompJs.Stomp.over(socket);
    stompChatClient.connect({}, function (frame) {
      stompChatClient.subscribe(`/receive/chat/${chatroomNo}`, function (message) {
        showMessage(JSON.parse(message.body));
      });
      resolve();
    },
      function (error) {
        console.error("error connecting to chatroom: ", error);
        reject();
      });
  });
}

// 소켓 통신 연결 해제
function disconnect() {
  if (stompChatClient && stompChatClient.connected) {
    stompChatClient.disconnect(() => {
    });
  }
}

// 소켓 메시지 전송
async function sendMessage(chatroomNo) {
  const messageContent = document.getElementById("new-message");

  if (messageContent.value.trim() != "") {
    stompChatClient.send(`/send/chat/${chatroomNo}`, {}, JSON.stringify({
      sender: await getSender(),
      chatroom: {
        no: chatroomNo
      },
      message: messageContent.value
    }));
    messageContent.value = "";
  }
}

// 소켓 메시지 출력
async function showMessage(chat) {
  const chat_info = document.querySelector(".chat-info");
  const message_box = document.createElement("div");
  const loginUser = await getSender();

  createChatContent(message_box, loginUser, chat);

  chat_info.appendChild(message_box);

  if (chat.sender.no == loginUser.no) {
    chat_info.scrollTop = chat_info.scrollHeight;
  } else {
    if (chat_info.scrollTop > chat_info.scrollHeight - chat_info.offsetHeight - 500) {
      chat_info.scrollTop = chat_info.scrollHeight;
    } else {
      createNewMessageAlert();
    }
  }
}

function createNewMessageAlert() {
  const chat_info = document.querySelector(".chat-info");

  if (document.querySelector(".new-message-alert") == null) {
    const new_message_alert = document.createElement("div");
    new_message_alert.className = "new-message-alert";
    new_message_alert.append(document.createElement("span"));
    new_message_alert.childNodes[0].innerHTML = "new message";
    new_message_alert.onclick = () => {
      chat_info.scrollTop = chat_info.scrollHeight;
    };

    chat_info.addEventListener("scroll", removeNewMessageAlert);

    chat_info.append(new_message_alert);
    fadeIn(new_message_alert);
  }
}

function removeNewMessageAlert() {
  const chat_info = document.querySelector(".chat-info");
  const new_message_alert = document.querySelector(".new-message-alert");

  if (chat_info.scrollTop >= chat_info.scrollHeight - chat_info.offsetHeight - 50) {
    chat_info.removeEventListener("scroll", removeNewMessageAlert);
    fadeOut(new_message_alert);
    setTimeout(() => {
      new_message_alert.remove();
    }, 500);
  }
}

// 채팅 모달 열기
function openChatroomModal() {
  closeAlertModal();

  const chat_btn = document.querySelector(".chat-btn");
  const chatroom_layer = document.querySelector(".chatroom-layer");
  chatroom_layer.style.backgroundColor = "white";

  fetchChatroomList();
  chat_btn.onclick = () => closeChatroomModal();
  fadeIn(chatroom_layer);

  document.querySelector("main").addEventListener("onmousedown", function () {
    closeChatroomModal();
  }, { once: true });
}

// 채팅 모달 닫기
function closeChatroomModal() {
  const chat_btn = document.querySelector(".chat-btn");
  const chatroom_layer = document.querySelector(".chatroom-layer");

  disconnect();

  chat_btn.onclick = () => openChatroomModal();
  fadeOut(chatroom_layer);
  setTimeout(function () {
    chatroom_layer.innerHTML = "";
  }, 500);
}

// 채팅방 목록 로딩
function fetchChatroomList() {

  const chatroom_layer = document.querySelector(".chatroom-layer");
  chatroom_layer.style.backgroundColor = "white";

  fetch(`/chat/listRoom`)
    .then(response => response.json())
    .then(async data => {

      const loginUser = await getSender();

      if (data.length == 0) {
        const div = document.createElement("div");
        div.className = "no-room-message";
        div.innerHTML = "채팅 목록이 없습니다.";
        chatroom_layer.append(div);
      }

      data.forEach(chatroom => {
        const participant = loginUser.no == chatroom.participant.no ? chatroom.owner : chatroom.participant;

        const div = document.createElement("div");
        div.className = "chatroom";
        div.onclick = () => openChat(chatroom.no, participant);

        const userspan = document.createElement("span");
        userspan.className = "chatroom-user";

        const chatspan = document.createElement("span");
        chatspan.className = "chatroom-content";

        const img = document.createElement("img");
        img.src = participant.photo == null
          ? "/images/user/default1.png"
          : `https://kr.object.ncloudstorage.com/bitcamp-moum/user/profile/${participant.photo}`;
        img.alt = "프로필";
        img.className = "profile";

        const nickname = document.createElement("div")
        nickname.innerHTML = participant.endDate == null ? participant.nickname : "탈퇴회원";
        nickname.className = "nickname";

        userspan.append(img, nickname);

        const message = document.createElement("div");
        message.className = "message";
        message.innerHTML = chatroom.lastMessage;

        if (loginUser.no != chatroom.senderNo && chatroom.read == 0) {
          message.innerHTML = `<span class='unread-count'>${chatroom.count}</span> ` + message.innerHTML;
        }

        const date = document.createElement("div");
        date.className = "date";
        date.innerHTML = formatDate(chatroom.chatDate);

        chatspan.append(message, date);

        div.append(userspan, chatspan);

        chatroom_layer.append(div);
      });
    })
    .catch(error => {
      console.error("Error fetching subcategories:", error);
    });
}

// 채팅방 열기
function openChat(chatroomNo, participant) {

  if (chatroomNo == 0) {
    checkChatroom();

  } else {

    const chatroom_layer = document.querySelector(".chatroom-layer");
    const chatroom = chatroom_layer.childNodes;
    for (i = 0; i < chatroom.length; i++) {
      fadeOut(chatroom[i]);
    }

    setTimeout(async function () {
      chatroom_layer.innerHTML = "";
      chatroom_layer.style.backgroundColor = "lightgrey";

      try {
        await fetchChatroom(chatroomNo);
        await fetchChatContent(chatroomNo, 1);
        createChatInputbox(chatroomNo, participant);
        connect(chatroomNo);
        countAlert();
      } catch (error) {
        console.error("error fetching chatroom info", error);
      }
    }, 500);
  }
}

// 채팅방 닫기
function closeChat() {
  const chatroom_layer = document.querySelector(".chatroom-layer");

  disconnect();

  for (i = 0; i < chatroom_layer.childNodes.length; i++) {
    fadeOut(chatroom_layer.childNodes[i]);
  }

  setTimeout(function () {
    chatroom_layer.innerHTML = "";

    fetchChatroomList();
  }, 500);
}

// 새 채팅방 생성
function checkChatroom() {
  const urlParams = new URLSearchParams(window.location.search);
  const boardNo = urlParams.get('no');

  fetch(`/chat/checkRoom?boardNo=${boardNo}`)
    .then(response => response.json())
    .then(async chatroom => {
      if (chatroom.no == 0) {

        const loginUser = await getSender();
        if (loginUser.no == chatroom.board.userNo) {
          alert("내 게시글입니다.");
          return;
        }

        const chatroom_layer = document.querySelector(".chatroom-layer");
        for (i = 0; i < chatroom_layer.childNodes.length; i++) {
          fadeOut(chatroom_layer.childNodes[i]);
        }

        setTimeout(async function () {
          const chatroom_layer = document.querySelector(".chatroom-layer");
          chatroom_layer.innerHTML = "";

          const board_info = document.createElement("div");
          board_info.className = "board-info";

          createBoardInfo(board_info, chatroom);

          const chat_info = document.createElement("div");
          chat_info.className = "chat-info";

          chatroom_layer.append(board_info, chat_info);

          createChatInputbox(chatroom.no, chatroom.board.user);
        }, 500);

      } else {
        openChat(chatroom.no, chatroom.participant);
      }
    })
    .catch(error => {
      console.error("Error fetching new chatroom:", error);
    });
}

// 새 채팅방에서 채팅 입력 시 DB에 채팅방 생성
function createChatroom(boardNo) {
  fetch(`/chat/addRoom?boardNo=${boardNo}`)
    .then(response => response.json())
    .then(chatroom => {

      if (chatroom == null) {
        alert("생성 중 오류 발생");
        return;
      }

      connect(chatroom.no)
        .then(() => {
          sendMessage(chatroom.no);
          document.querySelector(".chatroom-layer .chat-btn").onclick = () => sendMessage(chatroom.no);
        });
    })
    .catch(error => {
      console.error("error creating chatroom", error);
    })
}

// 채팅방 정보 로딩
function fetchChatroom(chatroomNo) {
  const chatroom_layer = document.querySelector(".chatroom-layer");

  return new Promise((resolve, reject) => {
    fetch(`/chat/openRoom?no=${chatroomNo}`)
      .then(response => response.json())
      .then(chatroom => {

        const board_info = document.createElement("div");
        board_info.className = "board-info";

        createBoardInfo(board_info, chatroom);

        chatroom_layer.appendChild(board_info);

        resolve();
      })
      .catch(error => {
        console.error("Error fetching chatroom:", error);
        reject(error);
      });
  });

}

// 채팅방 상단 게시글 정보 생성
function createBoardInfo(board_info, chatroom) {
  const br = document.createElement("br");

  // const board_status = document.createElement("span");
  // board_status.className = "board-status";
  // board_status.innerHTML = chatroom.board.status == true ? "거래완료" : "거래중";

  const board_title = document.createElement("span");
  board_title.className = "board-title";
  board_title.innerHTML = chatroom.board.title;

  // 채팅 닫기 버튼
  const exit_btn = document.createElement("button");
  exit_btn.className = "chat-close-btn";
  exit_btn.innerHTML = "X";

  exit_btn.addEventListener("click", function () {
    closeChat();
  });

  // document.body.appendChild(exit_btn);

  const transaction_type = document.createElement("span");
  transaction_type.className = "transaction-type";
  transaction_type.innerHTML = chatroom.board.collection == null ? "구매" : "판매";

  const transaction_price = document.createElement("span");
  transaction_price.className = "transaction-price";
  transaction_price.innerHTML = chatroom.board.price != 0 ? chatroom.board.price + "원" : "가격 미정";

  const link = document.createElement("a");
  if (chatroom.board.deleted) {
    link.onclick = () => alert("삭제된 게시글입니다.");
    link.href = "#";
  } else {
    link.href = `/board/boardView?no=${chatroom.board.no}`;
  }
  
  // link.append(board_status, board_title, br, transaction_type, transaction_price);
  link.append(board_title, br, transaction_type, transaction_price);
  board_info.append(link, exit_btn);
}

// 채팅 데이터 로딩
function fetchChatContent(chatroomNo, pageNo) {
  const chatroom_layer = document.querySelector(".chatroom-layer");

  return new Promise((resolve, reject) => {
    fetch(`/chat/loadChat?no=${chatroomNo}&pageNo=${pageNo}`)
      .then(response => response.json())
      .then(async data => {

        const chat_info = document.createElement("div");
        chat_info.className = "chat-info";

        if (data != "") {
          const loginUser = await getSender();

          data.reverse().forEach(chat => {
            const message_box = document.createElement("div");

            createChatContent(message_box, loginUser, chat);

            chat_info.appendChild(message_box);
          })

          if (data.length == 20) {
            chat_info.setAttribute("onscroll", `fetchMoreChatContent(${chatroomNo}, ${pageNo + 1})`);
          }
        }

        chatroom_layer.appendChild(chat_info);
        chat_info.scrollTop = chat_info.scrollHeight;

        resolve();
      })
      .catch(error => {
        console.error("Error fetching chat data:", error);
        reject(error);
      });
  });
}

// 채팅 데이터 추가로딩
function fetchMoreChatContent(chatroomNo, pageNo) {
  const chat_info = document.querySelector(".chat-info");

  if (chat_info.scrollTop === 0) {

    return new Promise((resolve, reject) => {
      fetch(`/chat/loadChat?no=${chatroomNo}&pageNo=${pageNo}`)
        .then(response => response.json())
        .then(async data => {

          if (data != "") {
            const loginUser = await getSender();
            const previousScrollHeight = chat_info.scrollHeight;

            data.forEach(chat => {
              const message_box = document.createElement("div");

              createChatContent(message_box, loginUser, chat);

              chat_info.insertBefore(message_box, chat_info.firstChild);
            })

            const newScrollHeight = chat_info.scrollHeight;
            chat_info.scrollTop = newScrollHeight - previousScrollHeight;

            if (data.length == 20) {
              chat_info.setAttribute('onscroll', `fetchMoreChatContent(${chatroomNo}, ${pageNo + 1})`);
            } else {
              chat_info.removeAttribute('onscroll');
            }
          }

          resolve();
        })
        .catch(error => {
          console.error("Error fetching chat data:", error);
          reject(error);
        });
    });
  }
}

// 채팅 데이터 출력
function createChatContent(message_box, loginUser, chat) {
  const img = document.createElement("img");
  img.className = "chat-profile-img";
  img.src = chat.sender.photo == null
          ? "/images/user/default1.png"
          : `https://kr.object.ncloudstorage.com/bitcamp-moum/user/profile/${chat.sender.photo}`;

  const time = document.createElement("span");
  time.className = "message-time";
  time.innerHTML = calcTime(chat.chatDate);

  const message = document.createElement("span");
  message.className = "message";
  message.innerHTML = chat.message;

  const nickname = document.createElement("div");
  nickname.className = "nickname";
  nickname.innerHTML = chat.sender.nickname;

  if (chat.sender.no == loginUser.no) {
    message_box.append(time, message);
    message_box.className = "message-box owner-message-box";
  } else {
    const left_span = document.createElement("span");
    const right_span = document.createElement("span");
    left_span.append(img);
    right_span.append(nickname, message, time);
    message_box.append(left_span, right_span);
    message_box.className = "message-box guest-message-box";
  }
}

// 채팅 입력창 생성
function createChatInputbox(chatroomNo, participant) {
  const chatroom_layer = document.querySelector(".chatroom-layer");

  const chat_inputbox = document.createElement("div");
  chat_inputbox.className = "chat-inputbox";

  const input = document.createElement("textarea");
  input.rows = "2";
  input.id = "new-message";
  input.className = "new-message";
  input.placeholder = "메시지 입력"

  //채팅 보내기 버튼
  const btn = document.createElement("button");
  btn.innerHTML = "보내기";
  btn.className = "chat-btn btn btn-dark";

  if (participant.endDate != null) {
    btn.setAttribute("onclick", `alert("이미 탈퇴한 회원입니다.")`);
  } else if (chatroomNo == 0) {
    const urlParams = new URLSearchParams(location.search);
    const boardNo = urlParams.get('no');

    btn.setAttribute("onclick", `createChatroom(${boardNo})`);
  } else {
    btn.setAttribute("onclick", `sendMessage(${chatroomNo});`);
  }

  chat_inputbox.append(input, btn);
  chatroom_layer.appendChild(chat_inputbox);

  input.addEventListener("keydown", function (event) {
    if (event.key === "Enter" && !event.shiftKey) {
      event.preventDefault();
      btn.click();
    }
  });
}

// sender 로그인 정보 확인
async function getSender() {
  if (cachedSender) {
    return cachedSender;
  }

  try {
    const response = await fetch(`/chat/sender`);
    const user = await response.json();
    cachedSender = user;
    return user;
  } catch (error) {
    console.error("Error fetching sender:", error);
    return null;
  }
}
