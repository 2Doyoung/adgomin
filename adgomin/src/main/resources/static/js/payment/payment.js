/**
 * 전역변수
 */
const verificationButton = document.getElementById("verificationButton");
const reSendButton = document.getElementById("reSendButton");
const certificationButton = document.getElementById("certificationButton");

const purchaseConditionPage = document.getElementById("purchaseConditionPage");
const privacyInfoPage = document.getElementById("privacyInfoPage");

const requestPaymentWindow = document.getElementById("requestPaymentWindow");

const paymentCloseBtn = document.getElementById("paymentCloseBtn");

const customerEmail = document.getElementById("customerEmail");
const customerName = document.getElementById("customerName");
const customerPhoneNumber = document.getElementById("customerPhoneNumber");

const totalAmount = document.getElementById("totalAmount").value;

const integerTotalAmount = parseInt(totalAmount);

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

if(purchaseConditionPage != null) {
    purchaseConditionPage.addEventListener("click", () => {
        let newWindow = window.open("/terms", "_blank");

        newWindow.onload = function() {
            const targetElement = newWindow.document.getElementById("refund");

            if (targetElement) {
                const targetPosition = targetElement.getBoundingClientRect().top + newWindow.pageYOffset;

                const headerHeight = newWindow.document.querySelector('header').offsetHeight;

                newWindow.scrollTo({
                    top: targetPosition - headerHeight,
                    behavior: "smooth"
                });
            }
        };
    })
}

if(privacyInfoPage != null) {
    privacyInfoPage.addEventListener("click", () => {
        window.open("/privacy", "_blank")
    })
}

if(requestPaymentWindow != null) {
    requestPaymentWindow.addEventListener("click", () => {
        let paymentWindow = document.getElementById("paymentWindow");
        paymentWindow.style.display = "flex";

        requestPaymentWidget();
    })
}

if(paymentCloseBtn != null) {
    paymentCloseBtn.addEventListener("click", () => {
        let paymentWindow = document.getElementById("paymentWindow");
        paymentWindow.style.display = "none";
    })
}

/**
 * 사용자 함수
 */
let requestPaymentWidget = () => {
    const requestPayment = document.getElementById("requestPayment");

    // ------  결제위젯 초기화 ------
    const clientKey = "test_gck_yZqmkKeP8gpga7Xa1Kzj3bQRxB9l";
    const tossPayments = TossPayments(clientKey);

    // 회원 결제
    const customerKey = customerEmail.value;
    const widgets = tossPayments.widgets({ customerKey });

    // ------ 주문의 결제 금액 설정 ------
    widgets.setAmount({
        currency: "KRW",
        value: integerTotalAmount,
    }).then(() => {
        return Promise.all([
            // ------  결제 UI 렌더링 ------
            widgets.renderPaymentMethods({
                selector: "#payment-method",
                variantKey: "DEFAULT",
            }),
            // ------  이용약관 UI 렌더링 ------
            widgets.renderAgreement({
                selector: "#agreement",
                variantKey: "AGREEMENT",
            }),
        ]);
    }).then(() => {
        // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
        requestPayment.addEventListener("click", function () {
            const orderId = generateUUID();

            widgets.requestPayment({
                orderId: orderId,
                orderName: mediaTitle,
                successUrl: window.location.origin + "/success",
                failUrl: window.location.origin + "/fail",
                customerEmail: customerEmail.value,
                customerName: customerName.value,
                customerMobilePhone: customerPhoneNumber.value,
            });

            const formData = new FormData();

            formData.append("orderId", orderId);
            formData.append("mediaOrder", mediaOrder);

            xhr("/payment/register", formData, "POST", "paymentRegister");
        });
    }).catch((error) => {
        console.error("Error:", error);
    });
}

let generateUUID = () => { // Public Domain/MIT
    let d = new Date().getTime(); // Timestamp
    let d2 = (performance && performance.now && (performance.now()*1000)) || 0; // Time in microseconds since page-load or 0 if unsupported
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        let r = Math.random() * 16; // random number between 0 and 16
        if(d > 0){ // Use timestamp until depleted
            r = (d + r)%16 | 0;
            d = Math.floor(d/16);
        } else { // Use microseconds since page-load if supported
            r = (d2 + r)%16 | 0;
            d2 = Math.floor(d2/16);
        }
        return (c === 'x' ? r : (r&0x3|0x8)).toString(16);
    });
}

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
    } else if(flag == "totalAmount") {
        console.log();
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