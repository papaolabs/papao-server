package com.papaolabs.api.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REGION_INFO_TB")
public class Region {
    @Id
    @GeneratedValue
    private Long id;
    private Long cityCode;
    private String cityName;
    private Long townCode;
    private String townName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getTownCode() {
        return townCode;
    }

    public void setTownCode(Long townCode) {
        this.townCode = townCode;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Region region = (Region) o;
        if (id != null ? !id.equals(region.id) : region.id != null) {
            return false;
        }
        if (cityCode != null ? !cityCode.equals(region.cityCode) : region.cityCode != null) {
            return false;
        }
        if (cityName != null ? !cityName.equals(region.cityName) : region.cityName != null) {
            return false;
        }
        if (townCode != null ? !townCode.equals(region.townCode) : region.townCode != null) {
            return false;
        }
        return townName != null ? townName.equals(region.townName) : region.townName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (townCode != null ? townCode.hashCode() : 0);
        result = 31 * result + (townName != null ? townName.hashCode() : 0);
        return result;
    }
}
