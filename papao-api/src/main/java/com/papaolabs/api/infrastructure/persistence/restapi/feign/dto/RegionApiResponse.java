package com.papaolabs.api.infrastructure.persistence.restapi.feign.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "response")
@RequiredArgsConstructor
public class RegionApiResponse {
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
            List<RegionItemDTO> item = new ArrayList<>();

            @Data
            public static class RegionItemDTO {
                private String uprCd;
                private String orgCd;
                private String orgdownNm;
            }
        }
    }
}
