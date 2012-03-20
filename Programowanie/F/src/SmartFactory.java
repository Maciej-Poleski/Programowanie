import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * User: Maciej Poleski
 * Date: 17.03.12
 * Time: 20:01
 */
public class SmartFactory {
    public static class HellNoException extends RuntimeException {
    }

    public static <T> T fixIt(Class<T> cl, final Object obj) {
        return (T) Proxy.newProxyInstance(cl.getClassLoader(), new Class<?>[]{cl}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Method selectedMethod = null;
                if (obj != null) {
                    methodSelection:
                    for (Method methodProposition : obj.getClass().getMethods()) {
                        if (method.getName().equals(methodProposition.getName()) == false)
                            continue;
                        if (method.getReturnType().isAssignableFrom(methodProposition.getReturnType()) == false)
                            continue;
                        if (method.getParameterTypes().length != methodProposition.getParameterTypes().length)
                            continue;
                        for (int i = 0; i < method.getParameterTypes().length; ++i) {
                            if (methodProposition.getParameterTypes()[i].isAssignableFrom(method.getParameterTypes()[i]) == false)
                                continue methodSelection;
                        }
                        checkNextException:
                        for (Class<?> exceptionProposition : methodProposition.getExceptionTypes()) {
                            if (RuntimeException.class.isAssignableFrom(exceptionProposition))
                                continue;
                            if (Error.class.isAssignableFrom(exceptionProposition))
                                continue;
                            for (Class<?> exceptionSupported : method.getExceptionTypes()) {
                                if (exceptionSupported.isAssignableFrom(exceptionProposition))
                                    continue checkNextException;
                            }
                            continue methodSelection;
                        }
                        selectedMethod = methodProposition;
                        break;
                    }
                }
                if (selectedMethod == null)
                    throw new HellNoException();
                try {
                    return selectedMethod.invoke(obj, args);
                } catch (IllegalArgumentException e) {
                    throw new HellNoException();
                } catch (NullPointerException e) {
                    throw new HellNoException();
                } catch (IllegalAccessException e) {
                    throw new HellNoException();
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }
        });
    }
}
