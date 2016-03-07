package ru.rpggame;

import java.io.BufferedReader;
import java.io.IOException;
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

    private String strNumHero;
    private String inputStr;

    private int monsterTern;

    public GameLogic() {
        initGame();
    }


    public void mainGameLoop() { //основная игровая логика

        InputStream inputStream = System.in;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);




        System.out.println("Игра началась!");
        System.out.println("Выберите героя:");

        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + ". " + heroPattern[i].getName() + ". " + heroPattern[i].getDescription());
        }

        try {
            this.strNumHero = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numHero = Integer.parseInt(strNumHero); //считываем введенное пользователем числов

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
            System.out.println("Ход игрока. 1 - атака, 2 - защита, 3 - пропустить ход, 9 - выйти из игры");
            mainHero.makeNewRound(); //сбрасываем параметры для начала нового раунда для героя

            try {
                this.inputStr = bufferedReader.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            int inputNum = Integer.parseInt(this.inputStr);


            currentMonster.makeNewRound(); //сброс параметров для начала нового раунда для монстра
            monsterTern = currentMonster.chooseTern(); //выбор варианта хода монстром

            if (monsterTern == 2) { //если выбор монстра - встать в защитную стойку
                currentMonster.setBlockStanse(); //то включаем ее
            }


            if (inputNum == 1) {

                //герой атакует
                currentMonster.getDamage(mainHero.makeAttack());


                if (!currentMonster.isAlive()) { //если текущий монстр умер

                    System.out.println(currentMonster.getName() + " повержен!");
                    mainHero.gainExp(currentMonster.getHpMax() * 2); //начисляем главному герою опыт
                    mainHero.killedEnemiesUp(); //увеличиваем счетчик убитых монстров

                    System.out.println();

                    //задержка в 2 секунды перед появлением нового монстра
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    currentMonster = (Monster)monsterPattern[rand.nextInt(7)].clone(); //вызываем случайного нового монстра

                    System.out.println("На поле боя выходит новый монстр - " + currentMonster.getName());
                    continue;
                }

            }
            else if (inputNum == 2) {
                //герой защищается
                mainHero.setBlockStanse();
            }
            else if (inputNum == 3) {
                //герой пропускает ход с целью восстановить здоровье
                mainHero.skipTern();

            }
            else if (inputNum == 9) {
                //выход из игры
                break;
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
        heroPattern[0] = new Hero("Рыцарь", "Великий рыцарь", 500, 30, 12, "Сбалансированный герой");
        heroPattern[1] = new Hero("Варвар", "Конан", 600, 50, 0, "Упор в атаку");
        heroPattern[2] = new Hero("Дворф", "Гимли", 400, 20, 30, "Упор в защиту");

        monsterPattern[0] = new Monster("Гуманоид", "Злобный гоблин", 120, 30, 2, "Довольно слабый монстр");
        monsterPattern[1] = new Monster("Гуманоид", "Сильный орк", 240, 50, 2, "Упор в силу");
        monsterPattern[2] = new Monster("Гуманоид", "Могучий тролль", 400, 25, 5, "Упор в живучесть");
        monsterPattern[3] = new Monster("Гуманоид", "Веселый молочник", 300, 25, 8, "Разносит по утрам молоко в бидоне");
        monsterPattern[4] = new Monster("Гуманоид", "Черный ворон", 90, 10, 10, "Слабая птица с отрым клювом");
        monsterPattern[5] = new Monster("Гуманоид", "Оживший скелет", 50, 120, 2, "Мало здоровья, но сильная атака");
        monsterPattern[6] = new Monster("Гуманоид", "Всадник без головы", 220, 15, 7, "Головы нет - пиши пропало!");

        currentRound = 1;
    }

}
