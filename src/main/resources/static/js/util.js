// 이메일 형식을 검사하는 정규 표현식
const emailPattern = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;

// 이메일 유효성을 검사하는 함수
function isValidEmail(email) {
  if (email === null || email === undefined) {
    return false;
  }
  return emailPattern.test(email);
}

// 이름 유효성을 검사하는 함수
function isValidName(name) {
  if (name === null || name === undefined) {
    return false;
  }
  const length = name.length;
  return length >= 2 && length <= 20;
}

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
