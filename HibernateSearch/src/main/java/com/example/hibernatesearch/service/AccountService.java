package com.example.hibernatesearch.service;

import com.example.hibernatesearch.model.Account;
import com.example.hibernatesearch.respository.AccountRepository;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private static final List<String> SEARCHABLE_FIELDS = Arrays.asList("username", "firstName", "lastName", "email");

    public List<Account> searchAccount(String text, List<String> fields, int limit) {

        List<String> fieldsToSearchBy = fields.isEmpty() ? SEARCHABLE_FIELDS : fields;

        boolean containsInvalidField = fieldsToSearchBy.stream().anyMatch(f -> !SEARCHABLE_FIELDS.contains(f));

        if (containsInvalidField) {
            throw new IllegalArgumentException();
        }

        return accountRepository.searchBy(text, limit, fields.toArray(new String[0]));
    }

    public void indexData() throws InterruptedException {
        EntityManager em = emf.createEntityManager();
        SearchSession searchSession = Search.session(em);
        MassIndexer indexer = searchSession.massIndexer(Account.class)
                .threadsToLoadObjects(7);
        indexer.startAndWait();
    }
}
