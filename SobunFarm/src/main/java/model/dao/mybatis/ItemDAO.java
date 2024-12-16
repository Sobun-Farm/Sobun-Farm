package model.dao.mybatis;

import model.domain.Item;
import model.dao.mybatis.mapper.ItemMapper;
import org.apache.ibatis.session.SqlSession;
import model.utils.MyBatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDAO {

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
