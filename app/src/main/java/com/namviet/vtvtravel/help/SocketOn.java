package com.namviet.vtvtravel.help;

public class SocketOn {

    private int i;
    private Object[] args;

    public SocketOn(){

    }

    public SocketOn(int i, Object[] args) {
        this.i = i;
        this.args = args;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
