package com.myprojects;

import com.myprojects.util.Node;
import com.myprojects.util.PriorityQueueUsingDll;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
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
        String fileName = "/Users/manishasinha/IdeaProjects/github-projects/my-projects/CookieLogParser/src/main/resources/cookielog.csv";
        String date = "2018-12-08";
        new CookieLogParserRunner().parseCookieLogs(fileName, date);
    }
}
