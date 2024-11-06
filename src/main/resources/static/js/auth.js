let lastSentTime = 0;  // 마지막 인증 코드 발송 시간을 저장하는 변수
let countdownTimer = null;

// 비밀번호 유효성 검사
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

function checkEmail() {
    const email = document.getElementById('email').value;

    if (!email.match(/[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/)) {
        document.getElementById('emailMessage').textContent = '유효한 이메일 주소를 입력해주세요.';
        document.getElementById('emailMessage').style.color = 'red';
        return;
    }

    const emailCheckButton = document.querySelector('button[onclick="checkEmail()"]');
    emailCheckButton.disabled = true;

    const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    const csrfToken = document.querySelector("meta[name='_csrf']").content;

    fetch('/user/check-duplicate?email=' + encodeURIComponent(email), {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        }
    })
        .then(response => response.json())
        .then(data => {
            if (!data.isEmailTaken) {
                document.getElementById('emailMessage').textContent = '등록되지 않은 이메일입니다.';
                document.getElementById('emailMessage').style.color = 'red';
                emailCheckButton.disabled = false;
                return;
            }

            return fetch('/auth/check-email', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify({ email: email })
            });
        })
        .then(response => response.text())
        .then(result => {
            if (result === "존재하지 않는 이메일입니다.") {
                document.getElementById('emailMessage').textContent = result;
                document.getElementById('emailMessage').style.color = 'red';
                emailCheckButton.disabled = false;
            } else {
                sessionStorage.setItem('authCode', result);
                document.getElementById('authCodeField').style.display = 'block';
                document.getElementById('emailMessage').textContent = '인증 코드가 발송되었습니다.';
                document.getElementById('emailMessage').style.color = 'green';
                startTimer();
            }
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('emailMessage').textContent = '인증 코드 발송에 실패했습니다.';
            document.getElementById('emailMessage').style.color = 'red';
            emailCheckButton.disabled = false;
        });
}

// 폼 제출 전 비밀번호와 이메일 인증 확인
document.getElementById('resetPasswordForm').addEventListener('submit', function(e) {
    e.preventDefault();

    // 이메일 인증 확인
    if (!emailChecked) {
        alert('이메일 인증이 필요합니다.');
        return;
    }

    // 비밀번호와 비밀번호 재확인이 일치하는지 확인
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    if (password !== confirmPassword) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
    }

    const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    const csrfToken = document.querySelector("meta[name='_csrf']").content;

    // 서버로 비밀번호 재설정 요청
    fetch('/auth/reset-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({
            email: document.getElementById('email').value,
            password: password // 수정된 비밀번호 필드 이름
        })
    })
        .then(response => {
            if (response.ok) {
                alert('비밀번호가 성공적으로 변경되었습니다.');
                window.location.href = '/home?openLoginModal=true'; // 로그인 페이지로 이동
            } else {
                alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
});
