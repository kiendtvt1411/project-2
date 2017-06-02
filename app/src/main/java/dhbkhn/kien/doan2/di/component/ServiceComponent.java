package dhbkhn.kien.doan2.di.component;

import dagger.Component;
import dhbkhn.kien.doan2.di.PerActivity;
import dhbkhn.kien.doan2.di.module.ServiceModule;
import dhbkhn.kien.doan2.service.SyncService;

/**
 * Created by kiend on 5/14/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}
