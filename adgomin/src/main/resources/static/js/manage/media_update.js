/**
 * 전역변수
 */
const mediaUpdateAdDetailCategory = document.getElementById("mediaUpdateAdDetailCategory");
const thumbnailImg = document.getElementById("thumbnailImg");
const updateButton = document.getElementById("updateButton");

let thumbnailImgSave;

let quill;

/**
 * 이벤트 함수
 */
updateButton.addEventListener("click", () => {
    let mediaSummary = document.getElementById("mediaSummary").value;
    let mediaSummarySpan = document.getElementById("mediaSummarySpan");

    if(mediaSummary.length == 0) {
        mediaSummarySpan.style.color = "#FF0040";
        document.getElementById("mediaSummary").focus();
    } else if(mediaSummary.length > 0) {
        mediaSummarySpan.style.color = "#BDBDBD";
    }
})

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
mediaUpdateAdDetailCategory.innerText = getAdDetailCategory;

let summaryLength = document.getElementById("summaryLength");
let mediaSummaryValue = mediaSummary.value;
let mediaSummaryLength = mediaSummaryValue.length;

summaryLength.innerText = " (" + mediaSummaryLength + " / 255)";

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

/**
 * XMLHttpRequest 성공 함수
 */
let successXhr = (responseObject, flag) => {

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