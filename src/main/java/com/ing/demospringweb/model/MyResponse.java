package com.ing.demospringweb.model;

public class MyResponse {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "result='" + result + '\'' +
                '}';
    }
}
