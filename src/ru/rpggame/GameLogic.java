package ru.rpggame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Abilis on 06.03.2016.
 */
public class GameLogic {

    private Hero[] heroPattern = new Hero[3];
    private Monster[] monsterPattern = new Monster[3];

    private Hero mainHero;
    private Monster currentMonster;

    private int currentRound;

    private String strNumHero;

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
            mainHero.showInfo();
            currentMonster.showInfo();

            //ход игрока
            System.out.println("Ход игрока. 1 - атака, 2 - защита, 3 - пропустить ход, 9 - выйти из игры");
            mainHero.makeNewRound(); //сбрасываем параметры для начала нового раунда

            


            break;


        } while (true);




    } //конец основной игровой логики

    public void initGame() { //инициализируется начальное состояние игры
        //задаются шаблоны для главного героя и монстров
        heroPattern[0] = new Hero("Рыцарь", "Великий рыцарь", 600, 30, 12);
        heroPattern[1] = new Hero("Варвар", "Конан", 600, 50, 0);
        heroPattern[2] = new Hero("Дворф", "Гимли", 600, 20, 25);

        monsterPattern[0] = new Monster("Гуманоид", "Злобный гоблин", 120, 30, 2);
        monsterPattern[1] = new Monster("Гуманоид", "Сильный орк", 240, 50, 2);
        monsterPattern[2] = new Monster("Гуманоид", "Могучий тролль", 400, 25, 5);

        currentRound = 1;
    }

}
