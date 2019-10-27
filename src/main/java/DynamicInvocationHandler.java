import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class DynamicInvocationHandler implements InvocationHandler {

    public Object target;

    public DynamicInvocationHandler(Object obj) {
        target = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method actualMethod = target.getClass().getMethod(name, parameterTypes);
        Object result = actualMethod.invoke(target, args);
        return result;
    }

}