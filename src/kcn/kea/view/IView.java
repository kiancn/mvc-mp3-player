package kcn.kea.view;

import kcn.kea.model.DataMap;

public interface IView
{
    /** Method could be implemented in each view, or inherited from a base view class. */
    DataMap fail(DataMap map);
    /** The meat of the thing.
     * The show method shows prepared data-map for viewing, interprets it for
     * immediate interaction, elicits data from user - saving it to data-map;
     * sends back the map to controller (for gestation in model-logic) */
    DataMap show(DataMap map);
}
