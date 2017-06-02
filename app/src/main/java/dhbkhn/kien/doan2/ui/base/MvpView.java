package dhbkhn.kien.doan2.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by kiend on 5/14/2017.
 */

public interface MvpView {

    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    boolean isNetworkConnected();

    void hideKeyboard();

}
