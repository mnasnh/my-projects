package com.myprojects;

import com.myprojects.util.Node;
import com.myprojects.util.PriorityQueueUsingDll;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostActiveCookieService {

    private  Map<String, PriorityQueueUsingDll> cookiesByDate;
    private  Map<String, Node> cookieById;

    public MostActiveCookieService()
    {
        cookiesByDate = new HashMap<>();
        cookieById = new HashMap<>();
    }


    public  List<String> getMostActiveCookiesList(String fileName, String lookupDate) {
        List<String> activeCookieList = new ArrayList<>();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream(fileName);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(streamReader);
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                if (lineNo++ == 1) {
                    // skip headers
                    continue;
                }

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
        addCookiesByMaxCount(lookupDate, activeCookieList);
        return activeCookieList;

    }

    private  void processLogs(String cookieId, String date) {

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

    private  void addCookiesByMaxCount(String lookupDate, List<String> activeCookieList) {
        PriorityQueueUsingDll dll = cookiesByDate.get(lookupDate);
        PriorityQueueUsingDll dllTemp = dll;
        if (dllTemp != null) {
            Node dllTempHead = dllTemp.getHead();
            activeCookieList.add(dllTempHead.getCookieId());
            dllTempHead = dllTempHead.getNext();
            while (dllTempHead != null && dllTempHead.getCount() == dll.getHead().getCount()) {
                activeCookieList.add(dllTempHead.getCookieId());
                dllTempHead = dllTempHead.getNext();
            }
        }
    }
}
