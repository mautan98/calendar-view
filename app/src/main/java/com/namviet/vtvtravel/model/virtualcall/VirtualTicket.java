package com.namviet.vtvtravel.model.virtualcall;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VirtualTicket implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("subscriber_mobile")
    @Expose
    private String creatorMobile;
    @SerializedName("receiverId")
    @Expose
    private int receiverId;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("modified")
    @Expose
    private long modified;
    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("finished")
    @Expose
    private long finished;
    @SerializedName("deadline")
    @Expose
    private long deadline;
    @SerializedName("subscriber_name")
    @Expose
    private String customerName;
    @SerializedName("customerId")
    @Expose
    private int customerId;

    protected VirtualTicket(Parcel in) {
        id = in.readString();
        code = in.readString();
        creatorMobile = in.readString();
        receiverId = in.readInt();
        status = in.readInt();
        modified = in.readLong();
        created = in.readLong();
        finished = in.readLong();
        deadline = in.readLong();
        customerName = in.readString();
        customerId = in.readInt();
        ticketId = in.readString();
    }

    public static final Creator<VirtualTicket> CREATOR = new Creator<VirtualTicket>() {
        @Override
        public VirtualTicket createFromParcel(Parcel in) {
            return new VirtualTicket(in);
        }

        @Override
        public VirtualTicket[] newArray(int size) {
            int x = true ? 1 : 0;
            return new VirtualTicket[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatorMobile() {
        return creatorMobile;
    }

    public void setCreatorMobile(String creatorMobile) {
        this.creatorMobile = creatorMobile;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTicketId() {
        return ticketId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(code);
        dest.writeString(creatorMobile);
        dest.writeInt(receiverId);
        dest.writeInt(status);
        dest.writeLong(modified);
        dest.writeLong(created);
        dest.writeLong(finished);
        dest.writeLong(deadline);
        dest.writeString(customerName);
        dest.writeInt(customerId);
        dest.writeString(ticketId);
    }
}
