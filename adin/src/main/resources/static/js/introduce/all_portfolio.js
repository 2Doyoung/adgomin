/**
 * 전역변수
 */
const introduceHome = document.getElementById("introduceHome");

const spans = document.querySelectorAll('.div-paging span span');

const portfolioCard = document.getElementsByClassName("portfolio-card");

/**
 * 이벤트 함수
 */
introduceHome.addEventListener("click", () => {
    const userOrder = document.getElementById("userOrder").value;

    window.location.href = "/introduce/" + userOrder;
})

for(let i = 0; i < spans.length; i++) {
    spans[i].addEventListener('click', (e) => {
        let link = e.target.querySelector('a');
        if (link) {
            link.click();
        }
    });
}

for(let i = 0; i < portfolioCard.length; i++) {
    portfolioCard[i].addEventListener("click", (e) => {
        const userOrder = document.getElementById("userOrder").value
        const portfolioOrder = e.currentTarget.dataset.parent;

        window.location.href = "/introduce/detail/portfolio/" + userOrder + "/" + portfolioOrder;
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