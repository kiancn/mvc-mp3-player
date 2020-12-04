package kcn.kea.view;

import kcn.kea.model.DataMap;
import kcn.kea.presentation.Console;
import kcn.kea.service.GET;

import java.util.Map;
import java.util.Set;

public abstract class BaseConsoleView implements IView
{
    protected String viewName = "unspecified";


    @Override
    public DataMap fail(DataMap map)
    {
        map.map().put("failingName",viewName);

        Console console = new Console(System.out);
        StringBuilder sB = new StringBuilder();

        sB.append("Loading view failed. DataMap for viewing pleasure;");

        /* printing content of map, help narrow in on errors */
        Set<Map.Entry<String, Object>> entrySet = map.map().entrySet();
        for(Map.Entry<String, Object> e : entrySet)
        {
            sB.append("\n").append("K: [").append(e.getKey()).append("]\tV:[").append(e.getValue().toString()).append("]");
        }

       GET.enterToContinue(sB.toString());

        return map;
    }

    /**
     * Method performs a shallow check on supplied map,
     */
    private boolean verifyDataMap(DataMap map, String[] strings)
    {

        for(String mapKey : strings)
        {
            if(!map.map().containsKey(mapKey)){return false;}
        }

        return true;
    }

    public String getViewName()
    {
        return viewName;
    }

    public void setViewName(String viewName)
    {
        this.viewName = viewName;
    }
}
