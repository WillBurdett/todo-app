import CompleteTodos from "./CompleteTodos.jsx"
import IncompleteTodos from "./IncompleteTodos.jsx"

export default function AllTodos({allTodos}) {

    let incompleteTodos = allTodos.filter(t => !t.complete)
    let completeTodos = allTodos.filter(t => t.complete)

    return (
        <>
            <IncompleteTodos incompleteTodos={incompleteTodos}/>
            <CompleteTodos completeTodos={completeTodos}/>
        </>
    )
}