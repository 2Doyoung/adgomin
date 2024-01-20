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

const mediaManage = document.getElementById("mediaManage");

const adminUserManage = document.getElementById("adminUserManage");
const adminAdManage = document.getElementById("adminAdManage");

const allCategoryDropdownSpan = document.querySelectorAll('.all-category-dropdown span');
const allCategorySubDropdownSpan = document.querySelectorAll('.all-category-sub-dropdown span');
const allCategoryRegionDropdownSpan = document.querySelectorAll('.all-category-region-dropdown span');
const allCategoryRegion2DropdownSpan = document.querySelectorAll('.all-category-region2-dropdown span');

const allCategoryDropdownWrap = document.getElementById("allCategoryDropdownWrap");

const allCategorySubDropdown = document.getElementById("allCategorySubDropdown");
const allCategoryRegionDropdown = document.getElementById("allCategoryRegionDropdown");
const allCategoryRegion2Dropdown = document.getElementById("allCategoryRegion2Dropdown");
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

if(mediaManage != null) {
	mediaManage.addEventListener("click", () => {
		window.location.href = "/media"
	});
}

if(adminUserManage != null) {
	adminUserManage.addEventListener("click", () => {
		window.location.href = "/admin?page=1"
	});
}

if(adminAdManage != null) {
	adminAdManage.addEventListener("click", () => {
		window.location.href = "/admin/adManage?page=1"
	});
}

for(let i = 0; i < allCategoryDropdownSpan.length; i++) {
	allCategoryDropdownSpan[i].addEventListener("click", () => {
		for (let j = 0; j < allCategoryDropdownSpan.length; j++) {
			if (j !== i) {
				allCategoryDropdownSpan[j].classList.remove("all-category-dropdown-highlight");
			}
		}

		allCategoryDropdownSpan[i].classList.add("all-category-dropdown-highlight");

		allCategorySubDropdown.style.visibility = 'visible';
	})
}

for(let i = 0; i < allCategorySubDropdownSpan.length; i++) {
	allCategorySubDropdownSpan[i].addEventListener("click", () => {
		for (let j = 0; j < allCategorySubDropdownSpan.length; j++) {
			if (j !== i) {
				allCategorySubDropdownSpan[j].classList.remove("all-category-dropdown-highlight");
			}
		}

		allCategorySubDropdownSpan[i].classList.add("all-category-dropdown-highlight");

		allCategoryRegionDropdown.style.visibility = 'visible';
		allCategoryRegion2Dropdown.style.visibility = 'visible';
	})
}

allCategoryDropdownWrap.addEventListener("mouseleave", () => {
	for(let i = 0; i < allCategoryDropdownSpan.length; i++) {
		allCategoryDropdownSpan[i].classList.remove("all-category-dropdown-highlight");
	}

	for(let i = 0; i < allCategorySubDropdownSpan.length; i++) {
		allCategorySubDropdownSpan[i].classList.remove("all-category-dropdown-highlight");
	}

	for(let i = 0; i < allCategoryRegionDropdownSpan.length; i++) {
		allCategoryRegionDropdownSpan[i].classList.remove("all-category-dropdown-highlight");
	}

	for(let i = 0; i < allCategoryRegion2DropdownSpan.length; i++) {
		allCategoryRegion2DropdownSpan[i].classList.remove("all-category-dropdown-highlight");
	}

	allCategorySubDropdown.style.visibility = 'hidden';
	allCategoryRegionDropdown.style.visibility = 'hidden';
	allCategoryRegion2Dropdown.style.visibility = 'hidden';
})
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