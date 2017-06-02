package dhbkhn.kien.doan2.ui.room;

import java.util.List;

import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusResponse;
import dhbkhn.kien.doan2.ui.base.MvpView;

/**
 * Created by kiend on 5/15/2017.
 */

public interface RoomMvpView extends MvpView{

    void showCurrentStatus(boolean lamp1,
                           boolean lamp2,
                           boolean lamp3,
                           float maxHumid,
                           float maxTemp,
                           float currentHumid,
                           float currentTemp);

    void sendAdjustment();

    // show status lamp after receive Json from Server Node JS
    void showStatusLamp(boolean lamp1, boolean lamp2, boolean lamp3);

    void showHumidity(float humid);

    void showTemperature(float temp);

    void updateMaxHumidTemp(float maxH, float maxT);

    void updateRoomStatus(RoomStatusResponse response);

    // show chart
    void updateChart(float humid, float temp);

    void prepareAndShowChartRoomOne(List<EspOne> espOneList);

    void prepareAndShowChartRoomTwo(List<EspTwo> espTwoList);
}
