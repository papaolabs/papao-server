package com.papaolabs.client.govdata.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "response")
@RequiredArgsConstructor
public class AnimalResponse {
    private Header header;
    private Body body;

    @Data
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    public static class Body {
        Items items;

        @Data
        public static class Items {
            List<AnimalItem> item;

            @Data
            public static class AnimalItem {
                private String noticeEdt;
                private String popfile;
                private String processState;
                private String sexCd;
                private String neuterYn;
                private String specialMark;
                private String careNm;
                private String careTel;
                private String careAddr;
                private String orgNm;
                private String chargeNm;
                private String officetel;
                private String noticeComment;
                private String numOfRows;
                private String pageNo;
                private String totalCount;
                private String resultCode;
                private String resultMsg;
                private String desertionNo;
                private String filename;
                private String happenDt;
                private String happenPlace;
                private String kindCd;
                private String colorCd;
                private String age;
                private String weight;
                private String noticeNo;
                private String noticeSdt;
            }
        }
    }
}
