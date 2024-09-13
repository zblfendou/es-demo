package com.es.demo.es_demo.service;

import com.es.demo.es_demo.document.Person;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final ElasticsearchOperations operations;
    public PersonService(ElasticsearchOperations operations) {
        this.operations = operations.withRefreshPolicy(RefreshPolicy.IMMEDIATE);
    }

    public String savePerson(Person person) {
        Person save = operations.save(person);
        return save.getId();
    }

    public void savePerson(List<Person> persons) {
        operations.save(persons);
    }

    public Person getPerson(String id) {
        return operations.get(id, Person.class);
    }

    public List<Person> getAllPerson() {
        PageRequest pageRequest = PageRequest.of(0, 1000);
        Query query = Query.findAll();
        query.setPageable(pageRequest);
        return operations.search(query, Person.class).getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    public void deletePerson(Person person) {
        operations.delete(person);
    }

    public boolean exists(String id) {
        return operations.exists(id, Person.class);
    }

    public long personSize() {
        Query query = Query.findAll();
        return operations.count(query, Person.class);
    }

    public void delAll(List<String> ids) {
        ids.forEach(id->operations.delete(id,Person.class));
    }

    public float getSumAge() {
        Query query = Query.findAll();
        query.setPageable(PageRequest.of(0, 1000));
        SearchHits<Person> searchHits = operations.search(query, Person.class);
        return searchHits.stream().map(SearchHit::getContent).mapToInt(Person::getAge).sum();
    }

    public Person getPersonByName(String name) {
        Criteria criteria = new Criteria("name").is(name);
        SearchHits<Person> searchHits = operations.search(new CriteriaQuery(criteria), Person.class);
        return searchHits.stream().map(SearchHit::getContent).findFirst().orElse(null);
    }

    public boolean updatePerson(Person person) {
        operations.update(person);
        return true;
    }
}
