/**
 * 전역변수
 */
const purchaseCharge = document.getElementById("purchaseCharge");
const purchaseSumPrice = document.getElementById("purchaseSumPrice");
const verificationButton = document.getElementById("verificationButton");

/**
 * 이벤트 함수
 */
verificationButton.addEventListener("click", () => {
    let name = document.getElementById("name");
    let phoneNumber = document.getElementById("phoneNumber");

    let nameError = document.getElementById("nameError");
    let phoneNumberError = document.getElementById("phoneNumberError");

    let verificationButton = document.getElementById("verificationButton");

    if(name.value === "") {
        nameError.style.display = "block";
    }

    if(phoneNumber.value === "" || !/^010\d{4}\d{4}$/.test(phoneNumber.value)) {
        phoneNumberError.style.display = "inline-block";
    }

    if(nameError.style.display !== "block" && phoneNumberError.style.display !== "inline-block") {
        verificationButton.value = "재전송";

        //xhr("/sendKakao?email=" + email, null, "GET", "emailDuplicate");
    }
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