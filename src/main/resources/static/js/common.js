// 공통
// let nicknameChecked = false;
let emailChecked = false;
let passwordMatch = false;

getWikiLink();

function openOverlay() {
    fadeIn(document.getElementsByClassName("overlay")[0]);
}

function closeModal() {
    const layers = document.getElementsByClassName("layer");

    fadeOut(document.getElementsByClassName("overlay")[0]);
    for (i = 0; i < layers.length; i++) {
        fadeOut(layers[i]);
        layers[i].innerHTML = "";
    }
}

// 채팅, 알림 화면 닫기
function closeHeaderModal(element) {
    fadeOut(element);
    element = "";
}

// fadeIn, fadeOut 사용 시 style에 아래 속성 주셔야 부드럽게 적용됩니다.
// opacity: 1 또는 0;
// transition: opacity 0.5s ease;

function fadeIn(element) {
    element.style.display = "block";
    setTimeout(function () {
        element.style.opacity = 1;
    }, 10);
}

function fadeInWithFlex(element) {
    element.style.display = "flex";
    setTimeout(function () {
        element.style.opacity = 1;
    }, 10);
}

function fadeOut(element) {
    element.style.opacity = 0;
    setTimeout(function () {
        element.style.display = "none";
    }, 500);
}

function formatDate(value) {
    if (value != null) {
        const date = new Date(value);
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        const seconds = date.getSeconds().toString().padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
    return "";
}

// 숫자만 입력받는 함수
function formatNumber(element) {
    element.value = element.value.replace(/[^0-9]/g, "");
}

// 채팅, 알림 시간 계산
function calcTime(dateValue) {
    const now = new Date();
    const date = new Date(dateValue);
    if (now.getFullYear() === date.getFullYear()) {
        if (now.getDate() === date.getDate()) {
            return date.getHours().toString().padStart(2, '0') + ":" + date.getMinutes().toString().padStart(2, '0');
        }

        return (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours().toString().padStart(2, '0') + ":" + date.getMinutes().toString().padStart(2, '0');
    } else {
        return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();
    }
}

// 업적 카운트 추가
/*
특정 action에서 추가로 실행시키고 싶은 부분이 있다면 아래와 같이 함수 정의

ignored : 이미 취득한 업적
success : 업적 횟수 추가
acquired : 업적 획득
failre : 업적 횟수 추가 오류

updateAchievement(123, {
    onSuccess: () => console.log("Custom success logic"), // success에 사용자 정의 함수 적용
    onFailure: () => console.log("Custom failure logic")  // failure에 사용자 정의 함수 적용
});
*/

async function updateAchievement(
    achievement_id,
    { onIgnored, onSuccess, onAcquired, onFailure } = {}
) {
    try {
        const response = await fetch(`/achievement/updateCount?id=${achievement_id}`);
        const result = await response.text();

        // 이미 취득한 업적
        if (result === "ignored") {
            if (onIgnored) onIgnored();
            return;
        }

        // 업적 취득
        if (result === "success") {
            if (onSuccess) onSuccess();
            return;
        }

        // 업적 취득
        if (result === "acquired") {
            try {
                // 업적 취득 알림 처리
                await fetch(`/alert/add?category=achievement&categoryNo=${achievement_id}`);
            } catch (alertError) {
                console.error("Error adding achievement alert:", alertError);
            }
            if (onAcquired) onAcquired();
            return;
        }

        // 업적 카운트 추가 실패
        if (result === "failure") {
            if (onFailure) onFailure();
            return;
        }
        
    } catch (error) {
        console.error("Error updating achievement count:", error);
        if (onFailure) onFailure();
    }
}

// function updateAchievement(
//     achievement_id,
//     { onIgnored, onSuccess, onAcquired, onFailure } = {}
// ) {
//     fetch(`/achievement/updateCount?id=${achievement_id}`)
//         .then(response => response.text())
//         .then(response => {
//             // 이미 취득한 업적
//             if (response == "ignored") {
//                 if (onIgnored) { onIgnored(); }
//                 return;
//             }

//             // 업적 카운트 추가 성공
//             if (response == "success") {
//                 if (onSuccess) { onSuccess(); }
//                 return;
//             }

//             // 업적 취득
//             if (response == "acquired") {

//                 // 업적취득 알림 처리
//                 fetch(`/alert/add?category=achievement&categoryNo=${achievement_id}`)
//                     .catch(error => {
//                         console.error("error adding alert: ", error);
//                     })

//                 if (onAcquired) { onAcquired(); }
//                 return;
//             }

//             // 업적 카운트 추가 실패
//             if (response == "failure") {
//                 if (onFailure) { onFailure(); }
//                 return;
//             }
//         })
//         .catch(error => {
//             console.error("error updating achievement count: ", error);
//         })
// }

// 위키 링크
function getWikiLink() {
    const hostname = location.hostname;
    const wikilink = document.getElementById("wiki-link");
    if (hostname.startsWith("localhost") || hostname.startsWith("dev")) {
        wikilink.href = "http://dev.moum.bangdpool.com:3000/w/Moum";
    } else if (hostname.startsWith("moum")) {
        wikilink.href = "https://wiki.moum.bangdpool.com/w/Moum";
    }
}

// 로그인

const modal = document.getElementById("loginModal");
const btn = document.getElementById("openModalBtn");

// 페이지 로드 시 실행되는 함수
document.addEventListener('DOMContentLoaded', function () {
    // 회원가입 성공 후 로그인 모달 열기
    const urlParams = new URLSearchParams(window.location.search);
    const openLoginModalFlag = urlParams.get('openLoginModal');

    if (openLoginModalFlag === 'true') {
        openLoginModal(); // 함수 호출 방식 수정
    }
});

// 로그인 모달에서 회원가입 모달로 전환하는 함수
function openSignupFromLoginModal() {
    closeLoginModal(); // 로그인 모달 닫기
    document.getElementById('loginFormContainer').innerHTML = "";
    openSignupModal(); // 회원가입 모달 열기
}

// 로그인 모달에서 비밀번호 변경 모달로 전환하는 함수
function openResetPasswordFromLoginModal() {
    closeLoginModal(); // 로그인 모달 닫기
    document.getElementById('loginFormContainer').innerHTML = "";
    openResetPasswordModal(); // 회원가입 모달 열기
}

// 로그인 모달 열기 및 form.html 로드
function openLoginModal() {
    const modal = document.getElementById("loginModal");
    fetch('/auth/form')
        .then(response => response.text())
        .then(data => {
            document.getElementById('loginFormContainer').innerHTML = data;
            modal.style.display = "block";
            modal.setAttribute('aria-hidden', false);
            populateEmailField();
            fadeIn(modal)
        });
}

// 회원가입 모달 열기 및 signup.html 로드
function openSignupModal() {
    const modal = document.getElementById("signupModal");
    fetch('/user/signup')
        .then(response => response.text())
        .then(data => {
            document.getElementById('signupFormContainer').innerHTML = data;
            modal.style.display = "block";
            modal.setAttribute('aria-hidden', false);

            // 모달 내부의 스크립트 실행을 위한 코드 추가
            const scripts = document.querySelector('#signupFormContainer').querySelectorAll('script');
            scripts.forEach(script => {
                const newScript = document.createElement('script');
                if (script.src) {
                    newScript.src = script.src;
                    newScript.async = false;
                } else {
                    newScript.textContent = script.textContent;
                }
                document.body.appendChild(newScript);
            });
            fadeIn(modal)
        });
}

// 비밀번호 재설정 모달 열기
function openResetPasswordModal() {
    const modal = document.getElementById("resetPasswordModal");
    fetch('/auth/reset-password')
        .then(response => response.text())
        .then(data => {
            document.getElementById('resetPasswordFormContainer').innerHTML = data;
            modal.style.display = "block";
            modal.setAttribute('aria-hidden', false);

            // 모달 내부 스크립트 실행을 위한 코드 추가
            const scripts = document.querySelector('#resetPasswordFormContainer').querySelectorAll('script');
            scripts.forEach(script => {
                const newScript = document.createElement('script');
                if (script.src) {
                    newScript.src = script.src;
                    newScript.async = false;
                } else {
                    newScript.textContent = script.textContent;
                }
                document.body.appendChild(newScript);
            });
            fadeIn(modal)
        });
}

// 로그인 모달 닫기
function closeLoginModal() {
    const modal = document.getElementById("loginModal");
    modal.style.display = "none";
    modal.setAttribute('aria-hidden', true);
    document.getElementById("loginFormContainer").innerHTML = "";
    fadeOut(modal);
}

// 회원가입 모달 닫기
function closeSignupModal() {
    const modal = document.getElementById("signupModal");
    modal.style.display = "none";
    modal.setAttribute('aria-hidden', true);
    document.getElementById("signupFormContainer").innerHTML = "";
    fadeOut(modal);
}

// 비밀번호 재설정 모달 닫기
function closeResetPasswordModal() {
    const modal = document.getElementById("resetPasswordModal");
    modal.style.display = "none";
    modal.setAttribute('aria-hidden', true);
    document.getElementById("resetPasswordFormContainer").innerHTML = "";
    fadeOut(modal);
}

// 모달 외부 클릭 시 모달 닫기 처리
window.onmousedown = function (event) {
    const loginModal = document.getElementById("loginModal");
    const signupModal = document.getElementById("signupModal");
    const resetPasswordModal = document.getElementById("resetPasswordModal");
    if (event.target === loginModal) {
        closeLoginModal();
    }
    if (event.target === signupModal) {
        closeSignupModal();
    }
    if (event.target === resetPasswordModal) {
        closeResetPasswordModal()
    }
}

// 이메일을 쿠키에 저장하는 함수
function setEmailCookie(email, days) {
    const expires = new Date(Date.now() + days * 864e5).toUTCString();
    document.cookie = 'savedEmail=' + encodeURIComponent(email) + '; expires=' + expires + '; path=/';
}

// 저장된 쿠키에서 이메일을 가져오는 함수
function getEmailCookie() {
    const cookie = document.cookie.split('; ').find(row => row.startsWith('savedEmail='));
    return cookie ? decodeURIComponent(cookie.split('=')[1]) : '';
}

// 로그인 폼 제출 시 이메일 저장 처리
function handleLoginSubmit() {
    const email = document.getElementById('email').value;
    const saveEmail = document.getElementById('saveEmail').checked;

    // '이메일 저장'이 체크된 경우 이메일 쿠키 저장
    if (saveEmail) {
        setEmailCookie(email, 30);  // 30일 동안 쿠키 유지
    } else {
        setEmailCookie('', -1);  // 체크 해제 시 쿠키 삭제
    }
}

// 페이지 로드 시 쿠키에 저장된 이메일을 폼에 채움
function populateEmailField() {
    const savedEmail = getEmailCookie();
    if (savedEmail) {
        const emailField = document.getElementById('email');
        const saveEmailCheckbox = document.getElementById('saveEmail');

        if (emailField && saveEmailCheckbox) {
            emailField.value = savedEmail;
            saveEmailCheckbox.checked = true;
        }
    }
}

// 로그인 실패시 알림
document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('error') === 'true') {
        swal({
            title: "로그인 실패",
            text: "이메일 또는 비밀번호가 맞지 않습니다.",
            icon: "error",
            button: "확인"
        }).then(() => {
            // error 파라미터 제거하고 로그인 모달 다시 열기
            const newUrl = window.location.pathname + window.location.search.replace(/[?&]error=true/, '');
            window.history.replaceState({}, document.title, newUrl);
            openLoginModal();
        });
    }
});

// 회원가입 성공 또는 실패시 알림
document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);

    // 회원가입 성공
    if (urlParams.get('signupSuccess') === 'true') {
        alert("회원가입이 완료되었습니다.");
    }

    // 회원가입 실패
    if (urlParams.get('signupError') === 'true') {
        alert("회원가입 중 오류가 발생했습니다.");
    }
});
