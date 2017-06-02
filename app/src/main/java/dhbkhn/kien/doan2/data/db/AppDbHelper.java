package dhbkhn.kien.doan2.data.db;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dhbkhn.kien.doan2.data.db.model.DaoMaster;
import dhbkhn.kien.doan2.data.db.model.DaoSession;
import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;
import dhbkhn.kien.doan2.data.db.model.User;
import io.reactivex.Observable;

/**
 * Created by kiend on 5/13/2017.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Long> insertUser(User user) {
        return Observable.fromCallable(() ->
            mDaoSession.getUserDao().insert(user));
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(() ->
            mDaoSession.getUserDao().loadAll()
        );
    }

    @Override
    public Observable<List<EspOne>> getAllDataEspOne() {
        return Observable.fromCallable(() -> mDaoSession.getEspOneDao().loadAll());
    }

    @Override
    public Observable<List<EspTwo>> getAllDataEspTwo() {
        return Observable.fromCallable(() -> mDaoSession.getEspTwoDao().loadAll());
    }

    @Override
    public Observable<Boolean> isDataEspOneEmpty() {
        return Observable.fromCallable(() -> !(mDaoSession.getEspOneDao().count() > 0));
    }

    @Override
    public Observable<Boolean> isDataEspTwoEmpty() {
        return Observable.fromCallable(() -> !(mDaoSession.getEspTwoDao().count() >0));
    }

    @Override
    public Observable<Boolean> saveDataEspOne(final EspOne espOne) {
        return Observable.fromCallable(() -> {
            mDaoSession.getEspOneDao().insert(espOne);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveDataEspTwo(final EspTwo espTwo) {
        return Observable.fromCallable(() -> {
            mDaoSession.getEspTwoDao().insert(espTwo);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveListDataEspOne(final List<EspOne> espOneList) {
        return Observable.fromCallable(() -> {
            mDaoSession.getEspOneDao().insertInTx(espOneList);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveListDataEspTwo(final List<EspTwo> espTwoList) {
        return Observable.fromCallable(() -> {
            mDaoSession.getEspTwoDao().insertInTx(espTwoList);
            return true;
        });
    }

    @Override
    public Observable<EspOne> getLastEspOne() {
        return Observable.fromCallable(() ->
            mDaoSession.getEspOneDao().load(mDaoSession.getEspOneDao().count() - 1)
        );
    }

    @Override
    public Observable<EspTwo> getLastEspTwo() {
        return Observable.fromCallable(() ->
            mDaoSession.getEspTwoDao().load(mDaoSession.getEspTwoDao().count() - 1)
        );
    }

    @Override
    public Observable<List<EspOne>> getTenLastEspOne() {
        return Observable.fromCallable(() ->
            mDaoSession.getEspOneDao().queryBuilder().limit(10).list()
        );
    }

    @Override
    public Observable<List<EspTwo>> getTenLastEspTwo() {
        return Observable.fromCallable(() ->
                mDaoSession.getEspTwoDao().queryBuilder().limit(10).list()
        );
    }
}
