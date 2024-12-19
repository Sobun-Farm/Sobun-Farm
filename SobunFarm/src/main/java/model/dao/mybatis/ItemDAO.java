package model.dao.mybatis;

import model.domain.Item;
import model.dao.mybatis.mapper.ItemMapper;
import org.apache.ibatis.session.SqlSession;
import model.utils.MyBatisUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import java.util.Map;

public class ItemDAO {
	//지우 추가
	private final String DB_URL = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";
    private final String DB_USER = "dbp240201";
    private final String DB_PASSWORD = "111135";

    public ItemDAO() {
        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC 드라이버 로드 실패", e);
        }
    }
    
    // 참여중인 소분
    public List<Item> findParticipationItemByUserId(Long userId) {
        String sql = "SELECT I.* " +
                     "FROM ITEM I " +
                     "JOIN PARTICIPATION P ON I.ITEMID = P.ITEMID " +
                     "JOIN APPUSER U ON P.USERID = U.USERID " +
                     "WHERE U.USERID = ?";
        List<Item> items = new ArrayList<>();

        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                    rs.getLong("ITEMID"),
                    rs.getString("TITLE"),
                    rs.getString("ITEMNAME"),
                    rs.getDouble("PRICE"),
                    rs.getInt("MAXPARTICIPANT"),
                    rs.getBoolean("ISJOINABLE"),
                    rs.getString("REGION"),
                    rs.getString("CATEGORY"),
                    rs.getString("DESCRIPTION"),
                    rs.getDate("DEADLINE"),
                    rs.getString("ITEMSTATUS"),
                    rs.getLong("USERID"),
                    rs.getString("PURCHASELOCATION"),
                    null
                );
                items.add(item); // 리스트에 아이템 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("아이템 조회 중 오류 발생", e);
        }

        return items; // 모든 아이템 반환
    }

    // 나의 소분
    public List<Item> findItemsByUserId() {
        String sql = "SELECT * FROM ITEM"; // OWNERID가 userId인 아이템 검색
        List<Item> items = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                    rs.getLong("ITEMID"),
                    rs.getString("TITLE"),
                    rs.getString("ITEMNAME"),
                    rs.getDouble("PRICE"),
                    rs.getInt("MAXPARTICIPANT"),
                    rs.getBoolean("ISJOINABLE"),
                    rs.getString("REGION"),
                    rs.getString("CATEGORY"),
                    rs.getString("DESCRIPTION"),
                    rs.getDate("DEADLINE"),
                    rs.getString("ITEMSTATUS"),
                    rs.getLong("USERID"),
                    rs.getString("PURCHASELOCATION"),
                    null
                );
                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException("아이템 검색 중 오류 발생", e);
        }

        return items;
    }
    
    // 특정 userId를 기준으로 아이템 리스트를 반환
    public List<Item> findItemsByUserId(Long userId) {
        String sql = "SELECT * FROM ITEM WHERE USERID = ?"; // OWNERID가 userId인 아이템 검색
        List<Item> items = new ArrayList<>();

        try (
        	Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
        	stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                    rs.getLong("ITEMID"),
                    rs.getString("TITLE"),
                    rs.getString("ITEMNAME"),
                    rs.getDouble("PRICE"),
                    rs.getInt("MAXPARTICIPANT"),
                    rs.getBoolean("ISJOINABLE"),
                    rs.getString("REGION"),
                    rs.getString("CATEGORY"),
                    rs.getString("DESCRIPTION"),
                    rs.getDate("DEADLINE"),
                    rs.getString("ITEMSTATUS"),
                    rs.getLong("USERID"),
                    rs.getString("PURCHASELOCATION"),
                    null
                );
                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException("아이템 검색 중 오류 발생", e);
        }

        return items;
    }

   public int getTotalItemCount(String region, String category) {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            Map<String, Object> params = new HashMap<>();
            params.put("region", region);
            params.put("category", category == null ? "%" : category);
            return mapper.getTotalItemCount(params);
        }
    }

   public List<Item> getItemsByPage(String region, String category, int offset, int itemsPerPage) {
       try (SqlSession session = MyBatisUtils.getSqlSession()) {
           System.out.println("DAO 호출 - Region: " + region);
           System.out.println("DAO 호출 - Category: " + category);
           System.out.println("DAO 호출 - Offset: " + offset);
           System.out.println("DAO 호출 - ItemsPerPage: " + itemsPerPage);

           ItemMapper mapper = session.getMapper(ItemMapper.class);
           Map<String, Object> params = new HashMap<>();
           params.put("region", region);
           params.put("category", category == null ? "%" : category);
           params.put("offset", offset);
           params.put("itemsPerPage", itemsPerPage);
           List<Item> items = mapper.getItemsByPage(params);

           System.out.println("DAO 반환된 아이템 개수: " + items.size());
           return items;
       }
   }

   // 아이템 insert
   public Long insertItem(Item item) {
	    try (SqlSession session = MyBatisUtils.getSqlSession()) {
	        ItemMapper mapper = session.getMapper(ItemMapper.class);
	        mapper.insertItem(item);
	        session.commit();
	        
	        return item.getItemId(); 
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Item 삽입 중 오류 발생", e);
	    }
	}
   
   // 방금 insert한 itemId 가져오기
   public Long getItemId() {
	    try (SqlSession session = MyBatisUtils.getSqlSession()) {
	        return session.selectOne("model.dao.mybatis.mapper.ItemMapper.getItemId");
	    } catch (Exception e) {
	        throw new RuntimeException("Item ID 조회 중 오류 발생", e);
	    }
	}
   
   // itemId로 물품 정보들 가져오기 
   public Item getItemById(long itemId) {
       SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession();
       try {
           return session.selectOne("model.dao.mybatis.mapper.ItemMapper.getItemById", itemId);
       } finally {
           session.close();
       }
   }

   public void deleteItem(long itemId) {
	    try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
	        session.delete("model.dao.mybatis.mapper.ItemMapper.deleteItem", itemId);
	        session.commit();
	    }
	}
  
   public void incrementParticipantsCount(long itemId) {
	    try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
	        session.update("model.dao.mybatis.mapper.ItemMapper.incrementParticipantsCount", itemId);
	        session.commit();
	    }
	}
   
   // 진행소분수
   public int countMyItem(Long userId) {
      String sql = "SELECT COUNT(*) AS TOTAL FROM ITEM WHERE USERID = ?";
      int result = 0;
      
      try (
              Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
               PreparedStatement stmt = conn.prepareStatement(sql)) {
              
              stmt.setLong(1, userId);
               ResultSet rs = stmt.executeQuery();

               while (rs.next()) {
                   result = rs.getInt("TOTAL");
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
      
      return result;
   }

}
