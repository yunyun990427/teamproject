package org.spring.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.spring.domain.BookmarkDTO;
import org.spring.domain.policy.Criteria;
import org.spring.domain.policy.PolicyBookmarkDTO;

public interface PolicyBoardMapper {

	// 전체 게시글 수 반환
	public int getTotalCount(Criteria cri);
		
	// 북마크 추가
    public void bookmark(PolicyBookmarkDTO dto);

    // 북마크 삭제
    public void bookmarkDel(PolicyBookmarkDTO dto);

    // 북마크 여부 확인

	public List<BookmarkDTO> getUserBookmarks(int user_num);

    public int bookmarkChk(@Param("serviceID") String serviceID, @Param("userNum") int userNum);
}
