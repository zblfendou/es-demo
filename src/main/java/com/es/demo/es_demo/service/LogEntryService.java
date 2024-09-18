package com.es.demo.es_demo.service;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.es.demo.es_demo.document.LogEntry;
import com.es.demo.es_demo.repositories.LogEntryRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogEntryService {
    private final ElasticsearchOperations operations;
    private final LogEntryRepository logEntryRepository;

    public LogEntryService(ElasticsearchOperations operations, LogEntryRepository logEntryRepository) {
        this.operations = operations;
        this.logEntryRepository = logEntryRepository;
    }

    public void saveLog(LogEntry logEntry) {
        operations.save(logEntry);
    }

    public void delAll() {
        logEntryRepository.deleteAll();
    }

    public void saveLogs(List<LogEntry> logEntries) {
        logEntryRepository.saveAll(logEntries);
    }
}
