var socket = new SockJS('/chat');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/queue/messages', function(message) {
        var messageObj = JSON.parse(message.body);
        displayMessage(messageObj);
    });
});

function sendMessage() {
    var message = document.getElementById("messageInput").value;
    var senderOrder = 1; // 보내는 사람 ID
    var receiverOrder = 2; // 받는 사람 ID
    var messageObj = {
        senderOrder: senderOrder,
        receiverOrder: receiverOrder,
        message: message
    };
    stompClient.send("/app/send", {}, JSON.stringify(messageObj));
    document.getElementById("messageInput").value = ""; // 메시지 입력란 비우기
}

function displayMessage(message) {
    var messagesDiv = document.getElementById("messages");
    var messageHTML = "<p><strong>Sender:</strong> " + message.senderOrder + "<br><strong>Message:</strong> " + message.message + "</p>";
    messagesDiv.innerHTML += messageHTML;
}
/**
 * 전역변수
 */

/**
 * 이벤트 함수
 */

/**
 * 사용자 함수
 */
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