package kcn.kea.view;

import kcn.kea.model.DataMap;

public class FailureView extends BaseConsoleView
{
    public FailureView()
    {
        setViewName("failure");
    }

    /**
     * The meat of the thing.
     * The show method shows prepared data-map for viewing, interprets it for
     * immediate interaction, elicits data from user - saving it to data-map;
     * sends back the map to controller (for gestation in model-logic)
     *
     * @param map
     */
    @Override
    public DataMap show(DataMap map)
    {
        return fail(map);
    }
}
