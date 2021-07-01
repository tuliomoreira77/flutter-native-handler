package com.example.flutternativehandler;

import com.example.flutternativehandler.handler.NativeHandlerResult;
import com.example.flutternativehandler.handler.NativeMethodHandler;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class NativeHandlerErrorTest {

    @Test
    public void testExceptionHandling() throws InterruptedException, ExecutionException, TimeoutException {
        Object argument = null;
        String methodName = "testExceptionHandling";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                fail();
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                System.out.println("DEBUG " + errorCode + " " + errorMessage);
                assertEquals("com.example.flutternativehandler.exceptions.ParsingException", errorCode);
                future.complete(true);
            }

            @Override
            public void notImplemented() {
                fail();
                future.complete(true);
            }
        }).handle(methodName, argument);
        future.get(1000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void receiveBadRequest() throws InterruptedException, ExecutionException, TimeoutException {
        String argument = "TestTest";
        String methodName = "receiveObjectReturnObject";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                fail();
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                System.out.println("DEBUG " + errorCode + " " + errorMessage);
                assertTrue(true);
                future.complete(true);
            }

            @Override
            public void notImplemented() {
                fail();
                future.complete(true);
            }
        }).handle(methodName, argument);
        future.get(1000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void methodNotImplemented() throws InterruptedException, ExecutionException, TimeoutException {
        boolean argument = false;
        String methodName = "methodNotImplemented";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                fail();
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                fail();
                future.complete(true);
            }

            @Override
            public void notImplemented() {
                assertTrue(true);
                future.complete(true);
            }
        }).handle(methodName, argument);
        future.get(1000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void receiveWrongObject() throws InterruptedException, ExecutionException, TimeoutException {
        NativeTestErrorRequest argument = new NativeTestErrorRequest(25L, "TestTest");
        String methodName = "receiveObjectReturnObject";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Gson gson = new Gson();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                fail();
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                System.out.println("DEBUG " + errorCode + " " + errorMessage);
                assertTrue(true);
                future.complete(true);
            }

            @Override
            public void notImplemented() {
                fail();
                future.complete(true);
            }
        }).handle(methodName, gson.toJson(argument));
        future.get(5000, TimeUnit.MILLISECONDS);
    }

}
