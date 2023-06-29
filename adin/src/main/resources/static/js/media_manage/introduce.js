/**
 * 전역변수
 */
const saveButton = document.getElementById("saveButton");
const regionCategory = document.getElementsByClassName("region-category");
const adCategoryList = document.getElementsByClassName("ad-category-list");
const mediaIntroduce = document.getElementById("mediaIntroduce");

/**
 * 이벤트 함수
 */
saveButton.addEventListener("click", () => {
    let nickname = document.getElementById("nickname").value;
    let nicknameSpan = document.getElementById("nicknameSpan");
    let adCategorySpan = document.getElementById("adCategorySpan");

    if(!/^[A-Za-z0-9가-힣]{2,18}$/.test(nickname)) {
        nicknameSpan.style.color = "#FF0040";
        document.getElementById("nickname").focus();
    } else if(/^[A-Za-z0-9가-힣]{2,18}$/.test(nickname)) {
        nicknameSpan.style.color = "#BDBDBD";
    }

    if(document.getElementsByClassName("ad-category-selected").length <= 3) {
        adCategorySpan.style.color = "#BDBDBD";
    }

})

for(let i = 0; i < regionCategory.length; i++) {
    regionCategory[i].addEventListener("click", () => {
        if(!regionCategory[0].classList.contains("region-selected")) {
            if(i == 0) {
                if(!(document.getElementsByClassName("region-selected").length > 0)) {
                    if(regionCategory[i].classList.contains("region-selected")) {
                        regionCategory[i].classList.remove("region-selected");
                    } else {
                        regionCategory[i].classList.add("region-selected");
                    }
                }
            } else if(i != 0) {
                if(regionCategory[i].classList.contains("region-selected")) {
                    regionCategory[i].classList.remove("region-selected");
                } else {
                    regionCategory[i].classList.add("region-selected");
                }
            }
        } else if(regionCategory[0].classList.contains("region-selected")) {
            if(i == 0) {
                regionCategory[i].classList.remove("region-selected");
            }
        } else {

        }
    })
}

for(let i = 0; i < adCategoryList.length; i++) {
    adCategoryList[i].addEventListener("click", () => {
        if(document.getElementsByClassName("ad-category-selected").length > 2) {
            let adCategorySpan = document.getElementById("adCategorySpan");
            if(adCategoryList[i].classList.contains("ad-category-selected")) {
                adCategoryList[i].classList.remove("ad-category-selected");

                adCategorySpan.style.color = "#BDBDBD";
            } else if(!adCategoryList[i].classList.contains("ad-category-selected")) {
                adCategorySpan.style.color = "#FF0040";
            }
        } else if(!(document.getElementsByClassName("ad-category-selected").length > 2)) {

            if(adCategoryList[i].classList.contains("ad-category-selected")) {
                adCategoryList[i].classList.remove("ad-category-selected");
            } else {
                adCategoryList[i].classList.add("ad-category-selected");
            }
        }
    })
}

mediaIntroduce.addEventListener("keyup", () => {
    let introduceLength = document.getElementById("introduceLength");
    let mediaIntroduceValue = mediaIntroduce.value;
    let mediaIntroduceLength = mediaIntroduceValue.length;

    if(mediaIntroduceLength > 255) {
        mediaIntroduce.value = mediaIntroduceValue.substr(0, 255);
        mediaIntroduceLength = mediaIntroduce.value.length;
    }

    introduceLength.innerText = " (" +  mediaIntroduceLength + " / 255)";
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