package de.webtwob.the.base.game.api.annotations;

import java.lang.annotation.*;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
@Documented
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.TYPE})
public @interface RequireMainThread {}
