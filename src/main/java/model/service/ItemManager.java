package model.service;

import java.util.List;

import model.dao.mybatis.ItemDAO;
import model.dao.mybatis.UserDAO;
import model.domain.Item;
import model.domain.User;

public class ItemManager {
    private static ItemManager instance = new ItemManager();
    private UserDAO userDao;
    private ItemDAO itemDao;

    private ItemManager() {
        userDao = new UserDAO();
        itemDao = new ItemDAO();
    }

    public static ItemManager getInstance() {
        return instance;
    }

    //참여중인 소분
    public List<Item> findParticipationItemByUserId(Long userId) {
        return itemDao.findParticipationItemByUserId(userId);
    }
    
    public List<Item> findItemList(Long userId) {
        try {
            // ItemDAO에서 아이템 리스트 가져오기
            return itemDao.findItemsByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("아이템 리스트 검색 중 오류 발생: " + e.getMessage(), e);
        }
    }
    public List<Item> findItemList() {
        try {
            // ItemDAO에서 아이템 리스트 가져오기
            return itemDao.findItemsByUserId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("아이템 리스트 검색 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
