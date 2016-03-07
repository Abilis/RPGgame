package ru.rpggame;

import java.util.ArrayList;

/**
 * Created by Abilis on 08.03.2016.
 */
public class Inventory {

    ArrayList<String> inv;

    public Inventory() {

        inv = new ArrayList<String>();
    }

    public void addNewItem(String _newItem) {
        inv.add(_newItem);
    }

    public int getInvSize() {
        return inv.size();
    }

    public void showAllItems() {

        System.out.println("Инвентарь:");
        System.out.println("0: окончить осмотр");

        if (inv.size() > 0) {
            for (int i = 0; i < inv.size(); i++) {
                System.out.println((i + 1) + ": " + inv.get(i));
            }
        }
        else {
            System.out.println("Инвентарь пуст");
        }

    }

    public String useItem (int _itemID) {
        if (_itemID == 0) {
            return "";
        }

        String item = inv.get(_itemID - 1);
        inv.remove(_itemID - 1);
        return item;
    }

}
