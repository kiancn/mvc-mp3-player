package kcn.kea.server;

import kcn.callbackmethod.CallMe;
import kcn.kea.model.DataMap;

import java.util.HashMap;
import java.util.Objects;


public class ControllerNameServer
{
    private static HashMap<String, CallMe<DataMap, DataMap>> controllerMethods;

    public static CallMe<DataMap, DataMap> call(String controllerMapping)
    {
        return ctrlMap().get(controllerMapping);
    }

    public static HashMap<String, CallMe<DataMap, DataMap>> ctrlMap()
    {
        if(Objects.isNull(controllerMethods))
        {
            controllerMethods = new HashMap<>();
        }
        return controllerMethods;
    }
}
