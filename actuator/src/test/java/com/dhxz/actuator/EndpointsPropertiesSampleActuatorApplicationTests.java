package com.dhxz.actuator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("endpoints")
public class EndpointsPropertiesSampleActuatorApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCustomErrorPath() {
        ResponseEntity<Map> user = this.restTemplate
                .withBasicAuth("user", getPassword())
                .getForEntity("/opps", Map.class);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = user.getBody();
        assertThat(body.get("error")).isEqualTo("None");
        assertThat(body.get("status")).isEqualTo(999);
    }

    @Test
    public void testCustomContextPath() {
        ResponseEntity<String> entity = this.restTemplate
                .withBasicAuth("user", getPassword())
                .getForEntity("/admin/health", String.class);
        System.out.println(entity.getBody());
        assertThat(entity.getBody().contains("\"status:\":\"UP\""));

    }

    private String getPassword() {
        return "password";
    }
}
