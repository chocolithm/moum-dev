// 채팅 

let stompClient = null;

// 채팅

function connect(chatroomNo) {
  const socket = new SockJS("/ws");
  stompClient = StompJs.Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    stompClient.subscribe(`/chat/receive/${chatroomNo}`, function (message) {
      console.log(message);
      showMessage(JSON.parse(message.body));
    });
  });
}

async function sendMessage(chatroomNo) {
  const messageContent = document.getElementById("new-message");
  if (messageContent.value != "") {
    stompClient.send(`/chat/send/${chatroomNo}`, {}, JSON.stringify({
      sender: await getSender(),
      chatroom: {
        no: chatroomNo
      },
      message: messageContent.value
    }));
    messageContent.value = "";
  }
}

async function showMessage(chat) {
  const chat_info = document.querySelector(".chat-info");
  const message_box = document.createElement("div");
  const loginUser = await getSender();

  createChatContent(message_box, loginUser, chat);

  chat_info.appendChild(message_box);

  if (chat.sender.no == loginUser.no) {
    chat_info.scrollTop = chat_info.scrollHeight;
  }
}

function openChatroomModal() {
  const chat_btn = document.querySelector(".chat-btn");
  const chatroom_layer = document.querySelector(".chatroom-layer");

  const urlParams = new URLSearchParams(window.location.search);
  const boardNo = urlParams.get('no');
  if (boardNo != null) {
    const open_chat_btn = document.createElement("button");
    open_chat_btn.className = "open-chat-btn btn";
    open_chat_btn.innerHTML = "채팅하기";
    open_chat_btn.setAttribute("onclick", "openChat(0)");
    chatroom_layer.appendChild(open_chat_btn);
  }

  fetchChatroomList();
  chat_btn.setAttribute("onClick", "closeChatroomModal()");
  fadeIn(chatroom_layer);
}

function closeChatroomModal() {
  const chat_btn = document.querySelector(".chat-btn");
  const chatroom_layer = document.querySelector(".chatroom-layer");

  chat_btn.setAttribute("onClick", "openChatroomModal()");
  fadeOut(chatroom_layer)
  setTimeout(function () {
    chatroom_layer.innerHTML = "";
  }, 500);
}

// 채팅방 목록 로딩
function fetchChatroomList() {

  const chatroom_layer = document.querySelector(".chatroom-layer");

  fetch(`/chat/listRoom`)
    .then(response => response.json())
    .then(async data => {

      const loginUser = await getSender();

      data.forEach(chatroom => {
        const div = document.createElement("div");
        div.className = "chatroom";
        div.setAttribute("onclick", `openChat(${chatroom.no})`);

        const userspan = document.createElement("span");
        const chatspan = document.createElement("span");
        userspan.className = "chatroom-user";
        chatspan.className = "chatroom-content";

        const participant = loginUser.no == chatroom.participant.no ? chatroom.owner : chatroom.participant;

        const img = document.createElement("img");
        img.src = participant.photo == null ? "/images/user/default1.png" : "/images/user/default2.png";
        img.alt = "프로필";
        img.className = "profile";

        const nickname = document.createElement("div")
        nickname.innerHTML = participant.nickname;
        nickname.className = "nickname";

        userspan.appendChild(img);
        userspan.appendChild(nickname);

        const message = document.createElement("div");
        message.className = "message";
        message.innerHTML = chatroom.lastMessage;
        const date = document.createElement("div");
        date.className = "date";
        date.innerHTML = formatDate(chatroom.chatDate);

        chatspan.appendChild(message);
        chatspan.appendChild(date);

        div.appendChild(userspan);
        div.appendChild(chatspan);

        chatroom_layer.appendChild(div);
      });
    })
    .catch(error => {
      console.error("Error fetching subcategories:", error);
    });
}

function openChat(chatroomNo) {
  const chatroom_layer = document.querySelector(".chatroom-layer");
  const chatroom = chatroom_layer.childNodes;
  for (i = 0; i < chatroom.length; i++) {
    fadeOut(chatroom[i]);
  }

  setTimeout(async function () {
    chatroom_layer.innerHTML = "";

    if (chatroomNo == 0) {
      checkChatroom();

    } else {
      try {
        await fetchChatroom(chatroomNo);
        await fetchChatContent(chatroomNo, 1);
        createChatInputbox(chatroomNo);
        connect(chatroomNo);
      } catch (error) {
        console.error("error fetching chatroom info", error);
      }
    }
  }, 500);
}

function closeChat() {
  const chatroom_layer = document.querySelector(".chatroom-layer");
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
        const chatroom_layer = document.querySelector(".chatroom-layer");

        const board_info = document.createElement("div");
        board_info.className = "board-info";

        createBoardInfo(board_info, chatroom);

        const chat_info = document.createElement("div");
        chat_info.className = "chat-info";

        chatroom_layer.appendChild(board_info);
        chatroom_layer.appendChild(chat_info);

        createChatInputbox(chatroom.no);

      } else {

        try {
          openChat(chatroom.no);
        } catch (error) {
          console.error("error fetching chatroom info", error);
        }

      }
    })
    .catch(error => {
      console.error("Error fetching new chatroom:", error);
    });
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

function createBoardInfo(board_info, chatroom) {
  console.log(chatroom);

  const br = document.createElement("br");

  const board_status = document.createElement("span");
  board_status.className = "board-status";
  board_status.innerHTML = chatroom.board.status == true ? "거래완료" : "거래중";

  const board_title = document.createElement("span");
  board_title.className = "board-title";
  board_title.innerHTML = chatroom.board.title;

  const exit_btn = document.createElement("img");
  exit_btn.className = "x";
  exit_btn.alt = "닫기";
  exit_btn.src = "/images/common/x_bg_black.png";
  exit_btn.setAttribute("onclick", "closeChat()");

  const transaction_type = document.createElement("span");
  transaction_type.className = "transaction-type";
  transaction_type.innerHTML = chatroom.board.collection == null ? "구매" : "판매";

  const transaction_price = document.createElement("span");
  transaction_price.className = "transaction-price";
  transaction_price.innerHTML = chatroom.board.price != 0 ? chatroom.board.price + "원" : "가격 미정";

  board_info.appendChild(board_status);
  board_info.appendChild(board_title);
  board_info.appendChild(exit_btn);
  board_info.appendChild(br);
  board_info.appendChild(transaction_type);
  board_info.appendChild(transaction_price);
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
    console.log('채팅데이터를 추가로 가져옵니다.');

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

// 채팅창 생성
function createChatContent(message_box, loginUser, chat) {
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

    message_box.appendChild(time);
    message_box.appendChild(message);
    message_box.className = "message-box owner-message-box";

  } else {

    message_box.appendChild(nickname);
    message_box.appendChild(message);
    message_box.appendChild(time);
    message_box.className = "message-box guest-message-box";
  }
}

// 채팅 입력창 생성
function createChatInputbox(chatroomNo) {
  const chatroom_layer = document.querySelector(".chatroom-layer");

  const chat_inputbox = document.createElement("div");
  chat_inputbox.className = "chat-inputbox";

  const input = document.createElement("textarea");
  input.rows = "2";
  input.id = "new-message";
  input.className = "new-message";

  const btn = document.createElement("button");
  btn.innerHTML = "보내기";
  btn.className = "chat-btn";
  if (chatroomNo == 0) {
    const urlParams = new URLSearchParams(window.location.search);
    const boardNo = urlParams.get('no');

    btn.setAttribute("onclick", `createChatroom(${boardNo})`);
  } else {
    btn.setAttribute("onclick", `sendMessage(${chatroomNo});`);
  }

  chat_inputbox.appendChild(input);
  chat_inputbox.appendChild(btn);

  chatroom_layer.appendChild(chat_inputbox);

  input.addEventListener("keydown", function (event) {

    if (event.key === "Enter" && !event.shiftKey) {
      event.preventDefault();
      btn.click();
    }

  });
}

function createChatroom(boardNo) {
  fetch(`/chat/addRoom?boardNo=${boardNo}`)
    .then(response => response.json())
    .then(async chatroom => {

      if (chatroom == null) {
        alert("생성 중 오류 발생");
        return;
      }
      await connect(chatroom.no);
      console.log("after connect");
      sendMessage(chatroom.no);
      const btn = document.querySelector(".chat-btn");
      btn.setAttribute("onclick", `sendMessage(${chatroom.no});`);
    })
    .catch(error => {
      console.error("error creating chatroom", error);
    })
}



// 채팅 시간 계산
function calcTime(chatDate) {
  const now = new Date();
  const date = new Date(chatDate);
  if (now.getFullYear() === date.getFullYear()) {
    if (now.getDate() === date.getDate()) {
      return date.getHours().toString().padStart(2, '0') + ":" + date.getMinutes().toString().padStart(2, '0');
    }

    return (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours().toString().padStart(2, '0') + ":" + date.getMinutes().toString().padStart(2, '0');
  } else {
    return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();
  }
}

// sender 로그인 정보 확인
function getSender() {
  return fetch(`/chat/sender`)
    .then(response => response.json())
    .then(user => {
      return user;
    })
    .catch(error => {
      console.error("Error fetching sender:", error);
    });
}