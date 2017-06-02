package dhbkhn.kien.doan2.ui.room;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusRequest;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusResponse;
import dhbkhn.kien.doan2.ui.base.BaseFragment;
import dhbkhn.kien.doan2.utils.ChartUtils;

/**
 * Created by kiend on 5/15/2017.
 */

public class RoomFragment extends BaseFragment implements RoomMvpView {

    public static final String TAG = RoomFragment.class.getSimpleName();

    public static final String ROOM_ID = "room_id";

    private int idRoom = 0;

    @Inject
    RoomMvpPresenter<RoomMvpView> mPresenter;

    // bind view
    @BindView(R.id.img_lamp_1)
    ImageView mImgLampOne;

    @BindView(R.id.img_lamp_2)
    ImageView mImgLampTwo;

    @BindView(R.id.img_lamp_3)
    ImageView mImgLampThree;

    @BindView(R.id.switch_lamp_1)
    Switch mSwLampOne;

    @BindView(R.id.switch_lamp_2)
    Switch mSwLampTwo;

    @BindView(R.id.switch_lamp_3)
    Switch mSwLampThree;

    @BindView(R.id.tv_lamp_1)
    TextView mTvLampOne;

    @BindView(R.id.tv_lamp_2)
    TextView mTvLampTwo;

    @BindView(R.id.tv_lamp_3)
    TextView mTvLampThree;

    @BindView(R.id.tv_current_humid)
    TextView mTvCurrentHumid;

    @BindView(R.id.tv_current_temp)
    TextView mTvCurrentTemp;

    @BindView(R.id.tv_max_humid)
    TextView mTvMaxHumid;

    @BindView(R.id.tv_max_temp)
    TextView mTvMaxTemp;

    @BindView(R.id.sb_humid)
    SeekBar mSbHumid;

    @BindView(R.id.sb_temp)
    SeekBar mSbTemp;

    // for combined chart
    @BindView(R.id.combined_chart)
    CombinedChart mChart;

    @BindView(R.id.cv_open_chart)
    CardView mCvOpenChart;

    @BindView(R.id.cv_close_chart)
    CardView mCvCloseChart;

    private float mHumid = 0f;
    private float mTemp = 0f;
    private float mMaxHumid = 0f;
    private float mMaxTemp = 0f;

    public static RoomFragment newInstance(int idRoom) {
        Bundle args = new Bundle();
        args.putInt(ROOM_ID, idRoom);
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        // always Attach View before doing something with presenter interface
        mPresenter.onAttach(this);

        setUp(view);

        mPresenter.onOpenSocketIO();

        return view;
    }

    @Override
    protected void setUp(View view) {
        idRoom = getArguments().getInt(ROOM_ID, 0);
        // show current status when disconnected with server Node Js
        mPresenter.onShowLastRoomStatusWhenStart(idRoom);

        mSwLampOne.setOnCheckedChangeListener(mySwitchOne);
        mSwLampTwo.setOnCheckedChangeListener(mySwitchTwo);
        mSwLampThree.setOnCheckedChangeListener(mySwitchThree);
        mSbHumid.setOnSeekBarChangeListener(mySeekbarHumidityListener);
        mSbTemp.setOnSeekBarChangeListener(mySeekbarTemperatureListener);

        initChart();
        mPresenter.onPrepareDataChart(idRoom);
    }

    @Override
    public void updateRoomStatus(RoomStatusResponse response) {
        if (response.getIdRoom() == idRoom) {
            showStatusLamp(response.isLampOne(), response.isLampTwo(), response.isLampThree());
            showHumidity(response.getHumidity());
            showTemperature(response.getTemperature());
            updateMaxHumidTemp(mMaxHumid, mMaxTemp);
            updateChart(response.getHumidity(), response.getTemperature());

            // Save to SQLite
            mPresenter.onSaveResponseToDatabase(response, mMaxHumid, mMaxTemp);
        }
    }

    @Override
    public void showCurrentStatus(boolean lamp1, boolean lamp2, boolean lamp3, float maxHumid, float maxTemp, float currentHumid, float currentTemp) {
        mSwLampOne.setChecked(lamp1);
        mSwLampTwo.setChecked(lamp2);
        mSwLampThree.setChecked(lamp3);
        showStatusLamp(lamp1, lamp2, lamp3);
        mMaxHumid = maxHumid;
        mMaxTemp = maxTemp;
        mTvCurrentHumid.setText(String.valueOf(currentHumid) + " " + getString(R.string.index_humid));
        mTvMaxHumid.setText(String.valueOf(maxHumid) + " " + getString(R.string.index_humid));
        mTvCurrentTemp.setText(String.valueOf(currentTemp) + " " + getString(R.string.index_C));
        mTvMaxTemp.setText(String.valueOf(maxTemp) + " " + getString(R.string.index_C));
    }

    void initChart() {
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE
        });

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
    }

    @OnClick(R.id.cv_send_adjust)
    public void send(View v){
        sendAdjustment();
    }

    @Override
    public void sendAdjustment() {
        if (idRoom == 0) return; // not exist room has id = 0

        RoomStatusRequest request = new RoomStatusRequest();
        request.setIdRoom(idRoom);
        request.setLampOne(mSwLampOne.isChecked());
        request.setLampTwo(mSwLampTwo.isChecked());
        request.setLampThree(mSwLampThree.isChecked());
        request.setMaxHumidity(mSbHumid.getProgress());
        request.setMaxTemperature(mSbTemp.getProgress());

        mPresenter.onClickToSendAdjustment(request,mHumid, mTemp);
    }

    @Override
    public void showStatusLamp(boolean lamp1, boolean lamp2, boolean lamp3) {
        @DrawableRes int light1 = lamp1 ? R.drawable.lightbulb_on : R.drawable.lightbulb_off;
        @DrawableRes int light2 = lamp2 ? R.drawable.lightbulb_on : R.drawable.lightbulb_off;
        @DrawableRes int light3 = lamp3 ? R.drawable.lightbulb_on : R.drawable.lightbulb_off;
        mSwLampOne.setChecked(lamp1);
        mSwLampTwo.setChecked(lamp2);
        mSwLampThree.setChecked(lamp3);

        if (lamp1) {
            mTvLampOne.setText(getString(R.string.status_lamp) + " " + getString(R.string.lamp_on));
        } else {
            mTvLampOne.setText(getString(R.string.status_lamp) + " " + getString(R.string.lamp_off));
        }

        if (lamp2) {
            mTvLampTwo.setText(getString(R.string.status_lamp) + " " + getString(R.string.lamp_on));
        } else {
            mTvLampTwo.setText(getString(R.string.status_lamp) + " " + getString(R.string.lamp_off));
        }

        if (lamp3) {
            mTvLampThree.setText(getString(R.string.status_lamp) + " " + getString(R.string.lamp_on));
        } else {
            mTvLampThree.setText(getString(R.string.status_lamp) + " " + getString(R.string.lamp_off));
        }

        mImgLampOne.setImageResource(light1);
        mImgLampTwo.setImageResource(light2);
        mImgLampThree.setImageResource(light3);
    }

    @Override
    public void showHumidity(float humid) {
        mTvCurrentHumid.setText(String.valueOf(humid) + " " + getString(R.string.index_humid));
        mHumid = humid;
    }

    @Override
    public void showTemperature(float temp) {
        mTvCurrentTemp.setText(String.valueOf(temp) + " " + getString(R.string.index_C));
        mTemp = temp;
    }

    @Override
    public void updateChart(float humid, float temp) {

    }

    @Override
    public void prepareAndShowChartRoomOne(List<EspOne> espOneList) {
        Log.d(TAG + "kienvip", espOneList.size() + "");
        CombinedData data = new CombinedData();
        data.setData(ChartUtils.generateLineChartRoomOne(espOneList));
        data.setData(ChartUtils.generateBarChartRoomOne(espOneList));
        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void prepareAndShowChartRoomTwo(List<EspTwo> espTwoList) {
        CombinedData data = new CombinedData();
        data.setData(ChartUtils.generateLineChartRoomTwo(espTwoList));
        data.setData(ChartUtils.generateBarChartRoomTwo(espTwoList));
        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void updateMaxHumidTemp(float maxH, float maxT) {
        mSbTemp.setProgress((int) maxH);
        mSbHumid.setProgress((int) maxT);
    }

    // listener

    //Show or close chart
    private boolean isShowChart = false;

    @OnClick(R.id.cv_open_chart)
    public void showChart(View v) {
        isShowChart = !isShowChart;
        showOrCloseChart();
    }

    @OnClick(R.id.ll_close_chart)
    public void closeChart(View v) {
        isShowChart = !isShowChart;
        showOrCloseChart();
    }

    private void showOrCloseChart() {
        if (isShowChart) {
            mCvOpenChart.setVisibility(View.GONE);
            mCvCloseChart.setVisibility(View.VISIBLE);
            mCvCloseChart.setFocusable(true);
            mPresenter.onPrepareDataChart(idRoom);
            mChart.invalidate();
        } else {
            mCvOpenChart.setVisibility(View.VISIBLE);
            mCvCloseChart.setVisibility(View.GONE);
        }
    }

    private Switch.OnCheckedChangeListener mySwitchOne = (compoundButton, b) -> {
        showStatusLamp(b, mSwLampTwo.isChecked(), mSwLampThree.isChecked());
    };

    private Switch.OnCheckedChangeListener mySwitchTwo = (compoundButton, b) -> {
        showStatusLamp(mSwLampOne.isChecked(), b, mSwLampThree.isChecked());
    };

    private Switch.OnCheckedChangeListener mySwitchThree = (compoundButton, b) -> {
        showStatusLamp(mSwLampOne.isChecked(), mSwLampTwo.isChecked(), b);
    };

    private SeekBar.OnSeekBarChangeListener mySeekbarTemperatureListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mTvMaxTemp.setText(String.valueOf(i) + " " + getString(R.string.index_C));
            mMaxTemp = i;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener mySeekbarHumidityListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mTvMaxHumid.setText(String.valueOf(i) + " " + getString(R.string.index_humid));
            mMaxHumid = i;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
