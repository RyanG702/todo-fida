package com.ryangrillo.fida.todolist.repository;

import com.ryangrillo.fida.todolist.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long>  {
}
