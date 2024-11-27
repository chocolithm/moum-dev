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

function addComment(boardId) {
    const content = document.getElementById("commentContent").value;

    if (content.trim() === "") {
        alert("댓글 내용을 입력해주세요.");
        return;
    }

    const commentData = { content: content };

    const csrfHeaders = getCsrfTokenHeaders(); // CSRF 토큰 및 헤더 가져오기

    fetch(`/Comment/board/${boardId}/comments`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeaders.header]: csrfHeaders.token // CSRF 헤더 설정
        },
        body: JSON.stringify(commentData)
    })
    .then(response => response.json())
    .then(data => {
        // 댓글 추가 성공 시 DOM에 새 댓글 추가 (맨 아래로 추가)
        const commentList = document.querySelector(".comment-list");
        const newComment = document.createElement("li");
        newComment.setAttribute("id", `comment-${data.no}`);
        // newComment.innerHTML = `
        //     <div class="comment-box">
        //         <div class="comment-box-deleteBtn">
        //             <button class="comment-delete-btn" 
        //                     onclick="deleteComment(${data.no})">삭제</button>
        //         </div>
        //         <div class="comment-header">
        //             <span>${data.user.nickname}</span>
        //             <span class="comment-date">${new Date(data.date).toLocaleDateString()}</span>
        //         </div>
        //         <div class="comment-content">${data.content}</div>
        //     </div>`;

        newComment.innerHTML = `
            <div class="comment-box">
                <div class="comment-header">
                    <span style="font-size: 13.5px">${data.user.nickname}</span>
                    <div style="display: flex; align-items: center;">
                        <span class="comment-date">${formatDate(data.date)}</span>
                        <button class="comment-delete-btn"
                                onclick="deleteComment(${data.no});">❌</button>
                    </div>
                </div>
                <div class="comment-content" style="font-size: 13.5px">${data.content}</div>
            </div>`;

        commentList.appendChild(newComment); // 새 댓글을 리스트의 맨 아래에 추가

        // 댓글 입력창 초기화
        document.getElementById("commentContent").value = "";
        countingLength(document.getElementById("commentContent"));
    })
    .catch(error => {
        console.error("댓글 추가 실패:", error);
    });
}


// CSRF 토큰 및 헤더 설정 함수
function getCsrfTokenHeaders() {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    return {
        header: csrfHeader,
        token: csrfToken
    };
}

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
            const commentElement = document.getElementById(`comment-${commentId}`);
            if (commentElement) {
                commentElement.remove();
            }
        } else {
            alert("댓글 삭제에 실패했습니다.");
        }
    })
    .catch(error => {
        console.error("댓글 삭제 오류:", error);
        alert("댓글 삭제에 실패했습니다.");
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

// // 댓글 삭제
// function deleteComment(commentId) {
//     if (!confirm("댓글을 삭제하시겠습니까?")) return;
//
//     const csrfHeaders = getCsrfTokenHeaders();
//
//     fetch(`/Comment/comments/${commentId}`, {
//         method: "DELETE",
//         headers: {
//             [csrfHeaders.header]: csrfHeaders.token
//         }
//     })
//         .then(response => response.text())
//         .then((response) => {
//             if (response === "success") {
//                 document.getElementById(`comment-${commentId}`).remove();
//             }
//         })
//         .catch(error => {
//             console.error("댓글 삭제 오류:", error);
//             alert("댓글 삭제에 실패했습니다.");
//         });
// }
//
//
//
// function getCsrfTokenHeaders() {
//     // CSRF 토큰을 메타 태그에서 가져옵니다.
//     const csrfToken = $("meta[name='_csrf']").attr("content");
//     const csrfHeader = $("meta[name='_csrf_header']").attr("content");
//     return {
//         header: csrfHeader,
//         token: csrfToken
//     };
// }


  function toggleLike(boardNo, userNo) {
      const csrfHeaders = getCsrfTokenHeaders();  // CSRF 토큰 헤더 설정

      $.ajax({
          url: "/board/toggleLike",
          type: "POST",
          data: { boardNo: boardNo, userNo: userNo },
          beforeSend: function (xhr) {
              xhr.setRequestHeader(csrfHeaders.header, csrfHeaders.token); // CSRF 헤더 추가
          },
          success: function (response) {
              $("#likeCount").text(response.likeCount); // 서버에서 받은 좋아요 수로 갱신
              $("#likeCount2").text(response.likeCount); // 서버에서 받은 좋아요 수로 갱신

//              Swal.fire(response.message); // 알림 메시지 표시
          },
          error: function (error) {
              console.error("좋아요 오류:", error);
//              Swal.fire("좋아요 처리에 실패했습니다.");
          }
      });
  }


function changeText(button) {
    // 버튼이 'btn-danger' 클래스를 가지면 '♥' 상태, 없으면 '♡' 상태
    if (button.innerHTML.trim() == '🩷') {
//    if (button.classList.contains('btn-danger')) {
        // ♥ -> ♡ 상태로 변경
//        button.classList.remove('btn-danger'); // 'btn-danger' 클래스 제거
//        button.classList.add('btn-outline-dark'); // 원래 상태 (흰색 배경)로 복귀
        button.innerText = '🤍'; // 텍스트 변경
//        button.style.color = ''; // 텍스트 색상 초기화 (기본 색상으로 복귀)
    } else {
        // ♡ -> ♥ 상태로 변경
//        button.classList.remove('btn-outline-dark'); // 흰색 배경 제거
//        button.classList.add('btn-danger'); // 'btn-danger' 클래스 추가
        button.innerText = '🩷'; // 텍스트 변경
//        button.style.color = 'red'; // '♥' 텍스트 색상을 빨간색으로 변경
    }
}


    // CSRF 토큰 헤더를 가져오는 함수
    function getCsrfTokenHeaders() {
        return {
            header: $('meta[name="_csrf_header"]').attr('content'),
            token: $('meta[name="_csrf"]').attr('content')
        };
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

function tradeComplete(boardNo, categoryNo) {
    $.ajax({
        url: `/board/complete/${boardNo}`,  // 가져온 no 값을 URL에 추가
        type: 'GET',
        success: async function (response) {
            if (response === 'success') {

                await updateTradeAchievement(categoryNo);

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

async function updateTradeAchievement(categoryNo) {
    switch (categoryNo) {
        case 1: // 건담
            await updateAchievement("FIRST_GUNDAM_TRADE");
            await updateAchievement("GUNDAM_PRO");
            break;
        case 2: // 레고
            await updateAchievement("FIRST_LEGO_TRADE");
            await updateAchievement("LEGO_PRO");
            break;
        case 3: // 신발
            await updateAchievement("FIRST_SHOES_TRADE");
            await updateAchievement("SHOE_PRO");
            break;
    }

    await updateAchievement("FIRST_TRADE");
    await updateAchievement("TEN_TRADE");
    await updateAchievement("THIRTY_TRADE");
}


function collectionLoadButton(no) {
    openCollectionViewModalFromBoard(no);
}
/*-----------------------------------------------------------------------*/


// boardList에만 쓰이므로 boardList.html로 이동

// document.addEventListener("DOMContentLoaded", () => {
//     const tbody = document.querySelector(".achievement-ranking-container tbody");
//     const rows = document.querySelectorAll(".achievement-ranking-container tbody tr");
//     let currentIndex = 0;
//     let animationTimer = null;

//     function showAllRows() {
//         rows.forEach(row => {
//             row.style.position = 'static';
//             row.style.opacity = '1';
//         });
//         tbody.style.height = 'auto';
//     }

//     function resetRows() {
//         rows.forEach(row => {
//             row.style.position = 'absolute';
//             row.style.opacity = '0';
//         });
//         tbody.style.height = '65px';
//         showNextRow();
//     }

//     function showNextRow() {
//         if (animationTimer) {
//             clearTimeout(animationTimer);
//         }

//         rows.forEach(row => {
//             row.style.opacity = '0';
//         });

//         rows[currentIndex].style.opacity = '1';

//         currentIndex = (currentIndex + 1) % rows.length;

//         animationTimer = setTimeout(showNextRow, 2500);
//     }

//     tbody.addEventListener('mouseenter', () => {
//         if (animationTimer) {
//             clearTimeout(animationTimer);
//             animationTimer = null;
//         }
//         showAllRows();
//     });

//     tbody.addEventListener('mouseleave', () => {
//         resetRows();
//     });

//     showNextRow();
// });

/* 게시글 등록 */

let imageUrls = [];

// Summernote 에디터 초기화 및 이미지 업로드 함수
$(document).ready(function () {
    $('#content').summernote({
        placeholder: '내용을 입력하세요 (최대 2048자)',
        height: 500,
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'italic', 'underline', 'strikethrough']],
            ['fontsize', ['fontsize']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['link', 'picture']],
            ['view', ['fullscreen', 'codeview', 'help']]
        ],
        callbacks: {
            onImageUpload: function (files) {
                // for (i = 0; i < files.length; i++) {
                //   uploadImage(files[i]);
                // }
                for (i = 0; i < files.length; i++) {
                    saveImages(files[i]);
                }
            },
            onPaste: function (e) {
                var clipboardData = e.originalEvent.clipboardData;
                if (clipboardData && clipboardData.items && clipboardData.items.length) {
                    var item = clipboardData.items[0];
                    if (item.kind === 'file') {
                        e.preventDefault();
                    }
                }
            }
        }
    });

    // 수집품 목록 불러오기
    loadCollections();

    // 수집품 선택 시 이미지 업데이트
    $('#collectionNo').change(function () {
        var filename = $(this).find(':selected').data('filename');
        if (filename) {
            $('#collectionImage').attr('src', 'https://kr.object.ncloudstorage.com/bitcamp-moum/board/' + filename);
        }
    });
});

function saveImages(file) {
    const reader = new FileReader();
    reader.onload = function (e) {
        const imageUrl = e.target.result;
        const fileName = file.name;

        imageUrls.push({ url: imageUrl, name: fileName });
        $('#content').summernote('insertImage', imageUrl);
    };
    reader.readAsDataURL(file);
}

// 이미지 업로드 함수
// function uploadImage(file) {
//   var data = new FormData();
//   data.append("file", file);

//   $.ajax({
//     url: '/board/uploadImage',
//     type: 'POST',
//     data: data,
//     contentType: false,
//     processData: false,
//     headers: {
//       [$('meta[name="csrf-header"]').attr('content')]: $('meta[name="csrf-token"]').attr('content')
//     },
//     success: function (imageUrl) {
//       $('#content').summernote('insertImage', imageUrl);
//     },
//     error: function () {
//       alert('이미지 업로드에 실패했습니다.');
//     }
//   });
// }

// 수집품 목록을 불러와 옵션에 추가
function loadCollections() {
    $.ajax({
        url: '/collection/list',
        method: 'GET',
        success: function (data) {
            var collectionSelect = $('#collectionNo');
            data.forEach(function (collection) {
                collectionSelect.append('<option value="' + collection.no + '">' + collection.name + '</option>');
            });
        },
        error: function () {
            alert('수집품 목록을 불러오는 데 실패했습니다.');
        }
    });
}

function updatePost() {
    var formData = new FormData($('#updateForm')[0]);
    var no = $('#boardNo').val();  // 숨겨진 input에서 no 값을 가져옴

    // 필수 입력 필드 확인
    if (formData.get("title") === "") { alert("제목을 입력하세요"); return; }
    if (formData.get("content") === "") { alert("내용을 입력하세요"); return; }
    if (formData.get("price") === "" && $('#boardType').val() === 'trade') { formData.set("price", 0); }
    if (formData.get("boardType") === "trade") {
        if (formData.get("tradeType") === 'default') { alert("거래 종류를 선택하세요"); return; }
        if (formData.get("tradeType") === 'sell' && (!formData.get('collection.no') || formData.get('collection.no') === '0')) {
            alert("수집품을 선택해주세요"); return;
        }
    }

    // Summernote 내용에서 이미지 존재 여부 확인
    var content = $('#content').summernote('code');
    var hasImage = $(content).find('img').length > 0;
    if (!hasImage) {
        alert("사진을 최소 1개 이상 본문에 넣어주세요");
        return;
    }

    formData.delete("files");
    const oldImages = [];
    const base64Images = [];
    $('.note-editable img').each(function () {
        const imageUrl = $(this).attr('src');
        if (imageUrl && imageUrl.startsWith('data:image')) {
            base64Images.push(imageUrl);
        } else if (imageUrl && imageUrl.startsWith('https://')) {
            oldImages.push(imageUrl);
        }
    });

    // base64 이미지를 파일 객체로 변환 후 formData에 추가
    base64Images.forEach((base64Image) => {
        const imageData = imageUrls.find(image => image.url === base64Image);
        const fileName = imageData ? imageData.name : 'image.png';

        const file = base64ToFile(base64Image, fileName);
        formData.append('files', file);
    });

    oldImages.forEach((ncpImage) => {
        const fileName = ncpImage.match(/board\/([^/]+)$/)[1];
        formData.append('oldFiles', fileName);
    });

    $.ajax({
        url: `/board/update/${no}`,  // 가져온 no 값을 URL에 추가
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        headers: {
            [$('meta[name="csrf-header"]').attr('content')]: $('meta[name="csrf-token"]').attr('content')
        },
        success: async function (response) {
            if (response === 'success') {
                alert('게시글이 수정되었습니다.');
                await updateAchievement("THIRTY_UPT_POST");
                await updateAchievement("TEN_UPT_POST");
                await updateAchievement("FIRST_UPT_POST");
                window.location.href = `/board/boardView?no=${no}`;
            } else {
                alert('게시글 수정에 실패했습니다.');
            }
        },
        error: function () {
            alert('서버 오류가 발생했습니다.');
        }
    });
}


function base64ToFile(base64Data, fileName) {
    const [metadata, base64String] = base64Data.split(',');
    const mimeType = metadata.match(/:(.*?);/)[1]; // MIME 타입 추출
    const byteCharacters = atob(base64String); // base64 디코딩
    const byteArrays = [];

    for (let offset = 0; offset < byteCharacters.length; offset += 1024) {
        const slice = byteCharacters.slice(offset, offset + 1024);
        const byteNumbers = new Array(slice.length);

        for (let i = 0; i < slice.length; i++) {
            byteNumbers[i] = slice.charCodeAt(i);
        }

        byteArrays.push(new Uint8Array(byteNumbers));
    }

    const blob = new Blob(byteArrays, { type: mimeType });
    return new File([blob], fileName, { type: mimeType });
}

function validatePhoto() {
    const photos = document.querySelector(".card-block").childNodes[1].children;
    for (i = 0; i < photos.length; i++) {
        const filename = photos[i].src.substring(photos[i].src.lastIndexOf("/") + 1);
        console.log(filename);
    }
}

/* 게시글 화면 */

let boardId = /*[[${board.no}]]*/[];
let comments = /*[[${comments}]]*/[];
const isTrade = /*[[${board.boardType == "trade" ? true : false}]]*/[];

// 수집품 조회화면 사진 인덱스
let collectionSlideIndex = 0;

// comments.forEach(renderComment);

async function deletePostWithDelAchieve() {

    if (confirm("삭제하시겠습니까?")) {
        await updateAchievement("THIRTY_DEL_POST");
        await updateAchievement("TEN_DEL_POST");
        await updateAchievement("FIRST_DEL_POST");
        document.postDeleteForm.submit();
        alert("삭제되었습니다");
    }
}




