package model.dao.mybatis.mapper;

import model.domain.Item;
import java.util.List;
import java.util.Map;

public interface ItemMapper {
	// 전체 물품 수 조회
    int getTotalItemCount(Map<String, Object> params);

    // 페이지별 물품 데이터 조회
    List<Item> getItemsByPage(Map<String, Object> params);
    
    //db에 물품 추가
    void insertItem(Item item);
}
