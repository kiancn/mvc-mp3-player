package kcn.kea.view;

import kcn.kea.model.DataMap;
import kcn.kea.service.GET;

public class MainMenuView extends BaseConsoleView
{
    public MainMenuView()
    {
        viewName = "mainMenuView";
    }

    /**
     * The meat of the thing.
     * The show method prepares data-map for viewing, interprets it for
     * immediate interaction, elicits data from user;
     * sends back a map to controller (for gestation in model-logic
     *
     * @param map
     */
    @Override
    public DataMap show(DataMap map)
    {
        StringBuilder message = new StringBuilder();

        message.append(
                "\tACTUAL-MVC MUSIC PLAYER\n" +
                "\tSponsored by Farmer Dating Mongolia\n" +
                "\t\t\t\t'Ever dream of being kidnapped on horseback\n" +
                "\t\t\t\t\t on your wedding night?" +
                "\n\n\tMain Menu:" +
                "\n\t\t1) Search Collection" +
                "\n\t\t2) Experience Error" +
                "\n\t\t3) Exit"+
                "\n\n\t\tEnter your choice:\t"
                      );
        Integer menuChoice = GET.getInteger(message.toString());
        map.map().put("menuChoice", menuChoice);
        return map;
    }
}
