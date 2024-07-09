
package com.isep.dfe.todos.services;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import com.isep.dfe.todos.models.Todo;

public interface TodoService {
    Todo create(Todo todo) throws UnsupportedEncodingException;
    Collection<Todo> list();
    Boolean delete(Long id);

}
