package com.ing.demospringweb.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CustomErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timeStamp;
    private int status;
    private String error;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
