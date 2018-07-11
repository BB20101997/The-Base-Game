package de.webtwob.the.base.game.api.service;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IMod {

    String getModId();

    default Collection<String> getRequiredModIDs() { return Collections.emptyList();}

}
