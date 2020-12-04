package kcn.kea.view;

import kcn.kea.model.DataMap;
import kcn.kea.service.GET;

public class SearchMainView extends BaseConsoleView implements IView
{

    public SearchMainView()
    {
        setViewName("searchMainView");
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
        StringBuilder queryString = new StringBuilder();
        queryString.append("Would you like to search songs by" +
                           "\n\t1) Title" +
                           "\n\t2) Band Name" +
                           "\n\t3) Genre" +
                           "\n\tor" +
                           "\n\t4) List all songs\n" +
                           "Please enter a number 1-3 to make your choice: \t"
                          );
        switch(GET.getInteger(queryString.toString()))
        {
            case 1:
                map = showTitleSearch(map);
                break;
            case 2:
                map = showBandSearch(map);
                break;
            case 3:
                map = showGenreSearch(map);
                break;
                case 4:
                map.map().put("searchType","listAll");
                break;
            default:
                map.map().put("default", "Number entered not within range 1-3");
                break;
        }
        return map;
    }

    public DataMap showTitleSearch(DataMap map)
    {
        String titleSearch = GET.getString("Enter search:\t");
        map.map().put("search", titleSearch);
        map.map().put("searchType", "title");
        return map;
    }

    public DataMap showBandSearch(DataMap map)
    {
        String bandSearch = GET.getString("Enter band name to search:\n");
        map.map().put("search", bandSearch);
        map.map().put("searchType", "band");
        return map;
    }

    public DataMap showGenreSearch(DataMap map)
    {
        StringBuilder message = new StringBuilder();
        message.append("You can now search songs by genre:\n").
                append("\tThe following genres are available:\n").
                append("\t\tCLASSICAL").
                append("\n\t\tROCK").
                append("\n\t\tJAZZ").
                append("\n\t\tFOLK").
                append("\n\nPlease enter your search:\t");
        String genreSearch = GET.getString(message.toString());
        map.map().put("search", genreSearch);
        map.map().put("searchType", "genre");
        return map;
    }

}
