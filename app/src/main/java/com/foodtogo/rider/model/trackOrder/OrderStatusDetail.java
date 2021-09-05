package com.foodtogo.rider.model.trackOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Order status detail.
 */
public class OrderStatusDetail {
    @SerializedName("ord_stage")
    @Expose
    private Integer ordStage;
    @SerializedName("ord_title")
    @Expose
    private String ordTitle;
    @SerializedName("ord_timing")
    @Expose
    private String ordTiming;

    /**
     * Gets stage completed.
     *
     * @return the stage completed
     */
    public String getStageCompleted() {
        return stageCompleted;
    }

    /**
     * Sets stage completed.
     *
     * @param stageCompleted the stage completed
     */
    public void setStageCompleted(String stageCompleted) {
        this.stageCompleted = stageCompleted;
    }

    @SerializedName("stage_completed")
    @Expose
    private String stageCompleted;

    /**
     * Gets ord stage.
     *
     * @return the ord stage
     */
    public Integer getOrdStage() {
        return ordStage;
    }


    /**
     * Sets ord stage.
     *
     * @param ordStage the ord stage
     */
    public void setOrdStage(Integer ordStage) {
        this.ordStage = ordStage;
    }

    /**
     * Gets ord title.
     *
     * @return the ord title
     */
    public String getOrdTitle() {
        return ordTitle;
    }

    /**
     * Sets ord title.
     *
     * @param ordTitle the ord title
     */
    public void setOrdTitle(String ordTitle) {
        this.ordTitle = ordTitle;
    }

    /**
     * Gets ord timing.
     *
     * @return the ord timing
     */
    public String getOrdTiming() {
        return ordTiming;
    }

    /**
     * Sets ord timing.
     *
     * @param ordTiming the ord timing
     */
    public void setOrdTiming(String ordTiming) {
        this.ordTiming = ordTiming;
    }
}
