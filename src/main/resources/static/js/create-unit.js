let uploadedFileIds = [];
$("#uploadButton").click(function () {
  var fileInput = $("#fileInput")[0];
  var files = fileInput.files;

  if (files.length === 0) {
    $("#uploadStatus").text("파일을 선택해주세요.");
    return;
  }

  var formData = new FormData();
  formData.append("file", files[0]);

  $.ajax({
    url: "/api/upload",
    type: "POST",
    data: formData,
    dataType: "json",
    processData: false,
    contentType: false,
    success: function (response) {
      if (response.result) {
        const result = JSON.parse(response.result);
        uploadedFileIds.push(result.id);
        $("#uploadStatus").text("파일 업로드 성공.");

        const imageContainer = `<div class="image-container">
                            <img src="${result.fileUrl}">
                            <button class="btn-close img-del-btn" aria-label="Delete Image" data-id="${result.id}"></button>
                        </div>`;
        $("#uploadedImages").append(imageContainer);
      }
    },
    error: function () {
      $("#uploadStatus").text("파일 업로드 실패.");
    },
  });
});

$(document).on("click", ".img-del-btn", function () {
  event.preventDefault();
  event.stopPropagation();

  const uploadResultId = $(this).data("id");
  $(this).parent(".image-container").remove();
  uploadedFileIds = uploadedFileIds.filter(
    (fileId) => fileId !== uploadResultId
  );
});

$("#saveButton").click(function () {
  event.preventDefault();

  const buildingId = $("#buildingId").val();
  if (!buildingId) {
    alert("건물정보 아이디가 없습니다.");
    return false;
  }

  const formDataArr = $("#unitCreateForm").serializeArray();
  const data = {};

  for (const { name, value } of formDataArr) {
    data[name] = value;
  }

  if (uploadedFileIds.length > 0) {
    data["uploadedFileIds"] = uploadedFileIds;
  }

  $.ajax({
    type: "POST",
    url: "/api/units/new/" + buildingId,
    contentType: "application/json; charset=UTF-8",
    dataType: "json",
    data: JSON.stringify(data),
    success: function (response) {
      window.location.href = `/units/${response.savedUnitId}`;
    },
    error: function (data) {
      handleAjaxErrorResponse(data);
    },
  });
});

$("#cancelButton").click(function () {
  event.preventDefault();
  history.back();
});

