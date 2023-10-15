package com.example.wanted.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseCleaner implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> entities = entityManager.getMetamodel().getEntities()
            .stream()
            .map(it -> {
                System.out.println(it);
                return it.getName().toUpperCase();
            })
            .toList();
    }
}
