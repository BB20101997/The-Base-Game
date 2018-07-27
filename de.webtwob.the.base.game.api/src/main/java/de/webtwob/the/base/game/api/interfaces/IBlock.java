package de.webtwob.the.base.game.api.interfaces;

import de.webtwob.the.base.game.api.interfaces.function.ILocalizable;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IBlock<DATA> extends IRegistrable , ILocalizable {

    String getLocalisationKey(DATA blockData);

}
