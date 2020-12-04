package kcn.kea.controller;

import kcn.callbackmethod.CallMe;
import kcn.kea.model.DataMap;

import java.util.HashMap;

public interface IController
{
    /** Implementing method must contain kv-pair matching all methods callable from
     * implementing controller. */
    HashMap<String, CallMe<DataMap,DataMap>> controlHooks();

}
