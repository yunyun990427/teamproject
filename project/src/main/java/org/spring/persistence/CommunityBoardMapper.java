package org.spring.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.spring.domain.community.CommunityBoardDTO;
import org.spring.domain.community.Criteria;

public interface CommunityBoardMapper {
	public void insertSelectKey(CommunityBoardDTO board);

	public CommunityBoardDTO read(Integer community_bno);

	public int delete(Integer community_bno);

	public int update(CommunityBoardDTO board);

	public List<CommunityBoardDTO> getListWithPaging(Criteria cri);

	public int getTotalCount(Criteria cri);
	
	public int getTotal(Criteria cri);

	public void updateFileName(CommunityBoardDTO board);
	
	@Select("SELECT * FROM user WHERE user_email = #{user_email} OR social_user_email = #{user_email}")
	int getUnum(String user_email);

	@Select("SELECT * FROM community_board WHERE community_bno = #{community_bno}")	
	public CommunityBoardDTO getBoardID(int community_bno);
	
}
