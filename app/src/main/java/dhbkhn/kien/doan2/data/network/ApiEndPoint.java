package dhbkhn.kien.doan2.data.network;

import dhbkhn.kien.doan2.utils.AppBuildConfig;

/**
 * Created by kiend on 5/14/2017.
 */

public final class ApiEndPoint {

    public static final String ENDPOINT_GOOGLE_LOGIN = AppBuildConfig.BASE_URL
            + "/588d14f4100000a9072d2943";
    public static final String ENDPOINT_FACEBOOK_LOGIN = AppBuildConfig.BASE_URL
            + "/588d15d3100000ae072d2944";
    public static final String ENDPOINT_SERVER_LOGIN = AppBuildConfig.BASE_URL
            + "/588d15f5100000a8072d2945";
    public static final String ENDPOINT_LOGOUT = AppBuildConfig.BASE_URL
            + "/588d161c100000a9072d2946";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
