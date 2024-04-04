/**
 * 전역변수
 */
const region = document.getElementById("region");
const adCategory = document.getElementById("adCategory");
const mediaUrl = document.getElementById("mediaUrl");

const tabs = document.querySelectorAll('.introduce-tap-list');

const portfolioSection = document.getElementById("portfolioSection");

/**
 * 이벤트 함수
 */
if(mediaUrl != null) {
    mediaUrl.addEventListener("click", () => {
        window.open("https://" + getMediaUrl, ".blank")
    })
}

tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        const targetId = tab.getAttribute('data-target');
        const targetSection = document.querySelector(targetId);

        const headerHeight = document.querySelector('header').offsetHeight;
        const introduceTapHeight = document.querySelector('.introduce-tap').offsetHeight;

        window.scrollTo({
            top: targetSection.offsetTop - headerHeight - introduceTapHeight,
            behavior: 'auto'
        });
    });
});

window.addEventListener('scroll', () => {
    const scrollPosition = window.scrollY;

    const headerHeight = document.querySelector('header').offsetHeight;

    const introduceTapHeight = document.querySelector('.introduce-tap').offsetHeight;

    tabs.forEach(tab => {
        const targetId = tab.getAttribute('data-target');
        const section = document.querySelector(targetId);

        if (section) {
            const sectionTop = section.offsetTop - headerHeight - introduceTapHeight;

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
let regionSplit = getRegion.split(',');

for(let i = 0; i < regionSplit.length; i++) {
    let regionSpanTag = document.createElement("span");
    regionSpanTag.append(regionSplit[i]);
    regionSpanTag.classList.add("region-span")

    region.appendChild(regionSpanTag);
}

let adCategorySplit = getAdCategory.split(',');

for(let i = 0; i < adCategorySplit.length; i++) {
    let adCategorySpanTag = document.createElement("span");
    adCategorySpanTag.append(adCategorySplit[i]);
    adCategorySpanTag.classList.add("ad-category-span")

    adCategory.appendChild(adCategorySpanTag);
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

    const allPortfolio = document.getElementById("allPortfolio");

    const menuAllPortfolio = document.getElementById("menuAllPortfolio");

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

    allPortfolio.addEventListener("click", () => {
        const userOrder = document.getElementById("userOrder").value;

        window.location.href = "/introduce/all/portfolio/" + userOrder + "?page=1";
    })

    menuAllPortfolio.addEventListener("click", () => {
        const userOrder = document.getElementById("userOrder").value;

        window.location.href = "/introduce/all/portfolio/" + userOrder + "?page=1";
    })

    for(let i = 0; i < portfolioCard.length; i++) {
        portfolioCard[i].addEventListener("click", (e) => {
            const userOrder = document.getElementById("userOrder").value
            const portfolioOrder = e.currentTarget.dataset.parent;

            window.location.href = "/introduce/detail/portfolio/" + userOrder + "/" + portfolioOrder;
        })
    }

    let portfolioUpdateSlider = () => {
        const translateValue = -portfolioCurrentIndex * portfolioSlideWidth + 'px';
        portfolioSlider.style.transform = 'translateX(' + translateValue + ')';
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