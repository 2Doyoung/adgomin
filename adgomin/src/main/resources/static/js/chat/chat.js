/**
 * 전역변수
 */
const chatRoomOrder = document.getElementById("chatRoomOrder").value;
const userOrder = document.getElementById("userOrder").value;
const partnerOrder = document.getElementById("partnerOrder").value;

const chatRoom = document.getElementsByClassName("chat-room");
const chatMessageRoomChat = document.getElementById("chatMessageRoomChat");

/**
 * 이벤트 함수
 */
for(let i = 0; i < chatRoom.length; i++) {
    chatRoom[i].addEventListener("click", (e) => {
        const chatRoomOrder = e.currentTarget.dataset.parent;

        window.location.href = "/app/chat?chatRoomOrder=" + chatRoomOrder;
    })
}

/**
 * 사용자 함수
 */
const socket = new SockJS('/chat');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/queue/messages', (message) => {
        let messageObj = JSON.parse(message.body);
        displayMessage(messageObj);
    });
});

let sendMessage = () => {
    let message = document.getElementById("messageInput").value;
    let messageObj = {
        chatRoomOrder : chatRoomOrder,
        senderOrder: userOrder,
        receiverOrder: partnerOrder,
        message: message
    };
    stompClient.send("/app/send", {}, JSON.stringify(messageObj));
    document.getElementById("messageInput").value = ""; // 메시지 입력란 비우기
}

let displayMessage = (message) => {
    let messagesDiv = document.getElementById("messages");
    let messageHTML = "";
    if (message.senderOrder == userOrder) {
        messageHTML = "<div class='chat-my-message'><span>" + message.message + "</span></div>";
    } else {
        messageHTML = "<div class='chat-partner-message'><span>" + message.message + "</span></div>";
    }
    messagesDiv.innerHTML += messageHTML;
    scrollToBottom();
}

let scrollToBottom = () => {
    let chatMessageRoomChat = document.getElementById("chatMessageRoomChat");
    chatMessageRoomChat.scrollTop = chatMessageRoomChat.scrollHeight;
}

if(chatMessageRoomChat != null) {
    chatMessageRoomChat.scrollTop = chatMessageRoomChat.scrollHeight;
}

if(chatRoomOrder != '') {
    for(let i = 0; i < chatRoom.length; i++) {
        if(chatRoomOrder == chatRoom[i].dataset.parent) {
            chatRoom[i].style.backgroundColor = "#FFFFFF";
        }
    }
}

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {

}

/**
 * XMLHttpRequest default 함수
 */
let defaultXhr = (responseObject, flag) => {

}

/**
 * XMLHttpRequest 함수
 */
let xhr = (url, formData, method, flag) => {
    const xhr = new XMLHttpRequest();

    xhr.open(method, url);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject['result']) {
                    case 'success':
                        successXhr(responseObject, flag);
                        break;
                    default:
                        defaultXhr(responseObject, flag);
                }
            } else {
                alert('서버와 통신하지 못하였습니다.');
            }
        }
    }
    xhr.send(formData);
}