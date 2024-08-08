package org.spring.service;

import java.util.HashMap;
import java.util.List;

import org.spring.domain.BookmarkDTO;
import org.spring.domain.policy.*;
import org.spring.model.PolicyResponse;
import org.spring.model.PolicyResponse2;
import org.spring.persistence.PolicyBoardMapper;
import org.spring.utils.RestApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyBoardService {
	
	@Autowired
    private PolicyBoardMapper boardMapper;
	private static final String SECRETKEY = "i3+6fMkkv8XL1CB7x0ZgBb5lNltDkXgPED80oyl9ur6fi8aaDnXR8nvU2U3TMzWDho62+4xfDaBh9odVCGIyiA==";

	public PolicyResponse listAll(Criteria cri) {
		HashMap<String, String> data = new HashMap<>();
		data.put("serviceKey", SECRETKEY);
		System.out.println("PolicyService (pageNum, amount): ("+cri.getPageNum()+", "+cri.getAmount()+")");
		
		data.put("page", Integer.toString(cri.getPageNum()));
		data.put("perPage", Integer.toString(cri.getAmount()));
		data.put("returnType", "JSON");

		String url = "https://api.odcloud.kr/api/gov24/v3/serviceList";
		HashMap<String, String> headerData = new HashMap<String, String>();
		headerData.put("Authorization", SECRETKEY);
		headerData.put("Content-type", "application/json");

		PolicyResponse result = RestApiUtil.ConnHttpGetType(url, data, headerData, PolicyResponse.class, false);
		return result;
	}
	
	public PolicyResponse getList(Criteria cri) {
		HashMap<String, String> data = new HashMap<>();
		data.put("serviceKey", SECRETKEY);
		
		data.put("page", Integer.toString(cri.getPageNum()));
		data.put("perPage", Integer.toString(cri.getAmount()));

		String districtValue = cri.getDistrict();
		if (districtValue != null && !districtValue.isEmpty()) {
			System.out.println("cond 소관기관: "+districtValue);
		    data.put("cond[소관기관명::LIKE]", (String) districtValue);
		}
		String typeValue = cri.getType();
		if (typeValue != null && !typeValue.isEmpty()) {
			System.out.println("cond 서비스분야: "+typeValue);
		    data.put("cond[서비스분야::EQ]", (String) typeValue);
		}
		String keyword = cri.getKeyword();
		if (keyword != null && !keyword.isEmpty()) {
			System.out.println("cond 지원내용: "+keyword);
			data.put("cond[서비스명::LIKE]", (String) keyword);
		}
		data.put("returnType", "JSON");

		String url = "https://api.odcloud.kr/api/gov24/v3/serviceList";
		HashMap<String, String> headerData = new HashMap<String, String>();
		headerData.put("Authorization", SECRETKEY);
		headerData.put("Content-type", "application/json");

		PolicyResponse result = RestApiUtil.ConnHttpGetType(url, data, headerData, PolicyResponse.class, false);
		return result;
	}
	
	public PolicyResponse2 get(String serviceID) {
		System.out.println("상세페이지 서비스ID: "+serviceID);
		HashMap<String, String> data = new HashMap<>();
		data.put("serviceKey", SECRETKEY);
		data.put("returnType", "JSON");
		data.put("cond[서비스ID::EQ]", serviceID);

		String url = "https://api.odcloud.kr/api/gov24/v3/serviceDetail";
		HashMap<String, String> headerData = new HashMap<String, String>();
		headerData.put("Authorization", SECRETKEY);
		headerData.put("Content-type", "application/json");

		PolicyResponse2 result = RestApiUtil.ConnHttpGetType(url, data, headerData, PolicyResponse2.class, false);
		return result;
	}

    // 북마크 추가 메서드
    public void bookmark(PolicyBookmarkDTO dto) {
        boardMapper.bookmark(dto);
    }

    // 북마크 삭제 메서드
    public void bookmarkDel(PolicyBookmarkDTO dto) {
        boardMapper.bookmarkDel(dto);
    }

    // 북마크 여부 확인 메서드
    public boolean bookmarkChk(String serviceID, int userNum) {
        return boardMapper.bookmarkChk(serviceID, userNum) > 0;
    }

	public List<BookmarkDTO> getUserBookmarks(int user_num) {
		
		return boardMapper.getUserBookmarks(user_num);
	}
}