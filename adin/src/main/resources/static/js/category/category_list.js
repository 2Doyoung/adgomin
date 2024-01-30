/**
 * 전역변수
 */
const category = document.getElementsByClassName("category-list-span");
const categoryLi = document.getElementsByClassName("category-li");
const adCategoryOption = document.getElementById("adCategoryOption");
const regionOption = document.getElementById("regionOption");
const orderOption = document.getElementById("orderOption");

const adCategoryCheckboxLine = document.getElementsByClassName("ad-category-checkbox-line");
const adCategoryCheckbox = document.getElementsByClassName("ad-category-checkbox");

const regionCheckboxLine = document.getElementsByClassName("region-checkbox-line");
const regionCheckbox = document.getElementsByClassName("region-checkbox");

const adCategoryApply = document.getElementById("adCategoryApply");
const regionApply = document.getElementById("regionApply");

const selectAdCategoryClose = document.getElementById("selectAdCategoryClose");
const selectRegionClose = document.getElementById("selectRegionClose");

const orderCheckboxLine = document.getElementsByClassName("order-checkbox-line");
const orderCheckbox = document.getElementsByClassName("order-checkbox");

const categoryCard = document.getElementsByClassName("category-card");

const spans = document.querySelectorAll('.div-paging span span');

/**
 * 이벤트 함수
 */
adCategoryOption.addEventListener("click", () => {
    let arrowIcon = document.getElementsByClassName("arrow-icon")[0];
    let adCategoryOptions = document.getElementsByClassName("ad-category-options")[0];

    let arrowIcon1 = document.getElementsByClassName("arrow-icon")[1];
    let regionOptions = document.getElementsByClassName("region-options")[0];

    let arrowIcon2 = document.getElementsByClassName("arrow-icon")[2];
    let orderOptions = document.getElementsByClassName("order-options")[0];

    if(arrowIcon1.classList.contains("arrow-icon-up") || arrowIcon2.classList.contains("arrow-icon-up-order")) {
        arrowIcon1.classList.remove("arrow-icon-up");
        arrowIcon1.classList.add("arrow-icon-down");
        regionOptions.classList.remove("region-options-visible");
        regionOptions.classList.add("region-options-hidden");

        arrowIcon2.classList.remove("arrow-icon-up-order");
        arrowIcon2.classList.add("arrow-icon-down-order");
        orderOptions.classList.remove("order-options-visible");
        orderOptions.classList.add("order-options-hidden");
    }

    if(arrowIcon.classList.contains("arrow-icon-down")) {
        arrowIcon.classList.remove("arrow-icon-down");
        arrowIcon.classList.add("arrow-icon-up");
        adCategoryOptions.classList.remove("ad-category-options-hidden");
        adCategoryOptions.classList.add("ad-category-options-visible");
    } else if(arrowIcon.classList.contains("arrow-icon-up")) {
        arrowIcon.classList.remove("arrow-icon-up");
        arrowIcon.classList.add("arrow-icon-down");
        adCategoryOptions.classList.remove("ad-category-options-visible");
        adCategoryOptions.classList.add("ad-category-options-hidden");
    }
})

regionOption.addEventListener("click", () => {
    let arrowIcon = document.getElementsByClassName("arrow-icon")[1];
    let regionOptions = document.getElementsByClassName("region-options")[0];

    let arrowIcon1 = document.getElementsByClassName("arrow-icon")[0];
    let adCategoryOptions = document.getElementsByClassName("ad-category-options")[0];

    let arrowIcon2 = document.getElementsByClassName("arrow-icon")[2];
    let orderOptions = document.getElementsByClassName("order-options")[0];

    if(arrowIcon1.classList.contains("arrow-icon-up") || arrowIcon2.classList.contains("arrow-icon-up-order")) {
        arrowIcon1.classList.remove("arrow-icon-up");
        arrowIcon1.classList.add("arrow-icon-down");
        adCategoryOptions.classList.remove("ad-category-options-visible");
        adCategoryOptions.classList.add("ad-category-options-hidden");

        arrowIcon2.classList.remove("arrow-icon-up-order");
        arrowIcon2.classList.add("arrow-icon-down-order");
        orderOptions.classList.remove("order-options-visible");
        orderOptions.classList.add("order-options-hidden");
    }

    if(arrowIcon.classList.contains("arrow-icon-down")) {
        arrowIcon.classList.remove("arrow-icon-down");
        arrowIcon.classList.add("arrow-icon-up");
        regionOptions.classList.remove("region-options-hidden");
        regionOptions.classList.add("region-options-visible");
    } else if(arrowIcon.classList.contains("arrow-icon-up")) {
        arrowIcon.classList.remove("arrow-icon-up");
        arrowIcon.classList.add("arrow-icon-down");
        regionOptions.classList.remove("region-options-visible");
        regionOptions.classList.add("region-options-hidden");
    }
})

orderOption.addEventListener("click", () => {
    let arrowIcon = document.getElementsByClassName("arrow-icon")[2];
    let orderOptions = document.getElementsByClassName("order-options")[0];

    let arrowIcon1 = document.getElementsByClassName("arrow-icon")[0];
    let adCategoryOptions = document.getElementsByClassName("ad-category-options")[0];

    let arrowIcon2 = document.getElementsByClassName("arrow-icon")[1];
    let regionOptions = document.getElementsByClassName("region-options")[0];

    if(arrowIcon1.classList.contains("arrow-icon-up") || arrowIcon2.classList.contains("arrow-icon-up")) {
        arrowIcon1.classList.remove("arrow-icon-up");
        arrowIcon1.classList.add("arrow-icon-down");
        adCategoryOptions.classList.remove("ad-category-options-visible");
        adCategoryOptions.classList.add("ad-category-options-hidden");

        arrowIcon2.classList.remove("arrow-icon-up");
        arrowIcon2.classList.add("arrow-icon-down");
        regionOptions.classList.remove("region-options-visible");
        regionOptions.classList.add("region-options-hidden");
    }

    if(arrowIcon.classList.contains("arrow-icon-down-order")) {
        arrowIcon.classList.remove("arrow-icon-down-order");
        arrowIcon.classList.add("arrow-icon-up-order");
        orderOptions.classList.remove("order-options-hidden");
        orderOptions.classList.add("order-options-visible");
    } else if(arrowIcon.classList.contains("arrow-icon-up-order")) {
        arrowIcon.classList.remove("arrow-icon-up-order");
        arrowIcon.classList.add("arrow-icon-down-order");
        orderOptions.classList.remove("order-options-visible");
        orderOptions.classList.add("order-options-hidden");
    }
})

for(let i = 0; i < adCategoryCheckboxLine.length; i++) {
    adCategoryCheckboxLine[i].addEventListener("click", () => {
        for (let j = 0; j < adCategoryCheckboxLine.length; j++) {
            if (j !== i) {
                adCategoryCheckbox[j].classList.remove("check-box-color");
            }
        }

        adCategoryCheckbox[i].classList.toggle("check-box-color");
    });
}

for(let i = 0; i < regionCheckboxLine.length; i++) {
    regionCheckboxLine[i].addEventListener("click", () => {
        for (let j = 0; j < regionCheckboxLine.length; j++) {
            if (j !== i) {
                regionCheckbox[j].classList.remove("check-box-color");
            }
        }

        regionCheckbox[i].classList.toggle("check-box-color");
    });
}

for(let i = 0; i < categoryLi.length; i++) {
    categoryLi[i].addEventListener("click", (e) => {
        let clickAdDetailCategory = e.currentTarget.querySelector("span").classList[1];
        window.location.href = "/category?order=" + order + "&adDetailCategory=" + clickAdDetailCategory + "&page=1";
    })
}

for(let i = 0; i < orderCheckboxLine.length; i++) {
    orderCheckboxLine[i].addEventListener("click", (e) => {
        order = e.target.children[0].children[0].dataset.parent;

        window.location.href = "/category?order=" + order + "&adDetailCategory=" + adDetailCategory + "&adCategory=" + adCategory + "&region=" + region + "&page=1";
    })
}

adCategoryApply.addEventListener("click", () => {
    for(let i = 0; i < adCategoryCheckbox.length; i++) {
        if(adCategoryCheckbox[i].classList.contains("check-box-color")) {
            adCategory = document.getElementsByClassName("ad-category-checkbox")[i].dataset.parent;
        }
    }

    window.location.href = "/category?order=" + order + "&adDetailCategory=" + adDetailCategory + "&adCategory=" + adCategory + "&region=" + region + "&page=1";
})

regionApply.addEventListener("click", () => {
    for(let i = 0; i < regionCheckbox.length; i++) {
        if(regionCheckbox[i].classList.contains("check-box-color")) {
            region = document.getElementsByClassName("region-checkbox")[i].dataset.parent;
        }
    }

    window.location.href = "/category?order=" + order + "&adDetailCategory=" + adDetailCategory + "&adCategory=" + adCategory + "&region=" + region + "&page=1";
})

if(selectAdCategoryClose != null) {
    selectAdCategoryClose.addEventListener("click", () => {
        window.location.href = "/category?order=" + order + "&adDetailCategory=" + adDetailCategory + "&adCategory=null" + "&region=" + region + "&page=1";
    })
}

if(selectRegionClose != null) {
    selectRegionClose.addEventListener("click", () => {
        window.location.href = "/category?order=" + order + "&adDetailCategory=" + adDetailCategory + "&adCategory=" + adCategory + "&region=null" + "&page=1";
    })
}

for(let i = 0; i < categoryCard.length; i++) {
    let categoryCardHover = document.getElementsByClassName("category-card-hover");

    categoryCard[i].addEventListener("mouseover", () => {
        categoryCardHover[i].style.visibility = "visible";
    });

    categoryCard[i].addEventListener("mouseout", () => {
        categoryCardHover[i].style.visibility = "hidden";
    });
}

for(let i = 0; i < spans.length; i++) {
    spans[i].addEventListener('click', (e) => {
        let link = e.target.querySelector('a');
        if (link) {
            link.click();
        }
    });
}
/**
 * 사용자 함수
 */
for(let i = 0; i < category.length; i++) {
    if(category[i].classList.contains(adDetailCategory)) {
        category[i].style.fontWeight = "600";
        category[i].style.color = "#303441";
    }
}

for(let i = 0; i < adCategoryCheckbox.length; i++) {
    if(adCategory === adCategoryCheckbox[i].dataset.parent) {
        adCategoryCheckbox[i].classList.add("check-box-color");
    }
}

for(let i = 0; i < regionCheckbox.length; i++) {
    if(region === regionCheckbox[i].dataset.parent) {
        regionCheckbox[i].classList.add("check-box-color");
    }
}

for(let i = 0; i < orderCheckbox.length; i++) {
    if(order === orderCheckbox[i].dataset.parent) {
        orderCheckbox[i].classList.add("check-box-color");
    }
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