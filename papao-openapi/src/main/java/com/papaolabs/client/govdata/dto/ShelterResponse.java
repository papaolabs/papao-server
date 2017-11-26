package com.papaolabs.client.govdata.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "response")
@RequiredArgsConstructor
public class ShelterResponse {
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
            List<ShelterItem> item;

            @Data
            public static class ShelterItem {
                private String careRegNo;
                private String careNm;
            }
        }
    }
}
