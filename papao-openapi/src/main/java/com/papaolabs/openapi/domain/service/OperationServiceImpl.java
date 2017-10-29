package com.papaolabs.openapi.domain.service;

import com.papaolabs.client.govdata.dto.RegionResponse.Body.Items.RegionItem;
import com.papaolabs.openapi.domain.model.Breed;
import com.papaolabs.openapi.domain.model.Breed.Category;
import com.papaolabs.openapi.domain.model.Region;
import com.papaolabs.openapi.domain.model.Shelter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.papaolabs.openapi.domain.model.Breed.Category.CAT;
import static com.papaolabs.openapi.domain.model.Breed.Category.DOG;
import static com.papaolabs.openapi.domain.model.Breed.Category.ETC;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class OperationServiceImpl implements OperationService {
    public final static List<Category> breedCategoryList = Arrays.asList(DOG, CAT, ETC);
    @NotNull
    private final GovDataService govDataService;

    public OperationServiceImpl(GovDataService govDataService) {
        this.govDataService = govDataService;
    }

    @Override
    public List<Breed> getBreedList() {
        return this.breedCategoryList.stream()
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

    private List<Breed> transform(Category category) {
        return this.govDataService.readKindItems(category.getCode())
                                  .stream()
                                  .map(x -> {
                                      Breed breed = new Breed();
                                      breed.setCategory(category);
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
                           region.setCityCode(Integer.valueOf(regionItem.getOrgCd()));
                           region.setCityName(regionItem.getOrgdownNm());
                           region.setTownCode(Integer.valueOf(x.getOrgCd()));
                           region.setTownName(x.getOrgdownNm());
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
                                      region.setCityCode(Integer.valueOf(regionItem.getUprCd()));
                                      region.setCityName(sidoName);
                                      region.setTownCode(Integer.valueOf(regionItem.getOrgCd()));
                                      region.setTownName(regionItem.getOrgdownNm());
                                      Shelter shelter = new Shelter();
                                      shelter.setRegion(region);
                                      shelter.setCode(x.getCareRegNo());
                                      shelter.setName(x.getCareNm());
                                      return shelter;
                                  })
                                  .collect(Collectors.toList());
    }
}
