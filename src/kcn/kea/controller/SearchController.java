package kcn.kea.controller;

import kcn.callbackmethod.CallMe;
import kcn.kea.model.DataMap;
import kcn.kea.model.Genre;
import kcn.kea.model.Track;
import kcn.kea.repository.ITrackRepository;
import kcn.kea.server.ControllerNameServer;
import kcn.kea.server.ViewNameServer;
import kcn.kea.service.GET;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;

public class SearchController implements IController
{
    private ITrackRepository trackRepository;

    private CallMe<DataMap, DataMap> showSearchOptionsCall;

    public SearchController(ITrackRepository trackRepository)
    {
        this.trackRepository = trackRepository;
    }

    /**
     * Implementing method must contain kv-pair matching all methods callable from
     * implementing controller.
     */
    @Override
    public HashMap<String, CallMe<DataMap, DataMap>> controlHooks()
    {
        HashMap<String, CallMe<DataMap, DataMap>> hooks = new HashMap<>();

        showSearchOptionsCall = new CallMe<DataMap, DataMap>(this, "showSearchOptions");
        System.out.println("Callback is broke:\t " + showSearchOptionsCall.isReferenceBroke());

        hooks.put("showSearchOptions", showSearchOptionsCall);

        try
        {
            ServerSocket serverSocket = new ServerSocket(80);
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        return hooks;
    }

    public DataMap showSearchOptions(DataMap dataMap)
    {
        dataMap.map().put("noOfTracks", trackRepository.findAllTracks().size());

        DataMap mainSearchOptions = ViewNameServer.viewMap().get("searchMainView").show(dataMap);

        switch((String)mainSearchOptions.map().get("searchType"))
        {
            case "title":
                dataMap = searchByTitle(dataMap);
                break;
            case "band":
                dataMap = searchByBand(dataMap);
                break;
            case "genre":
                dataMap = searchByGenre(dataMap);
                break;
            default:
                GET.enterToContinue();
        }

        /* play one of results or move on? */
        dataMap = ViewNameServer.viewMap().get("searchResults").show(dataMap);

        if(dataMap.map().containsKey("trackChoice"))
        {
            int trackChoice = (Integer)(dataMap.map().get("trackChoice"));
            if(trackChoice>0&&((List<Track>)(dataMap.map().get("trackList"))).size()>trackChoice){

                List<Track> trackList = (List<Track>)(dataMap.map().get("trackList"));
                Track track = trackList.get(trackChoice);


                dataMap.map().put("trackToPlay",track);
                ViewNameServer.viewMap().get("playView").show(dataMap);
            }



        }

        return dataMap;
    }

    public DataMap searchByTitle(DataMap map)
    {
        List<Track> tracksByName = trackRepository.findTracksByName((String)map.map().get("search"));
        map.map().put("trackList", tracksByName);
        return map;
    }
    public DataMap searchByBand(DataMap map)
    {
        List<Track> tracksByName = trackRepository.findTracksByBandNameContaining((String)map.map().get("search"));
        map.map().put("trackList", tracksByName);
        return map;
    }
    public DataMap searchByGenre(DataMap map)
    {
        List<Track> tracksByName = trackRepository.findTracksByGenre((Genre)map.map().get("search"));
        map.map().put("trackList", tracksByName);
        return map;
    }
}
