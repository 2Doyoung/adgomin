/**
	전역변수
 */
const loginBtn = document.getElementById('loginBtn');
const loginCover = document.getElementById('loginCover');
const closeBtn = document.getElementById('closeBtn');

/**
	이벤트 함수
 */
loginBtn.addEventListener('click', () => {
	loginCover.style.display = 'flex';
});

closeBtn.addEventListener('click', () => {
	loginCover.style.display = 'none';
});

/**
	사용자정의 함수
 */