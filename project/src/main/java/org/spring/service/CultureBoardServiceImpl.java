package org.spring.service;

import java.util.List;
import java.util.Map;

import org.spring.domain.BookmarkDTO;
import org.spring.domain.culture.Criteria;
import org.spring.domain.culture.CultureBoardDTO;
import org.spring.persistence.CultureBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CultureBoardServiceImpl implements CultureBoardService {

	@Autowired
	private CultureBoardMapper cultureboardMapper;

	@Override
    public CultureBoardDTO getBoard(int culture_bno) {
        return cultureboardMapper.selectBoard(culture_bno); 
    }

	@Override
	public List<CultureBoardDTO> listPage(Criteria cri, String culture_area, String culture_classify) {
		return cultureboardMapper.selectPage(cri, culture_area, culture_classify);
	}

	@Override
	public int getTotalCount(Criteria cri) {
		return cultureboardMapper.getTotalCount(cri);
	}

	@Override
	public boolean bookmarkChk(int culture_bno, int userNum) {
		return cultureboardMapper.bookmarkChk(culture_bno, userNum) > 0;
	}
	@Override
	public void bookmark(int culture_bno, int userNum, String culture_classify, String culture_title, String culture_place) {
		cultureboardMapper.bookmark(culture_bno, userNum, culture_classify, culture_title, culture_place);
	}
	@Override
	public void bookmarkDel(int culture_bno, int userNum, String culture_classify, String culture_title) {
		cultureboardMapper.bookmarkDel(culture_bno, userNum, culture_classify, culture_title);
	}

	@Override
	public List<CultureBoardDTO> listAll(Criteria cri, String culture_area, String culture_classify) {
		System.out.println("service listAll ~~~" + cultureboardMapper.selectAll(cri, culture_area, culture_classify));
		return cultureboardMapper.selectAll(cri, culture_area, culture_classify);
	}

//	  @Override
    public List<BookmarkDTO> getBookmarkedPosts(int userNum) {
        System.out.println("Service: Fetching bookmarks for userNum: " + userNum);
        List<BookmarkDTO> bookmarks = cultureboardMapper.getBookmarkedPosts(userNum);
        System.out.println("Service: Retrieved bookmarks: " + bookmarks);
        return bookmarks;
    }
    public List<BookmarkDTO> getUserBookmarks(int userNum) {
		
		 return cultureboardMapper.getUserBookmarks(userNum);
	}
}