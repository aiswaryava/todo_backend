package com.todos.services;

import java.util.List;

import com.todos.models.Todo;

public interface TodosService {
	
	List<Todo> getAllTodos();
	
	List<Todo> getTodosByuser(String user);
	
	Todo getTodosById(int id);
	
	Todo addTodo(Todo todo);
	
	Todo updateTodo(String name, int id, Todo todo);
	
	boolean deleteTodo(int id);

	List<Todo> getTodosByUser(String user);

	Todo getTodoById(int id);

	Todo addToDo(Todo todo);

}
