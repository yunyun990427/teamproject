package org.spring.domain.community;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import lombok.Data;

@Data
public class CommunityBoardDTO {
    private Integer community_bno;
    private String community_title;
    private String community_content;
    private String community_filename;
    private Timestamp community_regdate;
    private Integer community_viewcnt;
    private Integer user_num;
    private String writer;
    private String region;

    public CommunityBoardDTO() {}
    
    public CommunityBoardDTO(int community_bno, String community_title, String community_content,
            String community_filename, Timestamp community_regdate, int community_viewcnt, Integer user_num,String writer) {
        super();
        this.community_bno = community_bno;
        this.community_title = community_title;
        this.community_content = community_content;
        this.community_filename = community_filename;
        this.community_regdate = community_regdate;
        this.community_viewcnt = community_viewcnt;
        this.user_num = user_num;
        this.writer = writer;
    }

    public CommunityBoardDTO(int community_bno, String community_title, String community_content,
            String community_filename, Integer user_num, String writer) {
        super();
        this.community_bno = community_bno;
        this.community_title = community_title;
        this.community_content = community_content;
        this.community_filename = community_filename;
        this.user_num = user_num;
        this.writer = writer;
    }

    public CommunityBoardDTO(String community_title, String community_content, String community_filename) {
        super();
        this.community_title = community_title;
        this.community_content = community_content;
        this.community_filename = community_filename;
    }
    
    // Method to get formatted date
    public String getFormattedRegdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(this.community_regdate);
    }
}
