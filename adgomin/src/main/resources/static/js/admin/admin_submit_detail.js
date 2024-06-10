/**
 * 전역변수
 */
const mediaUserList = document.getElementById("mediaUserList");
const advertiserUserList = document.getElementById("advertiserUserList");
const region = document.getElementById("region");
const adCategory = document.getElementById("adCategory");
const mediaUrl = document.getElementById("mediaUrl");
const adDetailCategory = document.getElementById("adDetailCategory");
const judgeCompleteButton = document.getElementById("judgeCompleteButton");
const judgeRefuseButton = document.getElementById("judgeRefuseButton");

const modalCheck = document.getElementById("modalCheck");
const modalRefuseCheckbox = document.getElementById("modalRefuseCheckbox");
const modalRefuseClose = document.getElementById("modalRefuseClose");

let quill;

/**
 * 이벤트 함수
 */
mediaUserList.addEventListener("click", () => {
    window.location.href = "/admin?manage=mediaUserList&page=1"
})

advertiserUserList.addEventListener("click", () => {
    window.location.href = "/admin?manage=advertiserUserList&page=1"
})

mediaUrl.addEventListener("click", () => {
    window.open("http://" + getMediaUrl, ".blank")
})

judgeCompleteButton.addEventListener("click", () => {
    let mediaOrder = document.getElementById("mediaOrder").value;
    let email = document.getElementById("email").value;
    let editorHtml = quill.root.innerHTML;

    const formData = new FormData();

    formData.append("mediaOrder", mediaOrder);
    formData.append("email", email);
    formData.append("mediaDetailExplain", editorHtml);
    formData.append("mediaSubmitStatus", "Y");
    formData.append("confirmNotificationRead", "N");

    xhr("/admin/judgeComplete", formData, "PATCH", "adminJudgeComplete");
})

judgeRefuseButton.addEventListener("click", () => {
    let modalBg = document.getElementById("modalBg2");
    let refuseReason = document.getElementById("refuseReason");

    modalBg.style.display = "block";
    refuseReason.focus();
})

modalCheck.addEventListener("click", () => {
    window.location.href = "/admin?page=1";
})

modalRefuseCheckbox.addEventListener("click", () => {
    let mediaOrder = document.getElementById("mediaOrder").value;
    let refuseReason = document.getElementById("refuseReason").value;

    const formData = new FormData();

    formData.append("mediaOrder", mediaOrder);
    formData.append("mediaSubmitStatus", "C");
    formData.append("refuseReason", refuseReason);

    xhr("/admin/judgeRefuse", formData, "PATCH", "adminJudgeRefuse");
})

modalRefuseClose.addEventListener("click", () => {
    let modalBg2 = document.getElementsByClassName("modal-bg2");
    let refuseReason = document.getElementById("refuseReason");

    modalBg2[0].style.display = "none";
    refuseReason.value = "";
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

let htmlContent = getMediaDetailExplain
let Delta = Quill.import('delta');
let delta = new Delta().insert(htmlContent);
quill.clipboard.dangerouslyPasteHTML(htmlContent);

document.getElementById("focus").focus();

if(getRegion != null) {
    let regionSplit = getRegion.split(',');

    for(let i = 0; i < regionSplit.length; i++) {
        let regionSpanTag = document.createElement("span");
        regionSpanTag.append(regionSplit[i]);
        regionSpanTag.classList.add("region-span")

        region.appendChild(regionSpanTag);
    }
}

if(getAdCategory != null) {
    let adCategorySplit = getAdCategory.split(',');

    for(let i = 0; i < adCategorySplit.length; i++) {
        let adCategorySpanTag = document.createElement("span");
        adCategorySpanTag.append(adCategorySplit[i]);
        adCategorySpanTag.classList.add("ad-category-span")

        adCategory.appendChild(adCategorySpanTag);
    }
}

let adDetailCategorySpanTag = document.createElement("span");
adDetailCategorySpanTag.append(getAdDetailCategory);
adDetailCategorySpanTag.classList.add("ad-detail-category-span")

adDetailCategory.appendChild(adDetailCategorySpanTag);


/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {
    if(flag == "adminJudgeComplete") {
        let modalBg = document.getElementById("modalBg");
        let modalTitle = document.getElementById("modalTitle");

        modalBg.style.display = "block";
        modalTitle.innerText = "심사가 완료되었습니다.";
    } else if(flag == "adminJudgeRefuse") {
        window.location.href = "/admin?page=1";
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