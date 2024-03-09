/**
 * 전역변수
 */
const user = document.getElementById("user");
const userSecession = document.getElementById("userSecession");
const passwordChangeButton = document.getElementById("passwordChangeButton");

const modalCheck = document.getElementById("modalCheck");

/**
 * 이벤트 함수
 */
user.addEventListener("click", () => {
    window.location.href = "/user";
})

userSecession.addEventListener("click", () => {
    window.location.href = "/user?setting=userSecession";
})

passwordChangeButton.addEventListener("click", () => {
    let passwordPresent = document.getElementById("passwordPresent").value;
    let passwordChange = document.getElementById("passwordChange").value;
    let passwordChangeCheck = document.getElementById("passwordChangeCheck").value;

    let passwordError = document.getElementById("passwordError");
    let passwordCheckError = document.getElementById("passwordCheckError");

    if(passwordChange === "" || !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(passwordChange)) {
        passwordError.style.display = "inline-block";
    }

    if(passwordChangeCheck === "" || passwordChange !== passwordChangeCheck) {
        passwordCheckError.style.display = "inline-block";
    }

    if(passwordError.style.display !== "inline-block" && passwordCheckError.style.display !== "inline-block") {
        const formData = new FormData();

        formData.append("password", passwordPresent);

        xhr('/password/check', formData, 'POST', 'postPasswordCheck');
    }
})

document.getElementById("passwordChange").addEventListener("keyup", () => {
    let passwordChange = document.getElementById("passwordChange").value;
    let passwordError = document.getElementById("passwordError");

    let passwordChangeCheck = document.getElementById("passwordChangeCheck").value;
    let passwordCheckError = document.getElementById("passwordCheckError");

    if(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(passwordChange)) {
        passwordError.style.display = "none";
    } else if(!/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(passwordChange)) {
        passwordError.style.display = "inline-block";
    }

    if(passwordChange === passwordChangeCheck) {
        passwordCheckError.style.display = "none";
    } else if(passwordChange !== passwordChangeCheck) {
        passwordCheckError.style.display = "inline-block";
    }
});

document.getElementById("passwordChangeCheck").addEventListener("keyup", () => {
    let passwordChange = document.getElementById("passwordChange").value;
    let passwordChangeCheck = document.getElementById("passwordChangeCheck").value;
    let passwordCheckError = document.getElementById("passwordCheckError");

    if(passwordChange === passwordChangeCheck) {
        passwordCheckError.style.display = "none";
    } else if(passwordChange !== passwordChangeCheck) {
        passwordCheckError.style.display = "inline-block";
    }
});

document.getElementById("passwordPresent").addEventListener("keyup", () => {
    let presentPasswordError = document.getElementById("presentPasswordError");

    presentPasswordError.style.display = "none";
})

modalCheck.addEventListener("click", () => {
    window.location.href = "/user?setting=userPasswordChange";
})

/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag === "postPasswordCheck") {
        let passwordChange = document.getElementById("passwordChange").value;

        const formData = new FormData();

        formData.append("password", passwordChange);

        xhr('/user/passwordChange', formData, 'PATCH', 'patchUserPasswordChange');
    } else if(flag === "patchUserPasswordChange") {
        let modalBg = document.getElementById("modalBg");
        let modalTitle = document.getElementById("modalTitle");

        modalBg.style.display = "block";
        modalTitle.innerText = "비밀번호 변경이 완료되었습니다.";
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