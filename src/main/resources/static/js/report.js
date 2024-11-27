function openReport(type, no) {
    const report_layer = document.querySelector(".report-layer");
    openOverlay();
    fetchReportCategoriesAndContent(type, no);
    fadeInWithFlex(report_layer);
}

function fetchReportCategoriesAndContent(type, no) {
    const report_layer = document.querySelector(".report-layer");
    let url;

    if (type == "board") {
        url = location.pathname + location.search;
    } else if (type == "comment") {
        url = location.pathname + location.search + `#comment-${no}`;
    }

    fetch(`/report/listReportCategories`)
        .then(response => response.json())
        .then(categories => {

            let htmlContent = `
                <div>
                    <label for="report-category">신고유형</label>
                    <select name="report-category" id="report-category">
            `;


            categories.forEach(category => {
                htmlContent += `
                    <option value="${category.no}">${category.name}</option>
                `;
            });

            htmlContent += `
                    </select>
                </div>
                <div>
                    <textarea id="reportContent" placeholder="신고내용을 입력하세요."></textarea>
                </div>
                <div>
                    <button class="btn btn-warning report-button" onclick="report('${url}');">신고하기</button>
                </div>
            `;

            report_layer.innerHTML = htmlContent;

        });
}


function report(url) {

    Swal.fire({
        text: "신고하시겠습니까?",
        showCancelButton: true,
        confirmButtonText: "확인"
    }).then((result) => {
    
        if (result.isConfirmed) {
            if (validateReport()) {

                const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
                const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
                const formData = new FormData();
    
                formData.append("reportCategory.no", document.querySelector("#report-category").value);
                formData.append("reportContent",
                    `<a href="${url}" target="_blank">${document.querySelector("#reportContent").value.trim()}</a>`);
    
                fetch(`/report/add`, {
                    method: "POST",
                    body: formData,
                    headers: {
                        [csrfHeader]: csrfToken
                    }
                })
                    .then(response => response.text())
                    .then(response => {
                        switch (response) {
                            case "success":
                                Swal.fire({
                                    icon: "success",
                                    text: "신고되었습니다."
                                });
                                break;
                            case "failure":
                                Swal.fire({
                                    icon: "error",
                                    text: "알 수 없는 오류입니다."
                                });
                                break;
                        }
                        closeModal();
                    })
                    .catch(error => {
                        console.error("Error reporting:", error);
                    });
            }
        }
    });
}

function validateReport() {
    const report_category = document.querySelector("#report-category");
    const reportContent = document.querySelector("#reportContent");
    report_category.style = "border-color: #ccc";
    reportContent.style = "border-color: #ccc";

    if (report_category.value <= 0) {
        report_category.style = "border-color: red";
        return false;
    }
    if (reportContent.value.trim() == "") {
        reportContent.style = "border-color: red";
        return false;
    }
    return true;
}

function openRestrict(category, categoryNo) {
    const report_layer = document.querySelector(".report-layer");
    openOverlay();
    createRestrictPage(category, categoryNo);
    fadeInWithFlex(report_layer);
}

function createRestrictPage(category, categoryNo) {
    const report_layer = document.querySelector(".report-layer");
    const htmlContent = `
        <div>
            <textarea id="restrictContent" placeholder="안내사항 작성. 미입력시 기본사항"></textarea>
        </div>
        <div>
            <button class="btn btn-warning report-button" onclick="sendWarning('${category}', '${categoryNo}')">경고</button>
        </div>
        <div>
            <button class="btn btn-warning report-button" onclick="restrict('${category}', '${categoryNo}')", ">삭제</button>
        </div>
    `;

    report_layer.innerHTML = htmlContent;
}

function sendWarning(category, categoryNo) {
    const content = document.getElementById("restrictContent");

    if (category == "board") {
        if (content.value.trim() == "") {
            content.value = "게시글 신고가 접수되어 경고 처리되었습니다. 게시글 내용을 수정하시기 바랍니다.";
        }
    }

    fetch(`/alert/add?category=${category}Warning&categoryNo=${categoryNo}&content=${content.value}`)
        .then(response => response.text())
        .then(response => {
            if (response == "success") {
                Swal.fire({
                    icon: "success",
                    text: "처리되었습니다."
                });
                closeModal();
            }
        })
        .catch(error => {
            console.error("error sending warning: ", error);
        })
}

async function restrict(category, categoryNo) {
    const content = document.getElementById("restrictContent");

    if (category == "board") {
        if (content.value.trim() == "") {
            content.value = "게시글 신고가 접수되어 삭제 처리되었습니다.";
        }
        await fetch(`/board/get?no=${categoryNo}`)
            .then(response => response.json())
            .then(board => {
                content.value = `
                    %5B${board.title}%5D ${content.value}
                `;
            })
    }

    fetch(`/alert/add?category=${category}Restrict&categoryNo=${categoryNo}&content=${content.value}`)
        .then(response => response.text())
        .then(response => {
            if (response == "success") {
                fetch(`/report/restrict?category=${category}Restrict&categoryNo=${categoryNo}`)
                    .then(response => response.text())
                    .then(response => {
                        if (response == "success") {
                            Swal.fire({
                                icon: "success",
                                text: "처리되었습니다."
                            });
                            location.href="/board/boardList";
                        }
                    })
                    .catch(error => {
                        console.error("error executing restriction: ", error);
                    })
            }
        })
        .catch(error => {
            console.error("error sending alert: ", error);
        })

    
}