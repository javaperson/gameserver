package com.github.javaperson.gameserver;

public class ReturnMessage {
    private Object result;

    public ReturnMessage(Object result) {
        this.result = result;
    }

    public ReturnMessage() {
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
