package dhbkhn.kien.doan2.data.network;

import dhbkhn.kien.doan2.data.network.model.LoginRequest;
import dhbkhn.kien.doan2.data.network.model.LoginResponse;
import dhbkhn.kien.doan2.data.network.model.LogoutResponse;
import io.reactivex.Observable;

/**
 * Created by kiend on 5/14/2017.
 */

public interface ApiHelper {

    ApiHeader getApiHeader();

    // for login

    Observable<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    Observable<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest resquest);

    Observable<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest resquest);

    Observable<LogoutResponse> doLogoutApiCall();

}
