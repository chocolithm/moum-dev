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

function toggleFields(element) {
    const collectionSection = document.querySelector(".collection-select");

    if (element.value === "sell") {
        collectionSection.style.display = "block";
    } else {
        collectionSection.style.display = "none";
        collectionSection.childNodes[1].value = '0';
    }
}


// // 게시글 등록 처리
// function addPost() {
//     if (confirm("등록하시겠습니까?")) {
//         const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
//         const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');
//
//         const formData = new FormData();
//         formData.append("title", document.querySelector("#postForm #title").value);
//         formData.append("content", document.querySelector("#postForm #content").value);
//         formData.append("boardType", document.querySelector("#postForm input[name='boardType']:checked").value);
//
//         // 수집품 거래 글 전용 필드 추가
//         if (document.querySelector("#postForm input[name='boardType']:checked").value === "trade") {
//             formData.append("price", document.querySelector("#postForm #price").value);
//             formData.append("sellOrSoldStatus", document.querySelector("#postForm #status").value);
//             formData.append("transactionType", document.querySelector("#postForm input[name='transactionType']:checked").value);
//         }
//
//         // 파일 업로드 처리
//         const filesInput = document.querySelector("#postForm #files");
//         for (let i = 0; i < filesInput.files.length; i++) {
//             formData.append("files", filesInput.files[i]);
//         }
//
//         fetch(`/board/addPost`, {
//             method: "POST",
//             body: formData,
//             headers: {
//                 [csrfHeader]: csrfToken
//             }
//         })
//             .then(response => response.text())
//             .then(response => {
//                 switch (response) {
//                     case "login":
//                         alert("로그인이 필요합니다.");
//                         location.href = "/login";
//                         break;
//                     case "success":
//                         alert("게시글이 등록되었습니다.");
//                         location.href = "/board/boardList";
//                         break;
//                     case "failure":
//                         alert("등록에 실패했습니다.");
//                         break;
//                 }
//             })
//             .catch(error => {
//                 console.error("Error adding post:", error);
//             });
//     }
// }
// // 게시글 등록 처리
// function addDetailPost() {
//     if (confirm("등록하시겠습니까?")) {
//         const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
//         const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');
//
//         const formData = new FormData(document.getElementById("postForm"));
//         formData.append("boardType", "general");
//
//         const filesInput = document.getElementById("files");
//         const maxFileSize = 10 * 1024 * 1024; // 10MB
//         for (let i = 0; i < filesInput.files.length; i++) {
//             if (filesInput.files[i].size > maxFileSize) {
//                 alert("각 파일의 크기는 10MB를 초과할 수 없습니다.");
//                 return;
//             }
//             formData.append("files", filesInput.files[i]);
//         }
//
//         fetch(`/board/addDetailPost`, {
//             method: "POST",
//             body: formData,
//             headers: {
//                 [csrfHeader]: csrfToken
//             }
//         })
//         .then(response => response.text())
//         .then(response => {
//             switch (response) {
//                 case "login":
//                     alert("로그인이 필요합니다.");
//                     location.href = "/login";
//                     break;
//                 case "success":
//                     alert("게시글이 등록되었습니다.");
//                     location.href = "/board/boardList";
//                     break;
//                 case "failure":
//                     alert("등록에 실패했습니다.");
//                     break;
//             }
//         })
//         .catch(error => {
//             console.error("Error adding post:", error);
//         });
//     }
// }



// 댓글 내용 길이 카운팅 함수
function countingLength(element) {
    const maxLength = 300;
    const currentLength = element.value.length;
    const counter = document.getElementById('counter');

    if (currentLength > maxLength) {
        element.value = element.value.substring(0, maxLength);
        counter.textContent = `${maxLength}/${maxLength}자`;
    } else {
        counter.textContent = `${currentLength}/${maxLength}자`;
    }
}

// 댓글 추가
function addComment() {
    const content = document.getElementById("commentContent").value;
    if (content.trim() === "") {
        alert("댓글 내용을 입력해주세요.");
        return;
    }

    const csrfHeaders = getCsrfTokenHeaders();

    fetch(`/Comment/board/${boardId}/comments`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrfHeaders.header]: csrfHeaders.token
        },
        body: JSON.stringify({ content: content })
    })
        .then(response => response.json())
        .then(async comment => {
            // renderComment(comment);

            // 댓글 알림 처리
            await fetch(`/alert/add?category=comment&categoryNo=${boardId}`)
                .catch(error => {
                    console.error("error adding alert: ", error);
                })

            // 업적 추가
            await updateAchievement("HUNDRED_COMMENT");
            await updateAchievement("THIRTY_COMMENT");
            await updateAchievement("TEN_COMMENT");

            document.getElementById("commentContent").value = "";
            countingLength(document.getElementById("commentContent"));
            location.href=`/board/boardView?no=${boardId}`;
        })
        .catch(error => {
            console.error("댓글 등록 오류:", error);
            alert("댓글 등록에 실패했습니다.");
        });
}

// // 댓글 수정
// function editComment(commentId) {
//     const content = prompt("수정할 내용을 입력하세요.");
//     if (content === null || content.trim() === "") return;
//
//     const csrfHeaders = getCsrfTokenHeaders();
//
//     fetch(`/Comment/comments/${commentId}`, {
//         method: "PUT",
//         headers: {
//             "Content-Type": "application/json",
//             [csrfHeaders.header]: csrfHeaders.token
//         },
//         body: JSON.stringify({ content: content })
//     })
//         .then(response => response.json())
//         .then(updatedComment => {
//             document.querySelector(`#comment-${commentId} .content`).textContent = updatedComment.content;
//         })
//         .catch(error => {
//             console.error("댓글 수정 오류:", error);
//             alert("댓글 수정에 실패했습니다.");
//         });
// }

// 댓글 삭제
function deleteComment(commentId) {
    if (!confirm("댓글을 삭제하시겠습니까?")) return;

    const csrfHeaders = getCsrfTokenHeaders();

    fetch(`/Comment/comments/${commentId}`, {
        method: "DELETE",
        headers: {
            [csrfHeaders.header]: csrfHeaders.token
        }
    })
        .then(response => response.text())
        .then((response) => {
            if (response === "success") {
                document.getElementById(`comment-${commentId}`).remove();
            }
        })
        .catch(error => {
            console.error("댓글 삭제 오류:", error);
            alert("댓글 삭제에 실패했습니다.");
        });
}



function getCsrfTokenHeaders() {
    // CSRF 토큰을 메타 태그에서 가져옵니다.
    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    return {
        header: csrfHeader,
        token: csrfToken
    };
}

function toggleLike(boardNo, userNo) {
    const csrfHeaders = getCsrfTokenHeaders();

    $.ajax({
        url: "/board/toggleLike",
        type: "POST",
        data: { boardNo: boardNo, userNo: userNo },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeaders.header, csrfHeaders.token); // CSRF 헤더 추가
        },
        success: function (response) {
            $("#likeCount").text(response.likeCount); // 좋아요 수 업데이트

            Swal.fire(response.message);
        },
        error: function (error) {
            console.error("좋아요 오류:", error);
            Swal.fire("좋아요 처리에 실패했습니다.");
        }
    });
}


// 게시글 수정 함수
function editPost(boardNo) {
    if (boardNo) {
        window.location.href = "/board/update?no=" + boardNo;

    } else {
        console.error("게시글 번호가 정의되지 않았습니다.");
        alert("게시글 번호가 유효하지 않습니다.");
    }
}

async function deletePost(boardNo) {
    if (confirm("정말 이 게시글을 삭제하시겠습니까?")) {
        document.getElementById("deleteBoardNo").value = boardNo;
        document.getElementById("deleteForm").submit();
    }
}

function tradeComplete(boardNo) {
    $.ajax({
        url: `/board/complete/${boardNo}`,  // 가져온 no 값을 URL에 추가
        type: 'GET',
        success: function (response) {
            if (response === 'success') {
                alert('거래 완료 되었습니다');
                window.location.href = `/board/boardView?no=${boardNo}`;
            } else {
                alert('실패');
            }
        },
        error: function () {
            alert('서버 오류가 발생했습니다.');
        }
    });
}


function collectionLoadButton(no) {
    openCollectionViewModalFromBoard(no);
}
/*-----------------------------------------------------------------------*/


