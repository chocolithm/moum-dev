// 게시글 등록 화면 열기
function openPostFormModal() {
    fetchPostForm();
    openOverlay();
    fadeIn(document.querySelector(".post-form-layer"));
}



// 게시글 등록 화면 내용 가져오기
function fetchPostForm() {

    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute("content");

    // 필요한 초기화 함수가 있다면 호출합니다.
    // 예: 이미지 슬라이드 인덱스 초기화 등

    fetch(`/board/boardPostForm`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken
        }
    })
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');

            document.querySelector('.post-form-layer').innerHTML =
                doc.querySelector('.post-form-layer').innerHTML;

            // 필요한 이벤트 리스너를 추가합니다.
            // 예: 파일 선택 시 미리보기 기능 등
        })
        .catch(error => {
            console.error("Error fetching post form:", error);
        });
}

// document.addEventListener("DOMContentLoaded", function () {
//     // 초기 상태 설정
//     const initialBoardType = document.querySelector("input[name='boardType']:checked").value;
//     toggleFields(initialBoardType);
//
//     // 라디오 버튼 변경 시마다 상태 업데이트
//     document.querySelectorAll("input[name='boardType']").forEach((elem) => {
//         elem.addEventListener("change", function () {
//             toggleFields(this.value);
//         });
//     });
// });
// document.querySelector("#general").addEventListener("click", function (){
//     toggleFields(this);
// })
//
// document.querySelector("#trade").addEventListener("click", function (){
//     toggleFields(this);
// })

function toggleFields(boardType) {
    const collectionSection = document.getElementById("collectionSection");
    const tradeFields = document.getElementById("tradeFields");

    if (boardType.value === "trade") {
        collectionSection.style.display = "block";
        tradeFields.style.display = "block";
    } else {
        collectionSection.style.display = "none";
        tradeFields.style.display = "none";
    }
}


// 게시글 등록 처리
function addPost() {
    if (confirm("등록하시겠습니까?")) {
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        const formData = new FormData();
        formData.append("title", document.querySelector("#postForm #title").value);
        formData.append("content", document.querySelector("#postForm #content").value);
        formData.append("boardType", document.querySelector("#postForm input[name='boardType']:checked").value);

        // 수집품 거래 글 전용 필드 추가
        if (document.querySelector("#postForm input[name='boardType']:checked").value === "trade") {
            formData.append("price", document.querySelector("#postForm #price").value);
            formData.append("status", document.querySelector("#postForm #status").value);
            formData.append("transactionType", document.querySelector("#postForm input[name='transactionType']:checked").value);
            formData.append("contact", document.querySelector("#postForm #contact").value);
        }

        // 파일 업로드 처리
        const filesInput = document.querySelector("#postForm #files");
        for (let i = 0; i < filesInput.files.length; i++) {
            formData.append("files", filesInput.files[i]);
        }

        fetch(`/board/addPost`, {
            method: "POST",
            body: formData,
            headers: {
                [csrfHeader]: csrfToken
            }
        })
            .then(response => response.text())
            .then(response => {
                switch (response) {
                    case "login":
                        alert("로그인이 필요합니다.");
                        location.href = "/login";
                        break;
                    case "success":
                        alert("게시글이 등록되었습니다.");
                        location.href = "/board/boardList";
                        break;
                    case "failure":
                        alert("등록에 실패했습니다.");
                        break;
                }
            })
            .catch(error => {
                console.error("Error adding post:", error);
            });
    }
}
