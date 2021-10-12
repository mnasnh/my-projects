package com.myprojects;

import com.myprojects.util.Node;
import com.myprojects.util.PriorityQueueUsingDll;
import org.apache.commons.cli.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CookieLogParserRunner {

    private Map<String, PriorityQueueUsingDll> cookiesByDate;
    private Map<String, Node> cookieById;

    public CookieLogParserRunner() {
        cookiesByDate = new HashMap<>();
        cookieById = new HashMap<>();
    }

    public void parseCookieLogs(String fileName, String lookupDate) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream(fileName);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(streamReader);
            while ((line = br.readLine()) != null) {
                String[] logs = line.split(splitBy);
                String cookieId = logs[0];
                String date = logs[1].substring(0, 10);
                processLogs(cookieId, date);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printCookiesByMaxCount(lookupDate);

    }

    private void printCookiesByMaxCount(String lookupDate) {
        PriorityQueueUsingDll dll = cookiesByDate.get(lookupDate);
        PriorityQueueUsingDll dllTemp = dll;
        if (dllTemp != null) {
            Node dllTempHead = dllTemp.getHead();
            System.out.println(dllTempHead.getCookieId());
            dllTempHead = dllTempHead.getNext();
            while (dllTempHead != null && dllTempHead.getCount() == dll.getHead().getCount()) {
                System.out.println(dllTempHead.getCookieId());
                dllTempHead = dllTempHead.getNext();
            }
        }
    }

    private void processLogs(String cookieId, String date) {

        StringBuilder mapLookupKeySb = new StringBuilder();
        PriorityQueueUsingDll dll;
        String mapLookupKey = mapLookupKeySb.append(cookieId).append("|").append(date).toString();
        if (!cookiesByDate.containsKey(date)) {
            dll = new PriorityQueueUsingDll();
            Node newNode = new Node(cookieId, 1);
            dll.push(newNode);
            cookiesByDate.put(date, dll);
            cookieById.put(mapLookupKey, newNode);
        } else {
            dll = cookiesByDate.get(date);
            if (cookieById.containsKey(mapLookupKey)) {
                Node existing = cookieById.get(mapLookupKey);
                Node newNode = new Node(existing.getCookieId(), existing.getCount() + 1);
                dll.removeNode(existing);
                dll.push(newNode);
                cookieById.put(mapLookupKey, newNode);
            } else {
                Node node = new Node(cookieId, 1);
                cookieById.put(mapLookupKey, node);
                dll.push(node);
            }
        }
    }

    public static void main(String[] args) {

        CommandLine cmd = getCommandLine(args);
        String inputFileName = cmd.getOptionValue("filename");
        String lookupDate = cmd.getOptionValue("date");
        new CookieLogParserRunner().parseCookieLogs(inputFileName, lookupDate);
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
