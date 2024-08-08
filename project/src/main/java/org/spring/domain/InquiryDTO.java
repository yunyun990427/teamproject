package org.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {
    private int inquiry_num;
    private int user_num;
    private String inquiry_purpose;
    private String inquiry_details;
    private Timestamp inquiry_regdate;
    private String inquiry_progress;
}
