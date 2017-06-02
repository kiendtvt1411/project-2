package dhbkhn.kien.doan2.ui.room;

import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusRequest;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusResponse;
import dhbkhn.kien.doan2.di.PerActivity;
import dhbkhn.kien.doan2.ui.base.MvpPresenter;

/**
 * Created by kiend on 5/15/2017.
 */

@PerActivity
public interface RoomMvpPresenter<V extends RoomMvpView> extends MvpPresenter<V>{

    // adjust seek bar, change toggle, etc...
    // humid and temp are arguments will be save into SQLite
    void onClickToSendAdjustment(RoomStatusRequest request, float humid, float temp);

    void onSaveLastStatusRoomOne(EspOne espOne);

    void onSaveLastStatusRoomTwo(EspTwo espTwo);

    void onShowLastRoomStatusWhenStart(int idRoom);

    void onSaveResponseToDatabase(RoomStatusResponse response, float maxHumid, float maxTemp);

    void onOpenSocketIO();

    void onShowLastRoomOneStatus();

    void onShowLastRoomTwoStatus();

    void onPrepareDataChart(int idRoom);
}
