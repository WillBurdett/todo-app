import TodoCard from "../cards/TodoCard.jsx"

export default function IncompleteTodos({ incompleteTodos, toggleEditableTodoForm }) {
    let content = incompleteTodos.map((t) => <TodoCard key={t.id} todo={t} toggleEditableTodoForm={toggleEditableTodoForm}/>)

    return (
        <div>
            <h3>Todo</h3>
            {content.length > 0 ? content : <div className="ghost-card"></div>}
        </div>
    )
}