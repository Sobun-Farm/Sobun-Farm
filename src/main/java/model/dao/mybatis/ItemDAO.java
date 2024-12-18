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
	private final String DB_URL = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb"; // Oracle DB URL
    private final String DB_USER = "dbp240201"; // DB 사용자명
    private final String DB_PASSWORD = "111135"; // DB 비밀번호

    public ItemDAO() {
        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC 드라이버 로드 실패", e);
        }
    }
    
    //참여중인 소분
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

            // ResultSet의 모든 행을 순회
            while (rs.next()) {
                Item item = new Item(
                    rs.getLong("ITEMID"),              // itemId
                    rs.getString("TITLE"),            // title
                    rs.getString("ITEMNAME"),         // itemName
                    rs.getDouble("PRICE"),            // price
                    rs.getInt("PARTICIPANTSCOUNT"),    // participantsCount
                    rs.getInt("MAXPARTICIPANT"),     // maxParticipant
                    rs.getBoolean("ISJOINABLE"),      // isJoinable
                    rs.getString("REGION"),           // region
                    rs.getString("CATEGORY"),         // category
                    rs.getString("DESCRIPTION"),      // description
                    rs.getDate("DEADLINE"),           // deadline
                    rs.getString("ITEMSTATUS"),       // itemStatus
                    rs.getLong("USERID"),            // userId
                    rs.getString("PURCHASELOCATION"),  // purchaseLocation
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


    
    //나의 소분
    public List<Item> findItemsByUserId() {
        String sql = "SELECT * FROM ITEM"; // OWNERID가 userId인 아이템 검색
        List<Item> items = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                    rs.getLong("ITEMID"),              // itemId
                    rs.getString("TITLE"),            // title
                    rs.getString("ITEMNAME"),         // itemName
                    rs.getDouble("PRICE"),            // price
                    rs.getInt("PARTICIPANTSCOUNT"),    // participantsCount
                    rs.getInt("MAXPARTICIPANT"),     // maxParticipant
                    rs.getBoolean("ISJOINABLE"),      // isJoinable
                    rs.getString("REGION"),           // region
                    rs.getString("CATEGORY"),         // category
                    rs.getString("DESCRIPTION"),      // description
                    rs.getDate("DEADLINE"),           // deadline
                    rs.getString("ITEMSTATUS"),       // itemStatus
                    rs.getLong("USERID"),            // userId
                    rs.getString("PURCHASELOCATION"),  // purchaseLocation
                    null
                );
                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException("아이템 검색 중 오류 발생", e);
        }

        return items;
    }
    
    /**
     * 특정 userId를 기준으로 아이템 리스트를 반환
     *
     * @param userId 검색할 사용자 ID
     * @return 아이템 리스트
     */
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
                    rs.getLong("ITEMID"),              // itemId
                    rs.getString("TITLE"),            // title
                    rs.getString("ITEMNAME"),         // itemName
                    rs.getDouble("PRICE"),            // price
                    rs.getInt("PARTICIPANTSCOUNT"),    // participantsCount
                    rs.getInt("MAXPARTICIPANT"),     // maxParticipant
                    rs.getBoolean("ISJOINABLE"),      // isJoinable
                    rs.getString("REGION"),           // region
                    rs.getString("CATEGORY"),         // category
                    rs.getString("DESCRIPTION"),      // description
                    rs.getDate("DEADLINE"),           // deadline
                    rs.getString("ITEMSTATUS"),       // itemStatus
                    rs.getLong("USERID"),            // userId
                    rs.getString("PURCHASELOCATION"),  // purchaseLocation
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

}
