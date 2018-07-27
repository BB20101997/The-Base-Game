package de.webtwob.the.base.game.api.util;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class RegistryID {

    private static Map<String, RegistryID> idMap = new HashMap<>();
    private final  String                  modID;
    private final  String                  objectID;
    private final  String                  registryID;

    public RegistryID(final String regID) {
        registryID = regID;
        var split = regID.split("~", 3);
        modID = split[0];
        objectID = split[1];
    }

    public static RegistryID getID(String idString) {
        if (idString.split("~").length > 2) {
            //noinspection HardcodedFileSeparator
            throw new IllegalArgumentException("The ModID/ObjectID must not contain the '~' Character!");
        }
        return idMap.computeIfAbsent(idString, RegistryID::new);
    }

    public static RegistryID createRegistryID(String modID, String objectID) {

        var registryID = modID + '~' + objectID;
        if (registryID.split("~").length > 2) {
            //noinspection HardcodedFileSeparator
            throw new IllegalArgumentException("The ModID/ObjectID must not contain the '~' Character!");
        }

        return idMap.computeIfAbsent(registryID, RegistryID::new);
    }

    public String modID() {
        return modID;
    }

    public String objectID() {
        return objectID;
    }

    @Contract(pure = true)
    @Override
    public String toString() {
        return registryID;
    }
}
