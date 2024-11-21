package robotic.system.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OpenApiConfig.class)
class OpenApiConfigTest {

    private OpenApiConfig openApiConfig;

    @BeforeEach
    void setUp() {
        openApiConfig = new OpenApiConfig();
    }

    @Test
    void testCustomOpenAPI() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        assertNotNull(openAPI, "OpenAPI object should not be null");
        Info info = openAPI.getInfo();
        assertNotNull(info, "Info should not be null");
        assertEquals("Robotics Lab System API", info.getTitle(), "API title should match");
        assertEquals("1.0", info.getVersion(), "API version should match");
        assertEquals("API documentation for Robotics Lab System API", info.getDescription(), "API description should match");

        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("bearer-key");
        assertNotNull(securityScheme, "Security scheme should not be null");
        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType(), "Security scheme type should be HTTP");
        assertEquals("bearer", securityScheme.getScheme(), "Security scheme should be 'bearer'");
        assertEquals("JWT", securityScheme.getBearerFormat(), "Security scheme bearer format should be 'JWT'");
    }
}
