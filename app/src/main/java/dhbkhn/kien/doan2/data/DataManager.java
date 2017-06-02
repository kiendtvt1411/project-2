package dhbkhn.kien.doan2.data;

import dhbkhn.kien.doan2.data.db.DbHelper;
import dhbkhn.kien.doan2.data.network.ApiHelper;
import dhbkhn.kien.doan2.data.prefs.PreferencesHelper;
import dhbkhn.kien.doan2.data.realtime.RealtimeHelper;

/**
 * Created by kiend on 5/13/2017.
 */

public interface DataManager extends DbHelper, ApiHelper, PreferencesHelper, RealtimeHelper{

    void updateApiHeader(Long userId, String accessToken);

    void setUserAsLoggedOut();

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String username,
            String email,
            String profilePicPath
    );

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
