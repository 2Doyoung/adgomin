/**
 * 전역변수
 */
const joinCompleteButton = document.getElementById("joinCompleteButton");
const allCheck = document.getElementById("allCheck");
const checkBoxItem = document.getElementsByClassName("checkBoxItem");
const allCheckItem = document.getElementById("allCheckItem");
const checkBoxItemLine = document.getElementsByClassName("checkBoxItemLine");

/**
 * 이벤트함수
 */
joinCompleteButton.addEventListener("click", () => {
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let passwordCheck = document.getElementById("passwordCheck").value;
    let userType = document.getElementById("userType").value;

    let emailError = document.getElementById("emailError");
    let passwordError = document.getElementById("passwordError");
    let passwordCheckError = document.getElementById("passwordCheckError");
    let checkBoxError = document.getElementById("checkBoxError");
    let emailDuplicateError = document.getElementById("emailDuplicateError");

    let marketingYn = "0";

    if(email === "" || !/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/.test(email)) {
        emailError.style.display = "inline-block";
    }

    if(password === "" || !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(password)) {
        passwordError.style.display = "inline-block";
    }

    if(passwordCheck === "" || password !== passwordCheck) {
        passwordCheckError.style.display = "inline-block";
    }

    if(!document.getElementsByClassName("checkBoxItem")[0].classList.contains("check-box-color") || !document.getElementsByClassName("checkBoxItem")[1].classList.contains("check-box-color")) {
        checkBoxError.style.display = "inline-block";
    }

    if(emailError.style.display !== "inline-block" && passwordError.style.display !== "inline-block" && passwordCheckError.style.display !== "inline-block" && document.getElementsByClassName("checkBoxItem")[0].classList.contains("check-box-color") && document.getElementsByClassName("checkBoxItem")[1].classList.contains("check-box-color") && emailDuplicateError.style.display !== "inline-block") {
        const formData = new FormData();

        formData.append("email", email);
        formData.append("password", password);
        formData.append("userType", userType);
        if(document.getElementsByClassName("checkBoxItem")[2].classList.contains("check-box-color")) {
            marketingYn = "1";
        }
        formData.append("marketingYn", marketingYn);

        xhr('./join', formData, 'POST', 'postJoin');
    }
});

document.getElementById("email").addEventListener("keyup", () => {
    let email = document.getElementById("email").value;
    let emailError = document.getElementById("emailError");

    if(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/.test(email)) {
        emailError.style.display = "none";

        xhr("./join/emailDuplicate?email=" + email, null, "GET", "emailDuplicate");
    } else if(!/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/.test(email)) {
        emailError.style.display = "inline-block";
    }
});

document.getElementById("password").addEventListener("keyup", () => {
    let password = document.getElementById("password").value;
    let passwordError = document.getElementById("passwordError");

    let passwordCheck = document.getElementById("passwordCheck").value;
    let passwordCheckError = document.getElementById("passwordCheckError");

    if(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(password)) {
        passwordError.style.display = "none";
    } else if(!/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(password)) {
        passwordError.style.display = "inline-block";
    }

    if(password === passwordCheck) {
        passwordCheckError.style.display = "none";
    } else if(password !== passwordCheck) {
        passwordCheckError.style.display = "inline-block";
    }
});

document.getElementById("passwordCheck").addEventListener("keyup", () => {
    let password = document.getElementById("password").value;
    let passwordCheck = document.getElementById("passwordCheck").value;
    let passwordCheckError = document.getElementById("passwordCheckError");

    if(password === passwordCheck) {
        passwordCheckError.style.display = "none";
    } else if(password !== passwordCheck) {
        passwordCheckError.style.display = "inline-block";
    }
})

allCheck.addEventListener("click", () => {
    if(allCheckItem.classList.contains("check-box-color")) {
        allCheckItem.classList.remove("check-box-color");

        for(let i = 0; i < checkBoxItem.length; i++) {
            checkBoxItem[i].classList.remove("check-box-color");
        }
    } else if(!allCheckItem.classList.contains("check-box-color")) {
        allCheckItem.className += " check-box-color";
        for(let i = 0; i < checkBoxItem.length; i++) {
            checkBoxItem[i].className += " check-box-color";
        }
    } else {

    }

    if(document.getElementsByClassName("checkBoxItem")[0].classList.contains("check-box-color") && document.getElementsByClassName("checkBoxItem")[1].classList.contains("check-box-color")) {
        checkBoxError.style.display = "none";
    } else if(!document.getElementsByClassName("checkBoxItem")[0].classList.contains("check-box-color") || !document.getElementsByClassName("checkBoxItem")[1].classList.contains("check-box-color")) {
        checkBoxError.style.display = "inline-block";
    }
});

for(let i = 0; i < checkBoxItemLine.length; i++) {
    checkBoxItemLine[i].addEventListener("click", () => {
        let total = checkBoxItem.length;
        let checked = 0;

        if(checkBoxItem[i].classList.contains("check-box-color")) {
            checkBoxItem[i].classList.remove("check-box-color");
        } else if(!checkBoxItem[i].classList.contains("check-box-color")) {
            checkBoxItem[i].className += " check-box-color";
        } else {

        }

        for(let i = 0; i < checkBoxItem.length; i++) {
            if(checkBoxItem[i].classList.contains("check-box-color")) {
                checked++;
            }
        }

        if(total === checked) {
            allCheckItem.className += " check-box-color";
        } else if(total !== checked) {
            allCheckItem.classList.remove("check-box-color");
        }
    });
}

for(let i = 0; i < checkBoxItemLine.length - 1; i++) {
    let checkBoxError = document.getElementById("checkBoxError");
    checkBoxItemLine[i].addEventListener("click", () => {
       if(document.getElementsByClassName("checkBoxItem")[0].classList.contains("check-box-color") && document.getElementsByClassName("checkBoxItem")[1].classList.contains("check-box-color")) {
           checkBoxError.style.display = "none";
       } else if(!document.getElementsByClassName("checkBoxItem")[0].classList.contains("check-box-color") || !document.getElementsByClassName("checkBoxItem")[1].classList.contains("check-box-color")) {
           checkBoxError.style.display = "inline-block";
       }
    });
}

/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag === "emailDuplicate") {
        let emailDuplicateError = document.getElementById("emailDuplicateError");

        emailDuplicateError.style.display = "none";
    } else if(flag === "postJoin") {
        let email = responseObject["email"];
        let certified = responseObject["certified"];

        xhr("/sendEmail?email=" + email + "&certified=" + certified, null, "GET", "sendEmail");
        window.location.href = "/join/email";
    }
}

/**
 * XMLHttpRequest default 함수
 */
let defaultXhr = (responseObject, flag) => {
    if(flag === "emailDuplicate") {
        let emailDuplicateError = document.getElementById("emailDuplicateError");

        emailDuplicateError.style.display = "inline-block";
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