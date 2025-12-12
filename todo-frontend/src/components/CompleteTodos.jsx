import TodoCard from "../cards/TodoCard.jsx"

export default function CompleteTodos({completeTodos}) {
    let content = completeTodos.map((t) => <TodoCard key={t.id} todo={t} />)

    return (
        <div>
            <h3>Complete</h3>
            {content.length > 0 ? content : <div className="ghost-card"></div>}
        </div>
    )
}