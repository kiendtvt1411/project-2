package dhbkhn.kien.doan2.ui.history;

import javax.inject.Inject;

import dhbkhn.kien.doan2.data.DataManager;
import dhbkhn.kien.doan2.ui.base.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kiend on 5/16/2017.
 */

public class HistoryPresenter<V extends HistoryMvpView> extends BasePresenter<V>
        implements HistoryMvpPresenter<V> {

    @Inject
    public HistoryPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onClickDetailRoomOne() {
        getMvpView().openDetailFragment(1);
    }

    @Override
    public void onClickDetailRoomTwo() {
        getMvpView().openDetailFragment(2);
    }
}
