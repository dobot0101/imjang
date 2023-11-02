function disableOrMakeReadonlyAllInputs() {
  const inputs = document.getElementsByTagName("input");
  inputs.forEach((input, idx) => {
    if (input.type === "checkbox" || input.type === "radio") {
      input.disabled = true;
    } else {
      input.readOnly = true;
    }
  });
}
