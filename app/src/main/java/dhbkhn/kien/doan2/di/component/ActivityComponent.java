package dhbkhn.kien.doan2.di.component;

import dagger.Component;
import dhbkhn.kien.doan2.di.PerActivity;
import dhbkhn.kien.doan2.di.module.ActivityModule;
import dhbkhn.kien.doan2.ui.about.AboutFragment;
import dhbkhn.kien.doan2.ui.detail.DetailFragment;
import dhbkhn.kien.doan2.ui.history.HistoryFragment;
import dhbkhn.kien.doan2.ui.home.HomeActivity;
import dhbkhn.kien.doan2.ui.login.LoginActivity;
import dhbkhn.kien.doan2.ui.room.RoomFragment;
import dhbkhn.kien.doan2.ui.splash.SplashActivity;

/**
 * Created by kiend on 5/14/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(HomeActivity activity);

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

    void inject(AboutFragment fragment);

    void inject(RoomFragment fragment);

    void inject(HistoryFragment fragment);

    void inject(DetailFragment fragment);
}
