
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;

class App {

    public static void main(String args[]) throws Exception {
        URL[] urls = new URL[] { new File("./Library/build/libs/Library.jar").toURL() };
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        printAreaExample1(urlClassLoader);
        printAreaExample2(urlClassLoader);
    }

    private static void printAreaExample1(URLClassLoader urlClassLoader)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class appClass = urlClassLoader.loadClass("Rectangle");
        Object obj = appClass.newInstance();
        Wrapper wrap = wrap(obj);
        wrap.setLength(3);
        wrap.setBreadth(4);
        wrap.getArea();
        System.out.printf("area of rectangle with length %s bread %s is %s\n", wrap.getLength(), wrap.getBreadth(),
                wrap.getArea());
    }

    public static void printAreaExample2(ClassLoader urlClassLoader) {
        try {
            Class recClass = urlClassLoader.loadClass("Rectangle");
            Object obj = recClass.newInstance();
            Method setLengthMethod = recClass.getMethod("setLength", int.class);
            Method setBreadthMethod = recClass.getMethod("setBreadth", int.class);
            setLengthMethod.invoke(obj, 3);
            setBreadthMethod.invoke(obj, 4);
            Method getArea = recClass.getMethod("getArea");
            Method getLengthMethod = recClass.getMethod("getLength");
            Method getBreadthMethod = recClass.getMethod("getBreadth");
            int area = (int) getArea.invoke(obj);
            System.out.printf("area of rectangle with length %s bread %s is %s\n", getLengthMethod.invoke(obj),
                    getBreadthMethod.invoke(obj), area);
        } catch (InstantiationException ex) {
            System.err.println("Not able to create Instance of Class");
        } catch (IllegalAccessException ex) {
            System.err.println("Not able to access Class");
        } catch (ClassNotFoundException ex) {
            System.err.println("Not able to find Class");
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal state, Arguments passed are of different types");
        } catch (InvocationTargetException e) {
            System.err.println("Not able to invoke Class");
        } catch (NoSuchMethodException e) {
            System.err.println("Not able find method");
            e.printStackTrace();
        } catch (SecurityException e) {
            System.err.println("Not able to invoke method");
        }
    }

    public static Wrapper wrap(Object obj) {
        Wrapper wrapper = (Wrapper) Proxy.newProxyInstance(Wrapper.class.getClassLoader(),
                new Class[] { Wrapper.class }, new DynamicInvocationHandler(obj));
        return wrapper;
    }
}