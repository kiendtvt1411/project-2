package dhbkhn.kien.doan2.data;

import android.content.Context;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dhbkhn.kien.doan2.data.db.DbHelper;
import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;
import dhbkhn.kien.doan2.data.db.model.User;
import dhbkhn.kien.doan2.data.network.ApiHelper;
import dhbkhn.kien.doan2.data.network.ApiHeader;
import dhbkhn.kien.doan2.data.network.model.LoginRequest;
import dhbkhn.kien.doan2.data.network.model.LoginResponse;
import dhbkhn.kien.doan2.data.network.model.LogoutResponse;
import dhbkhn.kien.doan2.data.realtime.RealtimeHelper;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusRequest;
import dhbkhn.kien.doan2.data.prefs.PreferencesHelper;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusResponse;
import dhbkhn.kien.doan2.di.ApplicationContext;
import io.reactivex.Observable;

/**
 * Created by kiend on 5/13/2017.
 */

@Singleton
public class AppDataManager implements DataManager{

    public static final String TAG = "AppDataManager";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final ApiHelper mApiHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final RealtimeHelper mRealtimeHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper prefsHelper,
                          ApiHelper apiHelper,
                          RealtimeHelper realtimeHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = prefsHelper;
        mApiHelper = apiHelper;
        mRealtimeHelper = realtimeHelper;
    }

    // Load data from server NodeJs

    @Override
    public Socket getSocketIo() {
        return mRealtimeHelper.getSocketIo();
    }

    @Override
    public Observable<Boolean> doSendStatusRoom(RoomStatusRequest roomStatusRequest) {
        return mRealtimeHelper.doSendStatusRoom(roomStatusRequest);
    }

    @Override
    public Observable<RoomStatusResponse> getDataServerResponse(JSONObject obj) {
        return mRealtimeHelper.getDataServerResponse(obj);
    }

    // Base method
    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public Observable<Long> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public Observable<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return mApiHelper.doGoogleLoginApiCall(request);
    }

    @Override
    public Observable<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest
                                                                    request) {
        return mApiHelper.doFacebookLoginApiCall(request);
    }

    @Override
    public Observable<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
                                                                  request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Observable<LogoutResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setUserId(userId);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);

        updateApiHeader(userId, accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null);
    }

    @Override
    public Observable<List<EspOne>> getAllDataEspOne() {
        return mDbHelper.getAllDataEspOne();
    }

    @Override
    public Observable<List<EspTwo>> getAllDataEspTwo() {
        return mDbHelper.getAllDataEspTwo();
    }

    @Override
    public Observable<Boolean> isDataEspOneEmpty() {
        return mDbHelper.isDataEspOneEmpty();
    }

    @Override
    public Observable<Boolean> isDataEspTwoEmpty() {
        return mDbHelper.isDataEspTwoEmpty();
    }

    @Override
    public Observable<Boolean> saveDataEspOne(EspOne espOne) {
        return mDbHelper.saveDataEspOne(espOne);
    }

    @Override
    public Observable<Boolean> saveDataEspTwo(EspTwo espTwo) {
        return mDbHelper.saveDataEspTwo(espTwo);
    }

    @Override
    public Observable<Boolean> saveListDataEspOne(List<EspOne> espOneList) {
        return mDbHelper.saveListDataEspOne(espOneList);
    }

    @Override
    public Observable<Boolean> saveListDataEspTwo(List<EspTwo> espTwoList) {
        return mDbHelper.saveListDataEspTwo(espTwoList);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    @Override
    public Observable<EspOne> getLastEspOne() {
        return mDbHelper.getLastEspOne();
    }

    @Override
    public Observable<EspTwo> getLastEspTwo() {
        return mDbHelper.getLastEspTwo();
    }

    @Override
    public Observable<List<EspOne>> getTenLastEspOne() {
        return mDbHelper.getTenLastEspOne();
    }

    @Override
    public Observable<List<EspTwo>> getTenLastEspTwo() {
        return mDbHelper.getTenLastEspTwo();
    }
}
