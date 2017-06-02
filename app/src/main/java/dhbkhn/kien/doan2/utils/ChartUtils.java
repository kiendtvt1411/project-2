package dhbkhn.kien.doan2.utils;

import android.graphics.Color;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;

/**
 * Created by kiend on 5/30/2017.
 */

public final class ChartUtils {
    public static final String LINE_CHART_LABEL = "Temperature";

    public static final String BAR_CHART_LABEL = "Humidity";

    public static LineData generateLineChartRoomOne(List<EspOne> espList) {
        LineData d = new LineData();

        List<Entry> entries = new ArrayList<Entry>();
        int len = espList.size();

        for (int index = 0; index < len; index++)
            entries.add(new Entry(espList.get(index).getTemperature(), index));

        LineDataSet set = new LineDataSet(entries, LINE_CHART_LABEL);
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    public static BarData generateBarChartRoomOne(List<EspOne> espList) {

        List<BarEntry> entries = new ArrayList<BarEntry>();
        int len = espList.size();

        for (int index = 0; index < len; index++) {
            entries.add(new BarEntry(espList.get(index).getHumidity(),index));
        }

        BarDataSet set1 = new BarDataSet(entries, BAR_CHART_LABEL);
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData();
        d.addDataSet(set1);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
//        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }

    public static LineData generateLineChartRoomTwo(List<EspTwo> espList) {
        LineData d = new LineData();

        List<Entry> entries = new ArrayList<>();
        int len = espList.size();

        for (int index = 0; index < len; index++)
            entries.add(new Entry(espList.get(index).getTemperature(), index));

        LineDataSet set = new LineDataSet(entries, LINE_CHART_LABEL);
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    public static BarData generateBarChartRoomTwo(List<EspTwo> espList) {

        List<BarEntry> entries = new ArrayList<>();
        int len = espList.size();

        for (int index = 0; index < len; index++) {
            entries.add(new BarEntry(espList.get(index).getHumidity(),index));
        }

        BarDataSet set1 = new BarDataSet(entries, BAR_CHART_LABEL);
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData();
        d.addDataSet(set1);
        d.setBarWidth(barWidth);

//         make this BarData object grouped
//        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }
}
