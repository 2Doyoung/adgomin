/**
 * 전역변수
 */
const mediaManageY = document.getElementsByClassName("media-manage-y");
const mediaManageUpdateButton = document.getElementsByClassName("media-manage-update-button");
const mediaManageDeleteButton = document.getElementsByClassName("media-manage-delete-button");

const modalCancelButton = document.getElementById("modalCancelButton");
const modalConfirmButton = document.getElementById("modalConfirmButton");

const modalCheck2 = document.getElementById("modalCheck2");

let mediaSubmitStatus;
let mediaOrder;

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
        mediaSubmitStatus = e.currentTarget.dataset.parent;
        mediaOrder = e.currentTarget.dataset.parent2;

        let modalBg = document.getElementById("modalBg");

        modalBg.style.display = "block";
    })
}

modalCancelButton.addEventListener("click", () => {
    let modalBg = document.getElementById("modalBg");

    modalBg.style.display = "none";
})

modalConfirmButton.addEventListener("click", () => {
    if(mediaSubmitStatus == "T" || mediaSubmitStatus == "I" || mediaSubmitStatus == "C") {
        const formData = new FormData();

        formData.append("mediaOrder", mediaOrder);

        xhr("/manage/media/delete/add", formData, "DELETE", "manageMediaDeleteAdd");
    } else if(mediaSubmitStatus == "Y") {

    }
})

modalCheck2.addEventListener("click", () => {
    window.location.href = "/manage?manage=media";
})

/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag == "manageMediaDeleteAdd") {
        let modalBg = document.getElementById("modalBg");

        modalBg.style.display = "none";

        let modalBg2 = document.getElementById("modalBg2");
        let modalTitle = document.getElementById("modalTitle");

        modalBg2.style.display = "block";
        modalTitle.innerText = "광고매체 삭제가 완료되었습니다.";
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