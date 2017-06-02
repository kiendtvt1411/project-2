package dhbkhn.kien.doan2.ui.base;

import com.androidnetworking.error.ANError;

/**
 * Created by kiend on 5/14/2017.
 */

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDettach();

    void handleApiError(ANError error);

    void setUserAsLoggedOut();
}
