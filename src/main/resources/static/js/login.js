$("#signUpButton").click(function () {
  event.preventDefault();
  window.location.href = "/members/signup";
});

$(document).ready(() => {
  $("#email").val("tester@imjang.com");
  $("#password").val("test");
});
