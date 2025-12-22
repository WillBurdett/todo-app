import '../css/TodoCard.css'

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

  return (
    <article className={`todo-card ${complete ? 'complete' : ''}`}>
      <header className="todo-header">
        <h3>{title}</h3>
        <div className="badges">
          <span className="badge defcon">D{defcon}</span>
          <span className={`badge status ${complete ? 'done' : 'pending'}`}>
            {complete ? 'Done' : 'Pending'}
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
