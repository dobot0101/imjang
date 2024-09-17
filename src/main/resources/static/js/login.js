$(document).ready(function () {
  // 로그인 폼에 테스트 계정 입력
  $("#email").val("tester@imjang.com");
  $("#password").val("test");

  // 회원가입 버튼 클릭 시 회원가입 페이지로 이동
  $("#signUpButton").click(function (event) {
    event.preventDefault();
    window.location.href = "/members/signup";
  });

  // 로그인 폼 제출 시 ajax로 로그인 요청
  $('#loginButton').click(function (event) {
    const form = $('#loginForm');
    $.ajax({
      type: form.attr('method'), // post
      url: form.attr('action'),
      data: form.serialize(),
      dataType: 'json',
      success: function (response) {
        // response로 받은 jwt를 Authorization header에 포함하여 다음 요청부터는 jwt를 포함하여 요청
        const jwt = response.jwt;
        localStorage.setItem('jwt', jwt);

        $.ajax({
          type: 'GET',
          url: '/api/auth/verify',
          dataType: 'text',
          beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + jwt);
          },
          success: function (response) {
            window.location.href = "/home";
          },
          error: function (response) {
            // JWT 검증 실패 시 로그인 페이지로 리다이렉트
            alert('Session expired or invalid token. Please login again.');
            window.location.href = '/login';
          }
        });
      },
      error: function (response) {
        console.log(response)
        alert('Login failed');
      }
    });
  });
});
