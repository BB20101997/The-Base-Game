package de.webtwob.the.base.game.api;

import java.util.*;

/**
 * Created by BB20101997 on 17. Jul. 2018.
 */
public final class Mod {

    private final String      id;
    private final Set<String> dependencies;

    public interface StartBuildingMod{
        Dependencies withID(String s);

    }

    public abstract static class Dependencies{

        List<String> deps = new LinkedList<>();

        Dependencies dependsOn(String dep){
            deps.add(dep);
            return this;
        }

        abstract Mod finish();
    }


    private Mod(String id, Collection<String> dependencies) {
        this.id = id;
        if (dependencies.contains(id)) {
            throw new IllegalArgumentException("A Mod cannot depend on itself!");
        }
        this.dependencies = Set.copyOf(dependencies);
    }

    public String id() {
        return id;
    }

    public Set<String> requiredMods() {
        return dependencies;
    }

    public static StartBuildingMod create(){
        return id-> new Dependencies(){
            @Override
            Mod finish() {
                return new Mod(id,this.deps);
            }
        };
    }

}
