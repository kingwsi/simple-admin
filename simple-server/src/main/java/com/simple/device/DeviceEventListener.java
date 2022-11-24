package com.simple.device;

import com.tuya.connector.open.messaging.event.StatusReportMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * description:  <br>
 * date: 2022/8/22 13:25 <br>
 * author:  <br>
 */
@Slf4j
@Component
public class DeviceEventListener {
    @EventListener
    public void statusReportMessage(StatusReportMessage event) {
        log.info("### StatusReport event happened, eventInfo: {}", event);
    }
}
