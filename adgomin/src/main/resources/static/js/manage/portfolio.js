/**
 * 전역변수
 */
const manageMedia = document.getElementById("manageMedia");
const portfolioPost = document.getElementsByClassName("portfolio-post");

const portfolioManageUpdateButton = document.getElementsByClassName("portfolio-manage-update-button");
const portfolioManageDeleteButton = document.getElementsByClassName("portfolio-manage-delete-button");

const modalCancelButton = document.getElementById("modalCancelButton");
const modalConfirmButton = document.getElementById("modalConfirmButton");

const modalCheck2 = document.getElementById("modalCheck2");

let portfolioOrder;

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

for(let i = 0; i < portfolioManageDeleteButton.length; i++) {
    portfolioManageDeleteButton[i].addEventListener("click", (e) => {
        portfolioOrder = e.currentTarget.dataset.parent;

        let modalBg = document.getElementById("modalBg");

        modalBg.style.display = "block";
    })
}

modalCancelButton.addEventListener("click", () => {
    let modalBg = document.getElementById("modalBg");

    modalBg.style.display = "none";
})

modalConfirmButton.addEventListener("click", () => {
    const formData = new FormData();

    formData.append("portfolioOrder", portfolioOrder);

    xhr("/manage/portfolio/delete", formData, "DELETE", "managePortfolioDelete");
})

modalCheck2.addEventListener("click", () => {
    window.location.href = "/manage?manage=portfolio";
})

/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag == "managePortfolioDelete") {
        let modalBg = document.getElementById("modalBg");

        modalBg.style.display = "none";

        let modalBg2 = document.getElementById("modalBg2");
        let modalTitle = document.getElementById("modalTitle");

        modalBg2.style.display = "block";
        modalTitle.innerText = "포트폴리오 삭제가 완료되었습니다.";
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