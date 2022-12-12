package com.example.hibernatesearch.controller;

import com.example.hibernatesearch.model.Account;
import com.example.hibernatesearch.model.SearchRequestDTO;
import com.example.hibernatesearch.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> searchAccount(@RequestBody SearchRequestDTO searchRequestDTO) {
        List<Account> list = accountService.searchAccount(
                searchRequestDTO.getText(),
                searchRequestDTO.getFields(),
                searchRequestDTO.getLimit()
        );

        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/account/reindex")
    public ResponseEntity<Void> reindexAccount() throws InterruptedException {
        accountService.indexData();
        return ResponseEntity.ok().build();
    }
}
