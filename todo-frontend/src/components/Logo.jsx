import listLogo from '/list.svg'

export default function Logo() {

    return (
        <div>
          <a target="_blank" href='https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1'>
            <img src={listLogo} className="logo" alt="todo app logo" />
          </a>
        </div>
    )
}