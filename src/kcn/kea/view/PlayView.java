package kcn.kea.view;

import kcn.kea.model.DataMap;
import kcn.kea.model.Track;
import kcn.kea.service.GET;
import kcn.kea.service.IPlaySound;
import kcn.kea.service.SoundPlayer;

public class PlayView extends BaseConsoleView implements IView
{
    private IPlaySound player;

    public PlayView()
    {
        player = new SoundPlayer("src/data/error.mp3", true);
        setViewName("playView");

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
        if(map.map().containsKey("trackToPlay"))
        {
            player.play(((Track)(map.map().get("trackToPlay"))).getPathToFile());
        } else
        {
            player.play("");
            GET.enterToContinue("Track Data Not In DataMap.");
        }
        return map;
    }
}
