package ru.rpggame;

/**
 * Created by Abilis on 06.03.2016.
 */
public class Hero extends GameCharacter {

    private int currentExp;
    private int expToNextLevel;

    public Hero(String charClass, String name, int hp, int attack, int defense) {
        super(charClass, name, hp, attack, defense);
        this.currentExp = 0;
        this.expToNextLevel = 1000;
    }

    public void gainExp(int exp) { //метод получения опыта



    }

}
