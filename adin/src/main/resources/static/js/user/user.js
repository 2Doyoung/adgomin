/**
 * 전역변수
 */
const userPasswordChange = document.getElementById("userPasswordChange");
const userSecession = document.getElementById("userSecession");
const profileImg = document.getElementById("profileImg");

/**
 * 이벤트 함수
 */
userPasswordChange.addEventListener("click", () => {
    window.location.href = "/user?setting=userPasswordChange";
})

userSecession.addEventListener("click", () => {
    window.location.href = "/user?setting=userSecession";
})

profileImg.addEventListener("change", () => {
    const formData = new FormData();

    formData.append("profileFile", profileImg.files[0]);

    xhr("/change/profileImg", formData, "PATCH", "changeProfileImg");
})

/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag === "changeProfileImg") {
        window.location.href = "/user"
    }
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