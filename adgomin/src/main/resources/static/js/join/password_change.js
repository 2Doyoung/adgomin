/**
 * 전역변수
 */
const passwordChangeButton = document.getElementById("passwordChangeButton");
const email = document.getElementById("email").value;

/**
 * 이벤트 함수
 */
passwordChangeButton.addEventListener("click", () => {
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

        formData.append("email", email);
        formData.append("password", passwordChange);

        xhr('/passwordChange', formData, 'PATCH', 'patchPasswordChange');
    }
});

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
/**
 * 사용자 함수
 */
document.getElementById("passwordChange").focus();

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag === "patchPasswordChange") {
        window.location.href = "/password/change/complete"
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