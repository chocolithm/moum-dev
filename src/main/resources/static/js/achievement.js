

//회원별 업적 현황 가져오기
function fetchAchievementByUser() {
    fetch(`/achievement/listByUser`)
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');

            document.querySelector('.achievement-user-progress').innerHTML =
                doc.querySelector('.achievement-user-progress').innerHTML;

            sortAchievements();
            filterAchievements();

        })
        .catch(error => {
            console.error("Error fetching achievementList:", error);
        });

}

//회원 업적 화면 가져오기
function openAchievementListByUser() {
    fetchAchievementByUser();
    openOverlay();
    fadeIn(document.querySelector(".achievement-user-progress"));
}


// 정렬 함수 추가
function sortAchievements() {
    const achievementsContainer = document.querySelector('.achievement-container');
    const achievements = Array.from(document.querySelectorAll('.achievement-photo'));

    achievements.sort((a, b) => {
        const progressA = parseInt(a.getAttribute('progress'));
        const progressB = parseInt(b.getAttribute('progress'));
        return progressB - progressA; // 내림차순 정렬
    });

    achievements.forEach(achievement => achievementsContainer.appendChild(achievement));
}

function filterAchievements() {
    const checkedValues = Array.from(document.querySelectorAll('.filter:checked')).map(cb => cb.value);
    const achievements = Array.from(document.querySelectorAll('.achievement-photo'));

    achievements.forEach(achievement => {
        const progress = parseInt(achievement.getAttribute('progress'));
        const shouldShow = checkedValues.length === 0 ||
            (checkedValues.includes('not-started') && progress === 0) ||
            (checkedValues.includes('not-started') && progress > 0 && progress < 100) ||
            (checkedValues.includes('completed') && progress === 100);

        achievement.style.display = shouldShow ? 'block' : 'none';

        // Bootstrap Progress Bar 색상 및 애니메이션 설정
        const progressBar = achievement.querySelector('.progress-bar');
        if (progressBar) {
            let progressClass = 'bg-success'; // 기본 값은 초록색
            if (progress <= 40) {
                progressClass = 'bg-danger'; // 0% ~ 40% 빨간색
            } else if (progress <= 60) {
                progressClass = 'bg-warning'; // 41% ~ 60% 노란색
            } else if (progress <= 99) {
                progressClass = 'bg-info'; // 61% ~ 99% 하늘색
            }

            // 진행 상태에 따른 색상 클래스 추가
            progressBar.classList.add(progressClass);

            // 진행 바에 스트라이프와 애니메이션 클래스 추가
            progressBar.classList.add('progress-bar-striped', 'progress-bar-animated');

            // 진행 상태에 따른 너비 설정
            progressBar.setAttribute('style', `width: ${progress}%`);
        }
    });
}


