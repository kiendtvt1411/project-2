package dhbkhn.kien.doan2.di.module;

import android.app.Application;
import android.content.Context;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.data.AppDataManager;
import dhbkhn.kien.doan2.data.DataManager;
import dhbkhn.kien.doan2.data.db.AppDbHelper;
import dhbkhn.kien.doan2.data.db.DbHelper;
import dhbkhn.kien.doan2.data.network.ApiHeader;
import dhbkhn.kien.doan2.data.network.ApiHelper;
import dhbkhn.kien.doan2.data.network.AppApiHelper;
import dhbkhn.kien.doan2.data.prefs.AppPreferencesHelper;
import dhbkhn.kien.doan2.data.prefs.PreferencesHelper;
import dhbkhn.kien.doan2.data.realtime.AppRealtimeHelper;
import dhbkhn.kien.doan2.data.realtime.NodeJsServer;
import dhbkhn.kien.doan2.data.realtime.RealtimeHelper;
import dhbkhn.kien.doan2.di.AppInfo;
import dhbkhn.kien.doan2.di.ApplicationContext;
import dhbkhn.kien.doan2.di.DatabaseInfo;
import dhbkhn.kien.doan2.di.PreferenceInfo;
import dhbkhn.kien.doan2.utils.AppBuildConfig;
import dhbkhn.kien.doan2.utils.AppConstants;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by kiend on 5/14/2017.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    @AppInfo
    String provideApiKey(){
        return AppBuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName(){
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencsHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@AppInfo String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken()
        );
    }

    @Provides
    @Singleton
    Socket provideSocket() {
        try {
            Socket socket = IO.socket(NodeJsServer.PORT_NODE_JS);
            return socket;
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Provides
    @Singleton
    RealtimeHelper provideRealtimeHelper(AppRealtimeHelper appRealtimeHelper) {
        return appRealtimeHelper;
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig(){
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
