import TodoCard from "../cards/TodoCard.jsx"

export default function AllTodos({allTodos}) {
    let content = allTodos.map((t) => <TodoCard key={t.id} todo={t} />)

    return (
        <>{content}</>
    )
}