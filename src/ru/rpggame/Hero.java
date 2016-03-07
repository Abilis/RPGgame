package ru.rpggame;

/**
 * Created by Abilis on 06.03.2016.
 */
public class Hero extends GameCharacter {

    private int currentExp;
    private int expToNextLevel;
    private int killedEnemies;

    public Hero(String charClass, String name, int hp, int attack, int defense, String description) {

        super(charClass, name, hp, attack, defense, description);
        this.currentExp = 0;
        this.expToNextLevel = 1000;
        this.killedEnemies = 0;
    }

    public void gainExp(int exp) { //метод получения опыта

        currentExp += exp;
        System.out.println(name + " получил " + exp + " единиц опыта");

        if (currentExp > expToNextLevel) {
            currentExp -= expToNextLevel;
            expToNextLevel *= 1.3;
            level++;
            attack += 2;
            defense += 1;
            hpMax += 10;
            hp = hpMax;
            System.out.println(name + " повысил уровень! Атака возросла до " + attack + ", защита - до "
                    + defense + ". Здоровье полностью восстановлено");
        }

    }

    @Override
    public void showInfo() {
        System.out.println(name + ", hp: " + hp + "/" + hpMax + ", уровень: " + level +
                ", опыт: " + currentExp + "/" + expToNextLevel + ". Повержено врагов: " + killedEnemies);
    }

    public void killedEnemiesUp() {
        killedEnemies++;
    }

}
