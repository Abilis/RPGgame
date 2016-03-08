package ru.rpggame;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Abilis on 06.03.2016.
 */
public class Monster extends GameCharacter {

    private int healChance;
    private int attackChance;
    private int defenseChance;
    private ArrayList<String> loot;
    private int lootIndex;

    public Monster(String charClass, String name, int strengh, int agility, int stamina, String description) {

        super(charClass, name, strengh, agility, stamina, description);
        attackChance = 75;
        defenseChance = 25;
        createLoot();
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

    private ArrayList createLoot() {

        loot = new ArrayList<String>();
        loot.add(0, "Слабое зелье лечения");
        loot.add(1, "Среднее зелье лечения");

        return loot;
    }

    public String getLoot() {

        if ( 75 > GameLogic.rand.nextInt(100)) {
            lootIndex = 0;
        }
        else {
            lootIndex = 1;
        }

        System.out.println("От убитого монстра остается " + loot.get(lootIndex));

        return loot.get(lootIndex);
    }



}
