package kcn.kea.server;

import kcn.kea.view.IView;

import java.util.HashMap;
import java.util.Objects;

public class ViewNameServer
{

    private static HashMap<String, IView> views;

    private ViewNameServer(){ }

    public static HashMap<String, IView> viewMap()
    {
        if(Objects.isNull(views)){views = new HashMap<>();}
        return views;
    }

    public static void setViews(HashMap<String, IView> views)
    {
        ViewNameServer.views = views;
    }
}
