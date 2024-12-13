package model.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.dao.mybatis.mapper.CommunityMapper;
import model.domain.Community;

/**
 * 사용자 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * Community 테이블에서 커뮤니티 정보를 추가, 수정, 삭제, 검색 수행 
 */
public class CommunityDAO {
	private SqlSessionFactory sqlSessionFactory;
	
	public CommunityDAO() {
		String resource = "mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 커뮤니티 테이블에 새로운 행 생성 (PK 값은 Sequence를 이용하여 자동 생성)
	 */
	public Community create(Community comm) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(CommunityMapper.class).insertCommunity(comm);
			if (result > 0) {
				sqlSession.commit();
			} 
			return comm;
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 기존의 커뮤니티 정보를 수정
	 */
	public int update(Community comm) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(CommunityMapper.class).updateCommunity(comm);
			if (result > 0) {
				sqlSession.commit();
			} 
			return result;
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 커뮤니티의 회장을 변경  
	 */
	public int updateChair(Community comm) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(CommunityMapper.class).updateChair(comm);
			if (result > 0) {
				sqlSession.commit();
			} 
			return result;
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 주어진 ID에 해당하는 커뮤니티 정보를 삭제.
	 */
	public int remove(int commId) {		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(CommunityMapper.class).deleteCommunity(commId);
			if (result > 0) {
				sqlSession.commit();
			} 
			return result;	
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 주어진  ID에 해당하는 커뮤니티 정보를 데이터베이스에서 찾아 Community 도메인 클래스에 
	 * 저장하여 반환.
	 */
	public Community findCommunity(int commId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(CommunityMapper.class).selectCommunityByPrimaryKey(commId);			
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 주어진  ID에 해당하는 커뮤니티 정보를 데이터베이스에서 찾아 Community 도메인 클래스에 
	 * 저장하고, 동시에 그 커뮤니티에 속한 모든 회원들의 정보를 찾아 List<UserInfo> 에 저장하여 함께 반환함  
	 */
	public Community findCommunityWithMembers(int commId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(CommunityMapper.class).selectCommunityWithMembers(commId);			
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 전체 커뮤니티 정보를 검색하여 List에 저장 및 반환
	 */
	public List<Community> findCommunityList() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(CommunityMapper.class).selectAllCommunities();			
		} finally {
			sqlSession.close();
		}
	}
	
}
