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

const portfolioSection = document.getElementById("portfolioSection");

const allPortfolio = document.getElementById("allPortfolio");

const conversationLogin = document.getElementById("conversationLogin");

const mediaMange = document.getElementById("mediaMange");

const conversation = document.getElementById("conversation");
const conversationModal = document.getElementById("conversationModal");
const conversationCloseBtn = document.getElementById("conversationCloseBtn");
const conversationModalButton = document.getElementById("conversationModalButton");
const conversationTextarea = document.getElementById("conversationTextarea");

const purchaseLogin = document.getElementById("purchaseLogin");
const mediaUpdate = document.getElementById("mediaUpdate");

const purchase = document.getElementById("purchase");

/**
 * 이벤트 함수
 */
nickname.addEventListener("click", () => {
    window.open(appUrl + "/introduce/" + userOrder.value);
})

introduceAllPage.addEventListener("click", () => {
    window.open(appUrl + "/introduce/" + userOrder.value);
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

if(conversationLogin != null) {
    conversationLogin.addEventListener("click", () => {
        loginCover.style.display = "flex";
        document.getElementById("loginBoxEmail").focus();
    })
}

if(mediaMange != null) {
    mediaMange.addEventListener("click", () => {
        window.location.href = "/manage?manage=media";
    })
}

if(conversation != null) {
    conversation.addEventListener("click", () => {
        conversationModal.style.display = "flex";
    })
}

if(conversationCloseBtn != null) {
    conversationCloseBtn.addEventListener("click", () => {
        conversationModal.style.display = "none";
    })
}

if(conversationModalButton != null) {
    conversationTextarea.addEventListener("keyup", () => {
        let conversationLength = document.getElementById("conversationLength");
        let conversationTextareaValue = conversationTextarea.value;
        let conversationTextareaLength = conversationTextareaValue.length;

        let conversationSpan = document.getElementById("conversationSpan");

        if(conversationTextareaLength > 255) {
            conversationTextarea.value = conversationTextareaValue.substr(0, 255);
            conversationTextareaLength = conversationTextarea.value.length;
        }

        conversationLength.innerText = " (" + conversationTextareaLength + " / 255)";

        if(conversationTextarea.value.length < 15) {
            conversationSpan.style.color = "#FF0040";
        } else if(conversationTextarea.value.length >= 15) {
            conversationSpan.style.color = "#BDBDBD";
        }
    })

    conversationModalButton.addEventListener("click", () => {
        let conversationSpan = document.getElementById("conversationSpan");

        if(!(conversationSpan.style.color == 'rgb(255, 0, 64)')) {
            let receiverOrder = document.getElementById("userOrder").value;
            let message = document.getElementById("conversationTextarea").value;

            const formData = new FormData();

            formData.append("receiverOrder", receiverOrder);
            formData.append("message", message);
            formData.append("mediaOrder", mediaOrder);
            formData.append("mediaTitle", mediaTitle);
            formData.append("mediaPrice", mediaPrice);
            formData.append("thumbnailImgNm", thumbnailImgNm);
            formData.append("thumbnailImgFilePath", thumbnailImgFilePath);

            xhr("/app/conversation", formData, "PATCH", "appConversation");
        }
    })

}

if(purchaseLogin != null) {
    purchaseLogin.addEventListener("click", () => {
        loginCover.style.display = "flex";
        document.getElementById("loginBoxEmail").focus();
    })
}

if(mediaUpdate != null) {
    mediaUpdate.addEventListener("click", () => {
        window.location.href = "/manage/media/update?mediaOrder=" + mediaOrder;
    })
}

if(purchase != null) {
    purchase.addEventListener("click", () => {
        window.location.href = "/payment?mediaOrder=" + mediaOrder;
    })
}

/**
 * 사용자 함수
 */
document.title = "애드곰인 - " + mediaTitle;

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

if(portfolioSection != null) {
    const portfolioSlider = document.getElementById('portfolioSlider');
    const portfolioPrevButton = document.getElementById('portfolioPrevButton');
    const portfolioNextButton = document.getElementById('portfolioNextButton');
    const portfolioSlide = portfolioSlider.querySelector('.portfolio-card');
    const portfolioSlideStyles = getComputedStyle(portfolioSlide);
    const portfolioSlideWidth = portfolioSlide.offsetWidth + parseFloat(portfolioSlideStyles.marginLeft) + parseFloat(portfolioSlideStyles.marginRight);
    const portfolioSlidesToShow = Math.floor(portfolioSlider.offsetWidth / portfolioSlideWidth);
    let portfolioCurrentIndex = 0;

    const portfolioCard = document.getElementsByClassName("portfolio-card");

    portfolioNextButton.addEventListener('click', () => {
        if (portfolioCurrentIndex < portfolioSlider.children.length - portfolioSlidesToShow) {
            portfolioCurrentIndex++;
            portfolioUpdateSlider();
        }
    });

    portfolioPrevButton.addEventListener('click', () => {
        if (portfolioCurrentIndex > 0) {
            portfolioCurrentIndex--;
            portfolioUpdateSlider();
        }
    });

    for(let i = 0; i < portfolioCard.length; i++) {
        portfolioCard[i].addEventListener("click", (e) => {
            const userOrder = document.getElementById("userOrder").value
            const portfolioOrder = e.currentTarget.dataset.parent;

            window.location.href = "/introduce/detail/portfolio/" + userOrder + "/" + portfolioOrder;
        })
    }

    allPortfolio.addEventListener("click", () => {
        const userOrder = document.getElementById("userOrder").value;

        window.location.href = "/introduce/all/portfolio/" + userOrder + "?page=1";
    })

    let portfolioUpdateSlider = () => {
        const translateValue = -portfolioCurrentIndex * portfolioSlideWidth + 'px';
        portfolioSlider.style.transform = 'translateX(' + translateValue + ')';
    }
}

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag == "appConversation") {
        let chatRoomOrder = responseObject['chatRoomEntity'];
        window.location.href = "/app/chat?chatRoomOrder=" + chatRoomOrder;
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