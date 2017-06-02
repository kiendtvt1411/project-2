package dhbkhn.kien.doan2.di.module;

import android.app.Service;

import dagger.Module;

/**
 * Created by kiend on 5/14/2017.
 */

@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service mService) {
        this.mService = mService;
    }
}
