package ru.rpggame;

/**
 * Created by Abilis on 06.03.2016.
 */
public class GameLogic {

    private Hero[] heroPattern = new Hero[3];
    private Monster[] monsterPattern = new Monster[3];

    private Hero mainHero;
    private Monster currentMonster;

    private int currentRound;


    public void mainGameLoop() { //основная игровая логика





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
