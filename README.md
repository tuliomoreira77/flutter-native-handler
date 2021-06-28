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


# Exemple: 
```java

```
