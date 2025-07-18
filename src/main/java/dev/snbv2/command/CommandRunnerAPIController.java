package dev.snbv2.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for executing shell commands.
 * Provides endpoints for command execution via HTTP requests.
 */
@RestController
@RequestMapping("/api/v1")
public class CommandRunnerAPIController {

    private static final Log LOG = LogFactory.getLog(CommandRunnerAPIController.class);
    
    /**
     * Returns system information about the environment where the application is running.
     * This includes OS details, Java version, available processors, and memory information.
     * 
     * @return A map containing system information
     */
    @GetMapping("/system-info")
    public Map<String, String> getSystemInfo() {
        Map<String, String> systemInfo = new HashMap<>();
        
        // OS information
        systemInfo.put("os.name", System.getProperty("os.name"));
        systemInfo.put("os.version", System.getProperty("os.version"));
        systemInfo.put("os.arch", System.getProperty("os.arch"));
        
        // Java information
        systemInfo.put("java.version", System.getProperty("java.version"));
        systemInfo.put("java.vendor", System.getProperty("java.vendor"));
        
        // Runtime information
        Runtime runtime = Runtime.getRuntime();
        systemInfo.put("available.processors", String.valueOf(runtime.availableProcessors()));
        systemInfo.put("free.memory.mb", String.valueOf(runtime.freeMemory() / (1024 * 1024)));
        systemInfo.put("total.memory.mb", String.valueOf(runtime.totalMemory() / (1024 * 1024)));
        systemInfo.put("max.memory.mb", String.valueOf(runtime.maxMemory() / (1024 * 1024)));
        
        // Host information
        try {
            systemInfo.put("hostname", java.net.InetAddress.getLocalHost().getHostName());
        } catch (Exception e) {
            systemInfo.put("hostname", "Unknown");
        }
        
        LOG.debug("System information requested");
        return systemInfo;
    }

    /**
     * Executes a shell command received via HTTP POST request.
     * 
     * @param command The command object containing the command to execute
     * @return The output of the command as a string, or an error message if execution fails
     */
    @PostMapping(path = ("/command"))
    public String execute(@RequestBody Command command) {

        Process process;
        StringBuilder sb = new StringBuilder();

        try {
            process = Runtime.getRuntime().exec(command.getCommandAsArray());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line = "";

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        String result = sb.toString();

        LOG.debug(String.format(
                "Result of command [%s] = [%s]",
                command.getCommand(),
                result));

        return result;
    }

}
