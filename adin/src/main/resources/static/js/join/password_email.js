/**
 * 전역변수
 */
const passwordFindEmailButton = document.getElementById("passwordFindEmailButton");

/**
 * 이벤트 함수
 */
passwordFindEmailButton.addEventListener("click", () => {
    let passwordFindEmail = document.getElementById("passwordFindEmail").value;

    let passwordCheckEmailError = document.getElementById("passwordCheckEmailError");
    let emailDuplicateError = document.getElementById("emailDuplicateError");

    if(passwordFindEmail === "" || !/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/.test(passwordFindEmail)) {
        passwordCheckEmailError.style.display = "inline-block";
    }

    if(passwordCheckEmailError.style.display !== "inline-block" && emailDuplicateError.style.display !== "inline-block") {
        xhr("/passwordSendEmail?email=" + passwordFindEmail, null, "GET", "passwordSendEmail");
        window.location.href = "/passwordEmailAfter";
    }
});

document.getElementById("passwordFindEmail").addEventListener("keyup", () => {
    let passwordFindEmail = document.getElementById("passwordFindEmail").value;
    let passwordCheckEmailError = document.getElementById("passwordCheckEmailError");

    let emailDuplicateError = document.getElementById("emailDuplicateError");

    if(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/.test(passwordFindEmail)) {
        passwordCheckEmailError.style.display = "none";

        xhr("./join/emailDuplicate?email=" + passwordFindEmail, null, "GET", "emailDuplicate");
    } else if(!/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/.test(passwordFindEmail)) {
        passwordCheckEmailError.style.display = "inline-block";
        emailDuplicateError.style.display = "none";
    }
});

/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag === "emailDuplicate") {
        let emailDuplicateError = document.getElementById("emailDuplicateError");

        emailDuplicateError.style.display = "inline-block";
    }
}

/**
 * XMLHttpRequest default 함수
 */
let defaultXhr = (responseObject, flag) => {
    if(flag === "emailDuplicate") {
        let emailDuplicateError = document.getElementById("emailDuplicateError");

        emailDuplicateError.style.display = "none";
    }
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