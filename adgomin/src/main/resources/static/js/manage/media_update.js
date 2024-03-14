/**
 * 전역변수
 */
const introduce = document.getElementById("introduce");
const tempSaveButton = document.getElementById("tempSaveButton");
const submitButton = document.getElementById("submitButton");
const adCategoryList = document.getElementsByClassName("ad-category-list");
const mediaSummary = document.getElementById("mediaSummary");
const mediaPrice = document.getElementById("mediaPrice");
const thumbnailImg = document.getElementById("thumbnailImg");

const mediaSubmitStatus = document.getElementById("mediaSubmitStatus").value;

const modalCheck = document.getElementById("modalCheck");

let thumbnailImgSave;

let quill;
/**
 * 이벤트 함수
 */
introduce.addEventListener("click", () => {
    window.location.href = "/media";
})

tempSaveButton.addEventListener("click", () => {
    tempSaveAndSubmit("mediaRegisterTempSave");
})

submitButton.addEventListener("click", () => {
    if(mediaSubmitStatus == "N" || mediaSubmitStatus == "T") {
        tempSaveAndSubmit("mediaRegisterSubmit");
    } else if(mediaSubmitStatus == "I") {

    }
})

for(let i = 0; i < adCategoryList.length; i++) {
    adCategoryList[i].addEventListener("click", () => {
        if(mediaSubmitStatus == "N" || mediaSubmitStatus == "T") {
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
        } else if(mediaSubmitStatus == "I") {

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

modalCheck.addEventListener("click", () => {
    window.location.href = "/media?manage=mediaRegister";
})

thumbnailImg.addEventListener("change", (e) => {
    let ext = thumbnailImg.value.slice(thumbnailImg.value.lastIndexOf(".") + 1).toLowerCase();
    let extSpan = document.getElementById("extSpan");

    if(!(ext == "jpg" || ext == "png" || ext == "jpeg")) {
        extSpan.style.color = "#FF0040";

        const thumbnailBasicImg = document.getElementsByClassName("thumbnailBasicImg")[0];
        const thumbnailChange = document.getElementsByClassName("thumbnailChange")[0];

        const img = document.createElement('img');
        img.classList.add("thumbnailBasicImg");
        img.setAttribute('src', '/images/media-register-thumbnail.png');

        if(thumbnailBasicImg != undefined) {
            document.getElementsByClassName("thumbnail")[0].removeChild(thumbnailBasicImg);
        } else if(thumbnailChange != undefined) {
            document.getElementsByClassName("thumbnail")[0].removeChild(thumbnailChange);
        }

        document.getElementsByClassName("thumbnail")[0].appendChild(img);
    } else {
        extSpan.style.color = "#BDBDBD";

        const reader = new FileReader();

        reader.onload = (e) => {
            const thumbnailBasicImg = document.getElementsByClassName("thumbnailBasicImg")[0];
            const thumbnailChange = document.getElementsByClassName("thumbnailChange")[0];
            const img = document.createElement('img');
            img.classList.add("thumbnailChange");
            img.setAttribute('src', e.target.result);
            if(thumbnailBasicImg != undefined) {
                document.getElementsByClassName("thumbnail")[0].removeChild(thumbnailBasicImg);
            } else if(thumbnailChange != undefined) {
                document.getElementsByClassName("thumbnail")[0].removeChild(thumbnailChange);
            }
            document.getElementsByClassName("thumbnail")[0].appendChild(img);
        }

        reader.readAsDataURL(e.target.files[0]);

        thumbnailImgSave = e.target.files[0];
    }
})
/**
 * 사용자 함수
 */
let toolbarOptions =
    [
        ['bold', 'italic', 'underline', 'strike'],
        ["link", "image", "video"],
        [{ 'list': 'ordered' }, { 'list': 'bullet' }],
        [{ 'align': [] }],
    ];

quill = new Quill('#editor', {
    theme: 'snow',
    modules: {
        clipboard: {
            matchers: [['p', function(node, delta) {
                return delta;
            }]]
        },
        toolbar: toolbarOptions,
    }
});

quill.on('text-change', () => {
    document.getElementById("quillHtml").value = quill.root.innerHTML;
});

quill.getModule('toolbar').addHandler('image', () => {
    selectLocalImage();
});

let selectLocalImage = () => {
    const fileInput = document.createElement('input');
    fileInput.setAttribute('type', 'file');
    console.log("input.type " + fileInput.type);

    fileInput.click();

    fileInput.addEventListener("change", () => {
        const formData = new FormData();
        const file = fileInput.files[0];
        formData.append('mediaDetailExplanationImage', file);

        xhr("/media/detail/explanation/image", formData, "POST", "mediaDetailExplanationImage");
    });
}

let htmlContent = getMediaDetailExplain
let Delta = Quill.import('delta');
let delta = new Delta().insert(htmlContent);
quill.clipboard.dangerouslyPasteHTML(htmlContent);

document.getElementById("title").focus();

let getAdCategoryList = document.getElementsByClassName("ad-category-list");
for(let i = 0; i < getAdCategoryList.length; i++) {
    if(getAdCategoryList[i].innerText == getAdDetailCategorySelected) {
        getAdCategoryList[i].classList.add("ad-category-selected");
    }
}

const tempSaveAndSubmit = (mediaRegisterTempSaveOrSubmit) => {
    let title = document.getElementById("title").value;
    let titleSpan = document.getElementById("titleSpan");

    let adCategorySelected = document.getElementsByClassName("ad-category-selected");
    let adCategorySpan = document.getElementById("adCategorySpan");

    let extSpan = document.getElementById("extSpan");

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

    if(!(titleSpan.style.color == 'rgb(255, 0, 64)') && !(adCategorySpan.style.color == 'rgb(255, 0, 64)') && !(extSpan.style.color == 'rgb(255, 0, 64)')) {
        let title = document.getElementById("title").value;
        let adCategorySelected = document.getElementsByClassName("ad-category-selected")[0].innerText;
        let mediaSummary = document.getElementById("mediaSummary").value;
        let quillHtml = document.getElementById("quillHtml").value;
        let mediaPrice = document.getElementById("mediaPrice").value;

        const formData = new FormData();

        formData.append("mediaTitle", title);
        formData.append("adDetailCategory", adCategorySelected);
        formData.append("mediaSummary", mediaSummary);
        formData.append("mediaDetailExplain", quillHtml);
        formData.append("mediaPrice", mediaPrice);
        if(mediaRegisterTempSaveOrSubmit == "mediaRegisterTempSave") {
            formData.append("mediaSubmitStatus", "T");
        } else if(mediaRegisterTempSaveOrSubmit == "mediaRegisterSubmit") {
            formData.append("mediaSubmitStatus", "I");
        }

        xhr("/media/register", formData, "PATCH", mediaRegisterTempSaveOrSubmit);
    }
}

const thumbnailTempSaveAndSubmit = (thumbnailChangeTempSaveAndSubmit, modalTitleInnerText) => {
    const formData = new FormData();

    if(thumbnailImgSave != undefined) {
        formData.append("thumbnail", thumbnailImgSave);
        xhr("/change/thumbnail", formData, "PATCH", thumbnailChangeTempSaveAndSubmit);
    } else {
        modalFunction(modalTitleInnerText);
    }
}

const modalFunction = (modalTitleInnerText) => {
    let modalBg = document.getElementById("modalBg");
    let modalTitle = document.getElementById("modalTitle");

    modalBg.style.display = "block";
    modalTitle.innerText = modalTitleInnerText;
}

if(mediaSubmitStatus == "I") {
    let title = document.getElementById("title");
    let mediaSummary = document.getElementById("mediaSummary");
    let mediaPrice = document.getElementById("mediaPrice");
    let currencyDiv = document.getElementById("currencyDiv");
    let thumbnailImgLabel = document.getElementById("thumbnailImgLabel");
    let tempSaveButton = document.getElementById("tempSaveButton");
    let submitButton = document.getElementById("submitButton");

    title.readOnly = true;
    title.style.background = "#D8D8D8"

    mediaSummary.readOnly = true;
    mediaSummary.style.background = "#D8D8D8";

    mediaPrice.readOnly = true;
    mediaPrice.style.background = "#D8D8D8";
    currencyDiv.style.background = "#D8D8D8";

    thumbnailImgLabel.setAttribute("for", "");

    tempSaveButton.style.visibility = "hidden";

    submitButton.value = "심사중";

    quill = new Quill('#editor', {
        readOnly : true,
    });
}

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag == "mediaRegisterTempSave") {
        thumbnailTempSaveAndSubmit("thumbnailChangeTempSave", "광고매체 등록이 임시저장되었습니다.");
    } else if(flag == "mediaRegisterSubmit") {
        thumbnailTempSaveAndSubmit("thumbnailChangeSubmit", "광고매체가 정상적으로 제출되었습니다.");
    } else if(flag == "thumbnailChangeTempSave") {
        modalFunction("광고매체 등록이 임시저장되었습니다.");
    } else if(flag == "thumbnailChangeSubmit") {
        modalFunction("광고매체가 정상적으로 제출되었습니다.");
    }
}

/**
 * XMLHttpRequest default 함수
 */
let defaultXhr = (responseObject, flag) => {
    if(flag == "mediaDetailExplanationImage") {
        const range = quill.getSelection();

        let uploadPath = responseObject["result"];
        uploadPath = uploadPath.replace(/\\/g, '/');

        quill.insertEmbed(range.index, 'image', "/media/detail/image/display?uploadPath=" + uploadPath);
    }
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