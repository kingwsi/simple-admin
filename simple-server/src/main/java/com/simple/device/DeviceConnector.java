package com.simple.device;

import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.Path;
import org.springframework.stereotype.Service;

/**
 * description:  <br>
 * date: 2022/8/21 19:14 <br>
 * author: ws <br>
 */
@Service
public interface DeviceConnector {
    @GET("/v1.0/iot-03/devices/{device_id}/specification")
    Object getById(@Path("device_id") String deviceId);
}
