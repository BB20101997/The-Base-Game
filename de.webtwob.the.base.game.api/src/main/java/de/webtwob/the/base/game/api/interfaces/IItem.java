package de.webtwob.the.base.game.api.interfaces;

import de.webtwob.the.base.game.api.interfaces.function.IModAssociated;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IItem<DATA> extends IModAssociated {

    /**
     * @param  itemData an object containing the items data //TODO replace with proper Object type
     * @return the localisation key based on the itemData
     * */
    String getLocalisationKey(DATA itemData);

}
