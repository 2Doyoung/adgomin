/**
 * 전역변수
 */
const updateButton = document.getElementById("updateButton");

const manageMedia = document.getElementById("manageMedia");
const portfolioTitle = document.getElementById("portfolioTitle");

const adDetailCategoryList = document.getElementsByClassName("ad-detail-category-list");
const adCategoryList = document.getElementsByClassName("ad-category-list");
const regionCategory = document.getElementsByClassName("region-category");

const thumbnailImg = document.getElementById("thumbnailImg");

const detailImg = document.getElementById("detailImg");

let thumbnailImgSave;

let currentCount = getPortfolioImg.length;

let existDetailImgArr = getPortfolioImg;

let detailImgArr = [];

/**
 * 이벤트 함수
 */
updateButton.addEventListener("click", () => {
    let portfolioTitle = document.getElementById("portfolioTitle").value;
    let titleSpan = document.getElementById("titleSpan");

    let adDetailCategorySelected = document.getElementsByClassName("ad-detail-category-selected");
    let adDetailCategorySpan = document.getElementById("adDetailCategorySpan");

    let adCategorySelected = document.getElementsByClassName("ad-category-selected");
    let adCategorySpan = document.getElementById("adCategorySpan");

    let regionSelected = document.getElementsByClassName("region-selected");
    let regionSpan = document.getElementById("regionSpan");

    let thumbnailSpan = document.getElementById("thumbnailSpan");
    let extSpan = document.getElementById("extSpan");

    let detailSpan = document.getElementById("detailSpan");
    let details = document.getElementById("details");

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

    if(extSpan.style.color == 'rgb(255, 0, 64)') {
        thumbnailSpan.style.color = "#FF0040";
    } else {
        thumbnailSpan.style.color = "#BDBDBD";
    }

    if(details.innerHTML == '') {
        detailSpan.style.color = "#FF0040";
    } else {
        detailSpan.style.color = "#BDBDBD";
    }

    if(!(titleSpan.style.color == 'rgb(255, 0, 64)') && !(adDetailCategorySpan.style.color == 'rgb(255, 0, 64)')
        && !(adCategorySpan.style.color == 'rgb(255, 0, 64)') && !(regionSpan.style.color == 'rgb(255, 0, 64)')
        && !(thumbnailSpan.style.color == 'rgb(255, 0, 64)') && !(detailSpan.style.color == 'rgb(255, 0, 64)')) {
        let portfolioOrder = document.getElementById("portfolioOrder").value;
        let portfolioTitle = document.getElementById("portfolioTitle").value;
        let adDetailCategorySelected = document.getElementsByClassName("ad-detail-category-selected")[0].innerText;
        let adCategorySelected = document.getElementsByClassName("ad-category-selected")[0].innerText;
        let regionSelected = document.getElementsByClassName("region-selected")[0].innerText;

        const formData = new FormData();

        formData.append("portfolioOrder", portfolioOrder);
        formData.append("portfolioTitle", portfolioTitle);
        formData.append("portfolioAdDetailCategory", adDetailCategorySelected);
        formData.append("portfolioAdCategory", adCategorySelected);
        formData.append("portfolioRegion", regionSelected);

        xhr("/manage/portfolio/update", formData, "PATCH", "managePortfolioUpdate");
    }
})

manageMedia.addEventListener("click", () => {
    window.location.href = "/manage?manage=media";
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
    let thumbnailSpan = document.getElementById("thumbnailSpan");

    if(!(ext == "jpg" || ext == "png" || ext == "jpeg")) {
        extSpan.style.color = "#FF0040";

        const thumbnailBasicImg = document.getElementsByClassName("thumbnailBasicImg")[0];
        const thumbnailChange = document.getElementsByClassName("thumbnailChange")[0];

        const img = document.createElement('img');
        img.classList.add("thumbnailBasicImg");
        img.setAttribute('src', '/images/media-register-thumbnail.png');

        if(thumbnailBasicImg != undefined) {
            document.getElementsByClassName("thumbnail")[0].removeChild(thumbnailBasicImg);
        } else if(thumbnailChange != undefined) {
            document.getElementsByClassName("thumbnail")[0].removeChild(thumbnailChange);
        }

        document.getElementsByClassName("thumbnail")[0].appendChild(img);
    } else {
        extSpan.style.color = "#BDBDBD";
        thumbnailSpan.style.color = "#BDBDBD";

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

detailImg.addEventListener("change", () => {
    const files = document.getElementById('detailImg').files;
    const thumbnailsContainer = document.getElementById('details');
    const maxFiles = 5;
    const fileCount = document.getElementById("fileCount");

    currentCount += files.length;

    if (currentCount > maxFiles) {
        return;
    }

    for(let i = 0; i < files.length; i++) {
        const file = files[i];
        if (!file.type.match('image.*')) {
            currentCount--;
            continue;
        }
        fileCount.innerText = " (" +  currentCount + " / 5)";
        const reader = new FileReader();

        reader.onload = (e) => {
            const thumbnailContainer = document.createElement('div');
            const thumbnailImg = document.createElement('img');
            const deleteButton = document.createElement('button');

            thumbnailImg.src = e.target.result;

            thumbnailContainer.classList.add('detail-container');
            thumbnailImg.classList.add('detail-img');
            deleteButton.classList.add('delete-btn');
            deleteButton.innerHTML = '<i class="ri-delete-bin-fill" data-parent="' + file.lastModified + '"></i>';
            deleteButton.addEventListener('click', (e) => {
                let lastModified = e.currentTarget.children[0].dataset.parent;

                for(let i = 0; i < detailImgArr.length; i++) {
                    if(detailImgArr[i].lastModified == lastModified) {
                        detailImgArr.splice(i, 1);
                    }
                }

                thumbnailsContainer.removeChild(thumbnailContainer);
                currentCount--;

                fileCount.innerText = " (" +  currentCount + " / 5)";
            });

            thumbnailContainer.appendChild(thumbnailImg);
            thumbnailContainer.appendChild(deleteButton);
            thumbnailsContainer.appendChild(thumbnailContainer);
        };

        detailImgArr.push(file);
        reader.readAsDataURL(file);
    }
    document.getElementById("detailImg").value = "";
})

modalCheck.addEventListener("click", () => {
    window.location.href = "/manage?manage=portfolio";
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
    deleteButton.addEventListener('click', (e) => {
        let portfolioImgOrder = e.currentTarget.children[0].dataset.parent;

        for(let i = 0; i < existDetailImgArr.length; i++) {
            if(existDetailImgArr[i].portfolioImgOrder == portfolioImgOrder) {
                existDetailImgArr.splice(i, 1);
            }
        }

        thumbnailsContainer.removeChild(thumbnailContainer);
        currentCount--;

        fileCount.innerText = " (" +  currentCount + " / 5)";
    });

    thumbnailContainer.appendChild(thumbnailImg);
    thumbnailContainer.appendChild(deleteButton);
    thumbnailsContainer.appendChild(thumbnailContainer);
}
/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag == "managePortfolioUpdate") {
        const formData = new FormData();

        let portfolioOrder = document.getElementById("portfolioOrder").value;

        if(thumbnailImgSave != undefined) {
            formData.append("portfolioOrder", portfolioOrder);
            formData.append("thumbnail", thumbnailImgSave);
            xhr("/manage/portfolio/change/thumbnail", formData, "PATCH", "managePortfolioChangeThumbnail");
        } else {
            const formData = new FormData();

            let portfolioOrder = document.getElementById("portfolioOrder").value;
            formData.append("portfolioOrder", portfolioOrder);

            formData.append("existDetailImgArr",JSON.stringify(existDetailImgArr));

            for(let i = 0; i < detailImgArr.length; i++) {
                formData.append('detailImgArr', detailImgArr[i]);
            }

            xhr("/manage/portfolio/detail/img/update", formData, "PATCH", "managePortfolioDetailImgUpdate");
        }
    } else if(flag == "managePortfolioChangeThumbnail") {
        const formData = new FormData();

        let portfolioOrder = document.getElementById("portfolioOrder").value;
        formData.append("portfolioOrder", portfolioOrder);

        formData.append("existDetailImgArr",JSON.stringify(existDetailImgArr));

        for(let i = 0; i < detailImgArr.length; i++) {
            formData.append('detailImgArr', detailImgArr[i]);
        }

        xhr("/manage/portfolio/detail/img/update", formData, "PATCH", "managePortfolioDetailImgUpdate");
    } else if(flag == "managePortfolioDetailImgUpdate") {
        let modalBg = document.getElementById("modalBg");
        let modalTitle = document.getElementById("modalTitle");

        modalBg.style.display = "block";
        modalTitle.innerText = "포트폴리 수정이 완료되었습니다.";
    }
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