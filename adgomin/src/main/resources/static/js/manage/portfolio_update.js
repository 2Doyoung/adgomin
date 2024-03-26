/**
 * 전역변수
 */
const manageMedia = document.getElementById("manageMedia");

/**
 * 이벤트 함수
 */
manageMedia.addEventListener("click", () => {
    window.location.href = "/manage?manage=media";
})

/**
 * 사용자 함수
 */
let titleLength = document.getElementById("titleLength");
let portfolioTitleValue = document.getElementById("portfolioTitle").value;
let portfolioTitleLength = portfolioTitleValue.length;

titleLength.innerText = "(" + portfolioTitleLength + " / 30)";

let getAdDetailCategoryList = document.getElementsByClassName("ad-detail-category-list");
for(let i = 0; i < getAdDetailCategoryList.length; i++) {
    if(getAdDetailCategoryList[i].innerText == getPortfolioAdDetailCategorySelected) {
        getAdDetailCategoryList[i].classList.add("ad-detail-category-selected");
    }
}

let getAdCategoryList = document.getElementsByClassName("ad-category-list");
for(let i = 0; i < getAdCategoryList.length; i++) {
    if(getAdCategoryList[i].innerText == getPortfolioAdCategorySelected) {
        getAdCategoryList[i].classList.add("ad-category-selected");
    }
}

let getRegionCategory = document.getElementsByClassName("region-category");
for(let i = 0; i < getRegionCategory.length; i++) {
    if(getRegionCategory[i].innerText == getPortfolioRegionSelected) {
        getRegionCategory[i].classList.add("region-selected");
    }
}

const fileCount = document.getElementById("fileCount");
fileCount.innerText = " (" + getPortfolioImg.length + " / 5)";

for(let i = 0; i < getPortfolioImg.length; i++) {
    const thumbnailsContainer = document.getElementById('details');

    const thumbnailContainer = document.createElement('div');
    const thumbnailImg = document.createElement('img');
    const deleteButton = document.createElement('button');

    thumbnailImg.src = "/portfolio/thumbnail/image?mainImgNm=" + getPortfolioImg[i].imgNm + "&mainImgFilePath=" + getPortfolioImg[i].imgFilePath;

    thumbnailContainer.classList.add('detail-container');
    thumbnailImg.classList.add('detail-img');
    deleteButton.classList.add('delete-btn');
    deleteButton.innerHTML = '<i class="ri-delete-bin-fill" data-parent="' + getPortfolioImg[i].portfolioImgOrder + '"></i>';

    thumbnailContainer.appendChild(thumbnailImg);
    thumbnailContainer.appendChild(deleteButton);
    thumbnailsContainer.appendChild(thumbnailContainer);
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