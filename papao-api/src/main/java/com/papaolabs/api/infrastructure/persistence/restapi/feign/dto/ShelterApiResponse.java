package com.papaolabs.api.infrastructure.persistence.restapi.feign.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "response")
@RequiredArgsConstructor
public class ShelterApiResponse {
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
            List<ShelterItemDTO> item = new ArrayList();

            @Data
            public static class ShelterItemDTO {
                private String careRegNo;
                private String careNm;
            }
        }
    }
}
