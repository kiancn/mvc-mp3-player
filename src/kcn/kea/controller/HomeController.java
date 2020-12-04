package kcn.kea.controller;

import kcn.callbackmethod.CallMe;
import kcn.kea.model.DataMap;
import kcn.kea.repository.ITrackRepository;
import kcn.kea.server.ControllerNameServer;
import kcn.kea.server.ViewNameServer;

import java.util.HashMap;

public class HomeController implements IController
{
    private ITrackRepository trackRepository;

    public HomeController(ITrackRepository trackRepository)
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

        hooks.put("showMainMenu", new CallMe<DataMap, DataMap>(this, "showMainMenu"));

        return hooks;
    }

    public DataMap showMainMenu(DataMap dataMap)
    {

        do
        {
            /* testy */
            dataMap = ViewNameServer.viewMap().get("mainMenuView").show(dataMap);

            switch((Integer)dataMap.map().get("menuChoice"))
            {
                case 1:
                    dataMap = ControllerNameServer.ctrlMap().get("showSearchOptions").run(dataMap);
                    break;
                case 2:
                    dataMap = ViewNameServer.viewMap().get("failure").show(dataMap);
                    break;
                case 3:
                    dataMap.map().put("exitSignal", true);
            }


        } while(!dataMap.map().containsKey("exitSignal"));

        return dataMap;

    }
}
