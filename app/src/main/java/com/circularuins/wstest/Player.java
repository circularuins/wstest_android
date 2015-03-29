package com.circularuins.wstest;

import java.util.ArrayList;

/**
 * Created by wake on 2014/12/30.
 */
public class Player {

    private int hp;
    private int mp;
    private int atk;
    private int def;
    private int spd;
    private ArrayList<Command> cmds = new ArrayList<Command>();

    public Player(int hp, int mp, int atk, int def, int spd) {
        this.hp = hp;
        this.mp = mp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
    }

    //コマンドの追加
    public void addCmd(Command cmd) {
        cmds.add(cmd);
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getSpd() {
        return spd;
    }

    public ArrayList<Command> getCmds() {
        return cmds;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }
}
