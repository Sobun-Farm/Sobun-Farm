package model.service;

import model.dao.mybatis.ItemDAO;
import model.domain.Item;

public class ItemManager {
    private final ItemDAO itemDAO;

    // Singleton 패턴 (필요 시)
    private static final ItemManager instance = new ItemManager();

    private ItemManager() {
        this.itemDAO = new ItemDAO(); // DAO 초기화
    }

    public static ItemManager getInstance() {
        return instance;
    }

    // 아이템 등록 메서드
    public void registerItem(Item item) {
        try {
            itemDAO.insertItem(item);
        } catch (Exception e) {
            throw new RuntimeException("아이템 등록 중 오류 발생", e);
        }
    }

    // 기타 비즈니스 로직 추가 가능
}
