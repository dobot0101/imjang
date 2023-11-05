$("#saveButton").click(function () {
  event.preventDefault();

  const formDataArray = $("#buildingCreateForm").serializeArray();
  const data = {};
  for (const { name, value } of formDataArray) {
    data[name] = value;
  }

  const schoolTypes = [];
  document
    .querySelectorAll("input[type=checkbox][name=schoolTypes]:checked")
    .forEach((node) => {
      schoolTypes.push(node.value);
    });
  if (schoolTypes.length > 0) {
    data["schoolTypes"] = schoolTypes;
  }

  const facilityTypes = [];
  document
    .querySelectorAll("input[type=checkbox][name=facilityTypes]:checked")
    .forEach((node) => {
      facilityTypes.push(node.value);
    });
  if (facilityTypes.length > 0) {
    data["facilityTypes"] = facilityTypes;
  }

  const transportationTypes = [];
  document
    .querySelectorAll("input[type=checkbox][name=transportationTypes]:checked")
    .forEach((node) => {
      transportationTypes.push(node.value);
    });
  if (transportationTypes.length > 0) {
    data["transportationTypes"] = transportationTypes;
  }

  $.ajax({
    type: "POST",
    url: "/api/buildings",
    contentType: "application/json; charset=UTF-8",
    dataType: "json",
    data: JSON.stringify(data),
    success: function (response) {
      window.location.href = `/buildings/read/${response.savedBuildingId}`;
    },
    error: function (data) {
      console.log("저장 실패", data);
    },
  });
});

$("#cancelButton").click(function () {
  event.preventDefault();
  window.location.href = "/";
});
