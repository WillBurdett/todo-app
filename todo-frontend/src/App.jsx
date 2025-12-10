import listLogo from '/list.svg'
import './css/App.css'
import Main from './components/TodoBoard.jsx'

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
        <Main/>
      </div>
      <p className="read-the-docs">
        Click on the logo to learn more about this project
      </p>
    </>
  )
}

export default App
