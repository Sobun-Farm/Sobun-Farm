package model.domain;

import java.util.Date;

public class Item {
    private long itemId;
    private String title;
    private String itemName;
    private double price;
    private int participantsCount;
    private int maxParticipant;
    private boolean isJoinable;
    private String region;
    private String category;
    private String description;
    private Date deadline;
    private String itemStatus;
    private long userId;
    private String purchaseLocation;
    private byte[] photo;

    // 생성자
    public Item(long itemId, String title, String itemName, double price, int participantsCount,
                int maxParticipant, boolean isJoinable, String region, String category,
                String description, Date deadline, String itemStatus, long userId,
                String purchaseLocation, byte[] photo) {
        this.itemId = itemId;
        this.title = title;
        this.itemName = itemName;
        this.price = price;
        this.participantsCount = participantsCount;
        this.maxParticipant = maxParticipant;
        this.isJoinable = isJoinable;
        this.region = region;
        this.category = category;
        this.description = description;
        this.deadline = deadline;
        this.itemStatus = itemStatus;
        this.userId = userId;
        this.purchaseLocation = purchaseLocation;
        this.photo = photo;
    }

    // 기본 생성자
    public Item() {
    }

    // Getter 및 Setter
    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public boolean isJoinable() {
        return isJoinable;
    }

    public void setJoinable(boolean joinable) {
        isJoinable = joinable;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPurchaseLocation() {
        return purchaseLocation;
    }

    public void setPurchaseLocation(String purchaseLocation) {
        this.purchaseLocation = purchaseLocation;
    }
    
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}