package com.papaolabs.client.govdata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "response")
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
        private Items items;

        @Data
        public static class Items {
            private List<RegionItem> item;

            @Data
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class RegionItem {
                private String uprCd;
                private String orgCd;
                private String orgdownNm;
            }
        }
    }
}
