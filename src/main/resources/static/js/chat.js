// 채팅
function openChatroomPopup() {
  const chat_btn = document.querySelector(".chat-btn");
  const chatroom_layer = document.querySelector(".chatroom-layer");
  fetchChatroomList();
  chat_btn.setAttribute("onClick", "closeChatroomPopup()");
  fadeIn(chatroom_layer);
}

function closeChatroomPopup() {
  const chat_btn = document.querySelector(".chat-btn");
  const chatroom_layer = document.querySelector(".chatroom-layer");

  chat_btn.setAttribute("onClick", "openChatroomPopup()");
  fadeOut(chatroom_layer)
  setTimeout(function () {
    chatroom_layer.innerHTML = "";
  }, 500);
}

function fetchChatroomList() {

  const chatroom_layer = document.querySelector(".chatroom-layer");

  fetch(`/chat/listRoom`)
    .then(response => response.json())
    .then(data => {

      data.forEach(chatroom => {
        const div = document.createElement("div");
        div.className = "chatroom";
        div.setAttribute("onclick", `openChat(${chatroom.no})`);

        const userspan = document.createElement("span");
        const chatspan = document.createElement("span");
        userspan.className = "chatroom-user";
        chatspan.className = "chatroom-content";

        const img = document.createElement("img");
        img.src = chatroom.user.photo == null ? "/images/user/default1.png" : "/images/user/default2.png";
        img.alt = "프로필";
        img.className = "profile"

        const nickname = document.createElement("div")
        nickname.innerHTML = chatroom.user.nickname;
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
  const chatroom = document.getElementsByClassName("chatroom");
  for (i = 0; i < chatroom.length; i++) {
    fadeOut(chatroom[i]);
  }

  setTimeout(async function () {
    chatroom_layer.innerHTML = "";

    try {
      await fetchChatroom(chatroomNo);
      await fetchChatContent(chatroomNo, 1);
      createChatInputbox();
    } catch (error) {
      console.error("error fetching chatroom info", error);
    }

  }, 500);

}

function fetchChatroom(chatroomNo) {
  const chatroom_layer = document.querySelector(".chatroom-layer");

  return new Promise((resolve, reject) => {
    fetch(`/chat/openRoom?no=${chatroomNo}`)
      .then(response => response.json())
      .then(data => {

        const board_info = document.createElement("div");
        board_info.className = "board-info";

        const br = document.createElement("br");

        const board_status = document.createElement("span");
        board_status.className = "board-status";
        board_status.innerHTML = data.board.status == true ? "거래완료" : "거래중";

        const board_title = document.createElement("span");
        board_title.className = "board-title";
        board_title.innerHTML = data.board.title;

        const transaction_type = document.createElement("span");
        transaction_type.className = "transaction-type";
        transaction_type.innerHTML = data.board.collection == null ? "구매" : "판매";

        const transaction_price = document.createElement("span");
        transaction_price.className = "transaction-price";
        transaction_price.innerHTML = data.board.price != 0 ? data.board.price + "원" : "가격 미정";

        board_info.appendChild(board_status);
        board_info.appendChild(board_title);
        board_info.appendChild(br);
        board_info.appendChild(transaction_type);
        board_info.appendChild(transaction_price);

        chatroom_layer.appendChild(board_info);

        resolve();
      })
      .catch(error => {
        console.error("Error fetching chatroom:", error);
        reject(error);
      });
  });

}

function fetchChatContent(chatroomNo, pageNo) {
  const chatroom_layer = document.querySelector(".chatroom-layer");

  return new Promise((resolve, reject) => {
    fetch(`/chat/loadChat?no=${chatroomNo}&pageNo=${pageNo}`)
      .then(response => response.json())
      .then(data => {

        const chat_info = document.createElement("div");
        chat_info.className = "chat-info";

        const br = document.createElement("br");

        data.reverse().forEach(chat => {
          const message_box = document.createElement("div");

          if (chat.user.no == chat.chatroom.user.no) {
            const nickname = document.createElement("div");
            nickname.className = "nickname";
            nickname.innerHTML = chat.user.nickname;
            const message = document.createElement("span");
            message.className = "message";
            message.innerHTML = chat.message;

            message_box.appendChild(nickname);
            message_box.appendChild(message);
            message_box.className = "message-box guest-message-box";

          } else {
            const message = document.createElement("span");
            message.className = "message";
            message.innerHTML = chat.message;

            message_box.appendChild(message);
            message_box.className = "message-box owner-message-box";
          }

          chat_info.appendChild(message_box);
        })

        chatroom_layer.appendChild(chat_info);

        resolve();
      })
      .catch(error => {
        console.error("Error fetching chatroom:", error);
        reject(error);
      });
  });

}

function createChatInputbox() {
  const chatroom_layer = document.querySelector(".chatroom-layer");

  const chat_inputbox = document.createElement("div");
  chat_inputbox.className = "chat-inputbox";

  const input = document.createElement("textarea");
  input.rows = "2";
  input.className = "new-message";

  const btn = document.createElement("button");
  btn.innerHTML = "보내기";
  btn.className = "chat-btn";


  chat_inputbox.appendChild(input);
  chat_inputbox.appendChild(btn);

  chatroom_layer.appendChild(chat_inputbox);
}