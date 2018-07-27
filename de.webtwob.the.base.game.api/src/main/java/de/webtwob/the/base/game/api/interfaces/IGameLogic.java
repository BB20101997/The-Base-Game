package de.webtwob.the.base.game.api.interfaces;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public interface IGameLogic {

    default double secondsPerUpdate(){
        return 1/32d;
    }

    default void runGameLoop() {
        double loopStart;
        double previous = System.currentTimeMillis();
        double steps     = 0.0;
        double elapsed;

        while (!Thread.interrupted()) {
            loopStart = System.currentTimeMillis();
            elapsed = loopStart - previous;
            steps += elapsed;

            previous = loopStart;


            while (steps >= secondsPerUpdate()) {
                updateGameState();
                steps-=secondsPerUpdate();
            }
        }

    }


    void updateGameState();


}
