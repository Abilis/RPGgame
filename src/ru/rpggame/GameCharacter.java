package ru.rpggame;

/**
 * Created by Abilis on 06.03.2016.
 */
public class GameCharacter {

    protected String charClass;
    protected String name;
    protected int hp;
    protected int attack;
    protected int defense;


    public GameCharacter(String charClass, String name, int hp, int attack, int defense) {

        this.charClass = charClass;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;

    }


}
