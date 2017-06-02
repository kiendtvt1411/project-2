package dhbkhn.kien.doan2.ui.room;

import android.util.Log;

import com.androidnetworking.error.ANError;

import org.json.JSONObject;

import javax.inject.Inject;

import dhbkhn.kien.doan2.data.DataManager;
import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;
import dhbkhn.kien.doan2.data.realtime.NodeJsServer;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusRequest;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusResponse;
import dhbkhn.kien.doan2.ui.base.BasePresenter;
import dhbkhn.kien.doan2.utils.CommonUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kiend on 5/15/2017.
 */

public class RoomPresenter<V extends RoomMvpView> extends BasePresenter<V> implements RoomMvpPresenter<V> {

    private static final String TAG = RoomPresenter.class.getSimpleName();

    @Inject
    public RoomPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


    @Override
    public void onClickToSendAdjustment(RoomStatusRequest request, float humid, float temp) {
        getMvpView().showLoading();

        getCompositeDisposable().add(
                getDataManager().doSendStatusRoom(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean -> {
                            // do saving status room into SQLite
                            if (!isViewAttached()) {
                                return;
                            }

                            if (request.getIdRoom() == 1) {
                                EspOne espOne = new EspOne();
                                espOne.setLampOne(request.isLampOne());
                                espOne.setLampTwo(request.isLampTwo());
                                espOne.setLampThree(request.isLampThree());
                                espOne.setMaxHumidity(request.getMaxHumidity());
                                espOne.setMaxTempurature(request.getMaxTemperature());
                                espOne.setTemperature(temp);
                                espOne.setHumidity(humid);
                                espOne.setDate("Date : " + CommonUtils.getDateStamp() + " | Time : " + CommonUtils.getTimeStamp());
                                onSaveLastStatusRoomOne(espOne);
                            } else {
                                EspTwo espTwo = new EspTwo();
                                espTwo.setLampOne(request.isLampOne());
                                espTwo.setLampTwo(request.isLampTwo());
                                espTwo.setLampThree(request.isLampThree());
                                espTwo.setMaxHumidity(request.getMaxHumidity());
                                espTwo.setMaxTempurature(request.getMaxTemperature());
                                espTwo.setTemperature(temp);
                                espTwo.setHumidity(humid);
                                espTwo.setDate("Date : " + CommonUtils.getDateStamp() + " | Time : " + CommonUtils.getTimeStamp());
                                onSaveLastStatusRoomTwo(espTwo);
                            }
                        }, throwable -> {
                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                            // handle the login error here
                            if (throwable instanceof ANError) {
                                ANError anError = (ANError) throwable;
                                handleApiError(anError);
                            }
                        })
        );
    }

    @Override
    public void onSaveLastStatusRoomOne(EspOne espOne) {
        getCompositeDisposable().add(
                getDataManager().saveDataEspOne(espOne)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean ->
                                getMvpView().hideLoading()
                        )
        );
    }

    @Override
    public void onSaveLastStatusRoomTwo(EspTwo espTwo) {
        getCompositeDisposable().add(
                getDataManager().saveDataEspTwo(espTwo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean ->
                                getMvpView().hideLoading()
                        )
        );
    }

    @Override
    public void onShowLastRoomStatusWhenStart(int idRoom) {
        getMvpView().showLoading();

        if (idRoom == 1) {
            onShowLastRoomOneStatus();
        } else {
            onShowLastRoomTwoStatus();
        }
    }

    @Override
    public void onShowLastRoomOneStatus() {
        getCompositeDisposable().add(
                getDataManager().getLastEspOne()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(espOne -> {
                            if (!isViewAttached()) {
                                return;
                            }

                            if (espOne != null) {
                                getMvpView().showCurrentStatus(espOne.isLampOne(),
                                        espOne.isLampTwo(),
                                        espOne.isLampThree(),
                                        espOne.getMaxHumidity(),
                                        espOne.getMaxTempurature(),
                                        espOne.getHumidity(),
                                        espOne.getTemperature());

                                getMvpView().updateMaxHumidTemp(espOne.getMaxHumidity(), espOne.getMaxTempurature());

                                Log.d(TAG, espOne.getHumidity() + "");
                            }

                            getMvpView().hideLoading();
                        }, throwable -> {
                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                            // handle the login error here
                            if (throwable instanceof ANError) {
                                ANError anError = (ANError) throwable;
                                handleApiError(anError);
                            }
                        })
        );
    }

    @Override
    public void onShowLastRoomTwoStatus() {
        getCompositeDisposable().add(
                getDataManager().getLastEspTwo()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(espTwo -> {
                            if (!isViewAttached()) {
                                return;
                            }

                            if (espTwo != null) {
                                getMvpView().showCurrentStatus(espTwo.isLampOne(),
                                        espTwo.isLampTwo(),
                                        espTwo.isLampThree(),
                                        espTwo.getMaxHumidity(),
                                        espTwo.getMaxTempurature(),
                                        espTwo.getHumidity(),
                                        espTwo.getTemperature());

                                getMvpView().updateMaxHumidTemp(espTwo.getMaxHumidity(), espTwo.getMaxTempurature());
                            }

                            getMvpView().hideLoading();
                        }, throwable -> {
                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                            // handle the login error here
                            if (throwable instanceof ANError) {
                                ANError anError = (ANError) throwable;
                                handleApiError(anError);
                            }
                        })
        );
    }

    @Override
    public void onPrepareDataChart(int idRoom) {
        if (idRoom == 1) {
            getCompositeDisposable().add(getDataManager().getTenLastEspOne()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(espOneList -> {
                        if (!isViewAttached()) {
                            return;
                        }

                        if (espOneList != null) {
                            getMvpView().prepareAndShowChartRoomOne(espOneList);
                        }
                    })
            );
        }else{
            getCompositeDisposable().add(getDataManager().getTenLastEspTwo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(espTwoList ->{
                                if (!isViewAttached()) {
                                    return;
                                }

                                if (espTwoList != null) {
                                    getMvpView().prepareAndShowChartRoomTwo(espTwoList);
                                }
                            }
                    )
            );
        }
    }

    @Override
    public void onSaveResponseToDatabase(RoomStatusResponse response, float maxHumid, float maxTemp) {
        if (response.getIdRoom() == 1) {
            EspOne espOne = new EspOne();
            espOne.setLampOne(response.isLampOne());
            espOne.setLampTwo(response.isLampTwo());
            espOne.setLampThree(response.isLampThree());
            espOne.setMaxHumidity(maxHumid);
            espOne.setMaxTempurature(maxTemp);
            espOne.setTemperature(response.getTemperature());
            espOne.setHumidity(response.getHumidity());
            espOne.setDate("Date : " + CommonUtils.getDateStamp() + " | Time : " + CommonUtils.getTimeStamp());
            onSaveLastStatusRoomOne(espOne);
        } else {
            EspTwo espTwo = new EspTwo();
            espTwo.setLampOne(response.isLampOne());
            espTwo.setLampTwo(response.isLampTwo());
            espTwo.setLampThree(response.isLampThree());
            espTwo.setMaxHumidity(maxHumid);
            espTwo.setMaxTempurature(maxTemp);
            espTwo.setTemperature(response.getTemperature());
            espTwo.setHumidity(response.getHumidity());
            espTwo.setDate("Date : " + CommonUtils.getDateStamp() + " | Time : " + CommonUtils.getTimeStamp());
            onSaveLastStatusRoomTwo(espTwo);
        }
    }

    @Override
    public void onOpenSocketIO() {
        getDataManager().getSocketIo().on(NodeJsServer.SERVER_RESPONSE, args -> {
            JSONObject obj = (JSONObject) args[0];
            getCompositeDisposable().add(getDataManager().getDataServerResponse(obj)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        getMvpView().updateRoomStatus(response);
                    })
            );
        });
    }
}
