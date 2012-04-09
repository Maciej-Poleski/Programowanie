import java.lang.ref.WeakReference;
import java.lang.reflect.*;
import java.util.*;

/**
 * User: Maciej Poleski
 * Date: 23.03.12
 * Time: 16:05
 */
public class ExceptionCounter {
    private static class MethodInformation implements Comparable<MethodInformation> {
        final String name;
        final String returnType;
        final String computedParameterTypes;
        final String[] exceptionTypes;

        MethodInformation(Method method) {
            String[] parameterTypes;
            name = method.getName();
            returnType = simplifyType(method.getGenericReturnType(), method.getReturnType());
            parameterTypes = new String[method.getGenericParameterTypes().length];
            for (int i = 0; i < parameterTypes.length; ++i)
                parameterTypes[i] = simplifyType(method.getGenericParameterTypes()[i], method.getParameterTypes()[i]);
            exceptionTypes = new String[method.getGenericExceptionTypes().length];
            for (int i = 0; i < exceptionTypes.length; ++i)
                exceptionTypes[i] = simplifyType(method.getGenericExceptionTypes()[i], method.getExceptionTypes()[i]);
            computedParameterTypes = computeParameterTypes(parameterTypes);
        }

        MethodInformation(Constructor<?> constructor) {
            String[] parameterTypes;
            name = constructor.getName();
            returnType = "";
            parameterTypes = new String[constructor.getGenericParameterTypes().length];
            for (int i = 0; i < parameterTypes.length; ++i)
                parameterTypes[i] = simplifyType(constructor.getGenericParameterTypes()[i], constructor.getParameterTypes()[i]);
            exceptionTypes = new String[constructor.getGenericExceptionTypes().length];
            for (int i = 0; i < exceptionTypes.length; ++i)
                exceptionTypes[i] = simplifyType(constructor.getGenericExceptionTypes()[i], constructor.getExceptionTypes()[i]);
            computedParameterTypes = computeParameterTypes(parameterTypes);
        }

        private static String computeParameterTypes(String[] parameterTypes) {
            StringBuilder result = new StringBuilder();
            for (String parameter : parameterTypes)
                result.append(parameter).append(",");
            return result.substring(0, Math.max(0, result.length() - 1));
        }

        private static String simplifyType(Type type, Class<?> cl) {
            if (type instanceof ParameterizedType)
                return type.toString();
            else if (type instanceof GenericArrayType)
                return type.toString();
            else if (type instanceof TypeVariable)
                return type.toString();
            else {
                return cl.getCanonicalName();
            }
        }

        @Override
        public String toString() {
            return "" + (returnType.isEmpty() ? "" : returnType + " ") + name + "(" + computedParameterTypes + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodInformation that = (MethodInformation) o;

            return computedParameterTypes.equals(that.computedParameterTypes) && name.equals(that.name) && returnType.equals(that.returnType);
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + returnType.hashCode();
            result = 31 * result + computedParameterTypes.hashCode();
            return result;
        }

        @Override
        public int compareTo(MethodInformation o) {
            if (!name.equals(o.name))
                return name.compareTo(o.name);
            else if (!returnType.equals(o.returnType))
                return returnType.compareTo(o.returnType);
            else {
                return computedParameterTypes.compareTo(o.computedParameterTypes);
            }
        }
    }

    private static class ObjectInformation implements Iterable<MethodInformation> {
        final List<MethodInformation> information = new ArrayList<MethodInformation>();

        public ObjectInformation(Object object) {
            List<MethodInformation> information = new ArrayList<MethodInformation>();
            for (Method method : object.getClass().getDeclaredMethods())
                information.add(new MethodInformation(method));
            for (Constructor<?> constructor : object.getClass().getDeclaredConstructors())
                information.add(new MethodInformation(constructor));
            for (MethodInformation info : information)
                if (info.exceptionTypes.length != 0)
                    this.information.add(info);
        }

        @Override
        public Iterator<MethodInformation> iterator() {
            return information.iterator();
        }
    }

    static class ObjectReference {
        final WeakReference<Object> reference;
        final ObjectInformation info;

        ObjectReference(Object reference) {
            this.reference = new WeakReference<Object>(reference);
            info = new ObjectInformation(reference);
        }
    }

    private final List<ObjectReference> coreDatabase = new LinkedList<ObjectReference>();

    public void register(Object obj) {
        if (obj != null) {
            for (ObjectReference ref : coreDatabase)
                if (ref.reference.get() == obj)
                    return;
            coreDatabase.add(new ObjectReference(obj));
        }
    }

    public void printStatus() {
        SortedSet<String> exceptions = new TreeSet<String>();
        Collection<ObjectInformation> lockedInformation;
        for (; ; ) {
            try {

                lockedInformation = new ArrayList<ObjectInformation>();
                for (ListIterator<ObjectReference> i = coreDatabase.listIterator(); i.hasNext(); ) {
                    ObjectReference entry = i.next();
                    if (entry.reference.get() != null)
                        lockedInformation.add(entry.info);
                    else
                        i.remove();
                }
                break;
            } catch (ConcurrentModificationException ignored) {
            }
        }
        List<MethodInformation> allMethods = new ArrayList<MethodInformation>();
        for (ObjectInformation info : lockedInformation) {
            for (MethodInformation methodInfo : info) {
                Collections.addAll(exceptions, methodInfo.exceptionTypes);
            }
            allMethods.addAll(info.information);
        }
        Collections.sort(allMethods);
        for (String exception : exceptions) {
            System.out.println("Exception " + exception + ":");
            int suitable = 0;
            for (int i = 0; i < allMethods.size(); ++i) {
                if (Arrays.asList(allMethods.get(i).exceptionTypes).contains(exception))
                    ++suitable;
                if (i + 1 == allMethods.size() || !allMethods.get(i).equals(allMethods.get(i + 1))) {
                    if (suitable != 0) {
                        MethodInformation info = allMethods.get(i);
                        System.out.println(" " + info.toString() + " " + suitable);
                        suitable = 0;
                    }
                }
            }
        }
    }
}
