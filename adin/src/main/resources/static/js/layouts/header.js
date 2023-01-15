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