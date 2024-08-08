package org.spring.persistence;

import java.util.List;

import org.spring.domain.community.CommunityCommentDTO;

public interface CommunityCommentMapper {

	public void updateComment(CommunityCommentDTO comment);

 	public void insertComment(CommunityCommentDTO comment);

	public void deleteComment(int community_cno);

    public CommunityCommentDTO getCommentById(int community_cno);

	public List<CommunityCommentDTO> getCommentsByBoardId(int community_bno);

	public int getCommentCno(Integer user_num);
}
