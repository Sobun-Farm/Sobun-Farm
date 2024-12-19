package model.domain;

public class ItemGroup {
    private long itemId;

    public ItemGroup(long itemId) {
        this.itemId = itemId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
