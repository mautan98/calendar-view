package com.namviet.vtvtravel.model.f2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity
public class ClassForInvitedUser {
    @PrimaryKey
    @NonNull
    private String phone;

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }
}
