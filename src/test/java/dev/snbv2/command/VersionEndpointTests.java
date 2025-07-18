package dev.snbv2.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for the version endpoint in the CommandRunnerAPIController.
 * These tests verify that the endpoint correctly returns the application version.
 */
@WebMvcTest(CommandRunnerAPIController.class)
class VersionEndpointTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Value("${application.version}")
    private String expectedVersion;

    /**
     * Tests that the version endpoint returns the correct application version.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void versionEndpointShouldReturnApplicationVersion() throws Exception {
        // Execute test
        MvcResult result = mockMvc.perform(get("/api/v1/version")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Parse response and verify version
        String responseContent = result.getResponse().getContentAsString();
        Map<String, String> versionInfo = objectMapper.readValue(responseContent, new TypeReference<Map<String, String>>() {});
        
        // Verify version is present and not empty
        assertTrue(versionInfo.containsKey("version"));
        assertNotNull(versionInfo.get("version"));
        assertFalse(versionInfo.get("version").isEmpty());
        
        // If we have an expected version (not "unknown"), verify it matches
        if (!"unknown".equals(expectedVersion)) {
            assertEquals(expectedVersion, versionInfo.get("version"));
        }
    }
}