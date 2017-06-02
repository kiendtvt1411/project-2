package dhbkhn.kien.doan2.ui.home;

import dhbkhn.kien.doan2.di.PerActivity;
import dhbkhn.kien.doan2.ui.base.MvpPresenter;

/**
 * Created by kiend on 5/14/2017.
 */

@PerActivity
public interface HomeMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {

    void onDrawerOptionAboutClick();

    void onDrawerOptionLogoutClick();

    void onViewInitialized();

    void onCardExhausted(); // Make color :d

    void onNavMenuCreated();

}
