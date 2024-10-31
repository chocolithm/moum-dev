// signup에서 user.js 호출할 때마다 let 변수를 생성하면서 오류 발생하므로 header로 옮깁니다.
//let nicknameChecked = false;
//let emailChecked = false;
//let passwordMatch = false;

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

// 폼 제출 전 유효성 검사 강화
document.getElementById('signupForm').addEventListener('submit', function(e) {
    e.preventDefault(); // 우선 제출을 막습니다

    let isValid = true;
    let message = '';

    // 닉네임 체크
    if (!nicknameChecked) {
        message += '닉네임 중복 확인이 필요합니다.\n';
        isValid = false;
    }

    // 이메일 체크
    if (!emailChecked) {
        message += '이메일 중복 확인이 필요합니다.\n';
        isValid = false;
    }

    // 비밀번호 체크
    if (!passwordMatch) {
        message += '비밀번호가 일치하지 않습니다.\n';
        isValid = false;
    }

    if (!isValid) {
        alert(message);
        return false;
    }

    // 모든 검증을 통과하면 폼을 제출합니다
    this.submit();
});

// 프로필 관련 기능 추가
document.addEventListener('DOMContentLoaded', function() {
    // 프로필 수정 폼이 있는 경우에만 실행
    const profileForm = document.querySelector('.profile-form');
    if (profileForm) {
        // 파일 입력 미리보기
        const fileInput = document.getElementById('file');
        const profileImg = document.querySelector('.profile-img');

        fileInput?.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    profileImg.src = e.target.result;
                };
                reader.readAsDataURL(file);
            }
        });
    }

    // 닉네임 중복 체크 (프로필 수정 시)
    const nicknameInput = document.getElementById('nickname');
    if (nicknameInput) {
        const originalNickname = nicknameInput.value;
        nicknameInput.addEventListener('input', function() {
            if (this.value !== originalNickname) {
                const messageElement = document.getElementById('nicknameMessage') ||
                    document.createElement('span');
                messageElement.id = 'nicknameMessage';
                messageElement.textContent = '닉네임 중복 확인이 필요합니다.';
                messageElement.style.color = 'red';

                if (!document.getElementById('nicknameMessage')) {
                    this.parentNode.appendChild(messageElement);
                }
            }
        });
    }

    // 프로필 수정 폼 제출 전 유효성 검사
    if (profileForm) {
        profileForm.addEventListener('submit', function(e) {
            const nickname = document.getElementById('nickname').value;
            const password = document.getElementById('password').value;

            if (!nickname.trim()) {
                e.preventDefault();
                alert('닉네임을 입력해주세요.');
                return;
            }

            if (!nickname.match(/^[가-힣a-zA-Z0-9]{2,10}$/)) {
                e.preventDefault();
                alert('닉네임은 2~10자의 한글, 영문, 숫자만 사용 가능합니다.');
                return;
            }
        });
    }
});

// 닉네임 수정 창 보이게 하기
function showNicknameEdit() {
    document.getElementById('nicknameDisplay').style.display = 'none'; // 닉네임 텍스트 숨김
    document.getElementById('nicknameEdit').style.display = 'block'; // 닉네임 입력 칸 표시
    const nicknameInput = document.getElementById('nickname');
    nicknameInput.value = nicknameInput.getAttribute('value');
}

// 닉네임 수정 창 숨기기
function hideNicknameEdit() {
    const nicknameInput = document.getElementById('nickname');
    const originalNickname = nicknameInput.getAttribute('value'); // 원래 저장된 값 가져오기

    // 입력 필드 값을 원래 값으로 리셋
    nicknameInput.value = originalNickname;
    document.getElementById('nicknameDisplay').style.display = 'flex'; // 닉네임 텍스트 표시
    document.getElementById('nicknameEdit').style.display = 'none'; // 닉네임 입력 칸 숨김

    document.getElementById('nicknameMessage').textContent = '';
}

document.addEventListener('DOMContentLoaded', function() {
    let passwordMatch = true; // 초기값은 true (비밀번호를 변경하지 않는 경우도 허용)

    // 비밀번호 입력 필드 이벤트 리스너
    document.getElementById('password').addEventListener('input', checkPasswordMatch);
    document.getElementById('confirmPassword').addEventListener('input', checkPasswordMatch);

    // 폼 제출 전 유효성 검사 추가
    const submitButton = document.querySelector('button[type="submit"]');
    if (submitButton) {
        submitButton.addEventListener('click', function(e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if ((password || confirmPassword) && !passwordMatch) {
                e.preventDefault();
                alert('비밀번호가 일치하지 않습니다.');
                return false;
            }
        });
    }
});
