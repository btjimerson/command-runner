package dev.snbv2.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
     * Executes a shell command received via HTTP POST request.
     * 
     * @param command The command object containing the command to execute
     * @return The output of the command as a string, or an error message if
     *         execution fails
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
