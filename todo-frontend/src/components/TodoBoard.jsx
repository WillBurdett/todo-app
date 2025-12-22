import {useQuery} from '@tanstack/react-query'
import { fetchAllTodos } from '../utils/http.js'
import AllTodos from './AllTodos.jsx';
import { GetStartedMessage, LoadingMessage, ErrorMessage } from '../utils/messages';
import CreateTodoForm from './CreateTodoForm.jsx';
import { useState } from 'react';

export default function TodoBoard() {

    const [createTodoSelected, setCreateTodoSelected] = useState(false);

    const toggleCreateTodoForm = (showForm) => {
        setCreateTodoSelected(showForm)
    }

    const { data, isPending, isError, error } = useQuery({
        queryKey: ['allTodos'],
        queryFn: fetchAllTodos
    })

    let content;

    if (isPending) {
        content = <LoadingMessage/>
    }
    if (isError) {
        content = <ErrorMessage err={error}/>
    }
    if (data) {
        content = data.length > 0 ? <AllTodos allTodos={data} /> : <GetStartedMessage toggleCreateTodoForm={toggleCreateTodoForm}/>;
        if (createTodoSelected) {
            content = <CreateTodoForm toggleCreateTodoForm={toggleCreateTodoForm} />;
        }
    }

    return (
        <>{content}</>
    )
}