package dhbkhn.kien.doan2.ui.detail;

import java.util.List;

import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;
import dhbkhn.kien.doan2.ui.base.MvpView;

/**
 * Created by kiend on 5/30/2017.
 */

public interface DetailMvpView extends MvpView{

    void refreshHistoryRoomOne(List<EspOne> espOneList);

    void refreshHistoryRoomTwo(List<EspTwo> espTwoList);

    void reloadHistoryRoomOne(List<EspOne> espOneList);

    void reloadHistoryRoomTwo(List<EspTwo> espTwoList);
}
