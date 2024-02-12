/**
 * 전역변수
 */
const region = document.getElementById("region");
const adCategory = document.getElementById("adCategory");

const mediaDetailExplain = document.getElementById("mediaDetailExplain");

/**
 * 이벤트 함수
 */

/**
 * 사용자 함수
 */
document.title = "애드인 - " + mediaTitle;

let regionSplit = getRegion.split(",");

for(let i = 0; i < regionSplit.length; i++) {
    let regionSpanTag = document.createElement("span");

    regionSpanTag.append(regionSplit[i]);
    regionSpanTag.classList.add("region-span");

    region.appendChild(regionSpanTag);
}

let adCategorySplit = getAdCategory.split(",");

for(let i = 0; i < adCategorySplit.length; i++) {
    let adCategorySpanTag = document.createElement("span");

    adCategorySpanTag.append(adCategorySplit[i]);
    adCategorySpanTag.classList.add("ad-category-span");

    adCategory.appendChild(adCategorySpanTag);
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