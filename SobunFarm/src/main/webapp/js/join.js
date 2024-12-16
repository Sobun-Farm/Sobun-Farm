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

  // 이메일 중복 확인 함수
  async function checkEmailDuplicate() {
    const email = emailInput.value.trim();
    if (email === "") {
      emailError.textContent = "이메일을 입력해주세요.";
      return;
    }

    try {
      const response = await fetch(`/user?action=emailCheck&email=${encodeURIComponent(email)}`);
      const data = await response.json();

      if (data.available) {
        emailError.textContent = "사용 가능한 이메일입니다.";
        emailError.style.color = "green";
      } else {
        emailError.textContent = "이미 사용 중인 이메일입니다.";
        emailError.style.color = "red";
      }
    } catch (error) {
      console.error("Error:", error);
      emailError.textContent = "중복 확인 중 오류가 발생했습니다.";
      emailError.style.color = "red";
    }
  }

  // 닉네임 중복 확인 함수
	async function checkNicknameDuplicate() {
	  const nickname = nicknameInput.value.trim();
	  if (nickname === "") {
	    nicknameError.textContent = "닉네임을 입력해주세요.";
	    return;
	  }
	
	  try {
	    const response = await fetch(`/user?action=nicknameCheck&nickname=${encodeURIComponent(nickname)}`);
	    const data = await response.json();
	
	    if (data.available) {
	      nicknameError.textContent = "사용 가능한 닉네임입니다.";
	      nicknameError.style.color = "green";
	    } else {
	      nicknameError.textContent = "이미 사용 중인 닉네임입니다.";
	      nicknameError.style.color = "red";
	    }
	  } catch (error) {
	    console.error("Error:", error);
	    nicknameError.textContent = "중복 확인 중 오류가 발생했습니다.";
	    nicknameError.style.color = "red";
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
