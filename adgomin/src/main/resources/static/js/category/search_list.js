/**
 * 전역변수
 */
const spans = document.querySelectorAll('.div-paging span span');

const categoryCard = document.getElementsByClassName("category-card");

const orderOption = document.getElementById("orderOption");

const orderCheckboxLine = document.getElementsByClassName("order-checkbox-line");
const orderCheckbox = document.getElementsByClassName("order-checkbox");

/**
 * 이벤트 함수
 */
orderOption.addEventListener("click", () => {
    let arrowIcon = document.getElementsByClassName("arrow-icon")[0];
    let orderOptions = document.getElementsByClassName("order-options")[0];

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

for(let i = 0; i < spans.length; i++) {
    spans[i].addEventListener('click', (e) => {
        let link = e.target.querySelector('a');
        if (link) {
            link.click();
        }
    });
}

for(let i = 0; i < categoryCard.length; i++) {
    let categoryCardHover = document.getElementsByClassName("category-card-hover");

    categoryCard[i].addEventListener("click", (e) => {
        let mediaOrder = e.currentTarget.dataset.parent;

        window.location.href = "/post?mediaOrder=" + mediaOrder;
    })

    categoryCard[i].addEventListener("mouseover", () => {
        categoryCardHover[i].style.visibility = "visible";
    });

    categoryCard[i].addEventListener("mouseout", () => {
        categoryCardHover[i].style.visibility = "hidden";
    });
}

for(let i = 0; i < orderCheckboxLine.length; i++) {
    orderCheckboxLine[i].addEventListener("click", (e) => {
        order = e.currentTarget.children[0].children[0].dataset.parent;

        window.location.href = "/search?order=" + order + "&keyword=" + keyword + "&page=1";
    })
}
/**
 * 사용자 함수
 */
headerSearch.value = keyword;

document.title = "애드곰인 - " + keyword;

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