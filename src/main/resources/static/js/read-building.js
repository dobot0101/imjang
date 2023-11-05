$(document).ready(() => {
  disableOrMakeReadonlyAllInputs();
});

$("#updateButton").click(() => {
  const id = $("#buildingId").val();
  window.location.href = `/buildings/modify/${id}`;
});

$("#moveToHomeButton").click(() => {
  window.location.href = "/";
});

$("#deleteButton").click(() => {
  const id = $("#buildingId").val();
  $.ajax({
    type: "DELETE",
    url: `/api/buildings/${id}`,
    success: function (data) {
      window.location.href = "/";
    },
    error: function (data) {
      console.log("건물정보 삭제 실패");
    },
  });
});

$("#moveToUnitCreateButton").click(() => {
  const buildingId = $("#buildingId").val();
  if (!buildingId) {
    return false;
  }
  window.location.href = "/units/new/" + buildingId;
});
