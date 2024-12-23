package model.domain;

public class User {
    private String email;
    private String password;
    private String nickname;
    private String region;
    private Long userId;
    
    private String textBox;
    
    public User() {}

    public User(Long userId, String email, String nickname, String password, String region) {
        this.userId = userId;
    	this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.region = region;
        this.textBox = null;
    }

    public Long getUserId() {
        return userId;
    }
    
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
    
    public String getTextBox() {
    	return textBox;
    }
    
    public void setTextBox(String textBox) {
    	this.textBox = textBox;
    }

    // 비밀번호 확인 메서드
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
