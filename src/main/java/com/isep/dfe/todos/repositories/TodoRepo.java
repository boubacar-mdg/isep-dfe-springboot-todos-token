/**
 * @author      Boubacar Demba Mandiang
 * @copyright   Copyright (c) 2023,  Banque de Dakar Département Sécurité
 * @license     Logiciel privé sans licence
 */
package com.isep.dfe.todos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isep.dfe.todos.models.Todo;

import java.util.Collection;

public interface TodoRepo extends JpaRepository<Todo, Long> {
}

