/**
 * 전역변수
 */
const userPasswordChange = document.getElementById("userPasswordChange");
const userSecession = document.getElementById("userSecession");
const profileImg = document.getElementById("profileImg");
const nicknameChange = document.getElementById("nicknameChange");
const nickname = document.getElementById("nickname");

const modalCheck = document.getElementById("modalCheck");

const verificationButton = document.getElementById("verificationButton");
const certificationButton = document.getElementById("certificationButton");

const reSendButton = document.getElementById("reSendButton");

const requestVerificationButton = document.getElementById("requestVerificationButton");

const reVerificationCancel = document.getElementById("reVerificationCancel");
const reVerificationButton = document.getElementById("reVerificationButton");
const reCertificationButton = document.getElementById("reCertificationButton");
const requestSendButton= document.getElementById("requestSendButton");

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
    let ext = profileImg.value.slice(profileImg.value.lastIndexOf(".") + 1).toLowerCase();

    if(!(ext == "jpg" || ext == "png" || ext == "jpeg")) {
        let extSpan = document.getElementById("extSpan");
        extSpan.style.color = "#FF0040";
    } else {
        const formData = new FormData();

        formData.append("profileFile", profileImg.files[0]);

        xhr("/change/profileImg", formData, "PATCH", "changeProfileImg");
    }
})

nicknameChange.addEventListener("click", () => {
    if(!nicknameChange.readOnly) {
        let nickname = document.getElementById("nickname").value;
        let nicknameSpan = document.getElementById("nicknameSpan");

        if(!/^[A-Za-z0-9가-힣]{2,18}$/.test(nickname)) {
            nicknameSpan.style.color = "#FF0040";
            document.getElementById("nickname").focus();
        } else if(/^[A-Za-z0-9가-힣]{2,18}$/.test(nickname)) {
            const formData = new FormData();

            formData.append("nickname", nickname);

            xhr("/change/nickname", formData, "PATCH", "changeNickname");
        } else {

        }
    } else if(nicknameChange.readOnly) {

    } else {

    }
})

nickname.addEventListener("change", () => {
    nicknameChange.readOnly = false;
})

modalCheck.addEventListener("click", () => {
    window.location.href = "/user";
})

if(verificationButton != null){
    verificationButton.addEventListener("click", () => {
        let name = document.getElementById("name");
        let phoneNumber = document.getElementById("phoneNumber");

        let nameError = document.getElementById("nameError");
        let phoneNumberError = document.getElementById("phoneNumberError");

        if(name.value === "") {
            nameError.style.display = "block";
        }

        if(phoneNumber.value === "" || !/^010\d{4}\d{4}$/.test(phoneNumber.value)) {
            phoneNumberError.style.display = "inline-block";
        }

        if(nameError.style.display !== "block" && phoneNumberError.style.display !== "inline-block") {
            xhr("/send/kakao?phoneNumber=" + phoneNumber.value, null, "GET", "sendKakao");
        }
    })
}

if(certificationButton != null) {
    certificationButton.addEventListener("click", () => {
        let phoneNumberCertification = document.getElementById("phoneNumberCertification").value;

        const formData = new FormData();

        formData.append("phoneNumberCertification", phoneNumberCertification);

        xhr('/phone/number/certification/check', formData, 'POST', 'phoneNumberCertificationCheck');
    })
}

if(document.getElementById("name") != null) {
    document.getElementById("name").addEventListener("keyup", () => {
        let name = document.getElementById("name");
        let nameError = document.getElementById("nameError");

        if(name.value !== "") {
            nameError.style.display = "none";
        } else if(name.value === "") {
            nameError.style.display = "block";
        }
    })
}

if(document.getElementById("phoneNumber") != null) {
    document.getElementById("phoneNumber").addEventListener("keyup", (e) => {
        let phoneNumber = document.getElementById("phoneNumber");
        let phoneNumberError = document.getElementById("phoneNumberError");

        let cleanedValue = phoneNumber.value.replace(/[^0-9]/g, '');

        phoneNumber.value = cleanedValue;

        if(/^010\d{4}\d{4}$/.test(cleanedValue)) {
            phoneNumberError.style.display = "none";
        } else if(!/^010\d{4}\d{4}$/.test(cleanedValue)) {
            phoneNumberError.style.display = "inline-block";
        }
    })
}

if(reSendButton != null) {
    reSendButton.addEventListener("click", ()=> {
        let phoneNumber = document.getElementById("phoneNumber");

        let nameError = document.getElementById("nameError");
        let phoneNumberError = document.getElementById("phoneNumberError");

        if(nameError.style.display !== "block" && phoneNumberError.style.display !== "inline-block") {
            xhr("/send/kakao?phoneNumber=" + phoneNumber.value, null, "GET", "reSendKakao");
        }
    })
}

if(requestVerificationButton != null) {
    requestVerificationButton.addEventListener("click", () => {
        let identityVerificationCompleteDiv = document.getElementById("identityVerificationCompleteDiv");
        let requestIdentityVerificationBottom = document.getElementById("requestIdentityVerificationBottom");

        identityVerificationCompleteDiv.style.display = "none";
        requestIdentityVerificationBottom.style.display = "block";
    })
}

if(reVerificationCancel != null) {
    reVerificationCancel.addEventListener("click", () => {
        let requestIdentityVerificationBottom = document.getElementById("requestIdentityVerificationBottom");
        let identityVerificationCompleteDiv = document.getElementById("identityVerificationCompleteDiv");

        let reNameError = document.getElementById("reNameError");
        let rePhoneNumberError = document.getElementById("rePhoneNumberError");

        let reCertificationWrap = document.getElementById("reCertificationWrap");
        let reCertificationError = document.getElementById("reCertificationError");

        let reVerificationButton = document.getElementById("reVerificationButton");
        let requestSendButton = document.getElementById("requestSendButton");

        document.getElementById("reName").value = "";
        document.getElementById("rePhoneNumber").value = "";
        document.getElementById("rePhoneNumberCertification").value = "";

        requestIdentityVerificationBottom.style.display = "none";
        identityVerificationCompleteDiv.style.display = "block";

        reNameError.style.display = "none";
        rePhoneNumberError.style.display = "none";

        reCertificationWrap.style.display = "none";
        reCertificationError.style.display = "none";

        reVerificationButton.style.display = "inline-block";
        requestSendButton.style.display = "none"
    })
}

if(reVerificationButton != null) {
    reVerificationButton.addEventListener("click", () => {
        let reName = document.getElementById("reName");
        let rePhoneNumber = document.getElementById("rePhoneNumber");

        let reNameError = document.getElementById("reNameError");
        let rePhoneNumberError = document.getElementById("rePhoneNumberError");

        if(reName.value === "") {
            reNameError.style.display = "block";
        }

        if(rePhoneNumber.value === "" || !/^010\d{4}\d{4}$/.test(rePhoneNumber.value)) {
            rePhoneNumberError.style.display = "inline-block";
        }

        if(reNameError.style.display !== "block" && rePhoneNumberError.style.display !== "inline-block") {
            xhr("/send/kakao?phoneNumber=" + rePhoneNumber.value, null, "GET", "requestSendKakao");
        }
    })
}

if(reCertificationButton != null) {
    reCertificationButton.addEventListener("click", () => {
        let rePhoneNumberCertification = document.getElementById("rePhoneNumberCertification").value;

        const formData = new FormData();

        formData.append("phoneNumberCertification", rePhoneNumberCertification);

        xhr('/phone/number/certification/check', formData, 'POST', 'rePhoneNumberCertificationCheck');
    })
}

if(requestSendButton != null) {
    requestSendButton.addEventListener("click", ()=> {
        let rePhoneNumber = document.getElementById("rePhoneNumber");

        let reNameError = document.getElementById("reNameError");
        let rePhoneNumberError = document.getElementById("rePhoneNumberError");

        if(reNameError.style.display !== "block" && rePhoneNumberError.style.display !== "inline-block") {
            xhr("/send/kakao?phoneNumber=" + rePhoneNumber.value, null, "GET", "requestReSendKakao");
        }
    })
}

if(document.getElementById("reName") != null) {
    document.getElementById("reName").addEventListener("keyup", () => {
        let reName = document.getElementById("reName");
        let reNameError = document.getElementById("reNameError");

        if(reName.value !== "") {
            reNameError.style.display = "none";
        } else if(reName.value === "") {
            reNameError.style.display = "block";
        }
    })
}

if(document.getElementById("rePhoneNumber") != null) {
    document.getElementById("rePhoneNumber").addEventListener("keyup", (e) => {
        let rePhoneNumber = document.getElementById("rePhoneNumber");
        let rePhoneNumberError = document.getElementById("rePhoneNumberError");

        let cleanedValue = rePhoneNumber.value.replace(/[^0-9]/g, '');

        rePhoneNumber.value = cleanedValue;

        if(/^010\d{4}\d{4}$/.test(cleanedValue)) {
            rePhoneNumberError.style.display = "none";
        } else if(!/^010\d{4}\d{4}$/.test(cleanedValue)) {
            rePhoneNumberError.style.display = "inline-block";
        }
    })
}

/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag === "changeProfileImg") {
        let modalBg = document.getElementById("modalBg");
        let modalTitle = document.getElementById("modalTitle");

        modalBg.style.display = "block";
        modalTitle.innerText = "프로필 변경이 완료되었습니다.";
    } else if(flag === "changeNickname") {
        let modalBg = document.getElementById("modalBg");
        let modalTitle = document.getElementById("modalTitle");

        modalBg.style.display = "block";
        modalTitle.innerText = "닉네임 변경이 완료되었습니다.";
    } else if(flag == "sendKakao") {
        let verificationButton = document.getElementById("verificationButton");
        let reSendButton = document.getElementById("reSendButton");

        let certificationWrap = document.getElementById("certificationWrap");

        verificationButton.style.display = "none";
        reSendButton.style.display = "inline-block"
        certificationWrap.style.display = "block";
    } else if(flag == "phoneNumberCertificationCheck") {
        let name = document.getElementById("name").value;
        let phoneNumber = document.getElementById("phoneNumber").value;

        const formData = new FormData();

        formData.append("name", name);
        formData.append("phoneNumber", phoneNumber);

        xhr("/phone/number/certification/success", formData, "PATCH", "phoneNumberCertificationSuccess");
    } else if(flag == "phoneNumberCertificationSuccess") {
        let modalBg = document.getElementById("modalBg");
        let modalTitle = document.getElementById("modalTitle");

        modalBg.style.display = "block";
        modalTitle.innerText = "본인 인증이 완료되었습니다.";

        xhr("/phoneNumberSendEmail", null, "GET", "sendEmail");
    } else if(flag == "reSendKakao") {
        let certificationWrap = document.getElementById("certificationWrap");
        let reSendText = document.getElementById("reSendText");
        let certificationError = document.getElementById("certificationError");

        document.getElementById("phoneNumberCertification").value = '';

        certificationError.style.display = "none";
        certificationWrap.style.display = "none";

        setTimeout(() => {
            certificationWrap.style.display = "block";
        }, 100);

        reSendText.innerText = "인증번호가 카카오톡으로 재전송되었습니다.";
    } else if(flag == "requestSendKakao") {
        let reVerificationButton = document.getElementById("reVerificationButton");
        let requestSendButton = document.getElementById("requestSendButton");

        let reCertificationWrap = document.getElementById("reCertificationWrap");

        reVerificationButton.style.display = "none";
        requestSendButton.style.display = "inline-block"
        reCertificationWrap.style.display = "block";
    } else if(flag == "rePhoneNumberCertificationCheck") {
        let reName = document.getElementById("reName").value;
        let rePhoneNumber = document.getElementById("rePhoneNumber").value;

        const formData = new FormData();

        formData.append("name", reName);
        formData.append("phoneNumber", rePhoneNumber);

        xhr("/phone/number/certification/success", formData, "PATCH", "phoneNumberCertificationSuccess");
    } else if(flag == "requestReSendKakao") {
        let reCertificationWrap = document.getElementById("reCertificationWrap");
        let requestSendText = document.getElementById("requestSendText");
        let reCertificationError = document.getElementById("reCertificationError");

        document.getElementById("rePhoneNumberCertification").value = '';

        reCertificationError.style.display = "none";
        reCertificationWrap.style.display = "none";

        setTimeout(() => {
            reCertificationWrap.style.display = "block";
        }, 100);

        requestSendText.innerText = "인증번호가 카카오톡으로 재전송되었습니다.";
    }
}

/**
 * XMLHttpRequest default 함수
 */
let defaultXhr = (responseObject, flag) => {
    if(flag == "phoneNumberCertificationCheck") {
        let certificationError = document.getElementById("certificationError");

        certificationError.style.display = "none";
        setTimeout(() => {
            certificationError.style.display = "block";
        }, 100);
    } else if(flag == "rePhoneNumberCertificationCheck") {
        let reCertificationError = document.getElementById("reCertificationError");

        reCertificationError.style.display = "none";
        setTimeout(() => {
            reCertificationError.style.display = "block";
        }, 100);
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