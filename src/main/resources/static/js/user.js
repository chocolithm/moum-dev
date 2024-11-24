let lastSentTime = 0;  // 마지막 인증 코드 발송 시간을 저장하는 변수
let countdownTimer = null;

// 닉네임 입력 필드 변경 감지
document.getElementById('nickname').addEventListener('input', function() {
    const originalNickname = this.getAttribute('value'); // 원래 저장된 값 가져오기
    if (this.value !== originalNickname) {
        nicknameChecked = false;
        document.getElementById('nicknameMessage').textContent = '닉네임 중복 확인이 필요합니다.';
        document.getElementById('nicknameMessage').style.color = 'red';
    } else {
        nicknameChecked = true;
        document.getElementById('nicknameMessage').textContent = '';
    }
});

// 이메일 입력 필드 변경 감지
if (document.getElementById('email')) {
    document.getElementById('email').addEventListener('input', function() {
        emailChecked = false;
        document.getElementById('emailMessage').textContent = '이메일 중복 확인이 필요합니다.';
        document.getElementById('emailMessage').style.color = 'red';
    });
}

// 비밀번호 유효성 검사
if (document.getElementById('password')) {
    document.getElementById('password').addEventListener('input', function() {
        const password = this.value;
        const passwordMessage = document.getElementById('passwordMessage');
    
        if (!password.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/)) {
            passwordMessage.textContent = '최소 8자, 영문, 숫자, 특수문자를 포함해야 합니다.';
            passwordMessage.style.color = 'red';
        } else {
            passwordMessage.textContent = '사용 가능한 비밀번호입니다.';
            passwordMessage.style.color = 'green';
        }
        checkPasswordMatch();
    });
}

// 비밀번호 확인 체크
if (document.getElementById('confirmPassword')) {
    document.getElementById('confirmPassword').addEventListener('input', checkPasswordMatch);
}

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
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        }
    })
        .then(response => response.json())
        .then(data => {
            if (type === 'nickname') {
                handleNicknameCheckResult(data);
            } else if (type === 'email') {
                handleEmailCheckResult(data, value);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function handleNicknameCheckResult(data) {
    if (data.isNicknameTaken) {
        document.getElementById('nicknameMessage').textContent = '이미 사용중인 닉네임입니다.';
        document.getElementById('nicknameMessage').style.color = 'red';
        nicknameChecked = false;
    } else {
        document.getElementById('nicknameMessage').textContent = '사용 가능한 닉네임입니다.';
        document.getElementById('nicknameMessage').style.color = 'green';
        nicknameChecked = true;
    }
}

function handleEmailCheckResult(data, email) {
    if (data.isEmailTaken) {
        document.getElementById('emailMessage').textContent = '이미 사용중인 이메일입니다.';
        document.getElementById('emailMessage').style.color = 'red';
        emailChecked = false;
    } else {
        // 이메일 중복이 아닐 경우 인증 코드 발송
        sendAuthCode(email);
    }
}

function sendAuthCode(email) {
    if (!email.match(/[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/)) {
        document.getElementById('emailMessage').textContent = '유효한 이메일 주소를 입력해주세요.';
        document.getElementById('emailMessage').style.color = 'red';
        return;
    }

    const currentTime = new Date().getTime();

    // 버튼 비활성화 및 메시지 업데이트
    const emailCheckButton = document.querySelector('button[onclick*="checkDuplicate(\'email\'"]');
    if (emailCheckButton.disabled) return;
    emailCheckButton.disabled = true;

    // 인증 코드가 발송되었음을 표시하고 타이머 즉시 시작
    document.getElementById('emailMessage').textContent = '인증 코드가 발송되었습니다. 3분 후 다시 보낼 수 있습니다.';
    document.getElementById('emailMessage').style.color = 'green';
    if (!countdownTimer) startTimer();

    const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    const csrfToken = document.querySelector("meta[name='_csrf']").content;

    fetch('/emailCheck', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({ email: email })
    })
        .then(response => response.text())
        .then(authCode => {
            if (authCode !== "duplicate") {
                sessionStorage.setItem('authCode', authCode);
                document.getElementById('authCodeField').style.display = 'block';
            } else {
                // 중복된 이메일일 경우 메시지와 버튼 상태 복구
                emailCheckButton.disabled = false;
                clearInterval(countdownTimer);
                countdownTimer = null;
                document.getElementById('emailMessage').textContent = '중복된 이메일입니다.';
                document.getElementById('emailMessage').style.color = 'red';
                const timerElement = document.getElementById('timer');
                if (timerElement) timerElement.remove(); // 타이머 제거
            }
        })
        .catch(error => {
            console.error('인증 코드 요청 오류:', error);
            emailCheckButton.disabled = false;
            clearInterval(countdownTimer);
            countdownTimer = null;
            document.getElementById('emailMessage').textContent = '인증 코드 발송에 실패했습니다. 다시 시도해 주세요.';
            document.getElementById('emailMessage').style.color = 'red';
            const timerElement = document.getElementById('timer');
            if (timerElement) timerElement.remove(); // 타이머 제거
        });
}

function startTimer() {
    let timeLeft = 180; // 3분 = 180초

    let timerElement = document.getElementById('timer');
    if (!timerElement) {
        timerElement = document.createElement('span');
        timerElement.id = 'timer';
        timerElement.style.marginLeft = '10px';
        document.getElementById('emailMessage').appendChild(timerElement);
    }

    if (countdownTimer) {
        clearInterval(countdownTimer);
    }

    countdownTimer = setInterval(() => {
        const minutes = Math.floor(timeLeft / 60);
        const seconds = timeLeft % 60;
        timerElement.textContent = `(${minutes}:${seconds < 10 ? '0' : ''}${seconds})`;

        if (timeLeft <= 0) {
            clearInterval(countdownTimer);
            countdownTimer = null;

            // 타이머 종료 후 버튼 다시 활성화
            const emailCheckButton = document.querySelector('button[onclick*="checkDuplicate(\'email\'"]');
            if (emailCheckButton) {
                emailCheckButton.disabled = false;
            }

            // 타이머 텍스트 제거 및 메시지 업데이트
            timerElement.remove();
            document.getElementById('emailMessage').textContent = '인증 코드를 다시 요청할 수 있습니다.';
            document.getElementById('emailMessage').style.color = 'blue';
        }
        timeLeft--;
    }, 1000);
}

function verifyAuthCode() {
    const enteredCode = document.getElementById('authCode').value;
    const sentCode = sessionStorage.getItem('authCode');

    if (enteredCode === sentCode) {
        document.getElementById('authCodeMessage').textContent = '인증이 완료되었습니다.';
        document.getElementById('authCodeMessage').style.color = 'green';
        emailChecked = true;

        // 인증 완료 시 타이머 제거
        if (countdownTimer) {
            clearInterval(countdownTimer);
            countdownTimer = null;
        }

        // 타이머 표시 제거
        const timerElement = document.getElementById('timer');
        if (timerElement) {
            timerElement.remove();
        }

        // 이메일 중복 확인 버튼 비활성화 유지
        const emailCheckButton = document.querySelector('button[onclick*="checkDuplicate(\'email\'"]');
        if (emailCheckButton) {
            emailCheckButton.disabled = true;
        }
    } else {
        document.getElementById('authCodeMessage').textContent = '인증 코드가 일치하지 않습니다.';
        document.getElementById('authCodeMessage').style.color = 'red';
        emailChecked = false;
    }
}

// 페이지 이탈 시 타이머 정리
window.addEventListener('beforeunload', () => {
    if (countdownTimer) {
        clearInterval(countdownTimer);
    }
});

// 폼 제출 전 유효성 검사 강화
if (document.getElementById('signupForm')) {
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
}

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

// 회원 탈퇴 경고
function confirmWithdraw() {
    swal({
        title: "정말 탈퇴하시겠습니까?",
        text: "탈퇴 버튼 선택 시, 계정은 삭제되며 복구되지 않습니다.",
        icon: "warning",
        buttons: {
            cancel: {
                text: "취소",
                value: false,
                visible: true,
                closeModal: true,
            },
            confirm: {
                text: "탈퇴",
                value: true,
                visible: true,
                closeModal: true
            }
        },
        dangerMode: true
    }).then((willDelete) => {
        if (willDelete) {
            // AJAX를 사용하여 서버에 탈퇴 요청
            const form = document.getElementById("withdrawForm");
            const formData = new FormData(form);

            fetch(form.action, {
                method: 'POST',
                body: formData,
                headers: {
                    [document.querySelector("meta[name='_csrf_header']").content]: document.querySelector("meta[name='_csrf']").content
                }
            })
                .then(response => {
                    if (response.ok) {
                        swal({
                            title: "회원탈퇴 완료",
                            text: "탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.",
                            icon: "success",
                            button: "확인"
                        }).then(() => {
                            window.location.href = "/home";
                        });
                    } else {
                        throw new Error('탈퇴 처리 중 오류가 발생했습니다.');
                    }
                })
                .catch(error => {
                    swal("오류", error.message, "error");
                });
        }
    });
    return false; // 폼 기본 제출 방지
}

function validateAndPreviewImage(input) {
    const errorMessage = document.getElementById('errorMessage');
    const previewImage = document.getElementById('previewImage');
    const maxSize = 10 * 1024 * 1024; // 10MB in bytes

    if (input.files && input.files[0]) {
        const file = input.files[0];

        // 파일 크기 검증
        if (file.size > maxSize) {
            errorMessage.textContent = '파일 크기는 10MB를 초과할 수 없습니다.';
            errorMessage.style.display = 'block';
            input.value = ''; // 파일 선택 초기화
            return;
        }

        // 이미지 미리보기
        const reader = new FileReader();
        reader.onload = function(e) {
            previewImage.src = e.target.result;
            errorMessage.style.display = 'none';
        };
        reader.readAsDataURL(file);
    }
}

// 폼 제출 전에 유효성 검사를 수행하는 함수
function validateForm() {
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    let message = '';

    // 닉네임 중복 확인 여부 검사
    if (!nicknameChecked) {
        message += '닉네임 중복 확인이 필요합니다.\n';
    }

    // 비밀번호와 비밀번호 확인란 검사
    if (password || confirmPassword) {
        // 비밀번호 확인 칸만 채워진 경우 에러 처리
        if (!password && confirmPassword) {
            message += '비밀번호 입력 칸을 채워주세요.\n';
        } else if (password !== confirmPassword) {
            message += '비밀번호가 일치하지 않습니다.\n';
        } else if (!password.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/)) {
            message += '비밀번호는 최소 8자, 영문, 숫자, 특수문자를 포함해야 합니다.\n';
        }
    }

    // 에러 메시지가 있으면 제출을 막음
    if (message) {
        alert(message);
        return false;
    }
    return true;
}

// 비밀번호 입력 필드의 값에 따라 비밀번호 확인 필드 표시/숨기기
function toggleConfirmPasswordField() {
    const password = document.getElementById('password').value;
    const confirmPasswordField = document.getElementById('confirmPasswordField');

    if (password) {
        confirmPasswordField.style.display = 'block';
    } else {
        confirmPasswordField.style.display = 'none';
        document.getElementById('confirmPassword').value = ''; // 비밀번호 확인 필드 초기화
        document.getElementById('confirmPasswordMessage').textContent = ''; // 메시지 초기화
    }
}

// 비밀번호 확인 필드에 입력 이벤트 리스너 추가
document.getElementById('confirmPassword').addEventListener('input', checkPasswordMatch);

// 비밀번호 유효성 검사 (update 페이지용)
document.getElementById('password')?.addEventListener('input', function() {
    const password = this.value;
    const passwordMessage = document.getElementById('passwordMessage');
    const confirmPasswordField = document.getElementById('confirmPasswordField');

    if (password) {
        if (!password.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/)) {
            passwordMessage.textContent = '최소 8자, 영문, 숫자, 특수문자를 포함해야 합니다.';
            passwordMessage.style.color = 'red';
        } else {
            passwordMessage.textContent = '사용 가능한 비밀번호입니다.';
            passwordMessage.style.color = 'green';
        }
        // 비밀번호가 입력되면 확인 필드 표시
        confirmPasswordField.style.display = 'block';
    } else {
        passwordMessage.textContent = '';
        // 비밀번호가 비어있으면 확인 필드 숨김
        confirmPasswordField.style.display = 'none';
        document.getElementById('confirmPassword').value = '';
        document.getElementById('confirmPasswordMessage').textContent = '';
    }

    // 비밀번호 확인 체크 실행
    checkPasswordMatch();
});

// update 페이지용 비밀번호 확인 체크
function checkPasswordMatch() {
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');
    const confirmPasswordMessage = document.getElementById('confirmPasswordMessage');

    if (!password || !confirmPassword) return;

    if (password.value && confirmPassword.value) {
        if (password.value === confirmPassword.value) {
            confirmPasswordMessage.textContent = '비밀번호가 일치합니다.';
            confirmPasswordMessage.style.color = 'green';
            window.passwordMatch = true;
        } else {
            confirmPasswordMessage.textContent = '비밀번호가 일치하지 않습니다.';
            confirmPasswordMessage.style.color = 'red';
            window.passwordMatch = false;
        }
    } else {
        confirmPasswordMessage.textContent = '';
    }
}

// update 페이지용 폼 제출 전 유효성 검사
function validateForm() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    let isValid = true;

    // 비밀번호가 입력된 경우에만 검증
    if (password || confirmPassword) {
        if (!password) {
            alert('비밀번호를 입력해주세요.');
            isValid = false;
        } else if (!password.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/)) {
            alert('비밀번호는 최소 8자, 영문, 숫자, 특수문자를 포함해야 합니다.');
            isValid = false;
        } else if (password !== confirmPassword) {
            alert('비밀번호가 일치하지 않습니다.');
            isValid = false;
        }
    }

    return isValid;
}

// update 페이지 폼에만 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const updateForm = document.querySelector('.my-info-right-container form');
    if (updateForm) {
        updateForm.addEventListener('submit', function(e) {
            if (!validateForm()) {
                e.preventDefault();
            }
        });
    }

    // 초기 비밀번호 확인 필드 상태 설정
    const password = document.getElementById('password');
    const confirmPasswordField = document.getElementById('confirmPasswordField');
    if (password && confirmPasswordField) {
        if (password.value) {
            confirmPasswordField.style.display = 'block';
        } else {
            confirmPasswordField.style.display = 'none';
        }
    }
});

// 비밀번호 확인 입력 필드에 이벤트 리스너 추가
document.getElementById('confirmPassword')?.addEventListener('input', checkPasswordMatch);

////
