package dhbkhn.kien.doan2.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import dhbkhn.kien.doan2.data.db.model.DaoMaster;
import dhbkhn.kien.doan2.di.ApplicationContext;
import dhbkhn.kien.doan2.di.DatabaseInfo;

/**
 * Created by kiend on 5/13/2017.
 */

@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper{

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //when alter the table
        }
    }
}
