package com.papaolabs.openapi.interfaces.v1;

import com.papaolabs.openapi.domain.service.GovDataService;
import com.papaolabs.openapi.domain.service.GovDataServiceImpl;
import com.papaolabs.client.govdata.dto.AnimalResponse.Body.Items.AnimalItem;
import com.papaolabs.client.govdata.dto.KindResponse.Body.Items.KindItem;
import com.papaolabs.client.govdata.dto.RegionResponse.Body.Items.RegionItem;
import com.papaolabs.client.govdata.dto.ShelterResponse.Body.Items.ShelterItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/openapi/v1/govdata")
public class V1GovDataController {
    @NotNull
    private final GovDataService service;

    public V1GovDataController(GovDataServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/animals")
    public List<AnimalItem> readAnimalList(@RequestParam(defaultValue = "", required = false) String beginDate,
                                           @RequestParam(defaultValue = "", required = false) String endDate,
                                           @RequestParam(defaultValue = "", required = false) String speciesCode,
                                           @RequestParam(defaultValue = "", required = false) String kindCode,
                                           @RequestParam(defaultValue = "", required = false) String sidoCode,
                                           @RequestParam(defaultValue = "", required = false) String gunguCode,
                                           @RequestParam(defaultValue = "", required = false) String shelterCode,
                                           @RequestParam(defaultValue = "", required = false) String state,
                                           @RequestParam(defaultValue = "1", required = false) String index,
                                           @RequestParam(defaultValue = "100000", required = false) String size) {
        return this.service.readAnimalItems(beginDate,
                                            endDate,
                                            speciesCode,
                                            kindCode,
                                            sidoCode,
                                            gunguCode,
                                            shelterCode,
                                            state,
                                            index,
                                            size);
    }

    @GetMapping("/kinds")
    public List<KindItem> readKindList(@RequestParam(defaultValue = "", required = false) String speciesCode) {
        return this.service.readKindItems(speciesCode);
    }

    @GetMapping("/sidos")
    public List<RegionItem> readSidoList() {
        return this.service.readSidoItems();
    }

    @GetMapping("/gungus")
    public List<RegionItem> readGungList(@RequestParam(defaultValue = "", required = false) String sidoCode) {
        return this.service.readGunguItems(sidoCode);
    }

    @GetMapping("/shelters")
    public List<ShelterItem> readShelterList(@RequestParam(defaultValue = "", required = false) String sidoCode,
                                             @RequestParam(defaultValue = "", required = false) String gunguCode) {
        return this.service.readShelterItems(sidoCode, gunguCode);
    }
}
