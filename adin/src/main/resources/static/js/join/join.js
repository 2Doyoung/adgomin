const form = window.document.getElementById('form');

const Warning = {
    show: (text) => {
        form.querySelector('[rel="warningText"]').innerText = text;
        form.querySelector('[rel="warning"]').classList.add('visible');
    },
    hide: () => {
        form.querySelector('[rel="warning"]').classList.remove('visible');
    }
};


form.querySelector('[rel="nextBtn"]').addEventListener('click', () => {
    form.querySelector('[rel="warning"]').classList.remove('visible');

    if (form.classList.contains('step1')) {
        if (!form['termAgree'].checked) {
            form.querySelector('[rel="warningText"]').innerText = '계속하려면 애드인 이용약관에 동의해야 합니다.';
            form.querySelector('[rel="warning"]').classList.add('visible');
            return;
        }

        // 스텝 1에서 스텝 2로 넘어가기
        form.querySelector('[rel="stepText"]').innerText = '회원 가입';
        form.querySelector('[rel="nextBtn"]').value = '이메일 인증';
        form.classList.remove('step1');
        form.classList.add('step2');
    } else if (form.classList.contains('step2')) {
        if (form['email'].value === '') {
            Warning.show('이메일을 입력해주세요.');
            form['email'].focus();
            form['email'].select();
            return;
        }
        if (form['password'].value === '') {
            Warning.show('비밀번호를 입력해주세요.')
            form['password'].focus();
            form['password'].select();
            return;
        }
        if (form['passwordCheck'].value === '') {
            Warning.show('비밀번호를 한번 더 입력해주세요.')
            form['passwordCheck'].focus();
            return;
        }


        // Cover.show('회원가입 진행 중입니다.\n\n잠시만 기다려 주세요.');

        // const xhr = new XMLHttpRequest();
        // const formData = new FormData();
        // formData.append('email', form['email'].value);
        // formData.append('code', form['emailAuthCode'].value);
        // formData.append('salt', form['emailAuthSalt'].value);
        // formData.append('password', form['password'].value);
        //
        // xhr.open('POST', './join');
        // xhr.onreadystatechange = () => {
        //     if (xhr.readyState === XMLHttpRequest.DONE) {
        //         Cover.hide();
        //         if (xhr.status >= 200 && xhr.status < 300) {
        //             const responseObject = JSON.parse(xhr.responseText); // 문자열을 오브젝트로 바꿔주는 역할을 한다.
        //             switch (responseObject['result']) {
        //                 case 'email_not_verified' :
        //                     Warning.show('이메일 인증이 완료되지 않았습니다.');
        //                     break;
        //                 case 'success' :
        //                     form.querySelector('[rel="stepText"]').innerText = '회원가입 완료';
        //                     form.querySelector('[rel="nextButton"]').innerText = '로그인하러 가기';
        //                     form.classList.remove('step2');
        //                     form.classList.add('step3');
        //                     break;
        //                 default:
        //                     Warning.show('알 수 없는 이유로 회원가입에 실패하였습니다. 잠시 후 다시 시도해 주세요.');
        //
        //             }
        //         } else {
        //             Warning.show('서버와 통신하지 못했습니다. 잠시 후 다시 시도해 주세요.');
        //             form.querySelector('[rel="emailWarning"]').classList.add('visible');
        //         }
        //     }
        // };
        // xhr.send(formData);


        // 스텝 2 에서 스텝 3으로 넘어가기
        else if (form.classList.contains('step2')) {
            form.querySelector('[rel="stepText"]').innerText = '이메일 인증';
            form.querySelector('[rel="nextBtn"]').value = '애드인으로 가기';
            form.classList.remove('step2');
            form.classList.add('step3'); }
    } else if (form.classList.contains('step3')) {
        window.location.href = '/';
    }
});


//     Cover.show('인증번호를 전송하고 있습니다.\n\n잠시만 기다려 주세요.');
//     const xhr = new XMLHttpRequest();
//     const formData = new FormData();
//     formData.append('email', form['email'].value);
//
//     xhr.open('POST', './email');
//     xhr.onreadystatechange = () => {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             Cover.hide();       // 로딩창 숨기는 거
//             if (xhr.status >= 200 && xhr.status < 300) {
//                 const responseObject = JSON.parse(xhr.responseText); // 문자열을 오브젝트로 바꿔주는 역할을 한다.
//                 switch (responseObject['result']) {
//                     case 'success' :
//                         EmailWarning.show('인증번호를 전송하였습니다. 전송된 인증번호는 5분간만 유효합니다.');
//                         form['email'].setAttribute('disabled', 'disabled'); // 이메일 적는 곳 비활성화
//                         form['emailSend'].setAttribute('disabled', 'disabled'); // 버튼 비활성화
//                         form['emailAuthCode'].removeAttribute('disabled');  // 인증번호 입력하는 곳 활성화
//                         form['emailAuthCode'].focus();  // 인증번호 입력하는 곳 포커스
//                         form['emailAuthSalt'].value = responseObject['salt'];
//                         form['emailVerify'].removeAttribute('disabled'); // 인증번호 확인버튼 활성화
//                         break;
//                     case 'email_duplicated' :
//                         EmailWarning.show('해당 이메일은 이미 사용 중입니다.');
//                         form['email'].focus();
//                         form['email'].select();
//                         break;
//                     default:    // 예기치 못한 상황일 때 (알 수 없는 이유로 전송하지 못했을 때)
//                         EmailWarning.show('알 수 없는 이유로 인증 번호를 전송하지 못 하였습니다. 잠시 후 다시 시도해 주세요.');
//                         form['email'].focus();
//                         form['email'].select();
//
//                 }
//             } else {
//                 EmailWarning.show('서버와 통신하지 못했습니다.');
//             }
//         }
//     };
//     xhr.send(formData);
// });
//
// form['emailVerify'].addEventListener('click', () => {
//     if (form['emailAuthCode'].value === '') {
//         EmailWarning.show('인증 번호를 입력해 주세요.');
//         form['emailAuthCode'].focus();
//         return;
//     }
//     if (!new RegExp('^(\\d{6})$').test(form['emailAuthCode'].value)) {
//         EmailWarning.show('올바른 인증번호를 입력해 주세요.');
//         form['emailAuthCode'].focus();
//         form['emailAuthCode'].select();
//         return;
//     }
//     Cover.show('인증 번호를 확인하고 있습니다.\n\n잠시만 기다려 주세요.');
//     const xhr = new XMLHttpRequest();
//     const formData = new FormData();
//     formData.append('email', form['email'].value);  // 각각 EmailAuthEntity 의 email 과, input name 와 맞춰야 한다.
//     formData.append('code', form['emailAuthCode'].value);  // EmailAuthEntity 의 code 와 맞춰야 한다.
//     formData.append('salt', form['emailAuthSalt'].value); // EmailAuthEntity 의 salt 와 맞춰야 한다.
//     xhr.open('PATCH', 'email');
//
//     // 이메일 인증을 위해 : 이메일 주소와, code와 salt 가 일치해야 한다.
//     xhr.onreadystatechange = () => {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             Cover.hide();
//             if (xhr.status >= 200 && xhr.status < 300) {
//                 const responseObject = JSON.parse(xhr.responseText);
//                 switch (responseObject['result']) {
//                     case 'expired':
//                         EmailWarning.show('인증 정보가 만료되었습니다. 다시 시도해 주세요.');
//                         form['email'].removeAttribute('disabled');
//                         form['email'].focus();
//                         form['email'].select();
//                         form['emailSend'].removeAttribute('disabled');
//                         form['emailAuthCode'].value = '';
//                         form['emailAuthSalt'].setAttribute('disabled', 'disabled');
//                         form['emailAuthSalt'].value = '';
//                         form['emailVerify'].setAttribute('disabled', 'disabled');
//                         break;
//                     case 'success':
//                         EmailWarning.show('이메일이 정상적으로 인증되었습니다.');
//                         form['emailAuthSalt'].setAttribute('disabled', 'disabled');
//                         form['emailAuthCode'].setAttribute('disabled', 'disabled');
//                         form['emailVerify'].setAttribute('disabled', 'disabled');
//                         form['password'].focus();
//                         break;
//                     default:
//                         EmailWarning.show('인증 번호가 올바르지 않습니다.');
//                         form['emailAuthSalt'].focus();
//                         form['emailAuthSalt'].select();
//                 }
//             } else {
//                 EmailWarning.show('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.')
//             }
//         }
//     };
//     xhr.send(formData);
// });
