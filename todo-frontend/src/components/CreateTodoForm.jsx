import { useMutation, useQueryClient } from '@tanstack/react-query'
import { useState } from "react";
import { createTodo } from "../utils/http";
import '../css/CreateTodoForm.css'

export default function CreateTodoForm({ toggleCreateTodoForm }) {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [defcon, setDefcon] = useState("5");
    const [dueDate, setDueDate] = useState("");

    const queryClient = useQueryClient()

    const { mutate, isPending, isError, error } = useMutation({
        mutationFn: createTodo,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['allTodos'] })
            toggleCreateTodoForm(false)
        }
    })

    const handleSubmit = (e) => {
        e.preventDefault();

        const newTodo = {
            title,
            description,
            defcon,
            dueDate
        };

        mutate(newTodo)

        // Optional: reset form
        setTitle("");
        setDescription("");
        setDefcon("5");
        setDueDate("");
    };

    return (
        <form  className="todo-form" onSubmit={handleSubmit}>
            <div className='hover-grey'>
                <div className='close-form-button' onClick={() => toggleCreateTodoForm(false)}>
                    ⬅
                </div>
            </div>

                <div className="form-group--left">
                    <div className="form-group">
                        <label>Title</label>
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Description</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />
                    </div>


                <div className="form-row">
                    <div className="form-group">
                        <label>DEFCON</label>
                        <select
                            value={defcon}
                            onChange={(e) => setDefcon(e.target.value)}
                        >
                            {[1, 2, 3, 4, 5].map(level => (
                                <option key={level} value={level}>
                                    {level}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="form-group">
                        <label>Due Date</label>
                        <input
                            type="date"
                            value={dueDate}
                            onChange={(e) => setDueDate(e.target.value)}
                        />
                    </div>
                </div>

            </div>

            <button type="submit" className="todo-form--submit" >Create Todo</button>
        </form>
    );
}
