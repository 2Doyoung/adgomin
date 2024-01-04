/**
 * 전역변수
 */
const register = document.getElementById("register");
const saveButton = document.getElementById("saveButton");
const regionCategory = document.getElementsByClassName("region-category");
const adCategoryList = document.getElementsByClassName("ad-category-list");
const mediaIntroduce = document.getElementById("mediaIntroduce");
const profileImg = document.getElementById("profileImg");

const modalCheck = document.getElementById("modalCheck");

/**
 * 이벤트 함수
 */
register.addEventListener("click", () => {
    window.location.href = "/media?manage=mediaRegister";
})

profileImg.addEventListener("change", () => {
    let ext = profileImg.value.slice(profileImg.value.lastIndexOf(".") + 1).toLowerCase();

    if(!(ext == "jpg" || ext == "png" || ext == "jpeg")) {
        let extSpan = document.getElementById("extSpan");
        extSpan.style.color = "#FF0040";
    } else {
        const formData = new FormData();

        formData.append("profileFile", profileImg.files[0]);

        xhr("/change/profileImg", formData, "PATCH", "changeProfileImg");
    }
})

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

    if(!(nicknameSpan.style.color == 'rgb(255, 0, 64)')) {
        let mediaIntroduce = document.getElementById("mediaIntroduce").value;
        let regionSelected = document.getElementsByClassName("region-selected");
        let regionSelectedText = "";
        let adCategorySelected = document.getElementsByClassName("ad-category-selected");
        let adCategorySelectedText = "";
        let mediaUrl = document.getElementById("mediaUrlInput").value;

        const formData = new FormData();

        formData.append("nickname", nickname);
        formData.append("mediaIntroduce", mediaIntroduce);

        for(let i = 0; i < regionSelected.length; i++) {
            if(i == regionSelected.length - 1) {
                regionSelectedText += regionSelected[i].innerText
            } else if(!(i == regionSelected.length - 1)) {
                regionSelectedText += regionSelected[i].innerText + ","
            } else {

            }
        }

        for(let i = 0; i < adCategorySelected.length; i++) {
            if(i == adCategorySelected.length - 1) {
                adCategorySelectedText += adCategorySelected[i].innerText
            } else if(!(i == adCategorySelected.length - 1)) {
                adCategorySelectedText += adCategorySelected[i].innerText + ","
            } else {

            }
        }

        formData.append("region", regionSelectedText);
        formData.append("adCategory", adCategorySelectedText);
        formData.append("mediaUrl", mediaUrl);

        xhr("/media/introduce", formData, "PATCH", "mediaIntroduce");
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

modalCheck.addEventListener("click", () => {
    window.location.href = "/media";
})

/**
 * 사용자 함수
 */
let introduceLength = document.getElementById("introduceLength");
let mediaIntroduceValue = mediaIntroduce.value;
let mediaIntroduceLength = mediaIntroduceValue.length;

introduceLength.innerText = " (" +  mediaIntroduceLength + " / 255)";

if(getRegionSelected != null) {
    let getRegionSelectedArray = getRegionSelected.split(",");
    let getRegionCategory = document.getElementsByClassName("region-category");
    for(let i = 0; i < getRegionCategory.length; i++) {
        for(let j = 0; j < getRegionSelectedArray.length; j++) {
            if(getRegionCategory[i].innerText == getRegionSelectedArray[j]) {
                getRegionCategory[i].classList.add("region-selected")
            }
        }
    }
}

if(getAdCategorySelected != null) {
    let getAdCategorySelectedArray = getAdCategorySelected.split(",");
    let getAdCategoryList = document.getElementsByClassName("ad-category-list");
    for(let i = 0; i < getAdCategoryList.length; i++) {
        for(let j = 0; j < getAdCategorySelectedArray.length; j++) {
            if(getAdCategoryList[i].innerText == getAdCategorySelectedArray[j]) {
                getAdCategoryList[i].classList.add("ad-category-selected")
            }
        }
    }
}

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag === "mediaIntroduce") {
        let modalBg = document.getElementById("modalBg");
        let modalTitle = document.getElementById("modalTitle");

        modalBg.style.display = "block";
        modalTitle.innerText = "광고매체 소개 변경이 완료되었습니다.";
    } else if(flag === "changeProfileImg") {
        let modalBg = document.getElementById("modalBg");
        let modalTitle = document.getElementById("modalTitle");

        modalBg.style.display = "block";
        modalTitle.innerText = "프로필 변경이 완료되었습니다.";
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