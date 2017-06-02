package dhbkhn.kien.doan2.ui.detail;

import javax.inject.Inject;

import dhbkhn.kien.doan2.data.DataManager;
import dhbkhn.kien.doan2.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kiend on 5/30/2017.
 */

public class DetailPresenter<V extends DetailMvpView> extends BasePresenter<V>
        implements DetailMvpPresenter<V> {

    @Inject
    public DetailPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


    @Override
    public void onInitiateCardHolder(int idRoom) {
        if (idRoom == 1) {
            getCompositeDisposable().add(getDataManager()
                    .getTenLastEspOne()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(espOneList ->
                            {
                                if (!isViewAttached()) {
                                    return;
                                }

                                if (espOneList != null) {
                                    getMvpView().refreshHistoryRoomOne(espOneList);
                                }
                            }
                    )
            );
        } else {
            getCompositeDisposable().add(getDataManager()
                    .getTenLastEspTwo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(espTwoList ->
                    {
                        if (!isViewAttached()) {
                            return;
                        }

                        if (espTwoList != null) {
                            getMvpView().refreshHistoryRoomTwo(espTwoList);
                        }
                    })
            );
        }
    }

    @Override
    public void onCardExhausted(int idRoom) {
        if (idRoom == 1) {
            getCompositeDisposable().add(getDataManager()
                    .getTenLastEspOne()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(espOneList -> {
                        if (!isViewAttached()) {
                            return;
                        }

                        if (espOneList != null) {
                            getMvpView().reloadHistoryRoomOne(espOneList);
                        }
                    })
            );
        } else {
            getCompositeDisposable().add(getDataManager()
                    .getTenLastEspTwo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(espTwoList ->
                    {
                        if (!isViewAttached()) {
                            return;
                        }

                        if (espTwoList != null) {
                            getMvpView().reloadHistoryRoomTwo(espTwoList);
                        }
                    })
            );
        }
    }
}
