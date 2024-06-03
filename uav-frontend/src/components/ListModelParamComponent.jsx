import ButtonLink from "./ButtonLink";
import useListModelParamComponentHook from "../hooks/useListModelParamComponentHook";
import React from "react";
import {faChartLine, faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { useNavigate } from 'react-router-dom';


const ListModelParamComponent = () => {
    const {modelparams, getUavName, updateModelParam, removeModelParam} = useListModelParamComponentHook();


    const navigate = useNavigate();

    const handlePageClick = (id) => {
        navigate(`/pages/${id}`);
    };


        return (
        <div className="container">
            <h2 className="text-center py-3">Параметри моделі</h2>
            <ButtonLink text="Додати нові параметри" toAction="/add-modelparam"/>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Назва</th>
                    <th scope="col">БПЛА</th>
                    <th scope="col">Кількість БПЛА</th>
                    <th scope="col">Кільк. в групі</th>
                    <th scope="col">Час між групами</th>
                    <th scope="col">Відновл.ППО</th>
                    <th scope="col" className="text-center">Змінити</th>
                    <th scope="col" className="text-center">Видалити</th>
                    <th scope="col" className="text-center">Моделювання</th>
                </tr>
                </thead>
                <tbody>
                {modelparams.map((item) => {
                    return (
                        <tr key={item.id}>
                            <td>{item.modelParamName}</td>
                            <td>{getUavName(item.uavId)}</td>
                            <td>{item.nuav}</td>
                            <td>{item.nuavingroups}</td>
                            <td>{item.timeBetweenGroups}</td>
                            <td>{item.timeRecharge}</td>

                            <td className="text-center">
                                <button
                                    className="btn btn-outline-info me-2"
                                    onClick={() => updateModelParam(item.id)}
                                >
                                    <FontAwesomeIcon icon={faEdit} />
                                </button>
                            </td>
                            <td className="text-center">
                                <button
                                    className="btn btn-outline-danger"
                                    onClick={() => removeModelParam(item.id)}
                                >
                                    <FontAwesomeIcon icon={faTrash} />
                                </button>
                            </td>

                            <td className="text-center">
                                <button className="btn btn-outline-primary"
                                        onClick={() => handlePageClick(item.id)}>
                                    <FontAwesomeIcon icon={faChartLine}/>
                                </button>
                            </td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
};

export default ListModelParamComponent;
