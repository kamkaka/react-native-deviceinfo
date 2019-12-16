package com.reactlibrary;

import android.os.Build;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DeviceinfoModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public DeviceinfoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Deviceinfo";
    }

    @ReactMethod
    public void getDeviceInfo(String stringArgument, int numberArgument, Promise promise) {
        try {
            Map<Integer,String> apiAndOSMappings = getApiLevelAndOSNameMappings();
            WritableMap deviceInformation = Arguments.createMap();
            final String deviceName = Build.DEVICE;
            final String deviceBrand = Build.BRAND;
            final String deviceOSVersion = Build.VERSION.RELEASE;
            final int deviceAPILevel = Build.VERSION.SDK_INT;
            final String osName = apiAndOSMappings.get(deviceAPILevel);

            deviceInformation.putString("DEVICE_NAME", deviceName);
            deviceInformation.putString("DEVICE_BRAND", deviceBrand);
            deviceInformation.putString("DEVICE_OS_VERSION", deviceOSVersion);
            deviceInformation.putInt("DEVICE_API_LEVEL", deviceAPILevel);
            deviceInformation.putString("DEVICE_OS_NAME", osName);

            promise.resolve(deviceInformation);

        }catch (Exception exception){
            // Error occured
            promise.reject("DeviceInfo","Can not get device info due to "+exception.getMessage());
        }

    }


    private Map<Integer,String> getApiLevelAndOSNameMappings(){
        final Field[] fields = Build.VERSION_CODES.class.getFields();
        final Map<Integer,String> apilevelAndOSNameMap = new HashMap<>();
        for (Field field:fields
             ) {
            final String osName = field.getName();
            try{
                 final int apiLevel = field.getInt(new Object());
                 if(apiLevel>0){
                     apilevelAndOSNameMap.put(apiLevel,osName);
                 }
            }catch(Exception error){
               
            }
        }
        return apilevelAndOSNameMap;
    }
 }
