// 수집품 등록 화면 열기
function openCollectionFormPopup() {
    fetchCollectionForm();
    openOverlay();
    fadeIn(document.querySelector(".collection-form-layer"));
}

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

    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

    const formData = new FormData();
    formData.append("name", document.querySelector("#addForm #name").value);
    formData.append("enName", document.querySelector("#addForm #enName").value);
    formData.append("price", document.querySelector("#addForm #price").value);
    formData.append("maincategory.no", document.querySelector("#addForm #maincategoryNo").value);
    formData.append("subcategory.no", document.querySelector("#addForm #subcategoryNo").value);
    formData.append("purchasePlace", document.querySelector("#addForm #purchasePlace").value);
    formData.append("storageLocation", document.querySelector("#addForm #storageLocation").value);
    formData.append("status.no", document.querySelector("#addForm #statusNo").value);

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





// 수집품 조회 화면 열기
function openCollectionViewPopup(no) {
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

    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

    const formData = new FormData();
    formData.append("no", document.querySelector("#updateForm #no").value);
    formData.append("name", document.querySelector("#updateForm #name").value);
    formData.append("enName", document.querySelector("#updateForm #enName").value);
    formData.append("price", document.querySelector("#updateForm #price").value);
    formData.append("maincategory.no", document.querySelector("#updateForm #maincategoryNo").value);
    formData.append("subcategory.no", document.querySelector("#updateForm #subcategoryNo").value);
    formData.append("purchasePlace", document.querySelector("#updateForm #purchasePlace").value);
    formData.append("storageLocation", document.querySelector("#updateForm #storageLocation").value);
    formData.append("status.no", document.querySelector("#updateForm #statusNo").value);

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


// 수집품 삭제 처리
function deleteCollection(collectionNo) {

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


// 수집품 첨부파일 삭제 처리
function deleteFile(fileNo, collectionNo) {

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
