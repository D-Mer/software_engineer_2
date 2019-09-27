package com.example.cinema.vo;

import com.example.cinema.po.ProjectionSituation;

/**
 * @author S.W.R
 * @date 2019/5/15 5:41 PM
 */
public class ProjectionSituationVO {
    private Integer releaseNum;
    private Integer audienceNum;
    private Integer placingRate;

    public ProjectionSituationVO(ProjectionSituation projectionSituation){
        this.releaseNum = projectionSituation.getReleaseNum();
        this.audienceNum = projectionSituation.getAudienceNum();
        this.placingRate = projectionSituation.getPlacingRate();
    }

    public Integer getReleaseNum() {
        return releaseNum;
    }

    public void setReleaseNum(Integer releaseNum) {
        this.releaseNum = releaseNum;
    }

    public Integer getAudienceNum() {
        return audienceNum;
    }

    public void setAudienceNum(Integer audienceNum) {
        this.audienceNum = audienceNum;
    }

    public Integer getPlacingRate() {
        return placingRate;
    }

    public void setPlacingRate(Integer placingRate) {
        this.placingRate = placingRate;
    }
}
