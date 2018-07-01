package com.datasensorn.mqttservice.model.biz;

import com.datasensorn.mqttservice.model.biz.ChartPoint;

import java.util.Comparator;

/**
 * 图表的比较器
 */
public class ChartPointComparator implements Comparator<ChartPoint> {
    @Override
    public int compare(ChartPoint o1, ChartPoint o2) {
        Long o1t = o1.getTime().getTime();
        Long o2t = o2.getTime().getTime();

        if (o1t > o2t) {
            return 1;
        } else if (o1t == o2t) {
            return 0;
        } else {
            return -1;
        }
    }
}
