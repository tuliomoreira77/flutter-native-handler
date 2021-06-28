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
                if(methods.containsKey(method.getName())) {
                    throw new MethodMapperException("Repeated method name on handlers");
                }
                methods.put(method.getName(), new MethodInstance(method, instance));
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
                Object response = method.invoke(parseArguments(argument, method.getArgumentClass()));
                resultHandler.success(response);
            } catch (Exception e) {
                resultHandler.error(e.getClass().getName(), e.getMessage(), e.getLocalizedMessage());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T> Object parseArguments(Object argument, Class<T> expectedClass) {
        if(expectedClass == null) {
            return null;
        }
        if(expectedClass.equals(String.class)) {
            return (T) argument;
        }
        if(expectedClass.equals(byte[].class)) {
            return (T) argument;
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
