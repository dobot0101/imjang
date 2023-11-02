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
