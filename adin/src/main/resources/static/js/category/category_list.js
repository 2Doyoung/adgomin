/**
 * 전역변수
 */
const category = document.getElementsByClassName("category-list-span");
const categoryLi = document.getElementsByClassName("category-li");

/**
 * 이벤트 함수
 */

/**
 * 사용자 함수
 */
for(let i = 0; i < category.length; i++) {
    if(category[i].classList.contains(media)) {
        category[i].style.fontWeight = "600";
        category[i].style.color = "#303441";
    }
}

for(let i = 0; i < categoryLi.length; i++) {
    categoryLi[i].addEventListener("click", (e) => {
        let clickMedia = e.currentTarget.querySelector("span").classList[1];
        window.location.href = "/category?media=" + clickMedia;
    })
}

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