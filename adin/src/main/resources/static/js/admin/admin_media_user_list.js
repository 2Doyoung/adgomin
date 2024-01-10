/**
 * 전역변수
 */
const submitList = document.getElementById("submitList");
const advertiserUserList = document.getElementById("advertiserUserList");
const spans = document.querySelectorAll('.div-paging span span');

/**
 * 이벤트 함수
 */
submitList.addEventListener("click", () => {
    window.location.href = "/admin?page=1";
})

advertiserUserList.addEventListener("click", () => {
    window.location.href = "/admin?manage=advertiserUserList&page=1"
})

for(let i = 0; i < spans.length; i++) {
    spans[i].addEventListener('click', (e) => {
        let link = e.target.querySelector('a');
        if (link) {
            link.click();
        }
    });
}

function toggleOptions() {
    var options = document.getElementById("customSelectMarketing").querySelector(".marketing-options");
    var computedStyle = window.getComputedStyle(options);

    options.style.display = computedStyle.display === "none" ? "block" : "none";
}

function selectOption(option) {
    document.getElementById("customSelectMarketing").querySelector(".select-marketing").innerText = option;
    toggleOptions();
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