package com.dhxz.actuator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = {
        "management.server.port=0",
        "management.endpoints.web.base-path=/admin",
        "management.endpoint.health.show-details=never"
})
public class ManagementPortAndPathSampleActuatorApplicationTests {
    @LocalServerPort
    private int port = 9010;
    @LocalManagementPort
    private int managementPort = 9011;

    @Autowired
    private Environment environment;

    @Test
    public void testHome() {
        ResponseEntity<Map> entity = new TestRestTemplate("user", getPassword())
                .getForEntity("http://localhost:" + this.port, Map.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map body = entity.getBody();
        assertThat(body.get("message")).isEqualTo("Hello DHXZ");
    }

    @Test
    public void testMetrics() {
        testHome();
        ResponseEntity<Map> entity = new TestRestTemplate()
                .getForEntity("http://localhost:" + this.managementPort + "/admin/metrics", Map.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testEnvNotFound() {
        String unknownProperty = "test-dose-not-exist";
        assertThat(this.environment.containsProperty(unknownProperty)).isFalse();

        ResponseEntity<String> entity = new TestRestTemplate()
                .withBasicAuth("user", getPassword())
                .getForEntity("http://localhost:" + this.managementPort + "/admin/env" + unknownProperty, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testMissing() {
        ResponseEntity<String> entity = new TestRestTemplate("user", getPassword())
                .getForEntity("http://localhost:" + this.managementPort + "/admin/missing", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).contains("\"status\":404");
    }

    private String getPassword() {
        return "password";
    }
}
