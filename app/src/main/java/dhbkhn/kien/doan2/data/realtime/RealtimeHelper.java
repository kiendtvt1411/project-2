package dhbkhn.kien.doan2.data.realtime;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import dhbkhn.kien.doan2.data.realtime.model.RoomStatusRequest;
import dhbkhn.kien.doan2.data.realtime.model.RoomStatusResponse;
import io.reactivex.Observable;

/**
 * Created by kiend on 5/16/2017.
 */

public interface RealtimeHelper {

    // for node js

    Socket getSocketIo();

    Observable<Boolean> doSendStatusRoom(RoomStatusRequest roomStatusRequest);

    Observable<RoomStatusResponse> getDataServerResponse(JSONObject obj);
}
