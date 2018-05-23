package com.dhxz.actuator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = {
        "management.endpoints.web.base-path=/admin"
})
public class ManagementPathSampleActuatorApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHealth() {
        ResponseEntity<String> entity = this.restTemplate
                .withBasicAuth("user", getPassword())
                .getForEntity("/admin/health", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("\"status\":\"UP\"");
    }

    @Test
    public void testHomeIsSecure() {
        ResponseEntity<Map> entity = this.restTemplate
                .getForEntity("/", Map.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        Map body = entity.getBody();

        assertThat(body.get("error")).isEqualTo("Unauthorized");
        assertThat(entity.getHeaders()).doesNotContainKey("Set-Cookie");
    }

    private String getPassword() {
        return "password";
    }
}
