package dhbkhn.kien.doan2.ui.history;

import dhbkhn.kien.doan2.di.PerActivity;
import dhbkhn.kien.doan2.ui.base.MvpPresenter;

/**
 * Created by kiend on 5/16/2017.
 */

@PerActivity
public interface HistoryMvpPresenter<V extends HistoryMvpView> extends MvpPresenter<V> {

    void onClickDetailRoomOne();

    void onClickDetailRoomTwo();
}
