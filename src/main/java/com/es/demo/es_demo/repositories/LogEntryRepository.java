package com.es.demo.es_demo.repositories;

import com.es.demo.es_demo.document.LogEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogEntryRepository extends ElasticsearchRepository<LogEntry, String> {
}
