# Flutter Native Handler
A native implementation helper to make native methods calls from flutter more robust and keep the code clean.

Features:
Automatic object parse: 
  * Flutter can pass json string of objects to native calls and it will be deserialized to java objects;
  * Java method can return Java object and it will be serialized to json and returned to flutter;
Thread Pool execution:
  * To avoid lock the UI thread all calls to native methods are made on another thread;

Usage:

Returned type:
```java

```

Exemple of use: 
```java

```
