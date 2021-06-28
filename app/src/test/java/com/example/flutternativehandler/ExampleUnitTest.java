package com.example.flutternativehandler;

import com.example.flutternativehandler.handler.NativeHandlerResult;
import com.example.flutternativehandler.handler.NativeMethodHandler;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void returnDoubleReceiveDouble() throws ExecutionException, InterruptedException, TimeoutException {
        double argument = 2.0;
        String methodName = "returnDoubleReceiveDouble";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                assertEquals(argument, _result);
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
               fail();
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
    public void receiveIntegerReturnInteger() throws ExecutionException, InterruptedException, TimeoutException {
        int argument = 55;
        String methodName = "receiveIntegerReturnInteger";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                assertEquals(argument, _result);
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                fail();
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
    public void receiveByteArrayReturnByteArray() throws ExecutionException, InterruptedException, TimeoutException {
        byte[] argument = new byte[] {0x55, 0x22};
        String methodName = "receiveByteArrayReturnByteArray";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                assertEquals(argument, _result);
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                fail();
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
    public void receiveBooleanReturnBoolean() throws ExecutionException, InterruptedException, TimeoutException {
        boolean argument = false;
        String methodName = "receiveBooleanReturnBoolean";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                assertEquals(argument, _result);
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                fail();
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
    public void receiveStringReturnString() throws ExecutionException, InterruptedException, TimeoutException {
        String argument = "TestTest";
        String methodName = "receiveStringReturnString";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                assertEquals(argument, _result);
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                fail();
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
    public void receiveLongReturnLong() throws ExecutionException, InterruptedException, TimeoutException {
        Long argument = 100L;
        String methodName = "receiveLongReturnLong";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                assertEquals(argument, _result);
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                fail();
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
    public void receiveObjectReturnObject() throws InterruptedException, ExecutionException, TimeoutException {
        NativeTestRequest argument = new NativeTestRequest(20, "TestTest");
        String methodName = "receiveObjectReturnObject";
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Gson gson = new Gson();
        new NativeMethodHandler(new Object[] {new NativeHandlerTestClass()}, new NativeHandlerResult() {
            @Override
            public void success(Object _result) {
                NativeTesteResponse expectedResponse = new NativeTesteResponse(argument.getI()/1.0, argument.getS());
                assertEquals(gson.toJson(expectedResponse, expectedResponse.getClass()), _result);
                future.complete(true);
            }

            @Override
            public void error(String errorCode, String errorMessage, Object errorDetails) {
                fail();
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

}