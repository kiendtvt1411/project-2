package dhbkhn.kien.doan2.ui.home;

import dhbkhn.kien.doan2.ui.base.MvpView;

/**
 * Created by kiend on 5/14/2017.
 */

public interface HomeMvpView extends MvpView{

    void openLoginActivity();

    void showAboutFragment();

    void updateUserName(String currentUserName);

    void updateUserEmail(String currentUserEmail);

    void updateUserProfilePic(String currentUserProfilePicUrl);

    void updateAppVersion();
}
