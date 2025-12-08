import listLogo from '/list.svg'
import './App.css'
import AllTodos from './components/AllTodos'

function App() {

  return (
    <>
      <div>
        <a target="_blank">
          <img src={listLogo} className="logo" alt="todo app logo" />
        </a>
      </div>
      <h1>Todo List</h1>
      <div className="card main-body">
        <AllTodos/>
        <p>
          Create a <code>todo</code> to get started!
        </p>
      </div>
      <p className="read-the-docs">
        Click on the logo to learn more about this project
      </p>
    </>
  )
}

export default App
