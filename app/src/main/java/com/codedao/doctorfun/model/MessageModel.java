package com.codedao.doctorfun.model;

/**
 * Created by Bruce Wayne on 02/04/2017.
 */

public class MessageModel {
    private String mMessage;
    private int mType;

    public MessageModel() {
    }

    public MessageModel(String mMessage, int mType) {
        this.mMessage = mMessage;
        this.mType = mType;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }
}
