/**
 * 전역변수
 */
const submitList = document.getElementById("submitList");
const advertiserUserList = document.getElementById("advertiserUserList");
const spans = document.querySelectorAll('.div-paging span span');
const searchOption = document.getElementById("searchOption");
const marketingCheckboxLine = document.getElementsByClassName("marketing-checkbox-line");
const marketingCheckbox = document.getElementsByClassName("marketing-checkbox");
const emailCheckboxLine = document.getElementsByClassName("email-checkbox-line");
const emailCheckbox = document.getElementsByClassName("email-checkbox");
const useCheckboxLine = document.getElementsByClassName("use-checkbox-line");
const useCheckbox = document.getElementsByClassName("use-checkbox");

const apply = document.getElementById("apply");

const selectClear = document.getElementById("selectClear");

const marketingYn = document.getElementById("marketingYn").value;
const certifiedYn = document.getElementById("certifiedYn").value;
const useYn = document.getElementById("useYn").value;

/**
 * 이벤트 함수
 */
submitList.addEventListener("click", () => {
    window.location.href = "/admin?page=1";
})

advertiserUserList.addEventListener("click", () => {
    window.location.href = "/admin?manage=advertiserUserList&page=1"
})

for(let i = 0; i < spans.length; i++) {
    spans[i].addEventListener('click', (e) => {
        let link = e.target.querySelector('a');
        if (link) {
            link.click();
        }
    });
}

searchOption.addEventListener("click", () => {
    let arrowIcon = document.getElementsByClassName("arrow-icon")[0];
    let searchOptions = document.getElementsByClassName("search-options")[0];

    if(arrowIcon.classList.contains("arrow-icon-down")) {
        arrowIcon.classList.remove("arrow-icon-down");
        arrowIcon.classList.add("arrow-icon-up");
        searchOptions.classList.remove("search-options-hidden");
        searchOptions.classList.add("search-options-visible");
    } else if(arrowIcon.classList.contains("arrow-icon-up")) {
        arrowIcon.classList.remove("arrow-icon-up");
        arrowIcon.classList.add("arrow-icon-down");
        searchOptions.classList.remove("search-options-visible");
        searchOptions.classList.add("search-options-hidden");
    }
})

for (let i = 0; i < marketingCheckboxLine.length; i++) {
    marketingCheckboxLine[i].addEventListener("click", () => {
        for (let j = 0; j < marketingCheckboxLine.length; j++) {
            if (j !== i) {
                marketingCheckbox[j].classList.remove("check-box-color");
            }
        }

        marketingCheckbox[i].classList.toggle("check-box-color");
    });
}

for (let i = 0; i < emailCheckboxLine.length; i++) {
    emailCheckboxLine[i].addEventListener("click", () => {
        for (let j = 0; j < emailCheckboxLine.length; j++) {
            if (j !== i) {
                emailCheckbox[j].classList.remove("check-box-color");
            }
        }

        emailCheckbox[i].classList.toggle("check-box-color");
    });
}

for (let i = 0; i < useCheckboxLine.length; i++) {
    useCheckboxLine[i].addEventListener("click", () => {
        for (let j = 0; j < useCheckboxLine.length; j++) {
            if (j !== i) {
                useCheckbox[j].classList.remove("check-box-color");
            }
        }

        useCheckbox[i].classList.toggle("check-box-color");
    });
}

selectClear.addEventListener("click", () => {
    let checkBox = document.getElementsByClassName("ri-checkbox-circle-fill");

    for(let i = 0; i <checkBox.length; i++) {
        checkBox[i].classList.remove("check-box-color");
    }
})

apply.addEventListener("click", () => {
    let marketingCheckbox = document.getElementsByClassName("marketing-checkbox");
    let emailCheckbox = document.getElementsByClassName("email-checkbox");
    let useCheckbox = document.getElementsByClassName("use-checkbox");

    let marketingYn = '';
    let emailYn = '';
    let useYn = '';

    if(marketingCheckbox[0].classList.contains("check-box-color")) {
        marketingYn = 1;
    } else if(marketingCheckbox[1].classList.contains("check-box-color")) {
        marketingYn = 0;
    }

    if(emailCheckbox[0].classList.contains("check-box-color")) {
        emailYn = 1;
    } else if(emailCheckbox[1].classList.contains("check-box-color")) {
        emailYn = 0;
    }

    if(useCheckbox[0].classList.contains("check-box-color")) {
        useYn = 1;
    } else if(useCheckbox[1].classList.contains("check-box-color")) {
        useYn = 0;
    }

    window.location.href = "/admin?manage=mediaUserList&page=1&marketingYn=" + marketingYn + "&certifiedYn=" + emailYn + "&useYn=" + useYn;
})

/**
 * 사용자 함수
 */
if(marketingYn == '1') {
    marketingCheckbox[0].classList.add("check-box-color");
} else if(marketingYn == '0') {
    marketingCheckbox[1].classList.add("check-box-color");
}

if(certifiedYn == '1') {
    emailCheckbox[0].classList.add("check-box-color");
} else if(certifiedYn == '0') {
    emailCheckbox[1].classList.add("check-box-color");
}

if(useYn == '1') {
    useCheckbox[0].classList.add("check-box-color");
} else if(useYn == '0') {
    useCheckbox[1].classList.add("check-box-color");
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