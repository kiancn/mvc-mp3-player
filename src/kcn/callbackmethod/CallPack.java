package kcn.callbackmethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class instances are able to hold and pass around method-references.
 *
 * <p> There are supplied only two run-methods in this class, both return void.
 * <p></p><i> To use/run/execute the individual MeRefs and use their returns in meaningful ways,
 * call getMeRefs() and access each by index, and use the specifically needed run..(..) variant
 * you need from there. </i>
 * <p><p></p> This is a design decision, and I made it like this because
 * it makes it very transparent what you/user ends up doing with the return value.
 * </p></p><p></p>
 * <p><i><b>
 * Please also note different possible approaches to handling bad MeRefs in MePacks:
 * </i></b><i><p>
 * 1) Continuous checking (every run) is turned OFF by default,</i>
 * turn it on by running enableAutoHandleBrokeReferences(true):
 * the effect is that the MePack checks each MeRef before execution and
 * removes any broken instances.
 *
 * <p><i> 2) Single checks for bad references and removal of broke refs
 * can be achieved by running handleBrokeReferences().</i>
 * <b><p></p>
 * A) Please also note that a broken MeRef will NOT throw any exceptions,
 * even if they are broken, because the MeRef 'error'-checks itself internally,
 * logs any exceptions or null references and returns either null
 * or void depending on occasion.</b><p>
 * This means that a broken MeRef can be run from the MePack without
 * crashing that system.
 * It also means that user needs to take direct responsibility for broken MeRefs.
 */
public class CallPack<V, O>
{
    private List<CallMe<V, O>> methods; /* contained MeRefs*/
    private boolean alwaysCheckIfBroke; /* value of boolean effects all run methods */
    private ArrayList<String> removedMethodNames; /* names of dysfunctional, removed MeRefs */

    /**
     * Default Constructor; V and O are given, but no methods.
     * <p> NB. Not supplying anything inside the diamonds, ie. defaulting V,O
     * gives V, O the values of Object, Object </p>
     */
    public CallPack()
    {
        this.methods = new ArrayList<CallMe<V, O>>();
        removedMethodNames = new ArrayList<String>();
    }

    /**
     * Constructor; V and O are given, and list of MeRefs.
     * <p> Take note that the supplied list is not itself 'saved', but used
     * to copy the reference to each member MeRef; so the supplied list is
     * not further tied to the function of the MePack.
     * <p>
     */
    public CallPack(List<CallMe<V, O>> methodReferences)
    {
        this.methods = new ArrayList<CallMe<V, O>>();
        methods.addAll(methodReferences);
        removedMethodNames = new ArrayList<String>();
    }

    /**
     * Method returns the internal list of MeRefs for your running pleasure.
     */
    public List<CallMe<V, O>> getMeRefs()
    {
        return methods;
    }

    /**
     * Method executes run() on each MeRef in methods List
     * <p></p>Method necessitates that zero parameter, void return type methods are referenced supplied.
     * <p></p>* using a MeRef with the signature:   MeRef<>   is ideal for this purpose..
     **/
    public void run()
    {
        if(alwaysCheckIfBroke){ handleBadReferences(); }

        for(CallMe<V, O> method : methods)
        {
            method.run();
        }
    }

    /**
     * Method executes all method on it's list in supplied order:
     * - takes a single type V parameter
     * - returns void
     **/
    public void run(V... value)
    {
        if(alwaysCheckIfBroke){ handleBadReferences(); }

        for(CallMe<V, O> method : methods)
        {
            method.run(value);
        }
    }

    /**
     * Method executes all method on it's list in supplied order:
     * - There is no direct way to retrieve the object that delivered a value to the returns array.
     * @param values take 0 to many type V arguments (must of course match wrapped method parameter list)
     * @return returns an Object[], a result for each method.
     **/
    public O[] goGet(V... values)
    {
        if(alwaysCheckIfBroke){ handleBadReferences(); }

        Object[] returns = new Object[methods.size()];

        for(int i = 0, methodsSize = methods.size(); i < methodsSize; i++)
        {
            returns[i] = methods.get(i).run(values);
        }

        return (O[])returns;
    }

    /**
     * Method executes all method on it's list in supplied order:
     * - There is no direct way to retrieve the object that delivered a value to the returns array.
     * @param values take 0 to many type V arguments (must of course match wrapped method parameter list)
     * @return returns an Object[], a result for each method.
     **/
    public HashMap<Object,O> goGetMap(V... values)
    {
        if(alwaysCheckIfBroke){ handleBadReferences(); }

        HashMap<Object,O> resultsMap = new HashMap<>();

        for(int i = 0, methodsSize = methods.size(); i < methodsSize; i++)
        {
            resultsMap.put(methods.get(i).getExecutingObject(), methods.get(i).run(values));
        }

        return resultsMap;
    }

    /**
     * Add a method with appropriate signature to internal method list
     *
     * <p></p><p> And several references to the same MeRef is not a problem;
     * that really depends on your task as such.</p>
     */
    public void add(CallMe<V, O> method)
    {
        methods.add(method);
    }

    /**
     * Method adds supplied MeRef only if it is not already on methods-list,
     * returns true if insert was successful, false if duplicates were found.
     */
    public boolean addNoDuplicates(CallMe<V, O> method)
    {

        boolean duplicateFound = false;

        for(int i = 0; i < methods.size(); i++)
        {
            if(methods.get(i).getMethodObject().getName().equalsIgnoreCase(method.getMethodObject().getName()))
            {
                duplicateFound = true;
            }
        }

        if(!duplicateFound)
        {
            methods.add(method);
            return true;
        } else{ return false; }
    }


    /**
     * Remove a method from internal methods-list
     * Returns true if method could be and was removed,
     * otherwise false;
     */
    public boolean remove(CallMe<V, O> method)
    {
        if(methods.contains(method))
        {
            methods.remove(method);
            return true;
        } else return false;
    }

    /**
     * Method enables or disables checking of methods before execution.
     *
     * @param turnItOn true enables auto-checking.
     */
    public void enableAutoHandleBrokeReferences(boolean turnItOn)
    {
        alwaysCheckIfBroke = turnItOn;
    }

    /**
     * Method removes 'dead' MethodReferences from list
     * AND adds the name of removed method to internal list.
     **/
    public void handleBadReferences()
    {
        for(CallMe<V, O> methodReference : methods)
        {
            if(methodReference.isReferenceBroke())
            {   /* Adding name of method to be removed, to a log/list removedMethods   */
                removedMethodNames.add(methodReference.getMethodObject().getName());
                methods.remove(methodReference);
            }
        }
    }

    /**
     * Gets you the number of items on
     */
    public int size()
    {
        return methods.size();
    }
}


