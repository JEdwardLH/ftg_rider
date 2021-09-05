package com.foodtogo.rider.model.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Choice.
 */
public class Choice {
    @SerializedName("choicename")
    @Expose
    private String choicename;
    @SerializedName("choice_amount")
    @Expose
    private String choiceAmount;

    /**
     * Gets choicename.
     *
     * @return the choicename
     */
    public String getChoicename() {
        return choicename;
    }

    /**
     * Sets choicename.
     *
     * @param choicename the choicename
     */
    public void setChoicename(String choicename) {
        this.choicename = choicename;
    }

    /**
     * Gets choice amount.
     *
     * @return the choice amount
     */
    public String getChoiceAmount() {
        return choiceAmount;
    }

    /**
     * Sets choice amount.
     *
     * @param choiceAmount the choice amount
     */
    public void setChoiceAmount(String choiceAmount) {
        this.choiceAmount = choiceAmount;
    }
}
