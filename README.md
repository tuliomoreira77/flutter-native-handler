# Flutter Native Handler
A native implementation helper to make native methods calls from flutter more robust and keep the code clean.

# Features:
* Automatic object parse: 
  * Flutter can pass json string of objects to native calls and it will be deserialized to java objects;
  * Java method can return Java object and it will be serialized to json and returned to flutter;
* Thread Pool execution:
  * To avoid lock the UI thread all calls to native methods are made on another thread;

# Limitations:
At moment native methods can only receive one paramether, in future I will extend it.

# Usage:
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Add the dependency to the module build.gradle:
```gradle
dependencies {
  implementation 'com.github.tuliomoreira77:flutter-native-handler:Tag'
}
```

Create a handler and anotate methods with @NativeMethod
```java
public class BluetoothHandler {

    private final IBluetoothService bluetoothService;
    private final BluetoothWatcher bluetoothWatcher;

    public BluetoothHandler(IBluetoothService bluetoothService, BluetoothWatcher bluetoothWatcher) {
        this.bluetoothService = bluetoothService;
        this.bluetoothWatcher = bluetoothWatcher;
    }

    @NativeMethod
    public byte[] getRawData(String command) throws BluetoothException {
        byte[] response = bluetoothService.getRawResult(command);
        return response;
    }

    @NativeMethod
    public String getFormattedData(String command) throws BluetoothException {
        String response = bluetoothService.getStringResult(command);
        return response;
    }

    @NativeMethod
    public Double getNumericData(String command) throws BluetoothException {
        Double response = bluetoothService.getNumericResult(command);
        return response;
    }

    @NativeMethod
    public boolean connect() throws BluetoothException {
        bluetoothService.connect();
        bluetoothWatcher.startWatch();
        return true;
    }

    @NativeMethod
    public boolean isConnected() throws BluetoothException {
        return bluetoothService.isConnected();
    }
}
```

On MainActivity class register handler class instances and provide a implementation of result callbacks:
```Java
public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "flutter.dev/bluetooth";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
       
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
            .setMethodCallHandler((call, result) -> {
                //Create a NativeMethodHandler instance and provide a implementation to NativeHandlerResult 
                //This anonymous class will be suficient for most users, but you can provide your own implementation
                new NativeMethodHandler(registerHandlerClasses(), new NativeHandlerResult() {
                    @Override
                    public void success(Object _result) {
                        result.success(_result);
                    }

                    @Override
                    public void error(String errorCode, String errorMessage, Object errorDetails) {
                        result.error(errorCode, errorMessage, errorDetails);
                    }

                    @Override
                    public void notImplemented() {
                        result.notImplemented();
                    }
                }).handle(call.method, call.arguments); //call handle method passing the funcion name and the arguments
            });
    }
    
    //Register classes that contains the native methods
    private Object[] registerHandlerClasses() {
        return new Object[]{
                new BluetoothHandler(new BluetoothService()), new BluetoothWatcher())),
        };
    }
}
```
