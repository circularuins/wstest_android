package com.circularuins.wstest;

/**
 * Created by wake on 2014/12/30.
 */
public class Command {

    private String name;
    private int atk;
    private int cost;

    public Command(String name, int atk, int cost) {
        this.name = name;
        this.atk = atk;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getAtk() {
        return atk;
    }

    public int getCost() {
        return cost;
    }
}
