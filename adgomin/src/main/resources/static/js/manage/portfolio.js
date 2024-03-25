/**
 * 전역변수
 */
const manageMedia = document.getElementById("manageMedia");
const portfolioPost = document.getElementsByClassName("portfolio-post");

const portfolioManageUpdateButton = document.getElementsByClassName("portfolio-manage-update-button");
const portfolioManageDeleteButton = document.getElementsByClassName("portfolio-manage-delete-button");

/**
 * 이벤트 함수
 */
manageMedia.addEventListener("click", () => {
    window.location.href = "/manage?manage=media";
})

for(let i = 0; i < portfolioPost.length; i++) {
    portfolioPost[i].addEventListener("click", (e) => {
        let userOrder = document.getElementById("userOrder").value;
        let portfolioOrder = e.currentTarget.dataset.parent;

        window.location.href = "/introduce/detail/portfolio/" + userOrder + "/" + portfolioOrder;
    })
}

for(let i = 0; i < portfolioManageUpdateButton.length; i++) {
    portfolioManageUpdateButton[i].addEventListener("click", (e) => {
        let portfolioOrder = e.currentTarget.dataset.parent;

        window.location.href = "/manage/portfolio/update?portfolioOrder=" + portfolioOrder;
    })
}

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