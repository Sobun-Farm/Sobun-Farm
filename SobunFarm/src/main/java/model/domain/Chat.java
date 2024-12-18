package model.domain;

public class Chat {
    private long chatId;  // 채팅방 ID
    private long itemId;  // 해당 채팅방이 속한 물품 ID (Item과 연관됨)

    // 기본 생성자
    public Chat() {}

    //여기 생성자에 chatId 없앰. insert시 추가되는거라.(시퀀스)
    // 생성자
    public Chat(long itemId) {
        this.itemId = itemId;
    }

    // Getter 및 Setter
    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
