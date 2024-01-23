document.addEventListener("DOMContentLoaded", function () {
    // Fetch todos and render them
    fetchTodos();

    // Function to fetch todos from the server
    function fetchTodos() {
        fetch("/api/v1/todos")
            .then(response => response.json())
            .then(data => renderTodos(data))
            .catch(error => console.error('Error fetching todos:', error));
    }

    // Function to render todos on the page
    function renderTodos(todos) {
        const todoListContainer = document.getElementById("todoList");
        todoListContainer.innerHTML = "";

        if (todos.length > 0) {
            todos.forEach(todo => {
                const todoItem = document.createElement("div");
                todoItem.className = "todo-item";

                const deleteButton = document.createElement("button");
                deleteButton.innerText = "Delete";
                deleteButton.addEventListener("click", () => deleteTodo(todo.id));

                const completeButton = document.createElement("button");
                completeButton.innerText = "Mark as Complete";
                completeButton.addEventListener("click", () => markAsComplete(todo.id));

                const todoDescription = document.createElement("span");
                todoDescription.innerText = todo.description;

                todoItem.appendChild(todoDescription);
                todoItem.appendChild(completeButton);
                todoItem.appendChild(deleteButton);

                if (todo.completedDate) {
                    const completedDate = document.createElement("span");
                    completedDate.innerText = "Completed on: " + todo.completedDate;
//                    todoItem.appendChild(completedDate);
                    todoItem.insertBefore(completedDate, deleteButton);

                    completeButton.hidden = true
                    todoItem.classList.add("todo-item-completed")
                }

                todoListContainer.appendChild(todoItem);
            });
        } else {
            todoListContainer.innerHTML = "No todos";
        }
    }

    // Function to delete a todo
    function deleteTodo(todoId) {
        fetch(`/api/v1/todo/${todoId}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                // If delete is successful, fetch and render updated todos
                fetchTodos();
            } else {
                console.error('Error deleting todo:', response.statusText);
            }
        })
        .catch(error => console.error('Error deleting todo:', error));
    }

    // Function to mark a todo as complete
    function markAsComplete(todoId) {
        fetch(`/api/v1/todo/${todoId}`, {
            method: "PUT"
        })
        .then(response => {
            if (response.ok) {
                // If marking as complete is successful, fetch and render updated todos
                fetchTodos();
            } else {
                console.error('Error marking todo as complete:', response.statusText);
            }
        })
        .catch(error => console.error('Error marking todo as complete:', error));
    }
});
