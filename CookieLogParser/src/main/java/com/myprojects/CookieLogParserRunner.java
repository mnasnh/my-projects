package com.myprojects;

import com.myprojects.exception.LogParsingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.util.List;


/**
 * Main runner class
 */
@Slf4j
public class CookieLogParserRunner {

    public CookieLogParserRunner() {
    }

    /**
     * Entry point for the program. Takes command line arguments for file name and look up date.
     * Prints most active cookie Ids in a separate line to the console.
     * @param args
     */
    public static void main(String[] args) {
        CommandLine cmd = getCommandLine(args);
        String inputFileName = cmd.getOptionValue("filename");
        String lookupDate = cmd.getOptionValue("date");
        List<String> activeCookies;
        try {
            activeCookies = new MostActiveCookieService().getMostActiveCookiesList(inputFileName, lookupDate);
            for (String activeCookie : activeCookies) {
                System.out.println(activeCookie);
            }
        } catch (LogParsingException e) {
            log.error("Log Parsing Exception:", e);
        }

    }

    /**
     * Helper method to parse command line
     * @param args
     * @return cmd
     */
    private static CommandLine getCommandLine(String[] args) {
        Options options = new Options();
        Option filename = new Option("f", "filename", true, "input file name");
        filename.setRequired(true);
        options.addOption(filename);
        Option date = new Option("d", "date", true, "date in UTC");
        date.setRequired(true);
        options.addOption(date);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            log.error("Exception occurred while parsing command line", e);
            formatter.printHelp("cookie-log-parser", options);
            System.exit(1);
        }
        return cmd;
    }
}
