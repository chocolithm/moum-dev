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
    if (confirm("신고하시겠습니까?")) {
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        const formData = new FormData();
        formData.append("reportCategory.no", document.querySelector("#report-category").value);
        formData.append("reportContent",
            `<a href="${url}">${document.querySelector("#reportContent").value.trim()}</a>`);

        if (validateReport(formData)) {

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
                            alert("신고되었습니다.");
                            break;
                        case "failure":
                            alert("알 수 없는 오류입니다.");
                            break;
                    }
                    closeModal();
                })
                .catch(error => {
                    console.error("Error reporting:", error);
                });
        }
    }
}

function validateReport(formData) {
    if (formData.get("reportCategory.no") <= 0) {alert("신규유형을 다시 선택해주세요."); return false;}
    if (formData.get("reportContent") == "") {alert("내용을 입력해주세요."); return false;}
    return true;
}