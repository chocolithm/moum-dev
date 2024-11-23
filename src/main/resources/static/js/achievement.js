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

/* myinfo 업적 최상단으로 가져오기*/
// 선택된 업적을 처리하는 함수
function selectAchievement(element) {
    // 선택된 업적 텍스트와 ID 가져오기
    var achievementText = element.innerText;
    var achievementId = element.getAttribute('data-id');

    // 버튼에 선택된 업적 이름을 표시
    document.getElementById('achievement-selected').innerText = achievementText;

    // 선택된 업적 ID를 hidden input에 저장
    document.getElementById('user-achievement').value = achievementId;

    // 드롭다운을 자동으로 닫기
    // var dropdown = new bootstrap.Dropdown(element.closest('.dropdown-toggle'));
    // dropdown.hide();
}

// 페이지 로드시 선택된 업적이 있으면 드롭다운 최상단에 해당 업적을 표시
window.addEventListener('load', function() {
    if (document.getElementById('user-achievement')) {
        var selectedAchievementId = document.getElementById('user-achievement').value; // 수정 후 저장된 업적 ID
        var items = document.querySelectorAll('.dropdown-item');
        var selectedAchievement = null;
        var selectedLi = null;

        // 선택된 업적을 찾아서 드롭다운에서 최상단으로 이동
        items.forEach(item => {
            var itemId = item.getAttribute('data-id');
            if (itemId === selectedAchievementId) {
                selectedAchievement = item;
                selectedLi = item.closest('li');
            }
        });

        if (selectedAchievement) {
            var ul = selectedAchievement.closest('ul');

            // 현재 선택된 업적의 li 요소를 복제
            var clonedLi = selectedLi.cloneNode(true);

            // 원래 위치의 li 요소 제거
            selectedLi.remove();

            // 복제된 li 요소를 최상단에 삽입
            ul.insertBefore(clonedLi, ul.firstElementChild);

            // 복제된 요소에 대한 이벤트 리스너 재설정
            clonedLi.querySelector('.dropdown-item').addEventListener('click', function(e) {
                e.preventDefault();
                selectAchievement(this);
            });

            // 드롭다운 버튼에 선택된 업적 이름 표시
            document.getElementById('achievement-selected').innerText = selectedAchievement.innerText;
        }
    }
});



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
