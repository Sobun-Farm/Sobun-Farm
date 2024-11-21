document.addEventListener("DOMContentLoaded", function () {
  const emailInput = document.getElementById("email");
  const emailCheckButton = document.getElementById("email-check");
  const nicknameInput = document.getElementById("nickname");
  const nicknameCheckButton = document.getElementById("nickname-check");
  const passwordInput = document.getElementById("password");
  const passwordConfirmInput = document.getElementById("password-confirm");
  const regionSelect = document.getElementById("region");
  const signupButton = document.getElementById("signup-button");
  const passwordError = document.getElementById("password-error");

  // 더미 데이터 시뮬레이션
  const dummyEmails = ["test@example.com", "user@example.com"];
  const dummyNicknames = ["testUser", "exampleUser"];

  // 이메일 중복 확인 함수 (더미 데이터)
  function checkEmailDuplicate() {
    const email = emailInput.value.trim();
    if (email === "") {
      alert("이메일을 입력해주세요.");
      return;
    }

    if (dummyEmails.includes(email)) {
      alert("이미 사용 중인 이메일입니다.");
    } else {
      alert("사용 가능한 이메일입니다.");
    }
  }

  // 닉네임 중복 확인 함수 (더미 데이터)
  function checkNicknameDuplicate() {
    const nickname = nicknameInput.value.trim();
    if (nickname === "") {
      alert("닉네임을 입력해주세요.");
      return;
    }

    if (dummyNicknames.includes(nickname)) {
      alert("이미 사용 중인 닉네임입니다.");
    } else {
      alert("사용 가능한 닉네임입니다.");
    }
  }

  // 모든 필드의 유효성을 실시간으로 검증하는 함수
  function validateForm() {
    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();
    const passwordConfirm = passwordConfirmInput.value.trim();
    const nickname = nicknameInput.value.trim();
    const region = regionSelect.value.trim();

    if (
      email !== "" &&
      password !== "" &&
      passwordConfirm !== "" &&
      nickname !== "" &&
      region !== ""
    ) {
      signupButton.disabled = false; // 버튼 활성화
    } else {
      signupButton.disabled = true; // 버튼 비활성화
    }
  }

  // 가입하기 버튼 클릭 시 추가 검증
  signupButton.addEventListener("click", function (event) {
    const password = passwordInput.value.trim();
    const passwordConfirm = passwordConfirmInput.value.trim();

    // 비밀번호 일치 여부 확인
    if (password !== passwordConfirm) {
      event.preventDefault(); // 폼 제출 방지
      passwordError.textContent = "비밀번호가 일치하지 않습니다.";
    } else {
      passwordError.textContent = ""; // 에러 메시지 초기화
    }
  });

  // 각 입력 필드에 이벤트 리스너 추가
  emailInput.addEventListener("input", validateForm);
  passwordInput.addEventListener("input", validateForm);
  passwordConfirmInput.addEventListener("input", validateForm);
  nicknameInput.addEventListener("input", validateForm);
  regionSelect.addEventListener("change", validateForm);

  // 중복 확인 버튼 클릭 이벤트
  emailCheckButton.addEventListener("click", checkEmailDuplicate);
  nicknameCheckButton.addEventListener("click", checkNicknameDuplicate);
});
