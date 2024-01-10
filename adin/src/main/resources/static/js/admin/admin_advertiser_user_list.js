/**
 * 전역변수
 */
const submitList = document.getElementById("submitList");
const mediaUserList = document.getElementById("mediaUserList");
const spans = document.querySelectorAll('.div-paging span span');
const submitListTr = document.getElementsByClassName('submit-list-tr');

/**
 * 이벤트 함수
 */
submitList.addEventListener("click", () => {
    window.location.href = "/admin?page=1";
})

mediaUserList.addEventListener("click", () => {
    window.location.href = "/admin?manage=mediaUserList&page=1"
})


for(let i = 0; i < spans.length; i++) {
    spans[i].addEventListener('click', (e) => {
        let link = e.target.querySelector('a');
        if (link) {
            link.click();
        }
    });
}

for(let i = 0; i < submitListTr.length; i++) {
    submitListTr[i].addEventListener("click", (e) => {
        let mediaOrder = e.target.parentElement.children[0].innerText;

        window.location.href = "/admin/adminSubmitDetail?mediaOrder=" + mediaOrder;
    })
}
/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */

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