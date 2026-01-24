import CompleteTodos from "./CompleteTodos.jsx"
import IncompleteTodos from "./IncompleteTodos.jsx"
import "../css/TodoBoard.css"

export default function AllTodos({ allTodos, toggleEditableTodoForm }) {

    let incompleteTodos = allTodos.filter(t => !t.complete)
    let completeTodos = allTodos.filter(t => t.complete)

    return (
        <div className="todo-grid">
            <IncompleteTodos incompleteTodos={incompleteTodos} toggleEditableTodoForm={toggleEditableTodoForm}/>
            <CompleteTodos completeTodos={completeTodos} toggleEditableTodoForm={toggleEditableTodoForm}/>
        </div>
    )
}