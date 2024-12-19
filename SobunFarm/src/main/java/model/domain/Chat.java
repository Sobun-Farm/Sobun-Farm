package model.domain;

public class Chat {
    private Long chatId; // 채팅방 ID
    private Long itemId; // 아이템 ID
    private String itemName; // 아이템 이름 (Item 테이블에서 가져오기)
    private String itemImagePath; // 아이템 이미지 경로

    public Chat() {}

    public Chat(Long itemId) {
        this.itemId = itemId;
    }


    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemImagePath() {
        return itemImagePath;
    }

    public void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }
}
