package com.myprojects;

import org.apache.commons.cli.*;

import java.util.List;

public class CookieLogParserRunner {

    public CookieLogParserRunner() {
    }

    public static void main(String[] args) {

        CommandLine cmd = getCommandLine(args);
        String inputFileName = cmd.getOptionValue("filename");
        String lookupDate = cmd.getOptionValue("date");
        List<String> activeCookies = new MostActiveCookieService().getMostActiveCookiesList(inputFileName, lookupDate);
        for (String activeCookie : activeCookies) {
            System.out.println(activeCookie);
        }
    }

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
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
        return cmd;
    }
}
