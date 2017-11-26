package com.papaolabs.api.interfaces.v1.controller.response;

public class StatsDTO {
    private String beginDate;
    private String endDate;
    private Integer totalCount;
    private Integer saveCount;
    private Integer adoptionCount;
    private Integer returnCount;
    private Integer euthanasiaCount;
    private Integer naturalDeathCount;
    private Integer unknownCount;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getSaveCount() {
        return saveCount;
    }

    public void setSaveCount(Integer saveCount) {
        this.saveCount = saveCount;
    }

    public Integer getAdoptionCount() {
        return adoptionCount;
    }

    public void setAdoptionCount(Integer adoptionCount) {
        this.adoptionCount = adoptionCount;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Integer getEuthanasiaCount() {
        return euthanasiaCount;
    }

    public void setEuthanasiaCount(Integer euthanasiaCount) {
        this.euthanasiaCount = euthanasiaCount;
    }

    public Integer getNaturalDeathCount() {
        return naturalDeathCount;
    }

    public void setNaturalDeathCount(Integer naturalDeathCount) {
        this.naturalDeathCount = naturalDeathCount;
    }

    public Integer getUnknownCount() {
        return unknownCount;
    }

    public void setUnknownCount(Integer unknownCount) {
        this.unknownCount = unknownCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StatsDTO statsDTO = (StatsDTO) o;
        if (beginDate != null ? !beginDate.equals(statsDTO.beginDate) : statsDTO.beginDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(statsDTO.endDate) : statsDTO.endDate != null) {
            return false;
        }
        if (totalCount != null ? !totalCount.equals(statsDTO.totalCount) : statsDTO.totalCount != null) {
            return false;
        }
        if (saveCount != null ? !saveCount.equals(statsDTO.saveCount) : statsDTO.saveCount != null) {
            return false;
        }
        if (adoptionCount != null ? !adoptionCount.equals(statsDTO.adoptionCount) : statsDTO.adoptionCount != null) {
            return false;
        }
        if (returnCount != null ? !returnCount.equals(statsDTO.returnCount) : statsDTO.returnCount != null) {
            return false;
        }
        if (euthanasiaCount != null ? !euthanasiaCount.equals(statsDTO.euthanasiaCount) : statsDTO.euthanasiaCount != null) {
            return false;
        }
        if (naturalDeathCount != null ? !naturalDeathCount.equals(statsDTO.naturalDeathCount) : statsDTO.naturalDeathCount != null) {
            return false;
        }
        return unknownCount != null ? unknownCount.equals(statsDTO.unknownCount) : statsDTO.unknownCount == null;
    }

    @Override
    public int hashCode() {
        int result = beginDate != null ? beginDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (totalCount != null ? totalCount.hashCode() : 0);
        result = 31 * result + (saveCount != null ? saveCount.hashCode() : 0);
        result = 31 * result + (adoptionCount != null ? adoptionCount.hashCode() : 0);
        result = 31 * result + (returnCount != null ? returnCount.hashCode() : 0);
        result = 31 * result + (euthanasiaCount != null ? euthanasiaCount.hashCode() : 0);
        result = 31 * result + (naturalDeathCount != null ? naturalDeathCount.hashCode() : 0);
        result = 31 * result + (unknownCount != null ? unknownCount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StatsDTO{" +
            "beginDate='" + beginDate + '\'' +
            ", endDate='" + endDate + '\'' +
            ", totalCount=" + totalCount +
            ", saveCount=" + saveCount +
            ", adoptionCount=" + adoptionCount +
            ", returnCount=" + returnCount +
            ", euthanasiaCount=" + euthanasiaCount +
            ", naturalDeathCount=" + naturalDeathCount +
            ", unknownCount=" + unknownCount +
            '}';
    }
}
