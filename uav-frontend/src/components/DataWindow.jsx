import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import ButtonLink from "./ButtonLink.jsx";

const DataWindow = () => {
    const { id } = useParams();
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/simulation/pages/${id}`);
                console.log('Server response:', response.data); // Виведення відповіді сервера в консоль
                setData(response.data);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching data:', error); // Виведення помилки в консоль
                setError(error);
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (

        <div>
            <ButtonLink text="Назад" toAction="/modelparams"/>
            <h3>Результати моделювання</h3>
            <pre>{data}</pre>
            <ButtonLink text="Назад" toAction="/modelparams"/>
        </div>

    );
};

export default DataWindow;