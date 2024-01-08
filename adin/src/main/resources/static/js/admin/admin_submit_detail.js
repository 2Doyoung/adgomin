/**
 * 전역변수
 */
const region = document.getElementById("region");
const adCategory = document.getElementById("adCategory");
const mediaUrl = document.getElementById("mediaUrl");

/**
 * 이벤트 함수
 */
mediaUrl.addEventListener("click", () => {
    window.open("http://" + getMediaUrl, ".blank")
})

/**
 * 사용자 함수
 */
let regionSplit = getRegion.split(',');

for(let i = 0; i < regionSplit.length; i++) {
    let spanTag = document.createElement("span");
    spanTag.append(regionSplit[i]);
    spanTag.classList.add("region-span")

    region.appendChild(spanTag);
}

let adCategorySplit = getAdCategory.split(',');

for(let i = 0; i < adCategorySplit.length; i++) {
    let spanTag = document.createElement("span");
    spanTag.append(adCategorySplit[i]);
    spanTag.classList.add("ad-category-span")

    adCategory.appendChild(spanTag);
}


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