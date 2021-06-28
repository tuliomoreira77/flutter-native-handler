package com.example.flutternativehandler;

import com.example.flutternativehandler.annotation.NativeMethod;

public class NativeHandlerTestClass {

    @NativeMethod
    public double returnDoubleReceiveDouble(Double d) {
        return d;
    }

    @NativeMethod
    public int receiveIntegerReturnInteger(Integer i) {
        return i;
    }

    @NativeMethod
    public byte[] receiveByteArrayReturnByteArray(byte[] b) {
        return b;
    }

    @NativeMethod
    public boolean receiveBooleanReturnBoolean(Boolean b) {
        return b;
    }

    @NativeMethod
    public String receiveStringReturnString(String s) {
        return s;
    }

    @NativeMethod
    public Long receiveLongReturnLong(long g) {
        return g;
    }

    @NativeMethod
    public NativeTesteResponse receiveObjectReturnObject(NativeTestRequest request) {
        return new NativeTesteResponse(request.getI()/1.0, request.getS());
    }

}
