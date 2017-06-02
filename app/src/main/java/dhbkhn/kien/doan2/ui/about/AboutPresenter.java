package dhbkhn.kien.doan2.ui.about;

import javax.inject.Inject;

import dhbkhn.kien.doan2.data.DataManager;
import dhbkhn.kien.doan2.ui.base.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kiend on 5/15/2017.
 */

public class AboutPresenter<V extends AboutMvpView> extends BasePresenter<V>
        implements AboutMvpPresenter<V>{

    @Inject
    public AboutPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
