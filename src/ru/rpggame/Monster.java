package ru.rpggame;

/**
 * Created by Abilis on 06.03.2016.
 */
public class Monster extends GameCharacter {

    private int healChance;
    private int attackChance;
    private int defenseChance;

    public Monster(String charClass, String name, int strengh, int agility, int stamina, String description) {

        super(charClass, name, strengh, agility, stamina, description);
        attackChance = 75;
        defenseChance = 25;
    }

    public int chooseTern () {

        if (hp < 0.5 * hpMax) { //если здоровье монстра меньше 50% от максимального, есть вероятность, что он подлечится
            healChance = 80 - (100 * hp / hpMax);

            if (healChance > GameLogic.rand.nextInt(100)) {
                return 3;
            }
        }
        //если монстр не лечится, то с вероятностью 75% атакует и с вероятностью 25% встает в защитную стойку
        if (attackChance > GameLogic.rand.nextInt(100)) {
            return 1;
        }
        else {
            return 2;
        }

    }

}
