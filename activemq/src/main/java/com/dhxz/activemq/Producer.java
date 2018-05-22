package com.dhxz.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class Producer implements CommandLineRunner {

    @Autowired
    private JmsMessagingTemplate template;

    @Autowired
    private Queue queue;

    @Override
    public void run(String... args) throws Exception {
        send("send this message after program init");
        System.out.println("message was sent to the Queue");
    }

    public void send(String msg) {
        this.template.convertAndSend(this.queue,msg);
    }
}
