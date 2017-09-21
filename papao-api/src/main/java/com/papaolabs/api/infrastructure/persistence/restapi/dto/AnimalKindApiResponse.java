package com.papaolabs.api.infrastructure.persistence.restapi.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "response")
@RequiredArgsConstructor
public class AnimalKindApiResponse {
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
            List<KindItemDTO> item;

            @Data
            public static class KindItemDTO {
                private String kindCd;
                private String kNm;
            }
        }
    }
}
