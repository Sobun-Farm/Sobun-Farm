package model;

public class User {
    private String email;      // 이메일
    private String password;   // 비밀번호
    private String nickname;   // 닉네임
    private String region;     // 거주지역

    // 기본 생성자
    public User() {}

    // 모든 필드를 초기화하는 생성자
    public User(String email, String nickname, String password, String region) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.region = region;
    }

    // Getter 및 Setter 메서드
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    // 비밀번호 확인 메서드 (Optional)
    public boolean isPasswordMatch(String confirmPassword) {
        return this.password.equals(confirmPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
