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
// 게시글 등록 처리
function addDetailPost() {
    if (confirm("등록하시겠습니까?")) {
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        const formData = new FormData(document.getElementById("postForm"));
        formData.append("boardType", "general");

        const filesInput = document.getElementById("files");
        const maxFileSize = 10 * 1024 * 1024; // 10MB
        for (let i = 0; i < filesInput.files.length; i++) {
            if (filesInput.files[i].size > maxFileSize) {
                alert("각 파일의 크기는 10MB를 초과할 수 없습니다.");
                return;
            }
            formData.append("files", filesInput.files[i]);
        }

        fetch(`/board/addDetailPost`, {
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


// 댓글 저장
function saveComment(boardNo, userNo) {

    const content = document.getElementById('content');
    // isValid(content, '댓글');

    // const boardNo = [[ `${board.no}` ]];
    // const boardNo = [[ `${board.no}` ]];
    const params = {
        boardNo: boardNo,
        content: content.value,
        // writer : '홍길동',
        userNo: userNo
    }

    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');
    $.ajax({
        url: `/Comment/board/${boardNo}/comments`,
        type: 'post',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(params),
        async: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            alert('댓글 등록 완료.');
            content.value = '';
            document.getElementById('counter').innerText = '0/300자';
            findAllComment(boardNo);
        },
        error: function (request, status, error) {
            console.log(error)
        }
    })
}

// 전체 댓글 조회
function findAllComment(boardNo) {
    // const postId = [[ ${board.no} ]];
    $.ajax({
        url: `/Comment/board/${boardNo}/comments`,
        type: 'get',
        dataType: 'json',
        async: false,
        success: function (response) {
            console.log(response);
            // 1. 조회된 데이터가 없는 경우
            if (!response.length) {
                document.querySelector('.cm_list').innerHTML = '<div class="cm_none"><p>등록된 댓글이 없습니다.</p></div>';
                return false;
            }
            // 2. 렌더링 할 HTML을 저장할 변수
            let commentHtml = '';
            // 3. 댓글 HTML 추가
            response.forEach(row => {
                commentHtml += `
                        <div>
                            
                            <div class="cont"><div class="txt_con">${row.content}</div></div>
                            <p class="func_btns">
                                <button type="button" class="btns"><span class="icons icon_modify">수정</span></button>
                                <button type="button" class="btns"><span class="icons icon_del">삭제</span></button>
                            </p>
                        </div>
                    `;
            })
            // <span class="writer_img"><img src="/images/default_profile.png" width="30" height="30" alt="기본 프로필 이미지"/></span>
            // <p class="writer">
            //     <em>${row.writer}</em>
            //     <span class="date">${dayjs(row.createdDate).format('YYYY-MM-DD HH:mm')}</span>
            // </p>
            // 4. class가 "cm_list"인 요소를 찾아 HTML을 렌더링
            document.querySelector('.cm_list').innerHTML = commentHtml;
        },
        error: function (request, status, error) {
            console.log(error)
        }
    })
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

function deletePost(boardNo) {
    if (confirm("정말 이 게시글을 삭제하시겠습니까?")) {
        document.getElementById("deleteBoardNo").value = boardNo;
        document.getElementById("deleteForm").submit();
    }
}



