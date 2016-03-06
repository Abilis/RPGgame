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

    public int getHpMax() {
        return hpMax;
    }

    protected int attack;
    protected int defense;

    protected boolean blockStanse;
    protected int critChance;
    protected int level;
    protected boolean life;

    public boolean isAlive() {
        return life;
    }

    protected int inputDamage;

    public GameCharacter(String charClass, String name, int hp, int attack, int defense) {

        this.charClass = charClass;
        this.name = name;
        this.hp = hp;
        this.hpMax = hp;
        this.attack = attack;
        this.defense = defense;
        this.blockStanse = false;
        this.critChance = 10;
        this.level = 1;
        this.life = true;
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

    public int makeAttack() {

        int minAttach = (int)(attack * 0.8f);
        int deltaAttack = (int)(attack * 0.4f);

        int currentAttack = minAttach + GameLogic.rand.nextInt(deltaAttack);

        if (critChance > GameLogic.rand.nextInt(100) ) {

            currentAttack *= 2;
            System.out.println(name + " нанес критический урон в размере " + currentAttack + "единиц урона!");
        }
        else {
            System.out.println(name + " нанес урон в размере " + currentAttack + "единиц урона!");
        }

        return currentAttack;
    }

    public void getDamage(int inputDamage) {

        this.inputDamage -= this.defense; //из входящего урона отнимаем значение защиты

        if (blockStanse) { //если включена защитная стойка, то уменьшаем урон еще раз
            this.inputDamage -= this.defense;
            System.out.println(name + " заблокировал дополнительно " + defense + " единиц урона в защитной стойке!");
        }

        if (this.inputDamage < 0) { //проверка на отрицательный урон
            this.inputDamage = 0;
        }

        System.out.println(name + " получил " + inputDamage + " единиц урона");
        hp -= inputDamage;

        if (hp <= 0) {
            life = false;
        }

    }

}