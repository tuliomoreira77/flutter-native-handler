package com.example.flutternativehandler;

public class NativeTestErrorRequest {
    private Long error;
    private String string;

    public NativeTestErrorRequest(Long error, String s) {
        this.error = error;
        this.string = s;
    }

    public Long getError() {
        return error;
    }

    public void setError(Long error) {
        this.error = error;
    }

    public String getS() {
        return string;
    }

    public void setS(String s) {
        this.string = s;
    }
}
