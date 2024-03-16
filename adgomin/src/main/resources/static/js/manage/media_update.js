/**
 * 전역변수
 */
const mediaUpdateAdDetailCategory = document.getElementById("mediaUpdateAdDetailCategory");

/**
 * 이벤트 함수
 */

/**
 * 사용자 함수
 */
mediaUpdateAdDetailCategory.innerText = getAdDetailCategory;

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