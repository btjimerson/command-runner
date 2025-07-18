package dev.snbv2.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jakarta.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Web controller for the command runner application.
 * Handles web requests for the UI, manages command execution and history.
 */
@Controller
public class CommandRunnerController {

    private static final Log LOG = LogFactory.getLog(CommandRunnerController.class);

    /**
     * Displays the index page with a command form.
     * Initializes command history in the session if not already present.
     * 
     * @param model The Spring MVC model
     * @param session The HTTP session
     * @return The name of the view to render
     */
    @GetMapping(path = { "/", "/index" })
    public String index(Model model, HttpSession session) {
        Command command = new Command();
        model.addAttribute("command", command);
        if (session.getAttribute("commandHistory") == null) {
            session.setAttribute("commandHistory", new CommandHistory());
        }

        return "index";
    }

    /**
     * Executes a command submitted from the web form.
     * Adds the command to the history and displays the result.
     * 
     * @param command The command to execute
     * @param model The Spring MVC model
     * @param session The HTTP session
     * @return The name of the view to render
     */
    @PostMapping(path = ("/execute"))
    public String execute(@ModelAttribute Command command, Model model, HttpSession session) {

        Process process;
        StringBuilder sb = new StringBuilder();

        // Update the command history
        if (session.getAttribute("commandHistory") == null) {
            session.setAttribute("commandHistory", new CommandHistory());
        }
        CommandHistory commandHistory = (CommandHistory) session.getAttribute("commandHistory");

        commandHistory.addHistory(command.getCommand());

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
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
            return "index";
        }

        String result = sb.toString();

        LOG.debug(String.format(
                "Result of command [%s] = [%s]",
                command.getCommand(),
                result));

        model.addAttribute("result", result);
        return "index";
    }

}
