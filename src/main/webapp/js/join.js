document.addEventListener("DOMContentLoaded", function () {
  console.log("DOMContentLoaded 실행됨!"); // 페이지 로드 확인용 로그

  // DOM 요소 가져오기
  const emailInput = document.getElementById("email");
  const emailCheckButton = document.getElementById("email-check");
  const nicknameInput = document.getElementById("nickname");
  const nicknameCheckButton = document.getElementById("nickname-check");
  const passwordInput = document.getElementById("password");
  const passwordConfirmInput = document.getElementById("password-confirm");
  const regionSelect = document.getElementById("region");
  const signupButton = document.getElementById("signup-button");

  console.log("DOM 요소 로드 완료:", {
    emailInput,
    emailCheckButton,
    nicknameInput,
    nicknameCheckButton,
    passwordInput,
    passwordConfirmInput,
    regionSelect,
    signupButton,
  });

  // 이메일 중복 확인 함수
  async function checkEmailDuplicate() {
    console.log("이메일 중복 확인 함수 실행"); // 확인 로그
    const email = emailInput.value.trim();

    if (!email) {
      alert("이메일을 입력해주세요.");
      console.log("이메일이 입력되지 않았습니다."); // 로그 추가
      return;
    }

    try {
      console.log(
        "서버 요청 URL:",
        `/user?action=emailCheck&email=${encodeURIComponent(email)}`
      );
      const response = await fetch(
        `${window.location.origin}${window.location.pathname}?action=emailCheck&email=${encodeURIComponent(
          email
        )}`
      );

      const data = await response.json(); // JSON 파싱

      console.log("서버 응답 데이터:", data); // 서버 응답 로그 출력

      if (data.available) {
        alert("사용 가능한 이메일입니다.");
      } else {
        alert("이미 사용 중인 이메일입니다.");
      }
    } catch (error) {
      console.error("Error 발생:", error);
      alert("서버 오류가 발생했습니다.");
    }
  }

  // 닉네임 중복 확인 함수
  async function checkNicknameDuplicate() {
    console.log("닉네임 중복 확인 함수 실행"); // 함수 실행 로그
    const nickname = nicknameInput.value.trim();

    if (!nickname) {
      alert("닉네임을 입력해주세요.");
      console.log("닉네임이 입력되지 않았습니다."); // 로그 추가
      return;
    }

    try {
      console.log("서버로 닉네임 중복 확인 요청:", nickname);
      const response = await fetch(
        `${window.location.origin}${window.location.pathname}?action=nicknameCheck&nickname=${encodeURIComponent(
          nickname
        )}`
      );
      const data = await response.json();

      if (data.available) {
        console.log("사용 가능한 닉네임입니다.");
        alert("사용 가능한 닉네임입니다.");
      } else {
        console.log("이미 사용 중인 닉네임입니다.");
        alert("이미 사용 중인 닉네임입니다.");
      }
    } catch (error) {
      console.error("Error 발생:", error);
      alert("서버 오류가 발생했습니다.");
    }
  }

  // 비밀번호 확인 함수
  function validatePasswordOnSubmit(event) {
    console.log("비밀번호 확인 함수 실행"); // 함수 실행 로그
    const password = passwordInput.value.trim();
    const confirmPassword = passwordConfirmInput.value.trim();

    if (password !== confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      event.preventDefault(); // 폼 제출 중단
      return false;
    }
    return true;
  }

  // 필수 입력값 검증 함수
  function validateFields() {
    const emailFilled = emailInput.value.trim() !== "";
    const nicknameFilled = nicknameInput.value.trim() !== "";
    const passwordFilled = passwordInput.value.trim() !== "";
    const confirmPasswordFilled = passwordConfirmInput.value.trim() !== "";
    const regionSelected = regionSelect.value.trim() !== "";

    const isAllFilled =
      emailFilled &&
      nicknameFilled &&
      passwordFilled &&
      confirmPasswordFilled &&
      regionSelected;

    signupButton.disabled = !isAllFilled;
    console.log("입력값 확인:", isAllFilled ? "모든 필드 입력됨" : "필드 미입력 또는 지역 미선택");
  }

  // 이벤트 리스너 등록
  emailCheckButton.addEventListener("click", checkEmailDuplicate);
  nicknameCheckButton.addEventListener("click", checkNicknameDuplicate);

  emailInput.addEventListener("input", validateFields);
  nicknameInput.addEventListener("input", validateFields);
  passwordInput.addEventListener("input", validateFields);
  passwordConfirmInput.addEventListener("input", validateFields);
  regionSelect.addEventListener("change", validateFields);

  signupButton.addEventListener("click", validatePasswordOnSubmit);

  console.log("이벤트 리스너 등록 완료");
});