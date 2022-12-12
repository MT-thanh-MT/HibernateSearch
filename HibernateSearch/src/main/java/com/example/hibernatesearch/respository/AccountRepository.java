package com.example.hibernatesearch.respository;

import com.example.hibernatesearch.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends SearchRepository<Account, Long>{
}
