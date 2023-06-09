/**
 * 전역변수
 */
const userPasswordChange = document.getElementById("userPasswordChange");
const userSecession = document.getElementById("userSecession");
const profileImg = document.getElementById("profileImg");
const nicknameChange = document.getElementById("nicknameChange");
const nickname = document.getElementById("nickname");

const modalCheck = document.getElementById("modalCheck");

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