package ru.rpggame;

import java.util.Objects;

/**
 * Created by Abilis on 06.03.2016.
 */
public class GameCharacter implements Cloneable {

    protected String charClass;
    protected String name;

    public String getName() {
        return name;
    }

    protected int hp;
    protected int hpMax;
    protected int attack;
    protected int defense;

    protected boolean blockStanse;


    public GameCharacter(String charClass, String name, int hp, int attack, int defense) {

        this.charClass = charClass;
        this.name = name;
        this.hp = hp;
        this.hpMax = hp;
        this.attack = attack;
        this.defense = defense;

    }

    public Object clone() {

        try {
            return super.clone();
        }

        catch (CloneNotSupportedException e) {
            System.out.println("Клонирование невозможно!");
            return this;
        }
    }

    public void showInfo() {
        System.out.println("Имя: " + name + ", hp: " + hp + "/" + hpMax);
    }

    public void makeNewRound() {
        blockStanse = false;
    }


}
