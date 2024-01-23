// script.test.js
const fetchMock = require('jest-fetch-mock');

beforeAll(() => {
  fetchMock.enableMocks();
});

afterEach(() => {
  fetchMock.resetMocks();
});

test('fetchTodos should call fetch and render todos', async () => {
  // Arrange
  document.body.innerHTML = '<div id="todoList"></div>';
  fetchMock.mockResponseOnce(JSON.stringify([{ id: 1, description: 'Test Todo' }]));

  // Act
  await fetchTodos();

  // Assert
  expect(fetchMock).toHaveBeenCalledWith('/api/v1/todos');
  expect(document.getElementById('todoList').innerHTML).toContain('Test Todo');
});

test('renderTodos should render todos on the page', () => {
  // Arrange
  document.body.innerHTML = '<div id="todoList"></div>';
  const todos = [{ id: 1, description: 'Test Todo' }];

  // Act
  renderTodos(todos);

  // Assert
  const todoItem = document.querySelector('.todo-item');
  expect(todoItem).toBeDefined();
  expect(todoItem.innerHTML).toContain('Test Todo');
});

test('deleteTodo should call fetch and fetchTodos on successful delete', async () => {
  // Arrange
  fetchMock.mockResponseOnce('', { status: 200 });

  // Act
  await deleteTodo(1);

  // Assert
  expect(fetchMock).toHaveBeenCalledWith('/api/v1/todo/1', { method: 'DELETE' });
  expect(fetchTodos).toHaveBeenCalled();
});

test('deleteTodo should log an error on unsuccessful delete', async () => {
  // Arrange
  fetchMock.mockResponseOnce('', { status: 500 });

  // Act
  console.error = jest.fn();
  await deleteTodo(1);

  // Assert
  expect(console.error).toHaveBeenCalledWith('Error deleting todo:');
});

test('markAsComplete should call fetch and fetchTodos on successful mark as complete', async () => {
  // Arrange
  fetchMock.mockResponseOnce('', { status: 200 });

  // Act
  await markAsComplete(1);

  // Assert
  expect(fetchMock).toHaveBeenCalledWith('/api/v1/todo/1', { method: 'PUT' });
  expect(fetchTodos).toHaveBeenCalled();
});

test('markAsComplete should log an error on unsuccessful mark as complete', async () => {
  // Arrange
  fetchMock.mockResponseOnce('', { status: 500 });

  // Act
  console.error = jest.fn();
  await markAsComplete(1);

  // Assert
  expect(console.error).toHaveBeenCalledWith('Error marking todo as complete:');
});
