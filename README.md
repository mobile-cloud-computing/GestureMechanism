Gesture Mechanism
=================


Requirements
-------------
```xml
Android support v4 and v7 are required for this project to work.
Import the projects from your local android sdk installation
(~/android-sdk/extras/android/support/v4)
(~/android-sdk/extras/android/support/v7)

Right click in the project -> Properties -> Android -> (below box - add)
Include the imported directories.
```
```xml
AndroidManifest.xml needs to include following permissions:

<permission
    android:name="your_package.permission.C2D_MESSAGE"
    android:protectionLevel="signature" />

<uses-permission android:name="your_package.permission.C2D_MESSAGE" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
```xml
AndroidManifest.xml needs to define the following receiver:

<receiver
    android:name="com.google.android.gcm.GCMBroadcastReceiver"
    android:permission="com.google.android.c2dm.permission.SEND" >
    <intent-filter>
        <action android:name="com.google.android.c2dm.intent.RECEIVE" />
        <action android:name="com.google.android.c2dm.intent.REGISTRATION" />

        <category android:name="your_package" />
    </intent-filter>
</receiver>
```

Configuration
-------------




FAQ
-------------
