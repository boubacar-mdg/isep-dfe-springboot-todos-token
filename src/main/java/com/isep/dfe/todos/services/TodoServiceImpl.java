package com.isep.dfe.todos.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.isep.dfe.todos.models.Todo;
import com.isep.dfe.todos.repositories.TodoRepo;

import java.io.UnsupportedEncodingException;
import java.util.Collection;


@RequiredArgsConstructor
@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepo repo;
    @Override
    public Todo create(Todo todo) throws UnsupportedEncodingException {
        return repo.save(todo);
    }

    @Override
    public Collection<Todo> list() {
        return repo.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        repo.deleteById(id);
        return Boolean.TRUE;
    }

}
