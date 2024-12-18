package model.domain;

import java.sql.Timestamp;

public class ChatRoom {
    private long chatId;
    private long itemId;
    private String itemName;   // 아이템 이름 추가
    private String lastMessage; // 마지막 메시지
    private Timestamp lastMessageTime; // 마지막 메시지 시간

    // Getter 및 Setter 추가
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Timestamp getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Timestamp lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
