package com.dhxz.actuator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = {
        "management.server.port=0",
        "management.servier.address=localhost",
        "management.server.servlet.context-path:/admin"
})
public class ManagementAddressActuatorApplicationTests {
    @LocalServerPort
    private int port = 9010;
    @LocalManagementPort
    private int managementPort = 9011;

    @Test
    public void testHome(){
        ResponseEntity<Map> entity = new TestRestTemplate()
                .getForEntity("http://localhost:" + this.port, Map.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testHealth() {
        ResponseEntity<String> entity = new TestRestTemplate()
                .withBasicAuth("user", getPassword())
                .getForEntity("http://localhost:"
                        + this.managementPort + "/admin/actuator/health", String.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("\"status\":\"UP\"");
    }

    private String getPassword() {
        return "password";
    }
}
