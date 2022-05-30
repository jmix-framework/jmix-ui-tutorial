package com.company.tutorial.listener;

import com.company.tutorial.app.TestDataCreation;
import io.jmix.core.security.Authenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

@Component
public class CreateTestDataOnApplicationStart {

    @Autowired
    private TestDataCreation testDataCreation;

    @Authenticated
    @EventListener
    public void onApplicationStarted(ApplicationStartedEvent event) {
        testDataCreation.createData();
    }
}