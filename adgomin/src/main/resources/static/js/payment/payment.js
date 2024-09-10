/**
 * 전역변수
 */
const purchaseCharge = document.getElementById("purchaseCharge");
const purchaseSumPrice = document.getElementById("purchaseSumPrice");
const verificationButton = document.getElementById("verificationButton");
const reSendButton = document.getElementById("reSendButton");
const certificationButton = document.getElementById("certificationButton");

/**
 * 이벤트 함수
 */
if(verificationButton != null) {
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

if(certificationButton != null) {
    certificationButton.addEventListener("click", () => {
        let phoneNumberCertification = document.getElementById("phoneNumberCertification").value;

        const formData = new FormData();

        formData.append("phoneNumberCertification", phoneNumberCertification);

        xhr('/phone/number/certification/check', formData, 'POST', 'phoneNumberCertificationCheck');
    })
}

/**
 * 사용자 함수
 */
let mediaPriceAmount = parseInt(mediaPrice.replace(/,/g, ''));
let mediaPriceCharge = Math.floor(mediaPriceAmount * 0.035 / 10) * 10;
let formattedMediaPriceCharge = mediaPriceCharge.toLocaleString(undefined, { maximumFractionDigits: 0 });
purchaseCharge.innerText = formattedMediaPriceCharge;

let purchaseSumPriceFormatted = (mediaPriceAmount + mediaPriceCharge).toLocaleString(undefined, { maximumFractionDigits: 0 });
purchaseSumPrice.innerText = purchaseSumPriceFormatted

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
    } else if(flag == "reSendKakao") {
        let certificationWrap = document.getElementById("certificationWrap");

        let reSendText = document.getElementById("reSendText");

        certificationWrap.style.display = "none";
        setTimeout(() => {
            certificationWrap.style.display = "block";
        }, 100);

        reSendText.innerText = "인증번호가 카카오톡으로 재전송되었습니다.";
    } else if(flag == "phoneNumberCertificationCheck") {
        let name = document.getElementById("name").value;
        let phoneNumber = document.getElementById("phoneNumber").value;

        const formData = new FormData();

        formData.append("name", name);
        formData.append("phoneNumber", phoneNumber);

        xhr("/phone/number/certification/success", formData, "PATCH", "phoneNumberCertificationSuccess");
    } else if(flag == "phoneNumberCertificationSuccess") {
        let identityVerificationDiv = document.getElementById("identityVerificationDiv");
        let identityVerificationSuccessDiv = document.getElementById("identityVerificationSuccessDiv");

        let name = document.getElementById("name").value;
        let phoneNumber = document.getElementById("phoneNumber").value;

        let verificationSuccessName = document.getElementById("verificationSuccessName");
        let verificationSuccessPhoneNumber = document.getElementById("verificationSuccessPhoneNumber");

        identityVerificationDiv.style.display = "none";
        identityVerificationSuccessDiv.style.display = "block";

        verificationSuccessName.innerText = name;
        verificationSuccessPhoneNumber.innerText = phoneNumber;

        identityVerificationSuccessDiv.scrollIntoView({block: "center"});
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