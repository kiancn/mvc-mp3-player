package kcn.callbackmethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * Class instances hold a reference to *<b>a Method object and an Object object</b>* and,
 * is able to execute the method on the object .
 * The Reference has a generic twin in MeReference, which is safer to use and has much greater
 * flexibility.
 * <p>
 * NB. Naming of run methods is mechanical and I don't really like it, but didn't come up with something
 * better yet.
 */
public class CallbackMethod implements ICallback
{

    private Object objectToExecuteMethodOn;
    private Method methodToExecute;

    private boolean referenceIsBroke;

    private int[] exceptionsCaught;
    /*exception-counters ... unending */
    private int IllegalAccessExceptionCaught;
    private int InvocationTargetExceptionCaught;
    private int NullPointerExceptionCaught;
    private int NoSuchMethodExceptionCaught;
    private int ExecutingObjectMissingCaught;
    private int MethodObjectMissingCaught;

    public CallbackMethod(Object executingThing, Method method)
    {
        methodToExecute = method;
        objectToExecuteMethodOn = executingThing;
        methodToExecute.setAccessible(true); /* making method available even if it is private : if you
        reference it, I trust you want to do that, private or not. */
        /* It has been noticed that this functionality has severe limits:
         *  https://www.logicbig.com/how-to/code-snippets/jcode-reflection-method-setaccessible.html */
        initializeExceptionsArray();
    }

    private void initializeExceptionsArray()
    {
        exceptionsCaught = new int[]{IllegalAccessExceptionCaught,
                InvocationTargetExceptionCaught,
                NullPointerExceptionCaught,
                NoSuchMethodExceptionCaught,
                ExecutingObjectMissingCaught,
                MethodObjectMissingCaught
        };
    }
    /** This constructor works if the method named does not have overloads.
     * Using the constructor with a method that does have overloads, leads
     * to non-deterministic 'choice' of method. So don't. */
    public CallbackMethod(Object executingObject, String methodName)
    {
        Method[] methods = executingObject.getClass().getDeclaredMethods();

        boolean methodLocated = false;

        for(int i = 0; i < methods.length; i++)
        {
            if(methods[i].getName().equalsIgnoreCase(methodName))
            {
                methodToExecute = methods[i];
                methodLocated = true;
                break;
            }
        }

        if(methodToExecute == null || !methodLocated)
        {
            NoSuchMethodExceptionCaught++; // this is slightly bad, since no exception was thrown;
            // but conceptually they overlap
        }
        initializeExceptionsArray();
        objectToExecuteMethodOn = executingObject;
    }

    /** Use this constructor if the named method has overloads.
     * Note: Do not supply 'wrapper class'-objects as arguments if the formal parameter list
     * of the method requires fundamental types.)*/
    public CallbackMethod(Object executingThing, String methodName, Class... parameterTypes)
    {
        try
        {
            methodToExecute = executingThing.getClass().getMethod(methodName, parameterTypes);
            methodToExecute.setAccessible(true); /* it should enable */
        } catch(NoSuchMethodException e)
        {
            NoSuchMethodExceptionCaught++;
        } catch(NullPointerException e)
        {
            NullPointerExceptionCaught++;
        }
        objectToExecuteMethodOn = executingThing;

        initializeExceptionsArray();
    }

    /**
     * Method executes the method referenced on object supplied.
     * - no parameters
     * - returns void
     **/
    public Object run()
    {
        if(!isNullFound())
        {
            try
            {
                return methodToExecute.invoke(objectToExecuteMethodOn);
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++; /*just updating the counter and moving on */
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }

        return null;
    }

    /**
     * Method returns true if any object checked is null
     */
    public boolean isNullFound()
    {
        if(isExecutingObjectNull() || isMethodObjectNull()){ return true; } else{ return false; }
    }

    /**
     * Method returns true if the object meant to execute contained method is missing.
     */
    private boolean isExecutingObjectNull()
    {
        if(Objects.isNull(objectToExecuteMethodOn))
        {
            ExecutingObjectMissingCaught++;
            referenceIsBroke = true; /** this MethodReference is always broken if it's object is missing */
            return true;
        } else{return false;}
    }

    /**
     * Returns true if Method object is somehow null.
     */
    private boolean isMethodObjectNull()
    {
        if(Objects.isNull(methodToExecute))
        {
            MethodObjectMissingCaught++;
            referenceIsBroke = true; /* if it's method is missing MethodReference is always broken */
            return true;
        } else{return false;}
    }

    /*
      RUN METHODS
     */

    /**
     * Method executes the method referenced on object supplied.
     *
     * @param parameters any combination of Object (sub)class objects
     * @return Object type object, so you must type cast
     **/
    public Object run(Object... parameters)
    {
        if(!isNullFound())
        {
            try
            {
                return methodToExecute.invoke(objectToExecuteMethodOn, parameters);
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++; /*just updating the counter and moving on */
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }

        return null;
    }

    /*
     * GETTERS, NO SETTERS
     */

    /**
     * Method returns a java.lang.reflect.Method type object. <p></p><b>
     * This is the object that invoke is called on
     * (with objectToExecute as first parameter). </b>
     * <p> </p>
     */
    @Override
    public Method getMethodObject()
    {
        if(!isMethodObjectNull())
        {
            return methodToExecute;
        } else
        {
            NullPointerExceptionCaught++;
            return null;
        }
    }

    /**
     * Method return the object executing the method wrapped by this CallbackMethod.
     */
    @Override
    public Object getExecutingObject()
    {
        if(!isExecutingObjectNull())
        {
            return objectToExecuteMethodOn;
        } else
        {
            NullPointerExceptionCaught++;
            return null;
        }
    }

    /**
     * Method returns true if CallbackMethod is somehow broke:
     * either null is detected or exceptions have been caught.
     * <p>
     */
    public boolean isReferenceBroke()
    {
        /* checking for null and going through already occurred exceptions */
        referenceIsBroke = isNullFound() || didExceptionsHappen();

        return referenceIsBroke;
    }

    /**
     * Method will return true if any of the counter ints in exceptionsCaught
     * are above 0.
     */
    public boolean didExceptionsHappen()
    {
        initializeExceptionsArray();
        for(int value : exceptionsCaught)
        {
            if(value > 0)
            {
                return true;
            }
        }
        /* if code reaches here, no exceptions were found to be caught */
        return false;
    }

    /**
     * Method return the array of counted errors;
     * intent is for user to interpret the array to detect what type of error occurred
     * by interpreting the value at each index and act accordingly.
     * <p>If you  j u s t  want to know if something went wrong, pull isReferenceBroke()
     */
    public int[] getExceptionsCaught()
    {
        initializeExceptionsArray();
        return exceptionsCaught;
    }
}
