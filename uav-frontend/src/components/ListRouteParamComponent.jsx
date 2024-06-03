import ButtonLink from "./ButtonLink";
import useListRouteParamComponentHook from "../hooks/useListRouteParamComponentHook";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faTrash } from '@fortawesome/free-solid-svg-icons';
import { faRoute } from "@fortawesome/free-solid-svg-icons/faRoute";
import { Spinner } from "react-bootstrap";
import {useState} from "react"; // Import the Spinner component

const ListRouteParamComponent = () => {
    const { routeparams, updateRouteParam, removeRouteParam, calculateRoute } =
        useListRouteParamComponentHook();

    // State to track loading state during calculation
    const [calculating, setCalculating] = useState(false);

    const handleCalculateRoute = async (id) => {
        setCalculating(true); // Set loading state to true
        await calculateRoute(id);
        setCalculating(false); // Reset loading state to false after calculation
    };

    // Sort routeparams by timestamp in descending order
    const sortedRouteParams = [...routeparams].sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));

    return (
        <div className="container">
            <h2 className="text-center py-3">Параметри маршруту</h2>
            <ButtonLink text="Додати нові параметри" toAction="/add-routeparam"  />
            <ButtonLink text="Переглянути маршрут" toAction="/draw" />


            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Назва</th>
                    <th scope="col">Мапа X</th>
                    <th scope="col">Мапа Y</th>
                    <th scope="col">Шари</th>
                    <th scope="col">S X</th>
                    <th scope="col">S Y</th>
                    <th scope="col">S Z</th>
                    <th scope="col">T X</th>
                    <th scope="col">T Y</th>
                    <th scope="col">T Z</th>
                    <th scope="col">Алгоритм</th>
                    <th scope="col">Змінити</th>
                    <th scope="col">Видалити</th>
                    <th scope="col">Обчислити</th>
                </tr>
                </thead>
                <tbody>
                {sortedRouteParams.map((item, index) => (
                    <tr key={item.id} className={index === 0 ? 'table-success' : ''}>
                        <td>{item.routeParamName}</td>
                        <td>{item.dimX}</td>
                        <td>{item.dimY}</td>
                        <td>{item.layers}</td>
                        <td>{item.sourceX}</td>
                        <td>{item.sourceY}</td>
                        <td>{item.sourceZ}</td>
                        <td>{item.targetX}</td>
                        <td>{item.targetY}</td>
                        <td>{item.targetZ}</td>
                        <td>{item.algorithmType}</td>
                        <td className="text-center">
                            <button
                                className="btn btn-outline-info me-2"
                                onClick={() => updateRouteParam(item.id)}
                            >
                                <FontAwesomeIcon icon={faEdit} />
                            </button>
                        </td>
                        <td className="text-center">
                            <button
                                className="btn btn-outline-danger"
                                onClick={() => removeRouteParam(item.id)}
                            >
                                <FontAwesomeIcon icon={faTrash} />
                            </button>
                        </td>
                        <td className="text-center">
                            {calculating ? ( // Render spinner if calculating
                                <Spinner animation="border" role="status">
                                    <span className="sr-only">Loading...</span>
                                </Spinner>
                            ) : (
                                <button
                                    className="btn btn-outline-success"
                                    onClick={() => handleCalculateRoute(item.id)}
                                >
                                    <FontAwesomeIcon icon={faRoute} />
                                </button>
                            )}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    );
};

export default ListRouteParamComponent;
