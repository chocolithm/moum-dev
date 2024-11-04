// 수집품 등록 화면 열기
function openCollectionFormModal() {
    fetchCollectionForm();
    openOverlay();
    fadeIn(document.querySelector(".collection-form-layer"));
}

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

    fetch(`/collection/boardPostForm`, {
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

// 게시글 종류 선택에 따른 UI 변경
document.querySelectorAll("input[name='boardType']").forEach((elem) => {
    elem.addEventListener("change", function () {
        const collectionSection = document.getElementById("collectionSection");
        if (this.value === "trade") {
            collectionSection.style.display = "block";
        } else {
            collectionSection.style.display = "none";
        }
    });
});

// 수집품 선택 화면 열기
function openCollectionSelection() {
    // 수집품 선택 화면을 모달로 띄우거나, 팝업으로 구현합니다.
    // 여기서는 간단히 수집품 목록을 가져와 표시하는 것으로 예시를 들겠습니다.

    fetch(`/collection/list`, {
        method: "GET"
    })
        .then(response => response.json())
        .then(data => {
            // 수집품 목록을 표시하고 선택할 수 있도록 구현
            // 예를 들어, 수집품 목록을 모달로 띄워 선택 시 `selectedCollection`에 표시

            let collectionListHtml = '<ul>';
            data.forEach(collection => {
                collectionListHtml += `<li onclick="selectCollection(${collection.no}, '${collection.name}')">${collection.name}</li>`;
            });
            collectionListHtml += '</ul>';

            // 모달 창에 수집품 목록을 표시
            document.querySelector('#collectionSelectionModal .modal-body').innerHTML = collectionListHtml;
            // 모달 창 열기
            openModal('#collectionSelectionModal');
        })
        .catch(error => {
            console.error("Error fetching collections:", error);
        });
}

// 수집품 선택 시 호출되는 함수
function selectCollection(no, name) {
    // 선택된 수집품 정보를 표시
    const selectedDiv = document.querySelector("#selectedCollection");
    selectedDiv.dataset.collectionNo = no;
    selectedDiv.textContent = `선택된 수집품: ${name}`;

    // 모달 창 닫기
    closeModal('#collectionSelectionModal');
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

        // 선택된 수집품 정보가 있다면 추가
        const selectedCollectionNo = document.querySelector("#selectedCollection").dataset.collectionNo;
        if (selectedCollectionNo) {
            formData.append("collection.no", selectedCollectionNo);
        }

        // 파일 업로드 처리
        const filesInput = document.querySelector("#postForm #files");
        for (let i = 0; i < filesInput.files.length; i++) {
            formData.append("files", filesInput.files[i]);
        }

        fetch(`/collection/addPost`, {
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

/*----------------------------------------------*/
// 수집품 등록 화면 내용 가져오기
function fetchCollectionForm() {

    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute("content");

    initCollectionSlideIndex();

    fetch(`/collection/form`, {
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

            document.querySelector('.collection-form-layer').innerHTML =
                doc.querySelector('.collection-form-layer').innerHTML;

        })
        .catch(error => {
            console.error("Error fetching collection form:", error);
        });
}
// 수집품 등록 처리
function addCollection() {
    if (confirm("등록하시겠습니까?")) {
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        const formData = new FormData();
        formData.append("name", document.querySelector("#addForm #name").value.trim());
        formData.append("enName", document.querySelector("#addForm #enName").value.trim());
        formData.append("price", document.querySelector("#addForm #price").value);
        formData.append("maincategory.no", document.querySelector("#addForm #maincategoryNo").value);
        formData.append("subcategory.no", document.querySelector("#addForm #subcategoryNo").value);
        formData.append("purchasePlace", document.querySelector("#addForm #purchasePlace").value.trim());
        formData.append("storageLocation", document.querySelector("#addForm #storageLocation").value.trim());
        formData.append("status.no", document.querySelector("#addForm #statusNo").value);

        if (validateData(formData)) {
            const filesInput = document.querySelector("#addForm #files");
            for (let i = 0; i < filesInput.files.length; i++) {
                formData.append("files", filesInput.files[i]);
            }

            fetch(`/collection/add`, {
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
                            location.href = "/home";
                            break;
                        case "success":
                            alert("등록했습니다.");
                            location.href = "/home";
                            break;
                        case "failure":
                            alert("등록에 실패했습니다.");
                            break;
                    }
                })
                .catch(error => {
                    console.error("Error fetching collection add:", error);
                });
        }
    }
}

// 수집품 조회 화면 열기
function openCollectionViewModal(no) {
    fetchCollectionView(no);
    openOverlay();
    fadeIn(document.getElementsByClassName("collection-view-layer")[0]);
}

// 수집품 조회 화면 내용 가져오기
function fetchCollectionView(no) {

    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute("content");

    initCollectionSlideIndex();

    fetch(`/collection/view?no=${no}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');

            document.querySelector('.collection-view-layer').innerHTML =
                doc.querySelector('.collection-view-layer').innerHTML;

            document.addEventListener("DOMContentLoaded", function () {
                showSlides(collectionSlideIndex);
            });
        })
        .catch(error => {
            console.error("Error fetching collection view:", error);
        });

    console.log("collectionSlideIndex: " + collectionSlideIndex);
}


// 수집품 수정 처리
function updateCollection() {

    if (confirm("수정하시겠습니까?")) {
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        const formData = new FormData();
        formData.append("no", document.querySelector("#updateForm #no").value);
        formData.append("name", document.querySelector("#updateForm #name").value.trim());
        formData.append("enName", document.querySelector("#updateForm #enName").value.trim());
        formData.append("price", document.querySelector("#updateForm #price").value);
        formData.append("maincategory.no", document.querySelector("#updateForm #maincategoryNo").value);
        formData.append("subcategory.no", document.querySelector("#updateForm #subcategoryNo").value);
        formData.append("purchasePlace", document.querySelector("#updateForm #purchasePlace").value.trim());
        formData.append("storageLocation", document.querySelector("#updateForm #storageLocation").value.trim());
        formData.append("status.no", document.querySelector("#updateForm #statusNo").value);

        if (validateData(formData)) {
            const filesInput = document.querySelector("#updateForm #files");
            for (let i = 0; i < filesInput.files.length; i++) {
                formData.append("files", filesInput.files[i]);
            }

            fetch(`/collection/update`, {
                method: "PUT",
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
                            location.href = "/home";
                            break;
                        case "success":
                            alert("수정했습니다.");
                            break;
                        case "failure":
                            alert("수정에 실패했습니다.");
                            break;
                    }
                })
                .catch(error => {
                    console.error("Error fetching collection update:", error);
                });
        }
    }
}

function validateData(formData) {
    if (formData.get("name") == "") { alert("이름을 입력해주세요."); return false; }
    if (formData.get("maincategory.no") == "" || formData.get("maincategory.no") == 0) { alert("대분류를 선택해주세요."); return false; }
    if (formData.get("subcategory.no") == "" || formData.get("subcategory.no") == 0) { alert("소분류를 선택해주세요."); return false; }
    if (formData.get("storageLocation") == "") { alert("보관장소를 입력해주세요."); return false; }
    if (formData.get("status.no") == "" || formData.get("status.no") == 0) { alert("상태를 선택해주세요."); return false; }
    return true;
}


// 수집품 삭제 처리
function deleteCollection(collectionNo) {

    if (confirm("삭제하시겠습니까?")) {
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        fetch(`/collection/delete?no=${collectionNo}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken
            }
        })
            .then(response => response.text())
            .then(response => {
                switch (response) {
                    case "login":
                        alert("로그인이 필요합니다.");
                        location.href = "/home";
                        break;
                    case "success":
                        alert("삭제했습니다.");
                        location.href = "/home";
                        break;
                    case "failure":
                        alert("삭제에 실패했습니다.");
                        break;
                }
            })
            .catch(error => {
                console.error("Error fetching collection delete:", error);
            });
    }
}


// 수집품 첨부파일 삭제 처리
function deleteFile(fileNo, collectionNo) {

    if (confirm("사진을 삭제하시겠습니까?")) {
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        fetch(`/collection/deleteFile?no=${fileNo}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken
            }
        })
            .then(response => response.text())
            .then(response => {
                switch (response) {
                    case "login":
                        alert("로그인이 필요합니다.");
                        location.href = "/home";
                        break;
                    case "success":
                        alert("삭제했습니다.");
                        fetchCollectionView(collectionNo);
                        break;
                    case "failure":
                        alert("삭제에 실패했습니다.");
                        break;
                }
            })
            .catch(error => {
                console.error("Error fetching collection delete:", error);
            });
    }
}


// 사진 인덱스 초기화
function initCollectionSlideIndex() {
    collectionSlideIndex = 0;
}

// 슬라이드 display 처리
function showSlides(index) {
    const slides = document.querySelectorAll('.slide');
    if (index >= slides.length) { collectionSlideIndex = 0; }
    if (index < 0) { collectionSlideIndex = slides.length - 1; }

    slides.forEach((slide, i) => {
        slide.style.display = (i === collectionSlideIndex) ? 'block' : 'none';
    });
}

// 사진 슬라이드 처리
function changeSlide(n) {
    showSlides(collectionSlideIndex += n);
    console.log("collectionSlideIndex: " + collectionSlideIndex);
}

// 소분류 데이터 조회
function fetchSubcategories(maincategoryNo) {
    const subcategorySelect = document.getElementById("subcategoryNo");
    subcategorySelect.innerHTML = "";

    if (maincategoryNo == 0) {
        subcategorySelect.disabled = true;
    } else if (maincategoryNo > 0) {
        subcategorySelect.disabled = false;
        fetch(`/subcategory/list?maincategoryNo=${maincategoryNo}`)
            .then(response => response.json())
            .then(data => {
                data.forEach((subcategory, index) => {
                    const option = document.createElement("option");
                    option.value = subcategory.no;
                    option.text = subcategory.name;
                    if (index == 0) {
                        option.selected = true;
                    }
                    subcategorySelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error fetching subcategories:", error);
            });
    }
}

// 선택한 이미지 미리보기
function previewImage(event) {
    const files = event.target.files;
    const empty = document.querySelector(".empty-image");
    const slider = document.querySelector(".slider");
    const slides = document.querySelector(".slides");
    const newImages = document.getElementsByClassName("new-image");
    const currentImages = document.getElementsByClassName("current-image");
    const filenames = document.getElementById("filenames");

    filenames.innerHTML = "";
    for (i = newImages.length - 1; i >= 0; i--) {
        newImages[i].remove();
    }

    if (files && files.length > 0) {

        for (let i = 0; i < files.length; i++) {
            const reader = new FileReader();

            reader.onload = function (e) {
                const img = document.createElement("img");
                img.src = e.target.result;
                img.alt = "Uploaded Image";
                img.className = "collection-image slide new-image";
                img.onclick = function () {
                    triggerFileInput();
                };
                slides.appendChild(img);
            }

            reader.readAsDataURL(files[i]);
        }

        if (empty) {
            empty.style.display = "none";
        }

        slider.style.display = "block";
        if (slides.children.length + files.length > 1) {
            document.querySelector(".prev").style.display = "block";
            document.querySelector(".next").style.display = "block";
        } else {
            document.querySelector(".prev").style.display = "none";
            document.querySelector(".next").style.display = "none";
        }

        Array.from(files).forEach(file => {
            const element = document.createElement("p");
            element.textContent = file.name;
            filenames.appendChild(element);
        });
    } else if (currentImages && currentImages.length > 0) {
        initCollectionSlideIndex();
        showSlides(collectionSlideIndex);
    } else {
        if (empty) {
            empty.style.display = "block";
        }
        slider.style.display = "none";
    }
}

// 사이드바 대분류 체크에 따른 필터링 처리
function filterCategories(element) {
    const items = document.getElementsByClassName(element.value);

    if (element.checked) {
        for (i = 0; i < items.length; i++) {
            fadeIn(items[i]);
        }
    } else {
        for (i = 0; i < items.length; i++) {
            fadeOut(items[i]);
        }
    }
}

function triggerFileInput() {
    document.getElementById('files').click();
}
