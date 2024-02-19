/**
 * 전역변수
 */
const region = document.getElementById("region");
const adCategory = document.getElementById("adCategory");

const mediaDetailExplainDiv = document.getElementById("mediaDetailExplainDiv");

const tabs = document.querySelectorAll('.post-tap-list');

const nickname = document.getElementById("nickname");
const introduceAllPage = document.getElementById("introduceAllPage");
const userOrder = document.getElementById("userOrder");

/**
 * 이벤트 함수
 */
nickname.addEventListener("click", () => {
    window.open("http://localhost:8080/introduce/" + userOrder.value);
})

introduceAllPage.addEventListener("click", () => {
    window.open("http://localhost:8080/introduce/" + userOrder.value);
})

tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        const targetId = tab.getAttribute('data-target');
        const targetSection = document.querySelector(targetId);

        const headerHeight = document.querySelector('header').offsetHeight;
        const postTapHeight = document.querySelector('.post-tap').offsetHeight;

        window.scrollTo({
            top: targetSection.offsetTop - headerHeight - postTapHeight,
            behavior: 'auto'
        });
    });
});

window.addEventListener('scroll', () => {
    const scrollPosition = window.scrollY;

    const headerHeight = document.querySelector('header').offsetHeight;

    const postTapHeight = document.querySelector('.post-tap').offsetHeight;

    tabs.forEach(tab => {
        const targetId = tab.getAttribute('data-target');
        const section = document.querySelector(targetId);

        if (section) {
            const sectionTop = section.offsetTop - headerHeight - postTapHeight;

            if (scrollPosition >= sectionTop) {
                tabs.forEach(tab => tab.classList.remove('active'));
                tab.classList.add('active');
            }
        }
    });
});

/**
 * 사용자 함수
 */
document.title = "애드인 - " + mediaTitle;

mediaDetailExplainDiv.innerHTML = mediaDetailExplain;

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

let paragraphs = document.getElementsByTagName("p");

for(let i = 0; i < paragraphs.length; i++) {
    let children = paragraphs[i].childNodes;
    for(let j = 0; j < children.length; j++) {
        if (children[j].nodeName === "BR") {
            paragraphs[i].classList.add("p-br");
            break;
        }
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