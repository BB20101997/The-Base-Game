package de.webtwob.the.base.game.api.service;

import de.webtwob.the.base.game.api.Mod;

import java.util.function.Supplier;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
@FunctionalInterface
public interface IModFactory extends Supplier<Mod> {

}
