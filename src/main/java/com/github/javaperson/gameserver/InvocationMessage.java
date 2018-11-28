package com.github.javaperson.gameserver;

public class InvocationMessage {
    private int id;
    private Object[] args;

    public InvocationMessage(int id, Object[] args) {
        this.id = id;
        this.args = args;
    }

    public InvocationMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
