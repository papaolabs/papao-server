package com.papaolabs.openapi.interfaces.v1;

import com.papaolabs.openapi.domain.model.Animal;
import com.papaolabs.openapi.domain.model.Breed;
import com.papaolabs.openapi.domain.model.Region;
import com.papaolabs.openapi.domain.model.Shelter;
import com.papaolabs.openapi.domain.service.CaptureService;
import com.papaolabs.openapi.domain.service.OperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/openapi/v1/operation")
public class V1OperationController {
    @NotNull
    private final OperationService operationService;
    @NotNull
    private final CaptureService captureService;

    public V1OperationController(OperationService operationService, CaptureService captureService) {
        this.operationService = operationService;
        this.captureService = captureService;
    }

    @GetMapping("/kinds")
    public List<Breed> getBreedList() {
        return this.operationService.getBreedList();
    }

    @GetMapping("/capture/kinds")
    public String captureBreedList(@RequestParam(required = false) String tableName) {
        return this.captureService.captureBreedList(tableName);
    }

    @GetMapping("/regions")
    public List<Region> getRegionList() {
        return this.operationService.getRegionList();
    }

    @GetMapping("/shelters")
    public List<Shelter> getShelterList() {
        return this.operationService.getShelterList();
    }

    @GetMapping("/capture/regions")
    public String captureregionList(@RequestParam(required = false) String tableName) {
        return this.captureService.captureRegionList(tableName);
    }

    @GetMapping("/capture/shelters")
    public String captureShelterList(@RequestParam(required = false) String tableName) {
        return this.captureService.captureShelterList(tableName);
    }

    @GetMapping("/animals")
    public List<Animal> getAnimalList(@RequestParam(required = false) String beginDate,
                                      @RequestParam(required = false) String endDate,
                                      @RequestParam(required = false) String speciesCode,
                                      @RequestParam(required = false) String kindCode,
                                      @RequestParam(required = false) String sidoCode,
                                      @RequestParam(required = false) String gunguCode,
                                      @RequestParam(required = false) String shelterCode,
                                      @RequestParam(required = false) String state,
                                      @RequestParam(required = false, defaultValue = "1") String index,
                                      @RequestParam(required = false, defaultValue = "100000") String size) {
        return this.operationService.getAnimalList(beginDate,
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
}
