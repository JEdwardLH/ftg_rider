package com.foodtogo.rider.ui.dashboard.view;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseApplication;
import com.foodtogo.rider.util.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * The type Pie chart.
 */
public class PieChart {
    // private static final float ZERO =  0.0000f;

    SliceValue sliceDelivered;
    SliceValue sliceProcessing;
    SliceValue sliceNew;
    SliceValue sliceDummy;
    PieChartView pieChartView;

    public PieChart(PieChartView pieChartView) {
        this.pieChartView = pieChartView;
        List<SliceValue> values = new ArrayList<>();

        values.add(sliceDelivered = new SliceValue(0, ResourceUtils.getColor(R.color.colorDelivered)));
        values.add(sliceProcessing = new SliceValue(0, ResourceUtils.getColor(R.color.colorProcessing)));
        values.add(sliceNew = new SliceValue(0, ResourceUtils.getColor(R.color.colorNew)));
        values.add(sliceDummy = new SliceValue(1, ResourceUtils.getColor(R.color.colorGrayLite)));

        PieChartData data = new PieChartData(values);
        data.setHasLabels(false);
        data.setHasLabelsOnlyForSelected(false);
        data.setHasLabelsOutside(false);
        data.setHasCenterCircle(true);
        data.setSlicesSpacing(0);
        data.setCenterCircleColor(BaseApplication.getContext().getResources().getColor(R.color.colorWhite));

        pieChartView.setInteractive(false);
        pieChartView.setMinimumHeight(0);
        pieChartView.setPieChartData(data);
    }

    public void loadData(int newOrderCount, int processingOrderCount, int deliveredOrderCount) {
        boolean isEmpty = newOrderCount == 0 && processingOrderCount == 0 && deliveredOrderCount == 0;
        sliceDelivered.setTarget(deliveredOrderCount);
        sliceProcessing.setTarget(processingOrderCount);
        sliceNew.setTarget(newOrderCount);
        sliceDummy.setTarget(isEmpty ? 1 : 0);
        pieChartView.startDataAnimation();
    }
}