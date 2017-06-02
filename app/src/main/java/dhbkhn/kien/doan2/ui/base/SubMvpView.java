package dhbkhn.kien.doan2.ui.base;

/**
 * Created by kiend on 5/14/2017.
 */

public interface SubMvpView extends MvpView{

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void attachParentMvpView(MvpView mvpView);
}
