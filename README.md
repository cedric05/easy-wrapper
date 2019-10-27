
# Wrapper

Having to load class from different classloaders and invoking methods involves is tough job.

Here we are using java's proxyInvocationHandler to loosen up things

In this example, it uses a seperate interface with having all the methods which user wants to invoke from other classloader.

Now that we have Object and Wrapper Interface, This example creates Proxy object wrapping around target class Object using java's.

when ever user wants to invoke method, Invocation Handler observes that and calls actual method. This logic has been used in lot more projects like spring. but here it is being used to load/simplify invocation from different classloaders.


# To run example 

# $ gradle run

