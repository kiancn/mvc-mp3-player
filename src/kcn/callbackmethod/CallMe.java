package kcn.callbackmethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * A MeRef lets you roll up a reference to an object and a method;
 * and pass that method around and execute it from anywhere on command.
 * <p></p>
 * - Supplied generic types:<p><i>
 * <p>* generic type V is used input-parameters             (Values)
 * <p>* generic type O is generally used as return type     (Output)</i>
 * <p>
 * <p> (The just-mentioned rule is broken in run(O,V), which returns an Object) </p><p></p>
 * <p> Internal exception handling registers the different possible errors, and stops
 * the MeRef instance from functioning if even a single exception happens; the exception is captured in
 * the exceptionsCaught[] as ints for manual (user) processing. The MePack O,V is able
 * to detect defect MeRefs and remove them automatically. </p>
 * <p>
 * * Object type (in parameters and return types) is here to allow manual type
 * casting and greater flexibility.
 **/
public class CallMe<V, O>
        implements ICallback
{
    /* ~<>~ Fields ~<>~ */

    private final String[] errorDescriptionStrings = {"IllegalAccessExceptionCaught",
            "InvocationTargetExceptionCaught",
            "NoSuchMethodExceptionCaught",
            "ExecutingObjectMissingCaught",
            "MethodObjectMissingCaught"};
    private final Object objectReference; /* reference to the Object that executes the method (must own method) */
    private Method method; /* reference to instance of Method class, contents supplied at construction time */
    private Class[] argClasses; /* the option to supply an array of argClasses  */
    private boolean persistentNullChecks;/* if true referenced object is null-checked every execution */
    private boolean shortCircuitRun; /* bool used in all 'run' methods for avoiding fatal missing references*/
    private int[] exceptionsCaught;
    private int IllegalAccessExceptionCaught; /* see getExceptionsCaught() for usages of these ints */
    private int InvocationTargetExceptionCaught;
    private int ExecutingObjectMissingCaught;
    private int MethodObjectMissingCaught;
    private int NoSuchMethodExceptionCaught;

    /* ~<>~ Constructors ~<>~ */

    /* A proper description of the practical cases for each constructor is needed and upcoming  */


    /**
     * this constructor exists for the adventurous who might want to disable the auto-handling of null
     * references the other option is to pull getExecutingObject() and getExceptionsCaught()
     * and do handle possible exceptions manually
     */
    public CallMe(Object executingObject, String methodName, boolean handleHealthChecksAutomatically)
    {
        this(executingObject, methodName);
        persistentNullChecks = handleHealthChecksAutomatically;
    }

    /**
     * This constructor will only work if the method to be wrapped is not overloaded.
     * If you need to wrap an overloaded method, use one of the constructors that allow
     * a vararg of Class ( Class... ) as a parameter.
     */

    public CallMe(Object executingObject, String methodName)
    {

        objectReference = executingObject;

        Method[] methods = executingObject.getClass().getDeclaredMethods();

        boolean methodFound = false;

        for(Method method : methods)
        {
            if(method.getName().equalsIgnoreCase(methodName))
            {
                this.method = method;
                methodFound = true;
                break;
            }
        }

        if(!methodFound)
        {
            MethodObjectMissingCaught++;
            shortCircuitRun = true;
        }

        persistentNullChecks = true;
        generateExceptionsArray();
    }

    private void generateExceptionsArray()
    {
        exceptionsCaught = new int[]{IllegalAccessExceptionCaught,
                InvocationTargetExceptionCaught,
                NoSuchMethodExceptionCaught,
                ExecutingObjectMissingCaught,
                MethodObjectMissingCaught
        };
    }

    public CallMe(Object executingObject,
                  String methodName, boolean handleHealthChecksAutomatically, Class... parameterClasses)
    {
        this(executingObject, methodName, parameterClasses);
        persistentNullChecks = handleHealthChecksAutomatically;
    }

    /**
     *
     */
    public CallMe(Object executingObject, String methodName, Class... argClasses)
    {
        this.argClasses = argClasses;
        objectReference = executingObject;
        try
        {
            method = executingObject.getClass().getMethod(methodName, argClasses);
        } catch(NoSuchMethodException e)
        {
            NoSuchMethodExceptionCaught++;
            shortCircuitRun = true;
        }
        persistentNullChecks = true; /* safety by default */
        generateExceptionsArray();
    }

    public CallMe(Object executingObject, Method methodThatWillBeExecuted, boolean handleHealthChecksAutomatically)
    {
        this(methodThatWillBeExecuted, executingObject);
        persistentNullChecks = handleHealthChecksAutomatically;
    }


    public CallMe(Method methodObject, Object executingObject)
    {
        objectReference = executingObject;
        this.method = methodObject;
        persistentNullChecks = true; /* safety by default */
        generateExceptionsArray();
    }

    /* ~<>~ Methods ~<>~ */

    /**
     * Method executes supplied method with no parameters and a type O return object
     */
    @SuppressWarnings("unchecked") /* compiler is unsure of return type (because invoke does predict type)*/
    public O run()
    {
        O result = null;

        if(persistentNullChecks){ isNullFound();}

        if(!shortCircuitRun)
        {
            try
            {
                /* invoke is executed on Method object, return type is cast to O */
                result = (O)method.invoke(objectReference);
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++;
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }
        /* null returns are passed */
        return result;
    }

    /**
     * Method returns true if executing Object object or Method object is null.
     **/
    public boolean isNullFound()
    {
        /* if either checking-method returns true, return true*/
        if(isExecutingObjectNull() || isMethodObjectNull()){ return true; }
        /* else, null was not found*/
        return false;
    }

    /**
     * Method returns true is referenced 'method-executing' object was found null
     */
    public boolean isExecutingObjectNull()
    {
        if(Objects.isNull(objectReference))
        {
            ExecutingObjectMissingCaught++;
            shortCircuitRun = true;
            return true;
        } else{ return false; }
    }

    /**
     * Method returns true is contained Method type object is
     */
    public boolean isMethodObjectNull()
    {
        if(Objects.isNull(method)) /* the only risk to check is the method executing object */
        {
            MethodObjectMissingCaught++;
            shortCircuitRun = true;
            return true;
        } else{ return false; }
    }

    /**
     * Method executes supplied method with a variable number of V parameters,
     * and returns a type O object.
     * <i> * cannot be used to return an array of primary types! ( use wrapper classes )</i><p>
     *
     * @param values an array of type V objects.
     * @return object of type O
     **/
    @SuppressWarnings("unchecked") /* same (see run() )*/
    public O run(V... values)
    {

        if(persistentNullChecks){ isNullFound(); }
        if(!shortCircuitRun)
        {
            try
            {
                /* A. type cast return type to O; for the compiler
                 *  B. call invoke on referenced method with inputObject and values as
                 * parameters.
                 * C. return type O-object from this method. */
                return (O)method.invoke(objectReference, (Object[])values);
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++;
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }

        return null;
    }

    /**
     * Method returns the contained Method object: if you want to do more reflection
     * magic, here is direct access ...
     */
    @Override
    public Method getMethodObject(){ return method; }

    /**
     * Returns true if persistent null checks are enabled, meaning that executing object and method object
     * will be checked for presence before every use/access (in run..(..)-methods).
     */
    public boolean isPersistentCheckingEnabled(){ return persistentNullChecks; }

    /**
     * Method lets you turn on or off persistent null checks
     */
    public void setPersistentNullChecks(boolean checkForNull){ persistentNullChecks = checkForNull; }

    /**
     * Method returns true if either null was found or exceptions were registered.
     */
    public boolean isReferenceBroke()
    {
        if(didExceptionsHappen()){return true;}
        return isNullFound();
    }

    /**
     * Method returns true if exceptions were registered during execution
     * - this is the 'soft check', in that it does not directly check for null.
     */
    public boolean didExceptionsHappen()
    {
        /* handling the array counting up exceptions thrown */
        for(int exceptionCount : exceptionsCaught)
        {
            if(exceptionCount > 0) return true;
        }
        return false;
    }

    @Override
    public Object getExecutingObject()
    {
        if(!isNullFound()){ return objectReference; } else{ return null; }
    }

    public int[] getExceptionsCaught()
    {
        generateExceptionsArray(); /* re-initializing exceptionsCaught */
        return exceptionsCaught;
    }

    /**
     * Returns array of strings representing possible error-types in order
     * of the int-array counting caught exceptions exceptionsArray.
     * The array represents the possible exceptions and null objects,
     * but NOT and NEVER a log of actual events;
     * method is provided mainly for debugging purposes for.
     */
    public String[] getExceptionDescriptionStrings()
    {
        return errorDescriptionStrings;
    }
}
/*
 * This saved my life as to how to supply the array of V to invoke in run(V[]) :
 * https://yourmitra.wordpress.com/2008/09/26/using-java-reflection-to-invoke-a-method-with-array-parameters/
 *
 * On the perils of Generics and suppressing warnings
 * http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#FAQ300
 * https://www.codejava.net/java-core/the-java-language/suppresswarnings-annotation-examples
 * */