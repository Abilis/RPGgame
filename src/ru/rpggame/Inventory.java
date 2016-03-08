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
        sortInv();
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
        return item;
    }

    public void removeItemFromInventory (String _itemID) {
        inv.remove(_itemID);
    }

    private void sortInv() {

        for (int i = 0; i < inv.size(); i++) {

            for (int j = 0; j < inv.size() - 1; j++) {

                if (isStr1EarlyThanStr2(inv.get(j + 1), inv.get(j))) { //если первая строка раньше второй
                                                                        //то нужно поменять их местами
                    String tmp = inv.get(j);
                    inv.set(j, inv.get(j + 1));
                    inv.set(j + 1, tmp);
                }
            }

        }

    }

    private boolean isStr1EarlyThanStr2 (String str1, String str2) {

        int more =  str1.compareToIgnoreCase(str2);
        if (more > 0) {
            return false;
        }
        else {
            return true;
        }
    }

}
