package com.foodtogo.rider.model.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Order detail array.
 */
public class OrderDetailArray {
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("specialRequest")
    @Expose
    private String specialRequest;
    @SerializedName("ord_quantity")
    @Expose
    private Integer ordQuantity;
    @SerializedName("ord_unit_price")
    @Expose
    private String ordUnitPrice;
    @SerializedName("ord_tax_amt")
    @Expose
    private String ordTaxAmt;
    @SerializedName("sub_total")
    @Expose
    private Double subTotal;
    @SerializedName("ord_currency")
    @Expose
    private String ordCurrency;
    @SerializedName("pre_order_date")
    @Expose
    private String preOrderDate;
    @SerializedName("pdt_image")
    @Expose
    private String pdtImage;
    @SerializedName("choice")
    @Expose
    private List<Choice> choice = null;

    /**
     * Gets store name.
     *
     * @return the store name
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Sets store name.
     *
     * @param storeName the store name
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * Gets item name.
     *
     * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets item name.
     *
     * @param itemName the item name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets special request.
     *
     * @return the special request
     */
    public String getSpecialRequest() {
        return specialRequest;
    }

    /**
     * Sets special request.
     *
     * @param specialRequest the special request
     */
    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    /**
     * Gets ord quantity.
     *
     * @return the ord quantity
     */
    public Integer getOrdQuantity() {
        return ordQuantity;
    }

    /**
     * Sets ord quantity.
     *
     * @param ordQuantity the ord quantity
     */
    public void setOrdQuantity(Integer ordQuantity) {
        this.ordQuantity = ordQuantity;
    }

    /**
     * Gets ord unit price.
     *
     * @return the ord unit price
     */
    public String getOrdUnitPrice() {
        return ordUnitPrice;
    }

    /**
     * Sets ord unit price.
     *
     * @param ordUnitPrice the ord unit price
     */
    public void setOrdUnitPrice(String ordUnitPrice) {
        this.ordUnitPrice = ordUnitPrice;
    }

    /**
     * Gets ord tax amt.
     *
     * @return the ord tax amt
     */
    public String getOrdTaxAmt() {
        return ordTaxAmt;
    }

    /**
     * Sets ord tax amt.
     *
     * @param ordTaxAmt the ord tax amt
     */
    public void setOrdTaxAmt(String ordTaxAmt) {
        this.ordTaxAmt = ordTaxAmt;
    }

    /**
     * Gets sub total.
     *
     * @return the sub total
     */
    public Double getSubTotal() {
        return subTotal;
    }

    /**
     * Sets sub total.
     *
     * @param subTotal the sub total
     */
    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Gets ord currency.
     *
     * @return the ord currency
     */
    public String getOrdCurrency() {
        return ordCurrency;
    }

    /**
     * Sets ord currency.
     *
     * @param ordCurrency the ord currency
     */
    public void setOrdCurrency(String ordCurrency) {
        this.ordCurrency = ordCurrency;
    }

    /**
     * Gets pre order date.
     *
     * @return the pre order date
     */
    public String getPreOrderDate() {
        return preOrderDate;
    }

    /**
     * Sets pre order date.
     *
     * @param preOrderDate the pre order date
     */
    public void setPreOrderDate(String preOrderDate) {
        this.preOrderDate = preOrderDate;
    }

    /**
     * Gets pdt image.
     *
     * @return the pdt image
     */
    public String getPdtImage() {
        return pdtImage;
    }

    /**
     * Sets pdt image.
     *
     * @param pdtImage the pdt image
     */
    public void setPdtImage(String pdtImage) {
        this.pdtImage = pdtImage;
    }

    /**
     * Gets choice.
     *
     * @return the choice
     */
    public List<Choice> getChoice() {
        return choice;
    }

    /**
     * Sets choice.
     *
     * @param choice the choice
     */
    public void setChoice(List<Choice> choice) {
        this.choice = choice;
    }

}
