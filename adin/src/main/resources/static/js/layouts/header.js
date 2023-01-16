/**
	전역변수
 */
const loginBtn = document.getElementById('loginBtn');
const loginCover = document.getElementById('loginCover');
const closeBtn = document.getElementById('closeBtn');
const signupBtn = document.getElementById('signupBtn');

/**
	이벤트 함수
 */
loginBtn.addEventListener('click', () => {
	loginCover.style.display = 'flex';
});

if (closeBtn !== null) {
	closeBtn.addEventListener('click', () => {
		loginCover.style.display = 'none';
	});
}

signupBtn.addEventListener('click', () => {
	window.location.href = '/join'
});

/**
	사용자정의 함수
 */

/**
 	xhr 성공시
 */
let successXhr = (responseObject, flag) => {

}

/**
 	xhr 공통 함수
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
						alert('오류가 발생했습니다.');
				}
			} else {
				alert('서버와 통신하지 못하였습니다.');
			}
		}
	}
	xhr.send(formData);
}
