import TodoCard from "../cards/TodoCard.jsx"

export default function IncompleteTodos({incompleteTodos}) {
    let content = incompleteTodos.map((t) => <TodoCard key={t.id} todo={t} />)

    return (
        <div>
            {content}
        </div>
    )
}