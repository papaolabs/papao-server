package com.papaolabs.openapi.infrastructure.persistence.feign.govdata.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "response")
@RequiredArgsConstructor
public class RegionResponse {
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
            List<RegionItem> item;

            @Data
            public static class RegionItem {
                private String uprCd;
                private String orgCd;
                private String orgdownNm;
            }
        }
    }
}
