package com.namviet.vtvtravel.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.namviet.vtvtravel.model.f2.ClassForInvitedUser;
import com.namviet.vtvtravel.model.f2.Contact;
import com.namviet.vtvtravel.model.f2.DataConverterPhoneList;


@Database(entities = { Contact.class, ClassForInvitedUser.class}, version = 3)
@TypeConverters({ DataConverterPhoneList.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract VTVTravelDao foodDao();

    public static AppDatabase getInMemoryDatabase(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
//                    // To simplify the codelab, allow queries on the main thread.
//                    // Don't do this on a real app! See PersistenceBasicSample for an example.
//                    .allowMainThreadQueries()
//                    .build();
//
//        }
//        return INSTANCE;

        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "vtvtravel").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
