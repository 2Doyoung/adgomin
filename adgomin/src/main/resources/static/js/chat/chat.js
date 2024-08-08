/**
 * 전역변수
 */
//const socket = new SockJS('https://www.adgomin.com/chat');
const socket = new SockJS('/chat');
const stompClient = Stomp.over(socket);

const chatRoomOrder = document.getElementById("chatRoomOrder").value;
const userOrder = document.getElementById("userOrder").value;
const partnerOrder = document.getElementById("partnerOrder").value;

const chatRoom = document.getElementsByClassName("chat-room");
const chatMessageRoomChat = document.getElementById("chatMessageRoomChat");

const sendMessage = document.getElementById("sendMessage");

const messageInput = document.getElementById("messageInput");

const unreadMessage = document.getElementsByClassName("unread-message");

const myPostThumbnail = document.getElementById("myPostThumbnail");
const partnerPostThumbnail = document.getElementById("partnerPostThumbnail");

/**
 * 이벤트 함수
 */
for(let i = 0; i < chatRoom.length; i++) {
    chatRoom[i].addEventListener("click", (e) => {
        const chatRoomOrder = e.currentTarget.dataset.parent;

        let formData = new FormData();

        formData.append("isRead", '1');
        formData.append("chatRoomOrder", chatRoomOrder);
        xhr("/app/isRead", formData, "PATCH", "");

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

if(myPostThumbnail != null) {
    myPostThumbnail.addEventListener("click", (e) => {
        let mediaOrder = e.currentTarget.dataset.parent;

        window.open(appUrl + "/post?mediaOrder=" + mediaOrder);
    })
}

if(partnerPostThumbnail != null) {
    partnerPostThumbnail.addEventListener("click", (e) => {
        let mediaOrder = e.currentTarget.dataset.parent;

        window.open(appUrl + "/post?mediaOrder=" + mediaOrder);
    })
}

/**
 * 사용자 함수
 */
stompClient.debug = null
stompClient.connect({}, (frame) => {
    stompClient.subscribe('/user/queue/messages', (message) => {
        let messageObj = JSON.parse(message.body);
        if(chatRoomOrder == messageObj.chatRoomOrder) {
            displayMessage(messageObj);

            let formData = new FormData();

            formData.append("isRead", '1');
            formData.append("chatRoomOrder", chatRoomOrder);
            xhr("/app/isRead", formData, "PATCH", "");
        }
        updateChatList(messageObj);
        receiveChatNotification(messageObj);
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

let updateChatList = (message) => {
    let chatList = document.getElementById("chatList_" + message.chatRoomOrder);

    chatList.innerHTML = '';

    let chatItem = createChatItem(message);
    chatList.appendChild(chatItem);
}

let createChatItem = (message) => {
    let chatItem = document.createElement("div");
    chatItem.className = "chat-item";
    chatItem.dataset.senderOrder = message.senderOrder;

    let messageContent = document.createElement("span");
    messageContent.className = "message-content";
    messageContent.textContent = message.message;

    chatItem.appendChild(messageContent);
    return chatItem;
}

let unreadChatRooms = {};

let markChatRoomAsUnread = (chatRoomOrder) => {
    unreadChatRooms[chatRoomOrder] = true;

    updateChatRoomList();
}

let updateChatRoomList = () => {
    for (let i = 0; i < chatRoom.length; i++) {
        let datasetChatRoomOrder = chatRoom[i].dataset.parent;
        let unreadMessage = document.getElementsByClassName("unread-message");

        if(chatRoomOrder != datasetChatRoomOrder) {
            if (unreadChatRooms[datasetChatRoomOrder]) {
                unreadMessage[i].classList.add("unread");
            } else {
                unreadMessage[i].classList.remove("unread");
            }
        }
    }
}

let receiveChatNotification = (message) => {
    markChatRoomAsUnread(message.chatRoomOrder);
}

for(let i = 0; i < unreadMessage.length; i++) {
    let unreadMessageIsRead =  unreadMessage[i].dataset.parent;

    if(unreadMessageIsRead == 0) {
        unreadMessage[i].classList.add("unread");
    } else if(unreadMessageIsRead == 1) {
        unreadMessage[i].classList.remove("unread");
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