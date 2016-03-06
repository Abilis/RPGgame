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

        currentExp += exp;
        System.out.println(name + " получил " + exp + " единиц опыта");

        if (currentExp > expToNextLevel) {
            currentExp -= expToNextLevel;
            expToNextLevel *= 1.1;
            level++;
            attack += 5;
            defense += 1;
            hpMax += 50;
            hp = hpMax;
            System.out.println(name + " повысил уровень! Атака возросла до " + attack + ", защита - до "
                    + defense + ". Здоровье полностью восстановлено");
        }

    }

}
