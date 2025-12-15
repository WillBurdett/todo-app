import Logo from './components/Logo.jsx'
import './css/App.css'
import TodoBoard from './components/TodoBoard.jsx'

function App() {

  return (
    <>
      <Logo/>
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
