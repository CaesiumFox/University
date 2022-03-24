package io.github.caesiumfox.web4.rest;

import java.util.List;
import java.util.ArrayList;

import io.github.caesiumfox.web4.entity.HistoryEntry;

public class HistoryResponse {
    private String currentUsername;
    private List<HistoryEntry> entries;
    
    public HistoryResponse() {
        entries = new ArrayList<HistoryEntry>();
    }

    public HistoryResponse(String username, List<HistoryEntry> entries) {
        this.currentUsername = username;
        this.entries = entries;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public List<HistoryEntry> getEntries() {
        return entries;
    }
}
