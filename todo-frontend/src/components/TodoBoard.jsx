import {useQuery} from '@tanstack/react-query'
import { fetchAllTodos } from '../utils/http.js'
import AllTodos from './AllTodos.jsx';
import { GetStartedMessage, LoadingMessage, ErrorMessage } from '../utils/messages';

export default function TodoBoard() {

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
        content = data.length > 0 ? <AllTodos allTodos={data}/> : <GetStartedMessage/>;
    }

    return (
        <>{content}</>
    )
}