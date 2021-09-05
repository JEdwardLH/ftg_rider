package com.foodtogo.rider.util;

/**
 * The type Currency utils.
 */
public class CurrencyUtils {
    /**
     * Gets display currency.
     *
     * @param symbol the symbol
     * @param price  the price
     * @return the display currency
     */
    public static String getDisplayCurrency(String symbol, String price) {
        return symbol + price;
    }
}
