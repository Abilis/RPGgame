package ru.rpggame;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Abilis on 06.03.2016.
 */
public class GameLogic {

    public static Random rand = new Random();

    private Hero[] heroPattern = new Hero[7];
    private Monster[] monsterPattern = new Monster[7];

    private Hero mainHero;
    private Monster currentMonster;

    private int currentRound;

    private int monsterTern;

    public GameLogic() {
        initGame();
    }

    private boolean gameOver;



    public void mainGameLoop() { //основная игровая логика

        System.out.println("Игра началась!");

        System.out.println("Возможные герои:");

        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + ". " + heroPattern[i].getName() + ". " + heroPattern[i].getDescription());
        }

        int numHero = getAction(1, 3, "Выберите героя:"); //Выбор героя игроком


        mainHero = (Hero)heroPattern[numHero - 1].clone(); //создаем героя путем копирования из шаблона
        System.out.println("Вы выбрали героя " + mainHero.getName());

        currentMonster = (Monster)monsterPattern[rand.nextInt(7)].clone(); //создаем монстра путем копирования из шаблона

        do {
            //выводим текущую информацию о игроке и противнике в начале каждого раунда
            System.out.println("Раунд: " + currentRound);
            mainHero.showInfo();
            System.out.println("<против>");
            currentMonster.showInfo();

            //ход игрока
            mainHero.makeNewRound(); //сбрасываем параметры для начала нового раунда для героя


            int inputNum = getAction(0, 4, "Ход игрока. 1 - атака, 2 - защита, 3 - подлечиться," +
                    " 4 - инвентарь, 0 - выйти из игры");


            currentMonster.makeNewRound(); //сброс параметров для начала нового раунда для монстра
            monsterTern = currentMonster.chooseTern(); //выбор варианта хода монстром

            if (monsterTern == 2) { //если выбор монстра - встать в защитную стойку
                currentMonster.setBlockStanse(); //то включаем ее
            }


            switch (inputNum) {

                case 1:
                    //герой атакует
                    currentMonster.getDamage(mainHero.makeAttack());


                    if (!currentMonster.isAlive()) { //если текущий монстр умер

                        System.out.println(currentMonster.getName() + " повержен!");
                        mainHero.gainExp(currentMonster.getHpMax() * 2); //начисляем главному герою опыт
                        mainHero.killedEnemiesUp(); //увеличиваем счетчик убитых монстров

                        //даем с монстра случайный лут с вероятностью 33%
                        if (100 > rand.nextInt(100)) {
                            mainHero.getHeroInv().addNewItem(currentMonster.getLoot());
                        }
                        System.out.println();

                        //задержка в 2 секунды перед появлением нового монстра
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        currentMonster = (Monster) monsterPattern[rand.nextInt(7)].clone(); //вызываем случайного нового монстра

                        System.out.println("На поле боя выходит новый монстр - " + currentMonster.getName());
                        continue;
                    }
                    break;

                case 2:
                    //герой защищается
                    mainHero.setBlockStanse();
                    break;

                case 3:
                    //герой пропускает ход с целью восстановить здоровье
                    mainHero.skipTern();
                    break;

                case 4:
                    //вызор инвентаря героя

                    mainHero.getHeroInv().showAllItems();
                    int invInput = getAction(0, mainHero.getHeroInv().getInvSize(), "Выберите предмет для использования: ");

                    String usedItem = mainHero.getHeroInv().useItem(invInput);

                    if (usedItem != "") {
                        mainHero.setUsedItemFromInventorySuccess(mainHero.useItem(usedItem));

                        if (mainHero.getUsedItemFromInventorySuccess()) { //если применение предмета из инвентаря успешно
                            mainHero.getHeroInv().removeItemFromInventory(usedItem); //то удаляем этот предмет из инвентаря
                        }

                    }
                    else {
                        System.out.println(mainHero.getName() + " решил ничего не использовать");
                        mainHero.setInventoryShowed(true);
                    }

                    break;

                case 0:
                    //выход из игры
                    gameOver = true;
                    break;

            }

            //обработка выхода из цикла
            if (gameOver) {
                break;
            }

            //если был осмотр инвентаря - прерываем текущую итерацию цикла, чтобы монстр не бил героя
            if (mainHero.getInventoruShowed()) {
                continue;
            }

            //если применение предмета из инвентаря было неуспешно - прерываем текущую итерацию цикла
            if (!mainHero.getUsedItemFromInventorySuccess()) {
                continue;
            }

            //ход монстра

            switch (monsterTern) {

                case 1: mainHero.getDamage(currentMonster.makeAttack()); //монстр атакует главного героя
                    break;

                //защитная стойка уже включана, если выпала она. Поэтому здесь пропускаем

                case 3: currentMonster.skipTern(); //монстр пропускает ход, чтобы подлечиться
                    break;

            }



            //Если главный герой умер - выходим из цикла
            if (!mainHero.isAlive()) {
                System.out.println(mainHero.name + " скончался от ранений!");
                break;
            }

            currentRound++; //увеличиваем счетчик раундов

        } while (true);

        //объявляем результаты игры
        if (!mainHero.isAlive()) {
            System.out.println("Победил " + currentMonster.name);
        }

        if (!currentMonster.isAlive()) {
            System.out.println("Победил " + mainHero.name);
        }

        if (mainHero.isAlive() && currentMonster.isAlive()) {
            System.out.println(mainHero.name + " сбежал с поля боя!");
        }


        System.out.println("Игра окончена. Количество раундов: " + currentRound);
    } //конец основной игровой логики

    public void initGame() { //инициализируется начальное состояние игры
        //задаются шаблоны для главного героя и монстров
        heroPattern[0] = new Hero("Рыцарь", "Великий рыцарь", 12, 12, 20, "Сбалансированный герой");
        heroPattern[1] = new Hero("Варвар", "Конан", 18, 5, 20, "Упор в атаку");
        heroPattern[2] = new Hero("Дворф", "Гимли", 7, 20, 20, "Упор в защиту");

        monsterPattern[0] = new Monster("Гуманоид", "Злобный гоблин", 10, 10, 15, "Довольно слабый монстр");
        monsterPattern[1] = new Monster("Гуманоид", "Сильный орк", 15, 5, 15, "Упор в силу");
        monsterPattern[2] = new Monster("Гуманоид", "Могучий тролль", 10, 15, 30, "Упор в живучесть");
        monsterPattern[3] = new Monster("Гуманоид", "Веселый молочник", 13, 13, 15, "Разносит по утрам молоко в бидоне");
        monsterPattern[4] = new Monster("Гуманоид", "Черный ворон", 5, 25, 10, "Слабая птица с отрым клювом");
        monsterPattern[5] = new Monster("Гуманоид", "Оживший скелет", 25, 5, 7, "Мало здоровья, но сильная атака");
        monsterPattern[6] = new Monster("Гуманоид", "Всадник без головы", 12, 15, 15, "Головы нет - пиши пропало!");

        currentRound = 1;
        gameOver = false;
    }

    public int getAction(int min, int max, String str) {

        InputStream inputStream = System.in;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        int x = 1;

        do {

            if (str != "") {
                System.out.println(str);
            }

            try {
                String strInputAction = bufferedReader.readLine();
                x = Integer.parseInt(strInputAction);
            }
            catch (Exception e) {
                x = 1;
            }

        } while (x < min || x > max);

        return x;
    }

}