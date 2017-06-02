package dhbkhn.kien.doan2.di.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dhbkhn.kien.doan2.di.ActivityContext;
import dhbkhn.kien.doan2.di.PerActivity;
import dhbkhn.kien.doan2.ui.about.AboutMvpPresenter;
import dhbkhn.kien.doan2.ui.about.AboutMvpView;
import dhbkhn.kien.doan2.ui.about.AboutPresenter;
import dhbkhn.kien.doan2.ui.detail.DetailMvpPresenter;
import dhbkhn.kien.doan2.ui.detail.DetailMvpView;
import dhbkhn.kien.doan2.ui.detail.DetailPresenter;
import dhbkhn.kien.doan2.ui.history.HistoryMvpPresenter;
import dhbkhn.kien.doan2.ui.history.HistoryMvpView;
import dhbkhn.kien.doan2.ui.history.HistoryPresenter;
import dhbkhn.kien.doan2.ui.home.HomeMvpPresenter;
import dhbkhn.kien.doan2.ui.home.HomeMvpView;
import dhbkhn.kien.doan2.ui.home.HomePresenter;
import dhbkhn.kien.doan2.ui.login.LoginMvpPresenter;
import dhbkhn.kien.doan2.ui.login.LoginMvpView;
import dhbkhn.kien.doan2.ui.login.LoginPresenter;
import dhbkhn.kien.doan2.ui.room.RoomMvpPresenter;
import dhbkhn.kien.doan2.ui.room.RoomMvpView;
import dhbkhn.kien.doan2.ui.room.RoomPresenter;
import dhbkhn.kien.doan2.ui.splash.SplashMvpPresenter;
import dhbkhn.kien.doan2.ui.splash.SplashMvpView;
import dhbkhn.kien.doan2.ui.splash.SplashPresenter;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kiend on 5/14/2017.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return mActivity;
    }

    @Provides
    Activity provideActivity(){
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginMvpPresenter(LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    HomeMvpPresenter<HomeMvpView> provideHomeMvpPresenter(HomePresenter<HomeMvpView> presenter) {
        return presenter;
    }

    @Provides
    AboutMvpPresenter<AboutMvpView> provideAboutPresenter(AboutPresenter<AboutMvpView> presenter) {
        return presenter;
    }

    @Provides
    RoomMvpPresenter<RoomMvpView> provideRoomPresenter(RoomPresenter<RoomMvpView> presenter) {
        return presenter;
    }

    @Provides
    HistoryMvpPresenter<HistoryMvpView> provideHistoryPresenter(HistoryPresenter<HistoryMvpView> presenter) {
        return presenter;
    }

    @Provides
    DetailMvpPresenter<DetailMvpView> provideDetailPresenter(DetailPresenter<DetailMvpView> presenter) {
        return presenter;
    }
}
