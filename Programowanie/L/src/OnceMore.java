import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Maciej Poleski
 * Date: 14.04.12
 * Time: 18:45
 */
public class OnceMore {
    static class ClassProvider {
        public static final List<File> classpath = new ArrayList<File>();

        static {
            String rawClasspath = System.getProperty("java.class.path");
            String classpath[] = rawClasspath.split(System.getProperty("path.separator"));
            for (String path : classpath) {
                if (path.endsWith(".jar")) {
                    continue;
                }
                ClassProvider.classpath.add(new File(path));
            }
        }
    }

    private static final ClassProvider classProvider = new ClassProvider();

    static class ClassLoader extends java.lang.ClassLoader {

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            Class<?> result = findLoadedClass(name);
            if (result != null) {
                if (resolve) {
                    resolveClass(result);
                }
                return result;
            }
            if (store.containsKey(name)) {
                result = defineClass(name, store.get(name), 0, store.get(name).length);
                if (resolve) {
                    resolveClass(result);
                }
                return result;
            }
            for (File path : ClassProvider.classpath) {
                File file = new File(path, name.replace(".", System.getProperty("file.separator")) + ".class");
                if (!file.canRead()) {
                    continue;
                }
                InputStream stream;
                try {
                    stream = new FileInputStream(file);
                    List<Byte> source = new ArrayList<Byte>();
                    for (; ; ) {
                        int r = stream.read();
                        if (r == -1) {
                            break;
                        } else
                            source.add((byte) r);
                    }
                    byte bytes[] = new byte[source.size()];
                    int i = 0;
                    for (Byte b : source) {
                        bytes[i++] = b;
                    }
                    store.put(name, bytes);
                    result = defineClass(name, bytes, 0, bytes.length);
                    if (resolve) {
                        resolveClass(result);
                    }
                    return result;
                } catch (Exception ignored) {
                }
            }
            return super.loadClass(name, resolve);
        }
    }

    private static final ClassLoader loader = new ClassLoader();
    private static Map<String, byte[]> store = new HashMap<String, byte[]>();

    static class ObjectInputStream extends java.io.ObjectInputStream {
        public ObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        protected ObjectInputStream() throws IOException, SecurityException {
            super();
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            return Class.forName(desc.getName(), false, loader);
        }
    }

    public static void produceSaveAndRun(OutputStream out, String s) throws
            IOException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        Class<?> classHandle = Class.forName(s, false, loader);
        Object object = classHandle.newInstance();
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        ObjectOutputStream otmp = new ObjectOutputStream(temp);
        otmp.writeObject(object);
        otmp.close();
        ((Runnable) object).run();
        InputStream itmp = new ByteArrayInputStream(temp.toByteArray());
        java.io.ObjectInputStream i2tmp = new java.io.ObjectInputStream(itmp);
        object = i2tmp.readObject();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(store);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
    }

    public static void loadAndRun(InputStream in) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        try {
            store = (Map<String, byte[]>) objectInputStream.readObject();
        } catch (ClassNotFoundException ignored) {
        }
        prepare();
        try {
            ((Runnable) objectInputStream.readObject()).run();
        } catch (ClassNotFoundException ignored) {
        }
    }

    private static void prepare() {
        for (String name : store.keySet()) {
            try {
                loader.loadClass(name, false);
            } catch (ClassNotFoundException ignored) {
            }
        }
    }
}
