package dhbkhn.kien.doan2.ui.login;

import com.androidnetworking.error.ANError;

import javax.inject.Inject;

import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.data.DataManager;
import dhbkhn.kien.doan2.data.network.model.LoginRequest;
import dhbkhn.kien.doan2.ui.base.BasePresenter;
import dhbkhn.kien.doan2.utils.CommonUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kiend on 5/14/2017.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V>{

    private static final String TAG = "LoginPresenter";

    //to use dataManager and compositeDisposable from BasePresenter
    @Inject
    public LoginPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onServerLoginClick(String email, String password) {
        //validate email and password
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    getDataManager().updateUserInfo(
                            loginResponse.getAccessToken(),
                            loginResponse.getUserId(),
                            DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                            loginResponse.getUserName(),
                            loginResponse.getUserEmail(),
                            loginResponse.getGoogleProfilePicUrl());

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    getMvpView().openHomeActivity();
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
    public void onGoogleLoginClick() {
        // instruct LoginActivity to initiate google login
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doGoogleLoginApiCall(new LoginRequest.GoogleLoginRequest("test1", "test1"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getDataManager().updateUserInfo(
                            response.getAccessToken(),
                            response.getUserId(),
                            DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE,
                            response.getUserName(),
                            response.getUserEmail(),
                            response.getGoogleProfilePicUrl());

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    getMvpView().openHomeActivity();
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
                }));
    }

    @Override
    public void onFacebookLoginClick() {
        // instruct LoginActivity to initiate facebook login
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doFacebookLoginApiCall(new LoginRequest.FacebookLoginRequest("test3", "test4"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getDataManager().updateUserInfo(
                            response.getAccessToken(),
                            response.getUserId(),
                            DataManager.LoggedInMode.LOGGED_IN_MODE_FB,
                            response.getUserName(),
                            response.getUserEmail(),
                            response.getGoogleProfilePicUrl());

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    getMvpView().openHomeActivity();
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
                }));
    }
}
