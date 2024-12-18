package model.service;

import java.util.List;

import model.dao.mybatis.ItemDAO;
import model.dao.mybatis.UserDAO;
import model.domain.Item;
import model.domain.User;

public class ItemManager {
    private final ItemDAO itemDAO;
    private static ItemManager instance = new ItemManager();
    private UserDAO userDao;
//
//    // Singleton 패턴 (필요 시)
//    private static final ItemManager instance = new ItemManager();

    private ItemManager() {
        this.itemDAO = new ItemDAO(); // DAO 초기화
        this.userDao = new UserDAO();
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
    
    //참여중인 소분 
    public List<Item> findParticipationItemByUserId(Long userId) {
        return itemDAO.findParticipationItemByUserId(userId);
    }
    
    public List<Item> findItemList(Long userId) {
        try {
            // ItemDAO에서 아이템 리스트 가져오기
            return itemDAO.findItemsByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("아이템 리스트 검색 중 오류 발생: " + e.getMessage(), e);
        }
    }
    public List<Item> findItemList() {
        try {
            // ItemDAO에서 아이템 리스트 가져오기
            return itemDAO.findItemsByUserId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("아이템 리스트 검색 중 오류 발생: " + e.getMessage(), e);
        }
    }
    public int countMyItem(Long userId) {
        return itemDAO.countMyItem(userId);
 }

}
