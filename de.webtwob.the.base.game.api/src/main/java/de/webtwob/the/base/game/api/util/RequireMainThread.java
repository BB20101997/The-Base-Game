package de.webtwob.the.base.game.api.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.TYPE})
public @interface RequireMainThread {}
