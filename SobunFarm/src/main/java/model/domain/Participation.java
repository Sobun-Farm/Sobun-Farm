package model.domain;

public class Participation {
    private long participationId; // 시퀀스로 자동 생성되는 참여 ID
    private long itemId;
    private long userId;

    // 생성자: itemId와 userId만 필요
    public Participation(long itemId, long userId) {
        this.itemId = itemId;
        this.userId = userId;
    }

    public long getParticipationId() {
        return participationId;
    }

    public void setParticipationId(long participationId) {
        this.participationId = participationId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
