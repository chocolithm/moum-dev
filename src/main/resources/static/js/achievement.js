

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
    });
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
    });
}
