package com.myprojects;

import com.myprojects.exception.LogParsingException;
import com.myprojects.util.MaxFrequencyStack;
import com.myprojects.util.Node;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MostActiveCookieService {

    private Map<String, MaxFrequencyStack> cookiesByDate;
    private Map<String, Integer> cookieById;

    public MostActiveCookieService() {
        cookiesByDate = new HashMap<>();
        cookieById = new HashMap<>();
    }


    public List<String> getMostActiveCookiesList(String fileName, String lookupDate) throws LogParsingException {
        List<String> activeCookieList = new ArrayList<>();

        String line = "";
        String splitBy = ",";
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream(fileName);
        if (inputStream == null) {
            throw new LogParsingException("Received NULL input stream");
        }
        try {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(streamReader);
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                if (lineNo++ == 1) {
                    // skip headers
                    continue;
                }

                String[] logs = line.split(splitBy);
                if(logs.length<2)
                {
                    throw new LogParsingException("Invalid Log Entry");
                }
                String cookieId = logs[0];
                String date = logs[1].substring(0, 10);
                processLogs(cookieId, date);

            }

        } catch (IOException e) {
            log.error("Exception occurred while processing log file", e);
            throw new LogParsingException(e);
        }
        addCookiesByMaxCount(lookupDate, activeCookieList);
        return activeCookieList;

    }

    private void processLogs(String cookieId, String date) {

        StringBuilder mapLookupKeySb = new StringBuilder();
        MaxFrequencyStack dll;
        String mapLookupKey = mapLookupKeySb.append(cookieId).append("|").append(date).toString();
        if (!cookiesByDate.containsKey(date)) {
            dll = new MaxFrequencyStack();
            Node newNode = new Node(cookieId, 1);
            dll.push(newNode);
            cookiesByDate.put(date, dll);
            cookieById.put(mapLookupKey, newNode.getCount());
        } else {
            dll = cookiesByDate.get(date);
            if (cookieById.containsKey(mapLookupKey)) {
                int existingCount = cookieById.get(mapLookupKey);
                Node newNode = new Node(cookieId, existingCount + 1);
                dll.push(newNode);
                cookieById.put(mapLookupKey, existingCount + 1);
            } else {
                Node node = new Node(cookieId, 1);
                cookieById.put(mapLookupKey, 1);
                dll.push(node);
            }
        }
    }

    private void addCookiesByMaxCount(String lookupDate, List<String> activeCookieList) {
        MaxFrequencyStack dll = cookiesByDate.get(lookupDate);
        MaxFrequencyStack dllTemp = dll;
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
