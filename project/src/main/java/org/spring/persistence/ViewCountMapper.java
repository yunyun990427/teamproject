package org.spring.persistence;


import org.apache.ibatis.annotations.Mapper;
import org.spring.domain.community.ViewCountDTO;

@Mapper
public interface ViewCountMapper {
	  public int checkViewCnt(ViewCountDTO viewCnt);
	  
	  public void insertViewCnt(ViewCountDTO viewCnt);
	  
	  public void updateViewCnt(int community_bno);
	
}
