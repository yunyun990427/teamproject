package org.spring.service;

import java.util.List;

import org.spring.domain.community.CommunityBoardDTO;
import org.spring.domain.community.CommunityCommentDTO;
import org.spring.domain.community.Criteria;
import org.spring.domain.community.ViewCountDTO;


public interface CommunityBoardService {
	public void register(CommunityBoardDTO board);

	public CommunityBoardDTO get(Integer community_bno);

	public boolean modify(CommunityBoardDTO board);

	public boolean remove(Integer community_bno);

	public int getTotal(Criteria cri);

	public void updateViewCnt(int community_bno);

	public List<CommunityBoardDTO> getListWithPaging(Criteria cri);

	public int getTotalCount(Criteria cri);

	public void updateFileName(CommunityBoardDTO board);

	public Boolean checkViewCnt(ViewCountDTO viewCnt);

	public void insertViewCnt(ViewCountDTO viewCnt);

	public void registerComment(CommunityCommentDTO comment);

	public void modifyComment(CommunityCommentDTO comment);

	public void removeComment(int community_cno);
	
	public CommunityCommentDTO getCommentById(int community_cno);
	
	public List<CommunityCommentDTO> getCommentsByBoardId(int community_bno);

	public CommunityBoardDTO getBoardById(int community_bno);

	public int getCno(Integer user_num);

	

}
