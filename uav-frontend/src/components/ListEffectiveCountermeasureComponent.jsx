import ButtonLink from "./ButtonLink";
import useListEffectiveCountermeasureComponentHook from "../hooks/useListEffectiveCountermeasureComponentHook";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";
import React from "react";

const ListEffectiveCountermeasureComponent = () => {
  const { effectiveCountermeasures, getCountermeasureName, updateEffectiveCountermeasure, deleteEffectiveCountermeasureById } =
    useListEffectiveCountermeasureComponentHook();

  return (
    <div className="container">
      <h2 className="text-center my-3">Активні засоби протидії</h2>
      <ButtonLink text="Додати засіб" toAction="/add-effective-countermeasure" />
      <table className="table table-striped">
        <thead>
          <tr>
            <th scope="col">X</th>
            <th scope="col">Y</th>
            <th scope="col">Достовірність</th>
            <th scope="col">Засіб</th>
              <th scope="col" className="text-center">Змінити</th>
              <th scope="col" className="text-center">Видалити</th>
          </tr>
        </thead>
        <tbody>
          {effectiveCountermeasures.map((item) => {
            return (
              <tr key={item.countermeasureId}>
                <td>{item.x}</td>
                <td>{item.y}</td>
                <td>{item.credibility}</td>
                <td>{getCountermeasureName(item.countermeasureId)}</td>
                  <td className="text-center">
                    <button
                        className="btn btn-outline-info"
                        onClick={() => updateEffectiveCountermeasure(item.id)}
                    >
                        <FontAwesomeIcon icon={faEdit} />
                    </button>
                  </td>
                  <td className="text-center">
                    <button
                    className="btn btn-outline-danger"
                    onClick={() => deleteEffectiveCountermeasureById(item.id)}
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

export default ListEffectiveCountermeasureComponent;
