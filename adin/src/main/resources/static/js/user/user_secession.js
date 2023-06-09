/**
 * 전역변수
 */
const user = document.getElementById("user");
const userPasswordChange = document.getElementById("userPasswordChange");
const secessionButton = document.getElementById("secessionButton");

const modalCheck = document.getElementById("modalCheck");

/**
 * 이벤트 함수
 */
user.addEventListener("click", () => {
    window.location.href = "/user";
})

userPasswordChange.addEventListener("click", () => {
    window.location.href = "/user?setting=userPasswordChange";
})

secessionButton.addEventListener("click", () => {
    let password = document.getElementById("password").value;

    const formData = new FormData();

    formData.append("password", password);

    xhr('/password/check', formData, 'POST', 'postPasswordCheck');
})

modalCheck.addEventListener("click", () => {
    window.location.href = "/";
})

/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag === "postPasswordCheck") {
        let secessionReason = document.querySelector('input[name="secessionReason"]:checked').value;

        const formData = new FormData();

        formData.append("secessionReason", secessionReason);

        xhr('/user/secession', formData, 'DELETE', 'deleteUserSecession');
    } else if(flag === "deleteUserSecession") {
        let modalBg = document.getElementById("modalBg");
        let modalTitle = document.getElementById("modalTitle");

        modalBg.style.display = "block";
        modalTitle.innerText = "회원 탈퇴가 완료되었습니다.";
    }
}

/**
 * XMLHttpRequest default 함수
 */
let defaultXhr = (responseObject, flag) => {
    if(flag === "postPasswordCheck") {
        let presentPasswordError = document.getElementById("presentPasswordError");

        presentPasswordError.style.display = "inline-block";
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