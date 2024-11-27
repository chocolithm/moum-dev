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
