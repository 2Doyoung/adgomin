/**
 * 전역변수
 */
const portfolioAdDetailCategory = document.getElementById("portfolioAdDetailCategory");
const portfolioAdCategory = document.getElementById("portfolioAdCategory");
const portfolioRegion = document.getElementById("portfolioRegion");

/**
 * 이벤트 함수
 */

/**
 * 사용자 함수
 */
let portfolioAdDetailCategorySplit = getPortfolioAdDetailCategory.split(',');

for(let i = 0; i < portfolioAdDetailCategorySplit.length; i++) {
    let adDetailCategorySpanTag = document.createElement("span");
    adDetailCategorySpanTag.append(portfolioAdDetailCategorySplit[i]);
    adDetailCategorySpanTag.classList.add("ad-detail-category-span");

    portfolioAdDetailCategory.appendChild(adDetailCategorySpanTag);
}

let portfolioAdCategorySplit = getPortfolioAdCategory.split(',');

for(let i = 0; i < portfolioAdCategorySplit.length; i++) {
    let adCategorySpanTag = document.createElement("span");
    adCategorySpanTag.append(portfolioAdCategorySplit[i]);
    adCategorySpanTag.classList.add("ad-category-span");

    portfolioAdCategory.appendChild(adCategorySpanTag);
}

let portfolioRegionSplit = getPortfolioRegion.split(',');

for(let i = 0; i < portfolioRegionSplit.length; i++) {
    let regionSpanTag = document.createElement("span");
    regionSpanTag.append(portfolioRegionSplit[i]);
    regionSpanTag.classList.add("region-span");

    portfolioRegion.appendChild(regionSpanTag);
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