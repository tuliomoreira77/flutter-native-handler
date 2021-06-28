package com.example.flutternativehandler.handler;

import com.example.flutternativehandler.annotation.NativeMethod;
import com.example.flutternativehandler.injection.MethodMapperException;
import com.example.flutternativehandler.tasks.TaskManager;
import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class NativeMethodHandler {
    private final Object[] instances;
    private final Map<String, MethodInstance> methods;
    private final TaskManager methodRouterManager;
    private final NativeHandlerResult resultHandler;

    public NativeMethodHandler(Object[] instances, NativeHandlerResult resultHandler) {
        this.methods = new HashMap<>();
        this.methodRouterManager = new TaskManager(2,3,500);
        this.instances = instances;
        this.resultHandler = resultHandler;
        buildMethodRouter();
    }

    private void buildMethodRouter() {
        try {
            for(Object instance : instances) {
                addMethodsToMethodMap(instance.getClass(), instance);
            }
        } catch (MethodMapperException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void addMethodsToMethodMap(Class<?> clazz, Object instance) throws MethodMapperException {
        for(Method method : clazz.getMethods()) {
            if(method.isAnnotationPresent(NativeMethod.class)) {
                if(this.methods.containsKey(method.getName())) {
                    throw new MethodMapperException("Repeated method name on handlers");
                }
                this.methods.put(method.getName(), new MethodInstance(method, instance));
            }
        }
    }

    public void handle(String methodName, Object argument) {
        MethodInstance method = methods.get(methodName);
        if(method == null) {
            resultHandler.notImplemented();
            return;
        }
        methodRouterManager.runTask(() -> {
            try {
                Object parsedArgument = parseArguments(argument, method.getArgumentClass());
                Object response = parsedArgument != null ? method.invoke(parsedArgument) : method.invoke();
                resultHandler.success(parseResponse(response, method.getReturnedClass()));
            } catch (Exception e) {
                resultHandler.error(e.getClass().getName(), e.getMessage(), e.getLocalizedMessage());
            }
        });
    }

    private <T> Object parseResponse(Object response, Class<T> responseClass) {
        if(responseClass == null) {
            return null;
        }
        if(responseClass.equals(Void.class) || responseClass.equals(void.class)) {
            return null;
        }
        if(responseClass.equals(String.class)) {
            return (String) response;
        }
        if(responseClass.equals(byte[].class)) {
            return (byte[]) response;
        }
        if(responseClass.equals(Integer.class) || responseClass.equals(int.class)) {
            return (Integer) response;
        }
        if(responseClass.equals(Boolean.class) || responseClass.equals(boolean.class)) {
            return (Boolean) response;
        }
        if(responseClass.equals(Double.class) || responseClass.equals(double.class)) {
            return (Double) response;
        }
        if(responseClass.equals(Long.class) || responseClass.equals(long.class)) {
            return (Long) response;
        }
        Gson gson = new Gson();
        return gson.toJson(response, responseClass);
    }


    private <T> Object parseArguments(Object argument, Class<T> expectedClass) {
        if(expectedClass == null) {
            return null;
        }
        if(expectedClass.equals(String.class)) {
            return (String) argument;
        }
        if(expectedClass.equals(byte[].class)) {
            return (byte[]) argument;
        }
        if(expectedClass.equals(Integer.class) || expectedClass.equals(int.class)) {
            return (Integer) argument;
        }
        if(expectedClass.equals(Boolean.class) || expectedClass.equals(boolean.class)) {
            return (Boolean) argument;
        }
        if(expectedClass.equals(Double.class) || expectedClass.equals(double.class)) {
            return (Double) argument;
        }
        if(expectedClass.equals(Long.class) || expectedClass.equals(long.class)) {
            return (Long) argument;
        }
        Gson gson = new Gson();
        return gson.fromJson((String) argument, expectedClass);
    }

    private static class MethodInstance {
        private final Method method;
        private final Object instance;

        public MethodInstance(Method method, Object instance) {
            this.method = method;
            this.instance = instance;
        }

        public Class<?> getArgumentClass() {
            Class<?>[] arguments = method.getParameterTypes();
            return arguments.length > 0 ? method.getParameterTypes()[0] : null;
        }

        public Class<?> getReturnedClass() {
            return method.getReturnType();
        }

        public Object invoke(Object ...args) throws InvocationTargetException, IllegalAccessException {
            return args[0] == null ? method.invoke(instance) : method.invoke(instance, args);
        }
    }

}
