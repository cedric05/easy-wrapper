
# Wrapper

Having to load class from different classloaders and invoking methods involves is tough job.

Here we are using java's proxyInvocationHandler to loosen up things

In this example, it uses a seperate interface with having all the methods which user wants to invoke from other classloader.

Now that we have Object and Wrapper Interface, This example creates Proxy object wrapping around target class Object using java's.

when ever user wants to invoke method, Invocation Handler observes that and calls actual method. This logic has been used in lot more projects like spring. but here it is being used to load/simplify invocation from different classloaders.


# For Example
 code like this
 ```java
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

 ```

could be **simplified** to 

```java
        Class appClass = urlClassLoader.loadClass("Rectangle");
        Object obj = appClass.newInstance();
        Wrapper wrap = wrap(obj);
        wrap.setLength(3);
        wrap.setBreadth(4);
        wrap.getArea();
        System.out.printf("area of rectangle with length %s bread %s is %s\n", wrap.getLength(), wrap.getBreadth(),
                wrap.getArea());
```
with a [Wrapper Interface](src/main/java/Wrapper.java)



To run
``` bash
gradle run
```
