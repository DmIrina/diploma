import ButtonLink from "./ButtonLink";
import useListCountermeasureComponentHook from "../hooks/useListCountermeasureComponentHook";
import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";

const ListCountermeasureComponent = () => {
  const { countermeasures, updateCountermeasure, removeCountermeasure } =
    useListCountermeasureComponentHook();

  return (
    <div className="container">
      <h2 className="text-center py-3">Каталог засобів протидії</h2>
      <ButtonLink text="Новий засіб" toAction="/add-countermeasure" />
      <table className="table table-striped">
        <thead>
          <tr>
            <th scope="col">Назва</th>
            <th scope="col">Тип</th>
            <th scope="col">Мін.висота</th>
            <th scope="col">Макс.висота</th>
            <th scope="col">Дальність</th>
            <th scope="col">Кільк.цілей</th>
            <th scope="col">Вірогідність ураження</th>
            <th scope="col" className="text-center">Змінити</th>
            <th scope="col" className="text-center">Видалити</th>
          </tr>
        </thead>
        <tbody>
          {countermeasures.map((item) => {
            return (
              <tr key={item.id}>
                <td>{item.countermeasureName}</td>
                <td>{item.cmType}</td>
                <td>{item.hmin}</td>
                <td>{item.hmax}</td>
                <td>{item.distance}</td>
                <td>{item.nchannels}</td>
                <td>{item.damageProbability}</td>
                <td className="text-center">
                  <button className="btn btn-outline-info me-2" onClick={() => updateCountermeasure(item.id)}>
                    <FontAwesomeIcon icon={faEdit} />
                  </button>
                </td>
                <td className="text-center">
                  <button
                    className="btn btn-outline-danger"
                    onClick={() => removeCountermeasure(item.id)}
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

export default ListCountermeasureComponent;
