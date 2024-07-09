
package com.isep.dfe.todos.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@SuperBuilder
// Cette annotation spécifie que seules les propriétés non nulles doivent être incluses dans la sortie.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    protected long timestamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map<?,?> data;
    protected Map<?,?> count;
}
