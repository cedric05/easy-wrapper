
import java.io.File;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;

class App {

    public static void main(String args[]) throws Exception {
        URL[] urls = new URL[] { new File("./lib.jar").toURL() };
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class appClass = urlClassLoader.loadClass("Library");
        Object obj = appClass.newInstance();
        Wrapper wrap = wrap(obj);
        int b = wrap.add(1, 2);
        System.out.println(b);
    }

    public static Wrapper wrap(Object obj) {
        Wrapper wrapper = (Wrapper) Proxy.newProxyInstance(Wrapper.class.getClassLoader(),
                new Class[] { Wrapper.class }, new DynamicInvocationHandler(obj));
        return wrapper;
    }
}