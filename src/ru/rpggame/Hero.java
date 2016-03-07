package ru.rpggame;

/**
 * Created by Abilis on 06.03.2016.
 */
public class Hero extends GameCharacter {

    private int currentExp;
    private int expToNextLevel;
    private int killedEnemies;
    private Inventory heroInv;

    public Inventory getHeroInv() {
        return heroInv;
    }

    private boolean inventoryShowed; //переменная, показывающая, смотрел ли герой в инвентарь в текущей итерации цикла

    public boolean getInventoruShowed() {
        return inventoryShowed;
    }

    public void setInventoryShowed(boolean _inventoryShowed) {
        inventoryShowed = _inventoryShowed;
    }

    public Hero(String charClass, String name, int strengh, int agility, int stamina, String description) {

        super(charClass, name, strengh, agility, stamina, description);
        this.currentExp = 0;
        this.expToNextLevel = 100;
        this.killedEnemies = 0;
        heroInv = new Inventory();
        heroInv.addNewItem("Слабое зелье лечения");
    }

    public void gainExp(int exp) { //метод получения опыта

        currentExp += exp;
        System.out.println(name + " получил " + exp + " единиц опыта");

        if (currentExp >= expToNextLevel) {
            currentExp -= expToNextLevel;
            expToNextLevel *= 1.3;
            level++;
            strengh += 2;
            agility += 2;
            stamina += 4;
            setSecondaryParameters(); //при повышении уровня пересчитываем вторичные параметры
            hp = hpMax; //и полностью восстанавливаем здоровье
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

    @Override
    public void makeNewRound() {
        blockStanse = false;
        inventoryShowed = false;
    }

}
