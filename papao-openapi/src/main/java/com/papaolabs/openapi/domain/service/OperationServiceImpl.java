package com.papaolabs.openapi.domain.service;

import com.papaolabs.client.govdata.dto.AnimalResponse;
import com.papaolabs.client.govdata.dto.RegionResponse.Body.Items.RegionItem;
import com.papaolabs.openapi.domain.model.Animal;
import com.papaolabs.openapi.domain.model.Breed;
import com.papaolabs.openapi.domain.model.Breed.Species;
import com.papaolabs.openapi.domain.model.Region;
import com.papaolabs.openapi.domain.model.Shelter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.papaolabs.openapi.domain.model.Breed.Species.CAT;
import static com.papaolabs.openapi.domain.model.Breed.Species.DOG;
import static com.papaolabs.openapi.domain.model.Breed.Species.ETC;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isAllBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class OperationServiceImpl implements OperationService {
    public final static List<Species> BREED_SPECIES_LIST = Arrays.asList(DOG, CAT, ETC);
    @NotNull
    private final GovDataService govDataService;

    public OperationServiceImpl(GovDataService govDataService) {
        this.govDataService = govDataService;
    }

    @Override
    public List<Animal> getAnimalList(String beginDate,
                                      String endDate,
                                      String speciesCode,
                                      String kindCode,
                                      String sidoCode,
                                      String gunguCode,
                                      String shelterCode,
                                      String state,
                                      String index,
                                      String size) {
        List<AnimalResponse.Body.Items.AnimalItem> animalItems = this.govDataService.readAnimalItems(beginDate,
                                                                                                     endDate,
                                                                                                     speciesCode,
                                                                                                     kindCode,
                                                                                                     sidoCode,
                                                                                                     gunguCode,
                                                                                                     shelterCode,
                                                                                                     state,
                                                                                                     index,
                                                                                                     size);
        if (animalItems == null) {
            return Arrays.asList();
        }
        return animalItems.stream()
                   .map(x -> {
                       Animal animal = new Animal();
                       animal.setNoticeId(x.getNoticeNo());
                       animal.setNoticeBeginDate(x.getNoticeSdt());
                       animal.setNoticeEndDate(x.getNoticeEdt());
                       animal.setDesertionId(Long.valueOf(x.getDesertionNo()));
                       animal.setStateType(x.getProcessState());
                       animal.setImageUrl(x.getPopfile());
                       animal.setThumbImageUrl(x.getFilename());
                       animal.setBreedName(x.getKindCd());
                       animal.setColorName(x.getColorCd());
                       animal.setAge(convertAge(x.getAge()));
                       animal.setWeight(Float.valueOf(convertWeight(x.getWeight())));
                       animal.setGenderCode(x.getSexCd());
                       animal.setNeuterCode(x.getNeuterYn());
                       animal.setJurisdiction(x.getOrgNm());
                       animal.setShelterName(x.getCareNm());
                       animal.setShelterContact(x.getCareTel());
                       animal.setShelterAddress(x.getCareAddr());
                       animal.setUserName(x.getChargeNm());
                       animal.setUserContact(x.getOfficetel());
                       animal.setFeature(x.getSpecialMark());
                       animal.setNote(x.getNoticeComment());
/*
                                      animal.setPageSize(Integer.valueOf(x.getNumOfRows()));
                                      animal.setPageIndex(Integer.valueOf(x.getPageNo()));
                                      animal.setPageTotalCount(Integer.valueOf(x.getTotalCount()));
                                      animal.setResultCode(Integer.valueOf(x.getResultCode()));
                                      animal.setResultMessage(x.getResultMsg());
*/
                       animal.setHappenDate(x.getHappenDt());
                       animal.setHappenPlace(x.getHappenPlace());
                       return animal;
                   })
                   .collect(Collectors.toList());
    }

    @Override
    public List<Breed> getBreedList() {
        return this.BREED_SPECIES_LIST.stream()
                                      .map(this::transform)
                                      .flatMap(Collection::stream)
                                      .collect(Collectors.toList());
    }

    @Override
    public List<Region> getRegionList() {
        return this.govDataService.readSidoItems()
                                  .stream()
                                  .map(this::transform)
                                  .flatMap(Collection::stream)
                                  .collect(Collectors.toList());
    }

    @Override
    public List<Shelter> getShelterList() {
        return this.govDataService.readGunguItems(EMPTY)
                                  .stream()
                                  .map(this::shelterTransform)
                                  .flatMap(Collection::stream)
                                  .collect(Collectors.toList());
    }

    private List<Breed> transform(Species species) {
        return this.govDataService.readKindItems(species.getCode())
                                  .stream()
                                  .map(x -> {
                                      Breed breed = new Breed();
                                      breed.setSpecies(species);
                                      breed.setCode(Integer.valueOf(x.getKindCd()));
                                      breed.setName(x.getKNm());
                                      return breed;
                                  })
                                  .collect(Collectors.toList());
    }

    private List<Region> transform(RegionItem regionItem) {
        return Optional.ofNullable(this.govDataService.readGunguItems(regionItem.getOrgCd()))
                       .orElse(Arrays.asList(regionItem))
                       .stream()
                       .map(x -> {
                           Region region = new Region();
                           region.setSidoCode(Integer.valueOf(regionItem.getOrgCd()));
                           region.setSidoName(regionItem.getOrgdownNm());
                           region.setGunguCode(Integer.valueOf(x.getOrgCd()));
                           region.setGunguName(x.getOrgdownNm());
                           return region;
                       })
                       .collect(Collectors.toList());
    }

    private List<Shelter> shelterTransform(RegionItem regionItem) {
        List<RegionItem> regionItems = this.govDataService.readSidoItems();
        String sidoName = regionItems.stream()
                                     .filter(x -> x.getOrgCd()
                                                   .equals(regionItem.getUprCd()))
                                     .map(RegionItem::getOrgdownNm)
                                     .findFirst()
                                     .get();
        return this.govDataService.readShelterItems(regionItem.getUprCd(), regionItem.getOrgCd())
                                  .stream()
                                  .map(x -> {
                                      Region region = new Region();
                                      region.setSidoCode(Integer.valueOf(regionItem.getUprCd()));
                                      region.setSidoName(sidoName);
                                      region.setGunguCode(Integer.valueOf(regionItem.getOrgCd()));
                                      region.setGunguName(regionItem.getOrgdownNm());
                                      Shelter shelter = new Shelter();
                                      shelter.setRegion(region);
                                      shelter.setCode(x.getCareRegNo());
                                      shelter.setName(x.getCareNm());
                                      return shelter;
                                  })
                                  .collect(Collectors.toList());
    }

    private String convertWeight(String weight) {
        if (isEmpty(weight)) {
            return "-1";
        }
        if (weight.contains("(Kg)")) {
            weight = weight.replace("(Kg)", "");
            try {
                Float.valueOf(weight);
            } catch (NumberFormatException nfe) {
                weight = "-1";
            }
        }
        return weight;
    }

    private String convertAge(String age) {
        String result = age.replace(" ", "");
        if (isEmpty(result) || isAllBlank(result)) {
            return "-1";
        }
        if (result.contains("(년생)")) {
            return result.replace("(년생)", "");
        }
        return result;
    }
}
