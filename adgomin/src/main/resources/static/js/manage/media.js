/**
 * 전역변수
 */
const mediaManageY = document.getElementsByClassName("media-manage-y");
const mediaManageUpdateButton = document.getElementsByClassName("media-manage-update-button");
const mediaManageDeleteButton = document.getElementsByClassName("media-manage-delete-button");

const modalCancelButton = document.getElementById("modalCancelButton");
const modalConfirmButton = document.getElementById("modalConfirmButton");

/**
 * 이벤트 함수
 */
for(let i = 0; i < mediaManageY.length; i++) {
    mediaManageY[i].addEventListener("click", (e) => {
        let mediaOrder = e.currentTarget.dataset.parent;

        window.location.href = "/post?mediaOrder=" + mediaOrder;
    })
}

for(let i = 0; i < mediaManageUpdateButton.length; i++) {
    mediaManageUpdateButton[i].addEventListener("click", (e) => {
        let mediaSubmitStatus = e.currentTarget.dataset.parent;
        let mediaOrder = e.currentTarget.dataset.parent2;

        if(mediaSubmitStatus == "T" || mediaSubmitStatus == "I" || mediaSubmitStatus == "C") {
            window.location.href = "/media?manage=mediaRegister";
        } else if(mediaSubmitStatus == "Y") {
            window.location.href = "/manage/media/update?mediaOrder=" + mediaOrder;
        }
    })
}

for(let i = 0; i < mediaManageDeleteButton.length; i++) {
    mediaManageDeleteButton[i].addEventListener("click", (e) => {
        let mediaSubmitStatus = e.currentTarget.dataset.parent;
        let mediaOrder = e.currentTarget.dataset.parent2;

        let modalBg = document.getElementById("modalBg");

        modalBg.style.display = "block";
    })
}

modalCancelButton.addEventListener("click", () => {

})

modalConfirmButton.addEventListener("click", () => {

})

/**
 * 사용자 함수
 */

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