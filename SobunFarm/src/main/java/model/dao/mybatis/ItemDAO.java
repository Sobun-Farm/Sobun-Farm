package model.dao.mybatis;

import model.domain.Item;
import model.domain.Transaction;

import model.dao.mybatis.mapper.ItemMapper;
import org.apache.ibatis.session.SqlSession;
import model.utils.MyBatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDAO {
	//추가

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

   //아이템 insert
   public Long insertItem(Item item) {
	    try (SqlSession session = MyBatisUtils.getSqlSession()) {
	        ItemMapper mapper = session.getMapper(ItemMapper.class);
	        mapper.insertItem(item);
	        session.commit(); // 데이터 삽입 후 커밋
	        
	        return item.getItemId(); 
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Item 삽입 중 오류 발생", e);
	    }
	}
   //방금 insert한 itemId 가져오기
   public Long getItemId() {
	    try (SqlSession session = MyBatisUtils.getSqlSession()) {
	        return session.selectOne("model.dao.mybatis.mapper.ItemMapper.getItemId");
	    } catch (Exception e) {
	        throw new RuntimeException("Item ID 조회 중 오류 발생", e);
	    }
	}
   //itemId로 물품 정보들 가져오기 
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
}
