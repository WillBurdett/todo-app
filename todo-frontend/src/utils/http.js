
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
    throw new Error(`Failed to fetch todos: ${res.status} ${text}`)
  }

  const data = await res.json()
  return data
}
