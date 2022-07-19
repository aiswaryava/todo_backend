package com.todos.services.impl;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import com.todos.models.Todo;

import com.todos.repositories.TodosRepository;

import com.todos.services.TodosService;





@Service

public class TodoServiceDatabaseImpl implements TodosService {



    @Autowired

    private TodosRepository todosRepository;

    

    @Override

    public List<Todo> getAllTodos() {

        // TODO Auto-generated method stub

        return todosRepository.findAll();

    }


    @Override

    public Todo updateTodo(String name, int id, Todo todo) {

        // TODO Auto-generated method stub

    	Optional<Todo> existingTodo = todosRepository.findById(id);
        if(existingTodo.isPresent() && existingTodo.get().getUser().equals(name)){
            Todo updatedTodo = existingTodo.get();
            updatedTodo.setDescription(todo.getDescription());
            updatedTodo.setTargetDate(todo.getTargetDate());
            updatedTodo.setDone(todo.isDone());
            //save to db
            todosRepository.save(updatedTodo);
            return updatedTodo;
        }
        return null;
    }



    @Override

    public boolean deleteTodo(int id) {

        // TODO Auto-generated method stub

    	Optional<Todo> existingTodo = todosRepository.findById(id);
        if(existingTodo.isPresent()) {
            todosRepository.deleteById(id);
            return true;
        }    
        return false;
    }

    



	@Override
	public List<Todo> getTodosByuser(String user) {
		// TODO Auto-generated method stub
		 return todosRepository.findByUser(user);
		 }



	



	@Override
	public Todo addTodo(Todo todo) {
		// TODO Auto-generated method stub
        // Save the value to the db using repository save method

		return todosRepository.save(todo);
	}



	@Override
	public Todo getTodosById(int id) {
		Optional<Todo> todo = todosRepository.findById(id);
        if(todo.isPresent())
            return todo.get();
        return null;
		// TODO Auto-generated method stub
	
	}

	 


	@Override
	public Todo getTodoById(int id) {
		// TODO Auto-generated method stub
		Optional<Todo> todo = todosRepository.findById(id);
        if(todo.isPresent())
            return todo.get();
        return null;	
        }



	@Override
	public List<Todo> getTodosByUser(String user) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Todo addToDo(Todo todo) {
		// TODO Auto-generated method stub
		return null;
	}



}