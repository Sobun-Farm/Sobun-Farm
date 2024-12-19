package model.dao.mybatis;

import model.domain.ItemGroup;
import model.dao.mybatis.mapper.ItemGroupMapper;
import org.apache.ibatis.session.SqlSession;
import model.utils.MyBatisUtils;

public class ItemGroupDAO {

    // 아이템 그룹 등록
    public void insertItemGroup(ItemGroup itemGroup) {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            ItemGroupMapper mapper = session.getMapper(ItemGroupMapper.class);
            mapper.insertItemGroup(itemGroup);
            session.commit();
        } catch (Exception e) {
            throw new RuntimeException("ItemGroup 등록 중 오류 발생", e);
        }
    }
}
