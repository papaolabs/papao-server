package com.papaolabs.api.interfaces.v1.controller.response;

public class StatsDTO {
    private String beginDate;
    private String endDate;
    private Integer saveCount;
    private Integer adoptionCount;
    private Integer returnCount;
    private Integer euthanasiaCount;
    private Integer naturalDeathCount;

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
        return naturalDeathCount != null ? naturalDeathCount.equals(statsDTO.naturalDeathCount) : statsDTO.naturalDeathCount == null;
    }

    @Override
    public int hashCode() {
        int result = beginDate != null ? beginDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (saveCount != null ? saveCount.hashCode() : 0);
        result = 31 * result + (adoptionCount != null ? adoptionCount.hashCode() : 0);
        result = 31 * result + (returnCount != null ? returnCount.hashCode() : 0);
        result = 31 * result + (euthanasiaCount != null ? euthanasiaCount.hashCode() : 0);
        result = 31 * result + (naturalDeathCount != null ? naturalDeathCount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StatsDTO{" +
            "beginDate='" + beginDate + '\'' +
            ", endDate='" + endDate + '\'' +
            ", saveCount=" + saveCount +
            ", adoptionCount=" + adoptionCount +
            ", returnCount=" + returnCount +
            ", euthanasiaCount=" + euthanasiaCount +
            ", naturalDeathCount=" + naturalDeathCount +
            '}';
    }
}
