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

@Controller
public class CommandRunnerController {

    private static final Log LOG = LogFactory.getLog(CommandRunnerController.class);

    @GetMapping(path={"/","/index"})
    public String index(Model model, HttpSession session) {
        Command command = new Command();
        model.addAttribute("command", command);
        if (session.getAttribute("commandHistory") == null) {
            session.setAttribute("commandHistory", new CommandHistory());
        }

        return "index";
    }

    @PostMapping(path=("/execute"))
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
