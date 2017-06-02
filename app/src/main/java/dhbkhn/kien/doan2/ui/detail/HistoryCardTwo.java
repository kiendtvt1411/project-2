package dhbkhn.kien.doan2.ui.detail;

import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.data.db.model.EspTwo;

/**
 * Created by kiend on 5/30/2017.
 */

@NonReusable
@Layout(R.layout.card_history_layout)
public class HistoryCardTwo {

    private static final String TAG = "HistoryCardTwo";

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

    private EspTwo mEspTwo;

    public HistoryCardTwo(EspTwo mEspTwo) {
        this.mEspTwo = mEspTwo;
    }

    @Resolve
    private void onShowDataRoomOne() {
        if (mEspTwo == null) return;
        mTvDetailTime.setText(mEspTwo.getDate());
        if (mEspTwo.isLampOne()) {
            mImgDetailLampOne.setImageResource(R.drawable.lightbulb_on);
        } else {
            mImgDetailLampOne.setImageResource(R.drawable.lightbulb_off);
        }

        if (mEspTwo.isLampTwo()) {
            mImgDetailLampTwo.setImageResource(R.drawable.lightbulb_on);
        } else {
            mImgDetailLampTwo.setImageResource(R.drawable.lightbulb_off);
        }

        if (mEspTwo.isLampThree()) {
            mImgDetailLampThree.setImageResource(R.drawable.lightbulb_on);
        } else {
            mImgDetailLampThree.setImageResource(R.drawable.lightbulb_off);
        }

        mTvDetailTemp.setText(mEspTwo.getTemperature() + " ˚C");
        mTvDetailTempMax.setText(mEspTwo.getMaxTempurature() + " ˚C");
        mTvDetailHumid.setText(mEspTwo.getHumidity() + " %");
        mTvDetailHumidMax.setText(mEspTwo.getMaxHumidity() + " %");
    }

    @Click(R.id.img_detail_next)
    public void onNextCard() {

    }

    @Click(R.id.img_detail_prev)
    public void onPrevCard() {

    }
}
