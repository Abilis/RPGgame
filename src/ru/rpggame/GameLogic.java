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

    private Hero[] heroPattern = new Hero[3];
    private Monster[] monsterPattern = new Monster[3];

    private Hero mainHero;
    private Monster currentMonster;

    private int currentRound;

    private String strNumHero;
    private String inputStr;

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
            System.out.println((i + 1) + ". " + heroPattern[i].getName());
        }

        try {
            this.strNumHero = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numHero = Integer.parseInt(strNumHero); //считываем введенное пользователем числов

        mainHero = (Hero)heroPattern[numHero - 1].clone(); //создаем героя путем копирования из шаблона
        System.out.println("Вы выбрали героя " + mainHero.getName());

        currentMonster = (Monster)monsterPattern[0].clone(); //создаем монстра путем копирования из шаблона

        do {
            //выводим текущую информацию о игроке и противнике в начале каждого раунда
            System.out.println("Раунд: " + currentRound);
            mainHero.showInfo();
            currentMonster.showInfo();

            //ход игрока
            System.out.println("Ход игрока. 1 - атака, 2 - защита, 3 - пропустить ход, 9 - выйти из игры");
            mainHero.makeNewRound(); //сбрасываем параметры для начала нового раунда

            try {
                this.inputStr = bufferedReader.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            int inputNum = Integer.parseInt(this.inputStr);

            if (inputNum == 1) {

                //герой атакует
                currentMonster.getDamage(mainHero.makeAttack());

                if (!currentMonster.isAlive()) { //если текущий монстр умер

                    mainHero.gainExp(currentMonster.getHpMax() * 2); //начисляем главному герою опыт
                    currentMonster = (Monster)monsterPattern[rand.nextInt(3)].clone(); //вызываем случайного нового монстра

                    System.out.println("На поле боя выходит новый монстр - " + currentMonster.getName());
                }

            }
            else if (inputNum == 2) {
                //герой защищается
            }
            else if (inputNum == 3) {
                //герой пропускает ход
            }
            else if (inputNum == 9) {
                //выход из игры
                break;
            }

            //ход монстра
            currentMonster.makeNewRound();

            //монстр атакует главного героя
            mainHero.getDamage(currentMonster.makeAttack());


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


        System.out.println("Игра окончена");
    } //конец основной игровой логики

    public void initGame() { //инициализируется начальное состояние игры
        //задаются шаблоны для главного героя и монстров
        heroPattern[0] = new Hero("Рыцарь", "Великий рыцарь", 500, 30, 12);
        heroPattern[1] = new Hero("Варвар", "Конан", 600, 50, 0);
        heroPattern[2] = new Hero("Дворф", "Гимли", 400, 20, 30);

        monsterPattern[0] = new Monster("Гуманоид", "Злобный гоблин", 120, 30, 2);
        monsterPattern[1] = new Monster("Гуманоид", "Сильный орк", 240, 50, 2);
        monsterPattern[2] = new Monster("Гуманоид", "Могучий тролль", 400, 25, 5);

        currentRound = 1;
    }

}
