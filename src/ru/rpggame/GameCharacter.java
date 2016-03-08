package ru.rpggame;
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

    //первичные параметры
    protected int strengh;
    protected int agility;
    protected int stamina;

    //вторичные параметры
    protected int attack;
    protected int defense;
    protected float avoidChance;
    protected boolean blockStanse;
    protected int critChance;

    protected int level;
    protected boolean life;

    public boolean isAlive() {
        return life;
    }

    protected String description;

    public String getDescription() {
        return description;
    }

    public GameCharacter(String charClass, String name, int strengh, int agility, int stamina, String description) {

        this.charClass = charClass;
        this.name = name;
        this.blockStanse = false;
        this.level = 1;
        this.life = true;
        this.description = description;

        this.strengh = strengh;
        this.agility = agility;
        this.stamina = stamina;

        setSecondaryParameters();
        this.hp = hpMax;
    }

    protected void setSecondaryParameters() {

        this.attack = strengh * 2;
        this.hpMax = stamina * 4;
        this.defense = agility / 2;
        this.critChance = agility;
        this.avoidChance = (float)(3 + agility / 5);

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
        System.out.println(name + ", hp: " + hp + "/" + hpMax);
    }

    public void setBlockStanse() {
        blockStanse = true;
        System.out.println(name + " встал в защитную стойку!");
    }

    public void makeNewRound() {
        blockStanse = false;

    }

    public int makeAttack() {

        int minAttach = (int)(attack * 0.8f);
        int deltaAttack = (int)(attack * 0.4f);

        int currentAttack = minAttach + GameLogic.rand.nextInt(deltaAttack);

        if (critChance >= GameLogic.rand.nextInt(100) ) {

            currentAttack *= 2;
            System.out.println(name + " провел критическую атаку на " + currentAttack + " единиц урона!");
        }
        else {
            System.out.println(name + " провел атаку на " + currentAttack + " единиц урона!");
        }

        return currentAttack;
    }

    public void getDamage(int inputDamage) {

        if (avoidChance < GameLogic.rand.nextInt(100)) {

            int minDefense = defense - (int)(defense * 0.7f);
            int deltaDefense =(int)(defense * 0.6f);

            int currentDefense = minDefense + GameLogic.rand.nextInt(deltaDefense);

            inputDamage -= currentDefense; //из входящего урона отнимаем получившееся значение защиты

            if (blockStanse) { //если включена защитная стойка, то уменьшаем урон еще раз
                inputDamage -= currentDefense;
                System.out.println(name + " заблокировал дополнительно " + currentDefense + " единиц урона в защитной стойке!" +
                        " Всего заблокировано " + (2 * currentDefense) + " единиц урона");
            }

            if (inputDamage < 0) { //проверка на отрицательный урон
                inputDamage = 0;
            }

            System.out.println(name + " получил " + inputDamage + " единиц урона");
            hp -= inputDamage;

            if (hp <= 0) {
                life = false;
            }
        }
        else {
            System.out.println(name + " увернулся от атаки!");
        }

    }

    public void skipTern() {

        int heal;

        if (hp < hpMax / 2) {
            heal = (int)(0.15 * hpMax);
        }
        else {
            heal = (int)(0.1 * hpMax);
        }

        if (hp + heal > hpMax) {
            heal = hpMax - hp;
        }

        hp += heal;

        System.out.println(name + " решил в этот раз не атаковать, а регенерировать. Боевая медитация принесла " +
                            heal + " единиц здоровья");
    }

    public void cure (int _cure) {

        hp += _cure;

        if (hp > hpMax) {
            hp = hpMax;
            System.out.println(name + " полностью восстановил здоровье!");
        }
        else {
            System.out.println(name + " восстановил здоровье на " + _cure + " единиц!");
        }

    }

    public boolean useItem (String _item) {

        switch (_item) {

            case "Слабое зелье лечения":
                System.out.println(name + " использовал слабое зелье лечения!");
                cure(50);
                return true;

            case "Среднее зелье лечения":
                System.out.println(name + " использовал среднее зелье лечения!");
                cure(100);
                return true;

            default:
                System.out.println("Этот предмет невозможно использовать!");
                return false;
        }
    }

}
