package dhbkhn.kien.doan2.ui.detail;

import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.data.db.model.EspOne;

/**
 * Created by kiend on 5/30/2017.
 */

@NonReusable
@Layout(R.layout.card_history_layout)
public class HistoryCardOne {

    private static final String TAG = "HistoryCardOne";

    @View(R.id.tv_detail_time)
    private TextView mTvDetailTime;

    @View(R.id.img_detail_lamp_1)
    private ImageView mImgDetailLampOne;

    @View(R.id.img_detail_lamp_2)
    private ImageView mImgDetailLampTwo;

    @View(R.id.img_detail_lamp_3)
    private ImageView mImgDetailLampThree;

    @View(R.id.tv_detail_temp)
    private TextView mTvDetailTemp;

    @View(R.id.tv_detail_temp_max)
    private TextView mTvDetailTempMax;

    @View(R.id.tv_detail_humid)
    private TextView mTvDetailHumid;

    @View(R.id.tv_detail_humid_max)
    private TextView mTvDetailHumidMax;

    private EspOne mEspOne;

    public HistoryCardOne(EspOne mEspOne) {
        this.mEspOne = mEspOne;
    }

    @Resolve
    private void onShowDataRoomOne() {
        if (mEspOne == null) return;
        mTvDetailTime.setText(mEspOne.getDate());
        if (mEspOne.isLampOne()) {
            mImgDetailLampOne.setImageResource(R.drawable.lightbulb_on);
        } else {
            mImgDetailLampOne.setImageResource(R.drawable.lightbulb_off);
        }

        if (mEspOne.isLampTwo()) {
            mImgDetailLampTwo.setImageResource(R.drawable.lightbulb_on);
        } else {
            mImgDetailLampTwo.setImageResource(R.drawable.lightbulb_off);
        }

        if (mEspOne.isLampThree()) {
            mImgDetailLampThree.setImageResource(R.drawable.lightbulb_on);
        } else {
            mImgDetailLampThree.setImageResource(R.drawable.lightbulb_off);
        }

        mTvDetailTemp.setText(mEspOne.getTemperature() + " ˚C");
        mTvDetailTempMax.setText(mEspOne.getMaxTempurature() + " ˚C");
        mTvDetailHumid.setText(mEspOne.getHumidity() + " %");
        mTvDetailHumidMax.setText(mEspOne.getMaxHumidity() + " %");
    }

    @Click(R.id.img_detail_next)
    public void onNextCard() {

    }

    @Click(R.id.img_detail_prev)
    public void onPrevCard() {

    }
}
