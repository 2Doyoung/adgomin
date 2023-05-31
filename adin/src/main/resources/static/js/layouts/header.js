/**
 * 전역변수
 */
const loginBtn = document.getElementById("loginBtn");
const loginCover = document.getElementById("loginCover");
const closeBtn = document.getElementById("closeBtn");
const signupBtn = document.getElementById("signupBtn");

const loginBoxLoginButton = document.getElementById("loginBoxLoginButton");
const loginBoxSignupButton = document.getElementById("loginBoxSignupButton");

const logout = document.getElementById("logout");
const userSetting = document.getElementById("userSetting");

const passwordFind = document.getElementById("passwordFind");
/**
 * 이벤트 함수
 */
if(loginBtn != null) {
	loginBtn.addEventListener("click", () => {
		loginCover.style.display = "flex";
		document.getElementById("loginBoxEmail").focus();
	});
}

if (closeBtn !== null) {
	closeBtn.addEventListener("click", () => {
		document.getElementById("loginBoxEmail").value = "";
		document.getElementById("loginBoxPassword").value = "";

		document.getElementById("loginBoxEmailError").style.display = "none";
		document.getElementById("loginBoxEmailExistError").style.display = "none";
		document.getElementById("loginBoxEmail").style.marginBottom = "1rem";

		document.getElementById("loginBoxPasswordError").style.display = "none";
		document.getElementById("loginBoxPassword").style.marginBottom = "1rem";

		loginCover.style.display = "none";
	});
}

if(signupBtn != null) {
	signupBtn.addEventListener("click", () => {
		window.location.href = "/join"
	});
}

if(loginBoxSignupButton != null) {
	loginBoxSignupButton.addEventListener("click", () => {
		window.location.href = "/join"
	});
}

if(loginBoxLoginButton != null) {
	loginBoxLoginButton.addEventListener("click", () => {
		let loginBoxEmail = document.getElementById("loginBoxEmail").value;
		let loginBoxPassword = document.getElementById("loginBoxPassword").value;
		let rememberMe = document.getElementById("rememberMe").checked;

		let loginBoxEmailError = document.getElementById("loginBoxEmailError");
		let loginBoxPasswordError = document.getElementById("loginBoxPasswordError");

		if(loginBoxEmail === "") {
			loginBoxEmailError.style.display = "inline-block";
			document.getElementById("loginBoxEmail").style.marginBottom = "0px";
		}

		if(loginBoxPassword === "") {
			loginBoxPasswordError.style.display = "inline-block";
			document.getElementById("loginBoxPassword").style.marginBottom = "0px";
		}

		if(loginBoxEmailError.style.display !== "inline-block" && loginBoxPasswordError.style.display !== "inline-block") {
			const formData = new FormData();

			formData.append("email", loginBoxEmail);
			formData.append("password", loginBoxPassword);
			formData.append("rememberMe", rememberMe);

			xhrHeader("/login", formData, "POST", "postLogin");
		}
	});
}

document.getElementById("loginBoxEmail").addEventListener("keyup", (e) => {
	if(e.keyCode === 13) {
		loginBoxLoginButton.click();
	} else {
		let loginBoxEmailError = document.getElementById("loginBoxEmailError");
		let loginBoxEmailExistError = document.getElementById("loginBoxEmailExistError");
		let loginBoxPasswordError = document.getElementById("loginBoxPasswordError");

		loginBoxEmailError.style.display = "none";
		loginBoxEmailExistError.style.display = "none";
		document.getElementById("loginBoxEmail").style.marginBottom = "1rem";

		loginBoxPasswordError.style.display = "none";
		document.getElementById("loginBoxPassword").style.marginBottom = "1rem";
	}
});

document.getElementById("loginBoxPassword").addEventListener("keyup", (e) => {
	if(e.keyCode === 13) {
		loginBoxLoginButton.click();
	} else {
		let loginBoxPasswordError = document.getElementById("loginBoxPasswordError");

		loginBoxPasswordError.style.display = "none";
		document.getElementById("loginBoxPassword").style.marginBottom = "1rem";
	}
})

if(logout != null) {
	logout.addEventListener("click", () => {
		window.location.href = "/logouts";
	});
}

if(passwordFind != null) {
	passwordFind.addEventListener("click", () => {
		window.location.href = "/passwordEmail"
	});
}

if(userSetting != null) {
	userSetting.addEventListener("click", () => {
		window.location.href = "/user"
	});
}

/**
 * 사용자 함수
 */

/**
 * XMLHttpRequest 성공 함수
 */
let successXhrHeader = (responseObject, flag) => {
	if(flag === "postLogin") {
		window.location.href = "/"
	}
}

/**
 * XMLHttpRequest default 함수
 */
let defaultXhrHeader = (responseObject, flag) => {
	if(flag === "postLogin") {
		if(responseObject["result"] === "email_not_exist") {
			let loginBoxEmailExistError = document.getElementById("loginBoxEmailExistError");

			loginBoxEmailExistError.style.display = "inline-block";
			document.getElementById("loginBoxEmail").style.marginBottom = "0px";
		} else if(responseObject["result"] === "password_error") {
			let loginBoxPasswordError = document.getElementById("loginBoxPasswordError");

			loginBoxPasswordError.style.display = "inline-block";
			document.getElementById("loginBoxPassword").style.marginBottom = "0px";
		} else if(responseObject["result"] === "secession_user") {
			alert("탈퇴유저");
		} else if(responseObject["result"] === "no_certified_user") {
			let email = responseObject["email"];

			window.location.href = "/join/noCertified?email=" + email;
		}
	}
}

/**
 * XMLHttpRequest 함수
 */
let xhrHeader = (url, formData, method, flag) => {
	const xhr = new XMLHttpRequest();

	xhr.open(method, url);
	xhr.onreadystatechange = () => {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status >= 200 && xhr.status < 300) {
				const responseObject = JSON.parse(xhr.responseText);
				switch (responseObject['result']) {
					case 'success':
						successXhrHeader(responseObject, flag);
						break;
					default:
						defaultXhrHeader(responseObject, flag);
				}
			} else {
				alert('서버와 통신하지 못하였습니다.');
			}
		}
	}
	xhr.send(formData);
}