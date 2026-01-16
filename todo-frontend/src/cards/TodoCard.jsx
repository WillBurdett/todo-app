import '../css/TodoCard.css'
import { deleteTodo, markTodoComplete } from '../utils/http.js'
import { useMutation, useQueryClient } from '@tanstack/react-query'

export default function TodoCard({ todo }) {
  const {
    id,
    title,
    description,
    defcon,
    dueDate,
    complete,
    completedOn
  } = todo

  const fmt = (d) => {
    if (!d) return ''
    try {
      return new Date(d).toLocaleDateString(undefined, { year: 'numeric', month: 'short', day: 'numeric' })
    } catch {
      return d
    }
  }

  const queryClient = useQueryClient()

  const markCompleteMutation = useMutation({
    mutationFn: () => markTodoComplete(id),
    onSuccess: () => {
      // invalidate or update cache so UI refreshes
      queryClient.invalidateQueries({ queryKey: ['allTodos'] })
    }
  })

  const handleMarkTodoComplete = () => {
    markCompleteMutation.mutate()
  }

  const deleteMutation = useMutation({
    mutationFn: () => deleteTodo(id),
    onSuccess: () => {
      // invalidate or update cache so UI refreshes
      queryClient.invalidateQueries({ queryKey: ['allTodos'] })
    }
  })

  const handleDeleteTodo = () => {
    deleteMutation.mutate()
  }

  return (
    <article className={`todo-card ${complete ? 'complete' : ''}`}>
      <header className="todo-header">
        <h3>{title}</h3>
        <div className="badges">
          <span className="badge defcon">D{defcon}</span>
          <span className={`badge status done`} onClick={() => handleMarkTodoComplete()}>
            {complete ? 'Done' : '✔'}
          </span>
          <span className={`badge status delete`} onClick={() => handleDeleteTodo()}>
            {complete ? 'Done' : '❌'}
          </span>
        </div>
      </header>

      <p className="todo-desc">{description}</p>

      <footer className="todo-meta">
        {complete ? <span>Completed: {fmt(completedOn)}</span> : <span>Due: {fmt(dueDate)}</span>}
      </footer>
    </article>
  )
}
