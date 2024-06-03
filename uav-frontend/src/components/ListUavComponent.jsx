import ButtonLink from "./ButtonLink";
import useListUavComponentHook from "../hooks/useListUavComponentHook";
import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";
import {Spinner} from "react-bootstrap";
import {faRoute} from "@fortawesome/free-solid-svg-icons/faRoute";

const ListUavComponent = () => {
    const {uavs, updateUav, removeUav} =
        useListUavComponentHook();

    return (
        <div className="container">
            <h2 className="text-center py-3">Каталог засобів протидії</h2>
            <ButtonLink text="Новий засіб" toAction="/add-uav"/>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Назва</th>
                    <th scope="col">H мін</th>
                    <th scope="col">Н макс.</th>
                    <th scope="col">Дальність</th>
                    <th scope="col">Швидкість</th>
                    <th scope="col">Стійкість (ППО)</th>
                    <th scope="col">Завадостійкість (РЕБ)</th>
                    <th scope="col">Невидимість (РЛС)</th>
                    {/*<th scope="col">Ефективність в групі</th>*/}
                    <th scope="col">Змінити</th>
                    <th scope="col">Видалити</th>
                </tr>
                </thead>
                <tbody>
                {uavs.map((item) => {
                    return (
                        <tr key={item.id}>
                            <td>{item.uavName}</td>
                            <td>{item.hmin}</td>
                            <td>{item.hmax}</td>
                            <td>{item.distance}</td>
                            <td>{item.speed}</td>
                            <td>{item.resistance}</td>
                            <td>{item.noiseimmunity}</td>
                            <td>{item.stealth}</td>
                            {/*<td>{item.eag}</td>*/}
                            <td className="text-center">
                                <button
                                    className="btn btn-outline-info me-2"
                                    onClick={() => updateUav(item.id)}
                                >
                                    <FontAwesomeIcon icon={faEdit} />
                                </button>
                            </td>
                            <td className="text-center">
                                <button
                                    className="btn btn-outline-danger"
                                    onClick={() => removeUav(item.id)}
                                >
                                    <FontAwesomeIcon icon={faTrash} />
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

export default ListUavComponent;
