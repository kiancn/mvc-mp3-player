package kcn.callbackmethod;

import java.util.ArrayList;
import java.util.List;
/* 2019/09/19 added simple logging feature when a callback method has failed and is removed, and other
adjustments */
/*2019/11/04 renamed MethodPack to CallbackPack */

/**
 * A CallbackMethodPack contains a list of CallbackMethods;
 * <p>  - it is able to execute a collection of no-parameter, no/void return type methods.
 * <p>  - it is able to execute a collection of single <V> parameter and no/void return type methods.
 * <p>
 * <p> - for other tasks contained CallbackMethods are intended to be accessed and iterated over through
 * getMethods() .
 * <p> - Automatic checking for bad references is enabled by default:
 * </p><p>enableAutoHandlingOfBadReferences(false) will turn off error checking.</p>
 * <p><b>  - the CallbackMethodPack it is not strong on compiler-time type checking, be careful.
 * </b>
 * <p>
 * - the generic sibling CallMePack is much more versatile and much more type-safe
 *
 * <p>
 */
public class CallbackPack
{
    private List<CallbackMethod> methods;

    private boolean automaticErrorChecks; /* value of boolean effects all run method*/
    private int removedMethodsCount;
    private ArrayList<String> removedMethodsNamesList;

    public CallbackPack()
    {
        methods = new ArrayList<>();
        removedMethodsNamesList = new ArrayList<>();
        removedMethodsCount = 0;
    }

    public List<CallbackMethod> getMethods()
    {
        return methods;
    }

    /**
     * Method executes a collection of no-parameter, no/void return type methods.
     * Actually, it is run() on each CallbackMethod in methods that is executed...
     **/
    public void run()
    {
        if(automaticErrorChecks){ handleBadReferences(); }

        for(CallbackMethod method : methods) { method.run(); }
    }

    /**
     * Method executes a collection of <V> parameter and <V> return type.
     */
    public <V> void run(V... values)
    {
        if(automaticErrorChecks){ handleBadReferences(); }

        for(CallbackMethod method : methods) { method.run(values); }
    }


    /**
     * Method adds a MethodReference object to internal list
     **/
    public boolean add(CallbackMethod method)
    {
        methods.add(method);
        return true;
    }

    /**
     * Method removes a MethodReference object to internal list
     **/
    public boolean remove(CallbackMethod method)
    {
        if(methods.contains(method))
        {
            methods.remove(method);
            return true;
        } else return false;
    }

    /**
     * Method enables or disables automatic null-checks.
     * <p> Notice that the MethodPack </p>
     *
     * <p> Method does not perform any checks itself; going
     * forward, all methods (MeRefs) will be checked. </p>
     * <p><b>NB: Not enabled by default</b>, which maybe it
     * should be - but I prefer responsibility to safety.
     * <p></p>
     * <p> Note that MethodReferences check+log themselves internally.
     * They return null if they are somehow broken.</p>
     * </p>
     */
    public void enableAutoHandlingOfBadReferences(boolean turnOnAutomaticHandlingOfBadMeRefs)
    {
        automaticErrorChecks = turnOnAutomaticHandlingOfBadMeRefs;
    }

    /**
     * method removes 'dead' MethodReferences from list (I know it all fits one line)
     * <p> this is public to allow manual cleaning; </p>
     **/
    public void handleBadReferences()
    {
        for(CallbackMethod callback : methods)
        {
            if(callback.isReferenceBroke())
            {
                /* getting the name down before ejecting the bad apple */
                removedMethodsNamesList.add(callback.getMethodObject().getName());
                methods.remove(callback);
                removedMethodsCount++;
            }
        }
    }

    /**
     * Get length of methods list.
     */
    public int length(){ return methods.size(); }
    /**
     * Returns the number of methods removed from this pack; a method is removed if it reports that it is
     * any kind of faulty.
     */
    public int getRemovedMethodsCount(){ return removedMethodsCount; }

    public ArrayList<String> getRemovedMethodsNamesList(){ return removedMethodsNamesList; }
}
