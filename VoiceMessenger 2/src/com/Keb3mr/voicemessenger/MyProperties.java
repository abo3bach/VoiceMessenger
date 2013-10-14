package com.Keb3mr.voicemessenger;

import java.util.ArrayList;
import java.util.List;

import Message.Message;

public class MyProperties {
    private static MyProperties mInstance= null;

    public List<String> pubMsgids = new ArrayList<String>();

    public List<Message> pubMesslist = new ArrayList<Message>();
    protected MyProperties(){}

    public static synchronized MyProperties getInstance(){
        if(null == mInstance){
                mInstance = new MyProperties();
        }
        return mInstance;
    }
}