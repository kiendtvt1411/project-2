package dhbkhn.kien.doan2.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dhbkhn.kien.doan2.BaseApp;
import dhbkhn.kien.doan2.data.DataManager;
import dhbkhn.kien.doan2.di.ApplicationContext;
import dhbkhn.kien.doan2.di.module.ApplicationModule;
import dhbkhn.kien.doan2.service.SyncService;

/**
 * Created by kiend on 5/14/2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseApp app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
