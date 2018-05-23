package com.dhxz.actuator.customsecurity;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("cors")
public class CorsSampleActuatorApplicationTests {

    private TestRestTemplate restTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        LocalHostUriTemplateHandler handler = new LocalHostUriTemplateHandler(
                this.applicationContext.getEnvironment(), "http"
        );
        builder = builder.uriTemplateHandler(handler);
        this.restTemplate = new TestRestTemplate(builder);
    }

}
