package dhbkhn.kien.doan2.data.db;

import java.util.List;

import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;
import dhbkhn.kien.doan2.data.db.model.User;
import io.reactivex.Observable;

/**
 * Created by kiend on 5/13/2017.
 */

public interface DbHelper {

    Observable<Long> insertUser(final User user);

    Observable<List<User>> getAllUsers();

    Observable<List<EspOne>> getAllDataEspOne();

    Observable<List<EspTwo>> getAllDataEspTwo();

    Observable<Boolean> isDataEspOneEmpty();

    Observable<Boolean> isDataEspTwoEmpty();

    Observable<Boolean> saveDataEspOne(EspOne espOne);

    Observable<Boolean> saveDataEspTwo(EspTwo espTwo);

    Observable<Boolean> saveListDataEspOne(List<EspOne> espOneList);

    Observable<Boolean> saveListDataEspTwo(List<EspTwo> espTwoList);

    // get last data from server Nodejs to show in screen
    Observable<EspOne> getLastEspOne();

    Observable<EspTwo> getLastEspTwo();

    Observable<List<EspOne>> getTenLastEspOne();

    Observable<List<EspTwo>> getTenLastEspTwo();
}
