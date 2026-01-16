
export async function fetchAllTodos() {
  const res = await fetch('http://localhost:8080/todo', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    // credentials: 'include', // enable if your backend uses cookies
  })

  if (!res.ok) {
    const text = await res.text()
    throw new Error(`Failed to fetch todos: ${res.status} ${text}`)
  }

  const data = await res.json()
  return data
}

export async function createTodo(todo) {
  const res = await fetch('http://localhost:8080/todo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(todo)
  })

  if (!res.ok) {
    const text = await res.text()
    throw new Error(`Failed to create todo: ${res.status} ${text}`)
  }

  const data = await res.json()
  return data
}

export async function deleteTodo(id) {
  const res = await fetch('http://localhost:8080/todo/' + id, {
    method: 'DELETE'
  })

  if (!res.ok) {
    const text = await res.text()
    throw new Error(`Failed to delete todo: ${res.status} ${text}`)
  }

  return 204;
}

export async function markTodoComplete(id) {
  const res = await fetch('http://localhost:8080/todo/toggle-complete/' + id, {
    method: 'PUT'
  })

  if (!res.ok) {
    const text = await res.text()
    throw new Error(`Failed to delete todo: ${res.status} ${text}`)
  }

  return 200;
}
