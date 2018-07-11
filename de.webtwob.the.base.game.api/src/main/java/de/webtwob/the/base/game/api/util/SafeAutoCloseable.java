package de.webtwob.the.base.game.api.util;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public interface SafeAutoCloseable extends AutoCloseable {

    @Override
    void close();

}
