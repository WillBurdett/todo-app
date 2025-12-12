import listLogo from '/list.svg'
import './css/App.css'
import TodoBoard from './components/TodoBoard.jsx'

function App() {

  return (
    <>
      <div>
        <a target="_blank" href='https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1'>
          <img src={listLogo} className="logo" alt="todo app logo" />
        </a>
      </div>
      <h1>Todo List</h1>
      <div className="card main-body">
        <TodoBoard/>
      </div>
      <p className="read-the-docs">
        Click on the logo to learn more about this project
      </p>
    </>
  )
}

export default App
