import '../css/Utils.css'
import plusIcon from '/plus-symbol.svg'

export function GetStartedMessage({ toggleCreateTodoForm }) {

  const handleCreateTodoSelected = () => {
    toggleCreateTodoForm(true);
  };

  return (
    <span className='get-started-message-container'>
      <p>Create a <code>todo</code> to get started!</p><img src={plusIcon} className="plus-icon" alt="plus symbol" onClick={handleCreateTodoSelected} />
    </span>
  );
}

export function LoadingMessage() {
  return <p>Loading...</p>;
}

export function ErrorMessage({err}) {
    return <p>Error: {err.message}</p>;
}
