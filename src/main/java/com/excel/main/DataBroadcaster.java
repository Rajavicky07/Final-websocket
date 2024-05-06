package com.excel.main;

import java.util.Arrays;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public DataBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastSheetData(List<String[]> data) {
        System.out.println("Broadcasting data: " + Arrays.deepToString(data.toArray()));
        messagingTemplate.convertAndSend("/topic/sheetData", data);
    }
}
