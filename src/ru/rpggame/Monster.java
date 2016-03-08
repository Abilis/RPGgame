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
        loot.add(2, "Древняя истертая монета");
        loot.add(3, "Треснутый меч");
        loot.add(4, "Стеклянный глаз");
        loot.add(5, "Треугольный пакет от молока");
        loot.add(6, "Шкура тролля");
        loot.add(7, "Бедренная кость");
        loot.add(8, "Перо черной птицы");
        loot.add(9, "Сломанное стремя");
        loot.add(10, "Сломанный топор войны");

        return loot;
    }

    public String getLoot() {

        int randomLoot = GameLogic.rand.nextInt(100);

        if (randomLoot >= 0 && randomLoot < 10) {
            lootIndex = 0;
        }
        else if (randomLoot >= 10 && randomLoot < 15) {
            lootIndex = 1;
        }
        else if (randomLoot >= 15 && randomLoot < 25) {
            lootIndex = 2;
        }
        else if (randomLoot >= 25 && randomLoot < 35) {
            lootIndex = 3;
        }
        else if (randomLoot >= 35 && randomLoot < 45) {
            lootIndex = 4;
        }
        else if (randomLoot >= 45 && randomLoot < 55) {
            lootIndex = 5;
        }
        else if (randomLoot >= 55 && randomLoot < 65) {
            lootIndex = 6;
        }
        else if (randomLoot >= 65 && randomLoot < 75) {
            lootIndex = 7;
        }
        else if (randomLoot >= 75 && randomLoot < 85) {
            lootIndex = 8;
        }
        else if (randomLoot >= 85 && randomLoot < 95) {
            lootIndex = 9;
        }
        else if (randomLoot >= 95 && randomLoot < 100) {
            lootIndex = 10;
        }


        System.out.println("От убитого монстра остается " + loot.get(lootIndex));

        return loot.get(lootIndex);
    }



}
