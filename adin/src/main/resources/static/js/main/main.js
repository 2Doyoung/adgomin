/**
 * 전역변수
 */
const snsSlider = document.getElementById('snsSlider');
const snsPrevButton = document.getElementById('snsPrevButton');
const snsNextButton = document.getElementById('snsNextButton');
const snsSlide = snsSlider.querySelector('.popular-sns-card');
const snsSlideStyles = getComputedStyle(snsSlide);
const snsSlideWidth = snsSlide.offsetWidth + parseFloat(snsSlideStyles.marginLeft) + parseFloat(snsSlideStyles.marginRight);
const snsSlidesToShow = Math.floor(snsSlider.offsetWidth / snsSlideWidth);
let snsCurrentIndex = 0;

const transportSlider = document.getElementById('transportSlider');
const transportPrevButton = document.getElementById('transportPrevButton');
const transportNextButton = document.getElementById('transportNextButton');
const transportSlide = transportSlider.querySelector('.popular-transport-card');
const transportSlideStyles = getComputedStyle(transportSlide);
const transportSlideWidth = transportSlide.offsetWidth + parseFloat(transportSlideStyles.marginLeft) + parseFloat(transportSlideStyles.marginRight);
const transportSlidesToShow = Math.floor(transportSlider.offsetWidth / transportSlideWidth);
let transportCurrentIndex = 0;

const outdoorSlider = document.getElementById('outdoorSlider');
const outdoorPrevButton = document.getElementById('outdoorPrevButton');
const outdoorNextButton = document.getElementById('outdoorNextButton');
const outdoorSlide = outdoorSlider.querySelector('.popular-outdoor-card');
const outdoorSlideStyles = getComputedStyle(outdoorSlide);
const outdoorSlideWidth = outdoorSlide.offsetWidth + parseFloat(outdoorSlideStyles.marginLeft) + parseFloat(outdoorSlideStyles.marginRight);
const outdoorSlidesToShow = Math.floor(outdoorSlider.offsetWidth / outdoorSlideWidth);
let outdoorCurrentIndex = 0;

const popularSnsCard = document.getElementsByClassName("popular-sns-card");
const popularTransportCard = document.getElementsByClassName("popular-transport-card");
const popularOutdoorCard = document.getElementsByClassName("popular-outdoor-card");
/**
 * 이벤트 함수
 */
snsNextButton.addEventListener('click', () => {
    if (snsCurrentIndex < snsSlider.children.length - snsSlidesToShow) {
        snsCurrentIndex++;
        snsUpdateSlider();
    }
});

snsPrevButton.addEventListener('click', () => {
    if (snsCurrentIndex > 0) {
        snsCurrentIndex--;
        snsUpdateSlider();
    }
});

transportNextButton.addEventListener('click', () => {
    if (transportCurrentIndex < transportSlider.children.length - transportSlidesToShow) {
        transportCurrentIndex++;
        transportUpdateSlider();
    }
});

transportPrevButton.addEventListener('click', () => {
    if (transportCurrentIndex > 0) {
        transportCurrentIndex--;
        transportUpdateSlider();
    }
});

outdoorNextButton.addEventListener('click', () => {
    if (outdoorCurrentIndex < outdoorSlider.children.length - outdoorSlidesToShow) {
        outdoorCurrentIndex++;
        outdoorUpdateSlider();
    }
});

outdoorPrevButton.addEventListener('click', () => {
    if (outdoorCurrentIndex > 0) {
        outdoorCurrentIndex--;
        outdoorUpdateSlider();
    }
});


for(let i = 0; i < popularSnsCard.length; i++) {
    let popularSnsCardHover = document.getElementsByClassName("popular-sns-card-hover")

    popularSnsCard[i].addEventListener("mouseover", () => {
        popularSnsCardHover[i].style.visibility = "visible";
    });

    popularSnsCard[i].addEventListener("mouseout", () => {
        popularSnsCardHover[i].style.visibility = "hidden";
    });
}

for(let i = 0; i < popularTransportCard.length; i++) {
    let popularTransportCardHover = document.getElementsByClassName("popular-transport-card-hover")

    popularTransportCard[i].addEventListener("mouseover", () => {
        popularTransportCardHover[i].style.visibility = "visible";
    })

    popularTransportCard[i].addEventListener("mouseout", () => {
        popularTransportCardHover[i].style.visibility = "hidden";
    })
}

for(let i = 0; i < popularOutdoorCard.length; i++) {
    let popularOutdoorCardHover = document.getElementsByClassName("popular-outdoor-card-hover")

    popularOutdoorCard[i].addEventListener("mouseover", () => {
        popularOutdoorCardHover[i].style.visibility = "visible";
    })

    popularOutdoorCard[i].addEventListener("mouseout", () => {
        popularOutdoorCardHover[i].style.visibility = "hidden";
    })
}
/**
 * 사용자 함수
 */
let snsUpdateSlider = () => {
    const translateValue = -snsCurrentIndex * snsSlideWidth + 'px';
    snsSlider.style.transform = 'translateX(' + translateValue + ')';
}

let transportUpdateSlider = () => {
    const translateValue = -transportCurrentIndex * transportSlideWidth + 'px';
    transportSlider.style.transform = 'translateX(' + translateValue + ')';
}

let outdoorUpdateSlider = () => {
    const translateValue = -outdoorCurrentIndex * outdoorSlideWidth + 'px';
    outdoorSlider.style.transform = 'translateX(' + translateValue + ')';
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