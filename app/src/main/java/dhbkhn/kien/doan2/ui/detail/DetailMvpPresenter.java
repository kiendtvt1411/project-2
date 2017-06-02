package dhbkhn.kien.doan2.ui.detail;

import dhbkhn.kien.doan2.ui.base.MvpPresenter;

/**
 * Created by kiend on 5/30/2017.
 */

public interface DetailMvpPresenter <V extends DetailMvpView> extends MvpPresenter<V> {

    void onInitiateCardHolder(int idRoom);

    void onCardExhausted(int idRoom);
}
