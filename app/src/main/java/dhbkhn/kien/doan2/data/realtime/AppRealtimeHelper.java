package dhbkhn.kien.doan2.data.realtime;

import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import dhbkhn.kien.doan2.data.realtime.model.RoomStatusRequest;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusResponse;
import io.reactivex.Observable;

/**
 * Created by kiend on 5/16/2017.
 */

@Singleton
public class AppRealtimeHelper implements RealtimeHelper{

    private Socket mSocket;

    @Inject
    public AppRealtimeHelper(Socket socket) {
        mSocket = socket;
    }

    @Override
    public Socket getSocketIo() {
        return mSocket;
    }

    @Override
    public Observable<Boolean> doSendStatusRoom(RoomStatusRequest roomStatusRequest) {
        return Observable.fromCallable(() -> {
            mSocket.emit(NodeJsServer.ANDROID_SEND_ADJUSTMENT, roomStatusRequest.parseJsonToString());
            return true;
        });
    }

    @Override
    public Observable<RoomStatusResponse> getDataServerResponse(JSONObject obj) {
        RoomStatusResponse response = new GsonBuilder().create().fromJson(String.valueOf(obj), RoomStatusResponse.class);
        return Observable.fromCallable(() -> response);
    }
}
