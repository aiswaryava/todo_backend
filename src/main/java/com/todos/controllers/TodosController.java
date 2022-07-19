package com.todos.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.Link;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.todos.exception.ResourceNotFoundException;
import com.todos.models.Todo;
import com.todos.services.TodosService;

@RestController
@RequestMapping("/api/todos")
public class TodosController {
	
	@Autowired
	private TodosService todoService;
	
	//GET Method to get all todos
	//http://localhost:8080/todos
//	 @GetMapping("/todos") ==> Instead of giving url like this, we can use requestmapping annotation
	@GetMapping()
	public List<Todo> getAllTodos() {
		
//		return todoService.getAllTodos();
		
		List<Todo> allTodos = todoService.getAllTodos();

//        for(Todo todo : allTodos) {
//            int todoId = todo.getId();
//            Link selfLink = WebMvcLinkBuilder.linkTo(TodosController.class).slash(todoId).withSelfRel();
//            todo.add(selfLink);
//        }

        return allTodos;
		
	}
	
	//Get method to get all the todos for a particularuser
	//http://localhost:8080/todos/user/{name}
	 @GetMapping("user/{name}")
	public List<Todo> getAllTodosByUser(@PathVariable String name) {
		 List<Todo> userTodos = todoService.getTodosByuser(name);
		    if (userTodos.isEmpty()) {
		    	throw new ResourceNotFoundException( "User Not Found");
		    }
		 return todoService.getTodosByuser(name);
		
	}
	 
	//GET Method to get todo based on id
	    //http://localhost:8080/todos/{id}
//	 @GetMapping("/todos/{id}")
	 @GetMapping("/{id}")
	 public Todo getTodosById(@PathVariable int id) {
		 
		 Todo todo = todoService.getTodosById(id);
		 if(todo==null) {

//	            throw new RuntimeException("Not Found the user");
	            //Instead of giving exception message, we can give response status code, no need of creating new exception class
//			 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "To do not Found");
			 //dEveloper tools
			 throw new ResourceNotFoundException("resource not found");
	            

	}
		 
		return todo;
		 
	 }
	 
	 //posting new todo
	 //http://localhost/todos/
	 //If succcessful creation should return 201 HTTP created
	 @PostMapping()
	 public ResponseEntity<Todo>  addTodo(@RequestBody Todo todo) {
		 Todo newTodo = todoService.addTodo(todo);
		 if(newTodo == null) {
			 return ResponseEntity.noContent().build();
		 }
//		 return new ResponseEntity<Todo>(newTodo, HttpStatus.CREATED);
		 
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	                .buildAndExpand(newTodo.getId()).toUri();
	        return ResponseEntity.created(location).build();
		    
	        
	 }
	 
     //Update- PUT is used
     //localhost:8080/todos/user/{name}/{id}
	 @PutMapping("/user/{name}/{id}")
	    public ResponseEntity<Todo> updateTodo(@PathVariable String name, @PathVariable int id, @Valid @RequestBody Todo todo) {
	        Todo updatedTodo = todoService.updateTodo(name, id, todo);
	        if(updatedTodo == null) {
//	            throw new ResourceNotFoundException("No todo for giving Name and id");
				 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "To do not Found");

	        	
	        }
	        return new ResponseEntity<Todo>(updatedTodo,HttpStatus.OK);
	    }
	 
	 //Delete- delete the resource based on id
	    // DELETE - delete the resource based on id
	    // localhost:8080/todos/{id}
	 
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteTodo(@PathVariable int id) {
	        boolean result = todoService.deleteTodo(id);
	        if (!result) {
	            throw new ResourceNotFoundException("Resource not found for given id" + id);
	        }
	        return new ResponseEntity<String>("Successfully Deleted Todo", HttpStatus.OK);
	    }
	 
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach((error)->
	        {
	            String fieldName =  ((FieldError)error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        return errors;
	    }

}
