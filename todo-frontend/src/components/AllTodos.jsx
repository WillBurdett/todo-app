import {useQuery} from '@tanstack/react-query'
import { fetchAllTodos } from '../http'
import TodoCard from "../cards/TodoCard.jsx"

export default function AllTodos() {

    const { data, isPending, isError, error } = useQuery({
        queryKey: ['allTodos'],
        queryFn: fetchAllTodos
    })

    let content = null

    // if (isPending) {
    //     content = <LoadingIndicator/>
    // }

    // if (isError) {
    //     content = ( <ErrorBlock title="An error has occured" message={error.info?.message || 'Failed to fetch todos.'} />)
    // }

    // if (data) {
    //     content = 
    // }

    if (data) {
        content = data.map((t) => <TodoCard key={t.id} todo={t} />)
    }

    return (
        <>{content}</>
    )
}