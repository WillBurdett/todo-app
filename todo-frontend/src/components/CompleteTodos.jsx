import TodoCard from "../cards/TodoCard.jsx"

export default function CompleteTodos({ completeTodos }) {
    const cannotUpdateCompletedTodo = () => {
        console.log('You cannot edit a completed todo!')
    }
    let content = completeTodos.map((t) => <TodoCard key={t.id} todo={t} toggleEditableTodoForm={cannotUpdateCompletedTodo}/>)

    return (
        <div>
            <h3>Complete</h3>
            {content.length > 0 ? content : <div className="ghost-card"></div>}
        </div>
    )
}