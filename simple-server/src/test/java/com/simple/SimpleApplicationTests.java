package com.simple;

import com.simple.device.DeviceConnector;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SimpleApplicationTests {

    @Autowired
    private DeviceConnector device;
    @Test
    public void contextLoads() {
        Object object = device.getById("6ccfc4b19371119228mqms");
        System.out.println(object);
    }
}