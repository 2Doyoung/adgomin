/**
 * 전역변수
 */
const verificationButton = document.getElementById("verificationButton");
const certificationButton = document.getElementById("certificationButton");

const reSendButton = document.getElementById("reSendButton");

const modalCheck = document.getElementById("modalCheck");

/**
 * 이벤트 함수
 */
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

certificationButton.addEventListener("click", () => {
    let phoneNumberCertification = document.getElementById("phoneNumberCertification").value;

    const formData = new FormData();

    formData.append("phoneNumberCertification", phoneNumberCertification);

    xhr('/phone/number/certification/check', formData, 'POST', 'phoneNumberCertificationCheck');
})

document.getElementById("name").addEventListener("keyup", () => {
    let name = document.getElementById("name");
    let nameError = document.getElementById("nameError");

    if(name.value !== "") {
        nameError.style.display = "none";
    } else if(name.value === "") {
        nameError.style.display = "block";
    }
})

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

reSendButton.addEventListener("click", ()=> {
    let phoneNumber = document.getElementById("phoneNumber");

    let nameError = document.getElementById("nameError");
    let phoneNumberError = document.getElementById("phoneNumberError");

    if(nameError.style.display !== "block" && phoneNumberError.style.display !== "inline-block") {
        xhr("/send/kakao?phoneNumber=" + phoneNumber.value, null, "GET", "reSendKakao");
    }
})

modalCheck.addEventListener("click", () => {
    window.location.href = "/media";
})

/**
 * 사용자 함수
 */


/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag == "sendKakao") {
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