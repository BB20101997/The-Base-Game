package de.webtwob.the.base.game.api;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class MainThreadQueue {


    private  static Queue<FutureTask<?>> queue = new ConcurrentLinkedQueue<>();

    public static long MAIN_THREAD_ID = 0;

    public static void  invokeLater(Runnable runnable){
        invokeLater(()->{runnable.run();return null;});
    }

    public static  <V> Future<V> invokeLater(Callable<V> callable){
        var futur = new FutureTask<>(callable);
        queue.add(futur);
        return futur;
    }

    public static void waitAndInvoke(Runnable runnable) {
        waitAndInvoke(()->{runnable.run();return null;});
    }

    public static <V> V waitAndInvoke(Callable<V> callable){
        if(Thread.currentThread().getId()==MAIN_THREAD_ID){
            try {
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
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

    public static void runMainThreadQueue(){

        if(Thread.currentThread().getId()!=MAIN_THREAD_ID){
            throw  new IllegalStateException("The Main Thread Queue needs to be run on the Main Thread!");
        }

        while (!Thread.interrupted()){

            var futur = queue.poll();

            if(futur!=null){
                try {
                    futur.run();
                } catch (Exception ignore){

                }
            }

        }

    }

}
