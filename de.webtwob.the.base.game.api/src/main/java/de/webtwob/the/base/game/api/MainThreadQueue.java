package de.webtwob.the.base.game.api;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class MainThreadQueue {

    private static final System.Logger        LOGGER         = System.getLogger(MainThreadQueue.class.getName());
    public static        long                 MAIN_THREAD_ID = 0;
    public static        boolean              DEBUG_MODE     = false;
    private static       Queue<FutureTask<?>> queue          = new ConcurrentLinkedQueue<>();
    private static Thread.State state = Thread.State.NEW;

    {
        try{
            DEBUG_MODE = Boolean.parseBoolean(System.getProperty("DEBUG"));
        }catch (SecurityException sec){
            LOGGER.log(System.Logger.Level.ERROR,()->"Failed to retrieve Property DEBUG, defaulting to false",sec);
        }
    }

    public static void invokeLater(Runnable runnable) {
        invokeLater(() -> {
            runnable.run();
            return null;
        });
    }

    public static <V> Future<V> invokeLater(Supplier<V> callable) {
        var futur = new FutureTask<>(callable::get);
        queue.add(futur);
        return futur;
    }

    public static void waitAndInvoke(Runnable runnable) {
        waitAndInvoke(() -> {
            runnable.run();
            return null;
        });
    }

    public static <V> V waitAndInvoke(Supplier<V> callable) {
        if (Thread.currentThread().getId() == MAIN_THREAD_ID) {
            return callable.get();
        } else {
            try {
                return invokeLater(callable).get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void runMainThreadQueue() {

        if (Thread.currentThread().getId() != MAIN_THREAD_ID) {
            throw new IllegalStateException("The Main Thread Queue needs to be run on the Main Thread!");
        }

        state = Thread.State.RUNNABLE;

        while (!Thread.interrupted()) {

            var futur = queue.poll();

            if (futur != null) {
                try {
                    futur.run();
                } catch (Exception ignore) {

                }
            }

        }

        state = Thread.State.TERMINATED;

        LOGGER.log(System.Logger.Level.INFO, "Stopping MainThreadQueue");

    }

}
