/**
 * 전역변수
 */
const introduce = document.getElementById("introduce");

/**
 * 이벤트 함수
 */
introduce.addEventListener("click", () => {
    window.location.href = "/media";
})
/**
 * 사용자 함수
 */
ClassicEditor.create( document.querySelector( '#editor' ) );

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