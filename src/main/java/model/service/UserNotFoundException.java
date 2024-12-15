package model.service;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message); // 부모 클래스(Exception)의 생성자를 호출
    }
}
