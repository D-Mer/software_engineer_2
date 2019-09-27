package com.example.cinema.po;

/**
 * @author S.W.R
 * @date 2019/5/15 5:02 PM
 */
public class ProjectionSituation {
    private Integer releaseNum;
    private Integer audienceNum;
    private Integer placingRate;

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
        return audienceNum / (releaseNum / (2 * 100 / (5 * 10)));
    }

    public void setPlacingRate(Integer audienceNum, Integer releaseNum, Integer hallNum, Integer rowNum, Integer columnNum) {
        this.placingRate = audienceNum / (releaseNum / (hallNum / (rowNum * columnNum)));
    }
}
