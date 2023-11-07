function disableOrMakeReadonlyAllInputs() {
  const inputs = document.getElementsByTagName("input");
  for (let i = 0; i < inputs.length; i++) {
    const input = inputs[i];
    if (input.type === "checkbox" || input.type === "radio") {
      input.disabled = true;
    } else {
      input.readOnly = true;
    }
  }
}

function handleAjaxErrorResponse(data) {
  if (data.responseText) {
    const errorResponse = JSON.parse(data.responseText);
    if (errorResponse.message) {
      alert(errorResponse.message);
    }
  } else {
    alert("처리 중 에러 발생");
    console.error(data);
  }
}
