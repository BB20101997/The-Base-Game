package de.webtwob.the.base.game.main;

import java.util.stream.Stream;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class Main {

    public static void main(String[] args) {
        if(Stream.of(args).anyMatch("-server"::equals)){
            MainServer.main(args);
        }else {
            MainClient.main(args);
        }
    }

}
