package com.isep.dfe.todos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.isep.dfe.todos.models.Response;
import com.isep.dfe.todos.models.Todo;
import com.isep.dfe.todos.services.TodoService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Map;

@RestController
// URL de base de ce controller
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
@Tag(name = "todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/save")
     @Operation(summary = "Point de terminaison permettant de créer un tâche")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true)
    })
    public ResponseEntity<Response> saveTodo(@RequestBody @Valid Todo todo) throws UnsupportedEncodingException {
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timestamp(Instant.now().getEpochSecond())
                        .data(Map.of("item", todoService.create(todo)))
                        .message("Tâche enregistrée avec succès")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("")
    @Operation(summary = "Point de terminaison permettant d'avoir la liste des tâches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true)
    })
    public ResponseEntity<Response> getAllTodos() throws InterruptedException {
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timestamp(Instant.now().getEpochSecond())
                        .data(Map.of("items", todoService.list()))
                        .message("La liste des tâches")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Point de terminaison permettant de supprimer une tâche")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true)
    })
    public ResponseEntity<Response> deleteTodos(@PathVariable Long id) throws InterruptedException {
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timestamp(Instant.now().getEpochSecond())
                        .data(Map.of("items", todoService.delete(id)))
                        .message("Suppression de la tâche avec succès")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

}
