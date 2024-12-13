package model.dao.mybatis.mapper;

import java.util.List;

import model.domain.Community;

public interface CommunityMapper {

	public int insertCommunity(Community comm);   
 
	public int updateCommunity(Community comm);
	
	public int updateChair(Community comm);

	public int deleteCommunity(int commId);

	public Community selectCommunityByPrimaryKey(int commId);
	
	public Community selectCommunityWithMembers(int commId);

	public List<Community> selectAllCommunities();
}
