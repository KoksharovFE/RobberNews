package ru.astralight.koksharov.robbernews.DB;

import android.content.ContentResolver;
import android.database.Cursor;

/**
 * Created by Koksharov on 05.10.2017.
 */

public class DataResolve {

    static DataResolve resolve;

    public static DataResolve getInstance() {
        return resolve;
    }

//    Cursor query(){
//        getApplicationContext().getContentResolver()
//        ContentResolver resolver = getContentResolver();
//        return new Cursor();
//    }
}
