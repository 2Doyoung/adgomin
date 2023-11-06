/**
 * 전역변수
 */
const introduce = document.getElementById("introduce");
const tempSaveButton = document.getElementById("tempSaveButton");
const adCategoryList = document.getElementsByClassName("ad-category-list");
const mediaSummary = document.getElementById("mediaSummary");
const mediaPrice = document.getElementById("mediaPrice");

let editor;

/**
 * 이벤트 함수
 */
introduce.addEventListener("click", () => {
    window.location.href = "/media";
})

tempSaveButton.addEventListener("click", () => {
    let title = document.getElementById("title").value;
    let titleSpan = document.getElementById("titleSpan");

    let adCategorySelected = document.getElementsByClassName("ad-category-selected");
    let adCategorySpan = document.getElementById("adCategorySpan");

    if(title.length < 5 || title.length > 30) {
        titleSpan.style.color = "#FF0040";
        document.getElementById("title").focus();
    } else if(title.length >= 5 && title.length <= 30) {
        titleSpan.style.color = "#BDBDBD";
    }

    if(adCategorySelected[0] == undefined) {
        adCategorySpan.style.color = "#FF0040";
        document.getElementById("adCategoryFocus").focus();
    } else {
        adCategorySpan.style.color = "#BDBDBD";
    }

    if(!(titleSpan.style.color == 'rgb(255, 0, 64)') && !(adCategorySpan.style.color == 'rgb(255, 0, 64)')) {
        let title = document.getElementById("title").value;
        let adCategorySelected = document.getElementsByClassName("ad-category-selected")[0].innerText;
        let mediaSummary = document.getElementById("mediaSummary").value;
        let editorData = editor.getData();
        let mediaPrice = document.getElementById("mediaPrice").value;

        const formData = new FormData();

        formData.append("mediaTitle", title);
        formData.append("adDetailCategory", adCategorySelected);
        formData.append("mediaSummary", mediaSummary);
        formData.append("mediaDetailExplain", editorData);
        formData.append("mediaPrice", mediaPrice);

        xhr("/media/register", formData, "PATCH", "mediaRegister");
    }
})

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

mediaSummary.addEventListener("keyup", () => {
    let summaryLength = document.getElementById("summaryLength");
    let mediaSummaryValue = mediaSummary.value;
    let mediaSummaryLength = mediaSummaryValue.length;

    if(mediaSummaryLength > 255) {
        mediaSummary.value = mediaSummaryValue.substr(0, 255);
        mediaSummaryLength = mediaSummary.value.length;
    }

    summaryLength.innerText = " (" + mediaSummaryLength + " / 255)";
})

mediaPrice.addEventListener("keyup", (e) =>{
    let value = e.target.value;
    value = Number(value.replaceAll(',', ''));
    if(isNaN(value)) {
        mediaPrice.value = 0;
    }else {
        const formatValue = value.toLocaleString('ko-KR');
        mediaPrice.value = formatValue;
    }
})
/**
 * 사용자 함수
 */

ClassicEditor.create(document.querySelector('#editor'))
    .then(newEditor => {
        editor = newEditor;
    })
    .catch(error => {
        console.error( error );
    });

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