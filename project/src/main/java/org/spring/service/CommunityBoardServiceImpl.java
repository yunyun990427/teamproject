package org.spring.service;

import java.util.List;

import org.spring.domain.community.CommunityBoardDTO;
import org.spring.domain.community.CommunityCommentDTO;
import org.spring.domain.community.Criteria;
import org.spring.domain.community.ViewCountDTO;
import org.spring.persistence.CommunityBoardMapper;
import org.spring.persistence.CommunityCommentMapper;
import org.spring.persistence.ViewCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommunityBoardServiceImpl implements CommunityBoardService {

	@Autowired
	private CommunityBoardMapper boardMapper;

	@Autowired
	private ViewCountMapper viewCountMapper;

	@Autowired
	private CommunityCommentMapper commentMapper;

	@Override
	public void register(CommunityBoardDTO board) {
		boardMapper.insertSelectKey(board);
	}

	@Override
	public CommunityBoardDTO get(Integer community_bno) {
		return boardMapper.read(community_bno);
	}

	@Override
	public boolean modify(CommunityBoardDTO board) {
		return boardMapper.update(board) == 1;
	}


	@Override
	public boolean remove(Integer community_bno) {
		return boardMapper.delete(community_bno) == 1;
	}

	@Override
	public void updateFileName(CommunityBoardDTO board) {
		boardMapper.updateFileName(board);
	}

	@Override
	public CommunityBoardDTO getBoardById(int community_bno) {
		return boardMapper.getBoardID(community_bno);
	}

	@Override
	public int getTotalCount(Criteria cri) {
		return boardMapper.getTotalCount(cri);
	}

	@Override
	public List<CommunityBoardDTO> getListWithPaging(Criteria cri) {
		return boardMapper.getListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return boardMapper.getTotalCount(cri);
	}

	@Override
	public Boolean checkViewCnt(ViewCountDTO viewCnt) {
		return viewCountMapper.checkViewCnt(viewCnt) > 0;
	}

	@Override
	public void insertViewCnt(ViewCountDTO viewCnt) {
		viewCountMapper.insertViewCnt(viewCnt);
	}

	@Override
	public void updateViewCnt(int community_bno) {
		viewCountMapper.updateViewCnt(community_bno);
	}

	@Override
	public void registerComment(CommunityCommentDTO comment) {
		commentMapper.insertComment(comment);

	}

	@Override
	public void modifyComment(CommunityCommentDTO comment) {
		commentMapper.updateComment(comment);

	}

	@Override
	public void removeComment(int community_cno) {
		commentMapper.deleteComment(community_cno);

	}

	@Override
	public CommunityCommentDTO getCommentById(int community_cno) {
		return commentMapper.getCommentById(community_cno);
	}

	@Override
	public List<CommunityCommentDTO> getCommentsByBoardId(int community_bno) { // 추가
		return commentMapper.getCommentsByBoardId(community_bno);
	}

	@Override
	public int getCno(Integer user_num) {
		return commentMapper.getCommentCno(user_num);
	}



}
