package dhbkhn.kien.doan2;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import javax.inject.Inject;

import dhbkhn.kien.doan2.data.DataManager;
import dhbkhn.kien.doan2.di.component.ApplicationComponent;
import dhbkhn.kien.doan2.di.component.DaggerApplicationComponent;
import dhbkhn.kien.doan2.di.module.ApplicationModule;
import dhbkhn.kien.doan2.utils.AppBuildConfig;
import dhbkhn.kien.doan2.utils.AppLogger;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by kiend on 5/13/2017.
 */

public class BaseApp extends Application {

    @Inject
    DataManager mDataManager;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        AppLogger.init();

        mDataManager.getSocketIo().connect();

        AndroidNetworking.initialize(getApplicationContext());

        if (AppBuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public ApplicationComponent getComponent(){
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
