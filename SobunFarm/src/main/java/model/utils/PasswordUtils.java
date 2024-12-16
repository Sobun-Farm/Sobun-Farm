package model.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordUtils {
	// 비밀번호 해쉬화하기
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 암호화 실패", e);
        }
    }
    
    // 해쉬된 비밀번호 확인하기
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        String hashedInput = hashPassword(plainPassword); // 입력 비밀번호를 해시화
        return hashedInput.equals(hashedPassword); // 저장된 해시와 비교
    }
    
    
}
