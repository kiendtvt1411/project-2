package dhbkhn.kien.doan2.ui.splash;

import dhbkhn.kien.doan2.ui.base.MvpView;

/**
 * Created by kiend on 5/14/2017.
 */

public interface SplashMvpView extends MvpView {

    void openLoginActivity();

    void openMainActivity();

    void startSyncService();
}
