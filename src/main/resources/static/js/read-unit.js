document.addEventListener("DOMContentLoaded", (event) => {
  disableOrMakeReadonlyAllInputs();
});

$("#cancelButton").click(function () {
  event.preventDefault();
  history.back();
});

$("#modifyButton").click(function () {
  event.preventDefault();
  const unitId = $("#unitId").val();
  window.location = `/units/${unitId}/edit`;
});

$("#deleteButton").click(function () {
  const unitId = $("#unitId").val();
  $.ajax({
    type: "DELETE",
    url: `/api/units/${unitId}`,
    success: function (data) {
      window.location.href = "/";
    },
    error: function (data) {
      console.log("세대정보 삭제 실패");
    },
  });
});
