package dhbkhn.kien.doan2.ui.login;

import dhbkhn.kien.doan2.di.PerActivity;
import dhbkhn.kien.doan2.ui.base.MvpPresenter;

/**
 * Created by kiend on 5/14/2017.
 */

@PerActivity
public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onServerLoginClick(String email, String password);

    void onGoogleLoginClick();

    void onFacebookLoginClick();

}
