package com.namviet.vtvtravel.database;




import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.namviet.vtvtravel.model.f2.ClassForInvitedUser;
import com.namviet.vtvtravel.model.f2.Contact;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface VTVTravelDao {

    @Query("SELECT * FROM Contact")
    public List<Contact> getAllContact();

    @Query("SELECT * FROM Contact Where id = :idQuery")
    public Contact getContactFromId(String idQuery);

    @Insert(onConflict = REPLACE)
    public void insertContact(List<Contact> contact);

    @Query("DELETE FROM Contact")
    public void clearContact();

    @Insert(onConflict = REPLACE)
    public void insertOneContact(Contact contact);

    //


    @Query("SELECT * FROM ClassForInvitedUser Where phone = :idQuery")
    public ClassForInvitedUser getInvitedUserFromId(String idQuery);

    @Insert(onConflict = REPLACE)
    public void insertInvitedUser(List<ClassForInvitedUser> contact);

    @Query("DELETE FROM ClassForInvitedUser")
    public void clearInvitedUser();

    @Insert(onConflict = REPLACE)
    public void insertOneInvitedUser(ClassForInvitedUser contact);


}
