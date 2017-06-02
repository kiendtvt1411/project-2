package dhbkhn.kien.doan2.data.realtime;

import dhbkhn.kien.doan2.utils.AppBuildConfig;

/**
 * Created by kiend on 5/16/2017.
 */

public final class NodeJsServer {

    public static final String PORT_NODE_JS = AppBuildConfig.NODE_SERVER
            + ":6969";

    // both android and server send the same Json script
    // { id_room : int, lamp_1 : boolean, lamp_2 : boolean, lamp_3 : boolean, humidity : float, temperature : float}

    public static final String ANDROID_SEND_ADJUSTMENT = "ANDROID_SEND_ADJUSTMENT";

    public static final String SERVER_RESPONSE = "SERVER_RESPONSE";
}
