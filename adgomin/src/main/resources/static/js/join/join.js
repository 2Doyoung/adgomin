/**
 * 전역변수
 */
const joinServiceMedia = document.getElementById("joinServiceMedia");
const joinServiceAdvertiser = document.getElementById("joinServiceAdvertiser")

/**
 * 이벤트 함수
 */
if(joinServiceMedia !== null) {
    joinServiceMedia.addEventListener("click", () => {
        window.location = "/join?user_type=media"
    });
}

if(joinServiceAdvertiser !== null) {
    joinServiceAdvertiser.addEventListener("click", () => {
        window.location = "/join?user_type=advertiser"
    });
}


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