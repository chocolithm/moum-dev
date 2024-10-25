let nicknameChecked = false;
let emailChecked = false;
let passwordMatch = false;

// 닉네임 입력 필드 변경 감지
document.getElementById('nickname').addEventListener('input', function() {
    nicknameChecked = false;
    document.getElementById('nicknameMessage').textContent = '닉네임 중복 확인이 필요합니다.';
    document.getElementById('nicknameMessage').style.color = 'red';
});

// 이메일 입력 필드 변경 감지
document.getElementById('email').addEventListener('input', function() {
    emailChecked = false;
    document.getElementById('emailMessage').textContent = '이메일 중복 확인이 필요합니다.';
    document.getElementById('emailMessage').style.color = 'red';
});

// 비밀번호 유효성 검사
document.getElementById('password').addEventListener('input', function() {
    const password = this.value;
    const passwordMessage = document.getElementById('passwordMessage');

    // if (!password.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/)) {
    //     passwordMessage.textContent = '최소 8자, 영문, 숫자, 특수문자를 포함해야 합니다.';
    //     passwordMessage.style.color = 'red';
    // } else {
    //     passwordMessage.textContent = '사용 가능한 비밀번호입니다.';
    //     passwordMessage.style.color = 'green';
    // }
    checkPasswordMatch();
});

// 비밀번호 확인 체크
document.getElementById('confirmPassword').addEventListener('input', checkPasswordMatch);

function checkPasswordMatch() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const confirmPasswordMessage = document.getElementById('confirmPasswordMessage');

    if (password && confirmPassword) {
        if (password === confirmPassword) {
            confirmPasswordMessage.textContent = '비밀번호가 일치합니다.';
            confirmPasswordMessage.style.color = 'green';
            passwordMatch = true;
        } else {
            confirmPasswordMessage.textContent = '비밀번호가 일치하지 않습니다.';
            confirmPasswordMessage.style.color = 'red';
            passwordMatch = false;
        }
    }
}

// 중복 체크 함수
function checkDuplicate(type, value) {
    if (!value) {
        alert(type === 'nickname' ? '닉네임을 입력해주세요.' : '이메일을 입력해주세요.');
        return;
    }

    // 유효성 검사
    if (type === 'nickname' && !value.match(/^[가-힣a-zA-Z0-9]{2,10}$/)) {
        document.getElementById('nicknameMessage').textContent = '2~10자의 한글, 영문, 숫자만 사용 가능합니다.';
        document.getElementById('nicknameMessage').style.color = 'red';
        return;
    }
    if (type === 'email' && !value.match(/[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/)) {
        document.getElementById('emailMessage').textContent = '유효한 이메일 주소를 입력해주세요.';
        document.getElementById('emailMessage').style.color = 'red';
        return;
    }

    const params = new URLSearchParams();
    params.append(type, value);

    // CSRF 토큰 가져오기
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    const csrfToken = document.querySelector("meta[name='_csrf']").content;

    fetch('/user/check-duplicate?' + params.toString(), {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            if (type === 'nickname') {
                if (data.isNicknameTaken) {
                    document.getElementById('nicknameMessage').textContent = '이미 사용중인 닉네임입니다.';
                    document.getElementById('nicknameMessage').style.color = 'red';
                    nicknameChecked = false;
                } else {
                    document.getElementById('nicknameMessage').textContent = '사용 가능한 닉네임입니다.';
                    document.getElementById('nicknameMessage').style.color = 'green';
                    nicknameChecked = true;
                }
            } else if (type === 'email') {
                if (data.isEmailTaken) {
                    document.getElementById('emailMessage').textContent = '이미 사용중인 이메일입니다.';
                    document.getElementById('emailMessage').style.color = 'red';
                    emailChecked = false;
                } else {
                    document.getElementById('emailMessage').textContent = '사용 가능한 이메일입니다.';
                    document.getElementById('emailMessage').style.color = 'green';
                    emailChecked = true;
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// 폼 제출 전 유효성 검사
document.getElementById('signupForm').addEventListener('submit', function(e) {
    if (!nicknameChecked || !emailChecked || !passwordMatch) {
        e.preventDefault();
        let message = '';
        if (!nicknameChecked) message += '닉네임 중복 확인이 필요합니다.\n';
        if (!emailChecked) message += '이메일 중복 확인이 필요합니다.\n';
        if (!passwordMatch) message += '비밀번호가 일치하지 않습니다.\n';
        alert(message);
    }
});
