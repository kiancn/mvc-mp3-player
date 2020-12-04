package kcn.kea.view.search;

import kcn.kea.model.DataMap;
import kcn.kea.model.Track;
import kcn.kea.service.GET;
import kcn.kea.view.BaseConsoleView;

import java.util.List;

public class SearchResultsView extends BaseConsoleView
{
    public SearchResultsView()
    {
        setViewName("searchResults");
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
        StringBuilder searchResults = new StringBuilder();

        if(map.map().containsKey("trackList"))
        {
            List<Track> trackList = (List<Track>)(map.map().get("trackList"));

            searchResults.append("\nShowing " + trackList.size() + " search results:\t\t("+map.map().get("searchType").toString()+")");

            searchResults.append("\n\t").append("N").append(")\t\t").append("BAND").append("\t\t").append("TITLE").append("\t\t").append("GENRE").append("\n");

            int count = 0;
            for(Track t : trackList)
            {
                searchResults.append("\n\t").append(count++).append(")\t").append(t.getBand().getBandName()).append("\t").append(t.getTitle()).append("\t").append(t.getGenre().toString());
            }

            searchResults.append("\n\tPlay one of these songs?\t-enter 'N' next to wish\n").
                    append("\tTo abort this search, enter '-1'");

            int trackChoice = GET.getInteger(searchResults.toString());

            map.map().put("trackChoice",trackChoice);

            return map;

        } else
        {
            GET.enterToContinue("No tracks available to handle [." + getViewName() + "]");
        }


        return map;
    }
}
