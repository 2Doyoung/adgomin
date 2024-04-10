/**
 * 전역변수
 */
const socket = new SockJS('/chat');
const stompClient = Stomp.over(socket);

const chatRoomOrder = document.getElementById("chatRoomOrder").value;
const userOrder = document.getElementById("userOrder").value;
const partnerOrder = document.getElementById("partnerOrder").value;

const chatRoom = document.getElementsByClassName("chat-room");
const chatMessageRoomChat = document.getElementById("chatMessageRoomChat");

const sendMessage = document.getElementById("sendMessage");

const messageInput = document.getElementById("messageInput");

/**
 * 이벤트 함수
 */
for(let i = 0; i < chatRoom.length; i++) {
    chatRoom[i].addEventListener("click", (e) => {
        const chatRoomOrder = e.currentTarget.dataset.parent;

        window.location.href = "/app/chat?chatRoomOrder=" + chatRoomOrder;
    })
}

if(sendMessage != null){
    sendMessage.addEventListener("click", () => {
        let message = document.getElementById("messageInput").value;
        let messageTrim = document.getElementById("messageInput").value.trim();
        if(messageTrim == '') {

        } else if(messageTrim != '') {
            let messageObj = {
                chatRoomOrder : chatRoomOrder,
                senderOrder: userOrder,
                receiverOrder: partnerOrder,
                message: (message).replace(/\n/g, "<br>"),
            };
            stompClient.send("/app/send", {}, JSON.stringify(messageObj));
            document.getElementById("messageInput").value = "";
            sendMessage.classList.add("disabled");
        }
    })
}

if(messageInput != null) {
    messageInput.addEventListener("keydown", (e) => {
        if (e.shiftKey && e.keyCode === 13) {
            document.getElementById("sendMessage").click();

            e.preventDefault();
        }
    })

    messageInput.addEventListener("keyup", (e) => {
        let message = document.getElementById("messageInput").value.trim();
        if(message == '') {
            sendMessage.classList.add("disabled");
        } else if(message != '') {
            sendMessage.classList.remove("disabled");
        }
    })
}

/**
 * 사용자 함수
 */
stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/queue/messages', (message) => {
        let messageObj = JSON.parse(message.body);
        if(chatRoomOrder == messageObj.chatRoomOrder) {
            displayMessage(messageObj);
        }
    });
});

let displayMessage = (message) => {
    let today = new Date();

    let year = (today.getFullYear() + '').slice(-2);
    let month = ('0' + (today.getMonth() + 1)).slice(-2);
    let day = ('0' + today.getDate()).slice(-2);

    let hours = ('0' + today.getHours()).slice(-2);
    let minutes = ('0' + today.getMinutes()).slice(-2);

    let date = year + "." + month + "." + day;
    let time = hours + ":" + minutes

    let messagesDiv = document.getElementById("messages");
    let messageHTML = "";
    if (message.senderOrder == userOrder) {
        messageHTML = "<div class='chat-my-message'>" +
                            "<div class='chat-my-message-timestamp'>" +
                                "<div>" + date + "</div>" +
                                "<div>" + time + "</div>" +
                            "</div>" +
                            "<span>" + (message.message).replace(/\n/g, "<br>") + "</span>" +
                       "</div>";
    } else {
        messageHTML = "<div class='chat-partner-message'>" +
                            "<span>" + (message.message).replace(/\n/g, "<br>") + "</span>" +
                            "<div class='chat-partner-message-timestamp'>" +
                                "<div>" + date + "</div>" +
                                "<div>" + time + "</div>" +
                            "</div>" +
                      "</div>";
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