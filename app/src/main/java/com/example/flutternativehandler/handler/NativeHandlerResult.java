package com.example.flutternativehandler.handler;

public interface NativeHandlerResult {
    public void success(final Object _result);
    public void error(final String errorCode, final String errorMessage, final Object errorDetails);
    public void notImplemented();
}
