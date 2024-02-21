/**
 * 전역변수
 */
const portfolioTitle = document.getElementById("portfolioTitle");
const saveButton = document.getElementById("saveButton");

const adDetailCategoryList = document.getElementsByClassName("ad-detail-category-list");
const adCategoryList = document.getElementsByClassName("ad-category-list");
const regionCategory = document.getElementsByClassName("region-category");

const thumbnailImg = document.getElementById("thumbnailImg");

let thumbnailImgSave;

/**
 * 이벤트 함수
 */
saveButton.addEventListener("click", () => {
    let portfolioTitle = document.getElementById("portfolioTitle").value;
    let titleSpan = document.getElementById("titleSpan");

    let adDetailCategorySelected = document.getElementsByClassName("ad-detail-category-selected");
    let adDetailCategorySpan = document.getElementById("adDetailCategorySpan");

    let adCategorySelected = document.getElementsByClassName("ad-category-selected");
    let adCategorySpan = document.getElementById("adCategorySpan");

    let regionSelected = document.getElementsByClassName("region-selected");
    let regionSpan = document.getElementById("regionSpan");

    let thumbnailImg = document.getElementById("thumbnailImg");
    let thumbnailSpan = document.getElementById("thumbnailSpan");
    let extSpan = document.getElementById("extSpan");

    if(portfolioTitle.length < 1) {
        titleSpan.style.color = "#FF0040";
        document.getElementById("portfolioTitle").focus();
    } else if(portfolioTitle.length > 0) {
        titleSpan.style.color = "#BDBDBD";
    }

    if(adDetailCategorySelected[0] == undefined) {
        adDetailCategorySpan.style.color = "#FF0040";
        document.getElementById("adDetailCategoryFocus").focus();
    } else {
        adDetailCategorySpan.style.color = "#BDBDBD";
    }

    if(adCategorySelected[0] == undefined) {
        adCategorySpan.style.color = "#FF0040";
        document.getElementById("adCategoryFocus").focus();
    } else {
        adCategorySpan.style.color = "#BDBDBD";
    }

    if(regionSelected[0] == undefined) {
        regionSpan.style.color = "#FF0040";
        document.getElementById("regionFocus").focus();
    } else {
        regionSpan.style.color = "#BDBDBD";
    }

    if(thumbnailImg.value == '' || extSpan.style.color == 'rgb(255, 0, 64)') {
        thumbnailSpan.style.color = "#FF0040";
    } else {
        thumbnailSpan.style.color = "#BDBDBD";
    }
})
portfolioTitle.addEventListener("keyup", () => {
    let titleLength = document.getElementById("titleLength");
    let portfolioTitleValue = portfolioTitle.value;
    let portfolioTitleLength = portfolioTitleValue.length;

    if(portfolioTitleLength > 30) {
        portfolioTitle.value = portfolioTitleValue.substr(0, 30);
        portfolioTitleLength = portfolioTitle.value.length;
    }

    titleLength.innerText = " (" +  portfolioTitleLength + " / 30)";
})

for(let i = 0; i < adDetailCategoryList.length; i++) {
    adDetailCategoryList[i].addEventListener("click", () => {
        if(document.getElementsByClassName("ad-detail-category-selected").length > 0) {
            if(adDetailCategoryList[i].classList.contains("ad-detail-category-selected")) {
                adDetailCategoryList[i].classList.remove("ad-detail-category-selected");
            }
        } else if(!(document.getElementsByClassName("ad-detail-category-selected").length > 0)) {
            if(adDetailCategoryList[i].classList.contains("ad-detail-category-selected")) {
                adDetailCategoryList[i].classList.remove("ad-detail-category-selected");
            } else {
                adDetailCategoryList[i].classList.add("ad-detail-category-selected");
            }
        }
    })
}

for(let i = 0; i < adCategoryList.length; i++) {
    adCategoryList[i].addEventListener("click", () => {
        if(document.getElementsByClassName("ad-category-selected").length > 0) {
            if(adCategoryList[i].classList.contains("ad-category-selected")) {
                adCategoryList[i].classList.remove("ad-category-selected");
            }
        } else if(!(document.getElementsByClassName("ad-category-selected").length > 0)) {
            if(adCategoryList[i].classList.contains("ad-category-selected")) {
                adCategoryList[i].classList.remove("ad-category-selected");
            } else {
                adCategoryList[i].classList.add("ad-category-selected");
            }
        }
    })
}

for(let i = 0; i < regionCategory.length; i++) {
    regionCategory[i].addEventListener("click", () => {
        if(document.getElementsByClassName("region-selected").length > 0) {
            if(regionCategory[i].classList.contains("region-selected")) {
                regionCategory[i].classList.remove("region-selected");
            }
        } else if(!(document.getElementsByClassName("region-selected").length > 0)) {
            if(regionCategory[i].classList.contains("region-selected")) {
                regionCategory[i].classList.remove("region-selected");
            } else {
                regionCategory[i].classList.add("region-selected");
            }
        }
    })
}

thumbnailImg.addEventListener("change", (e) => {
    let ext = thumbnailImg.value.slice(thumbnailImg.value.lastIndexOf(".") + 1).toLowerCase();
    let extSpan = document.getElementById("extSpan");

    if(!(ext == "jpg" || ext == "png" || ext == "jpeg")) {
        extSpan.style.color = "#FF0040";
    } else {
        extSpan.style.color = "#BDBDBD";

        const reader = new FileReader();

        reader.onload = (e) => {
            const thumbnailBasicImg = document.getElementsByClassName("thumbnailBasicImg")[0];
            const thumbnailChange = document.getElementsByClassName("thumbnailChange")[0];
            const img = document.createElement('img');
            img.classList.add("thumbnailChange");
            img.setAttribute('src', e.target.result);
            if(thumbnailBasicImg != undefined) {
                document.getElementsByClassName("thumbnail")[0].removeChild(thumbnailBasicImg);
            } else if(thumbnailChange != undefined) {
                document.getElementsByClassName("thumbnail")[0].removeChild(thumbnailChange);
            }
            document.getElementsByClassName("thumbnail")[0].appendChild(img);
        }

        reader.readAsDataURL(e.target.files[0]);

        thumbnailImgSave = e.target.files[0];
    }
})
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