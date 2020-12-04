package kcn.callbackmethod;

import java.lang.reflect.Method;

/* though the generic and the non-generic types of references cannot go into each others containers,
* there are other task for which users would/might like to access them as one kind of thing
* */
public interface ICallback
{
    /* method will return true if implementing method reference has detected exceptions or nulls (and thus
    made itself inert.) */
    boolean isReferenceBroke();

    /* method will return a Method type object, the object carrying and wrapping method execution */
    Method getMethodObject();

    /* method will return an Object type object, the object set to execute method */
    Object getExecutingObject();
}
