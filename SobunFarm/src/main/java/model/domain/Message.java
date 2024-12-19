package model.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private long messageId; // 메시지 ID (DB에서 자동 생성)
    private long chatId; // 채팅방 ID
    private long userId; // 사용자 ID
    private String content; // 메시지 내용
    private LocalDateTime timestamp; // 메시지 전송 시간
    
    private String sender; // 메시지를 보낸 사람의 닉네임

    public Message() {}

    public Message(long chatId, long userId, String content, LocalDateTime timestamp) {
        this.chatId = chatId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public long getMessageId() {
        return messageId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    // 포맷된 timestamp 반환
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        return timestamp.format(formatter);
    }
}
