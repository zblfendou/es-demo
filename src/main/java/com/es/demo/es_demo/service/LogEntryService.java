package com.es.demo.es_demo.service;

import com.es.demo.es_demo.document.LogEntry;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class LogEntryService {
    private final ElasticsearchOperations operations;

    public LogEntryService(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    public String saveLog(LogEntry logEntry) {
        LogEntry save = operations.save(logEntry);
        return save.getId();
    }
}
