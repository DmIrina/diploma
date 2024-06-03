import ButtonLink from "./ButtonLink";
import useListEffectiveUavComponentHook from "../hooks/useListEffectiveUavComponentHook";

const ListEffectiveUavComponent = () => {
  const { effectiveUavs, getUavName, updateEffectiveUav, deleteEffectiveUavById } =
    useListEffectiveUavComponentHook();

  return (
    <div className="container">
      <h2 className="text-center my-3">Список активних БПЛА:</h2>
      <ButtonLink text="Add EffectiveUav" toAction="/add-effective-uav" />
      <table className="table table-striped">
        <thead>
          <tr>
            <th scope="col">X</th>
            <th scope="col">Y</th>
            <th scope="col">Статус</th>
            <th scope="col">Засіб</th>
            <th scope="col">Action #1</th>
            <th scope="col">Action #2</th>
          </tr>
        </thead>
        <tbody>
        {effectiveUavs.map((item) => {
            return (
                <tr key={item.id}>
                    <td>{item.x}</td>
                    <td>{item.y}</td>
                    <td>
                        {item.status ? (
                            <span className="text-success">&#10004;</span>
                        ) : (
                            <span className="text-danger">&#10008;</span>
                        )}
                    </td>
                    <td>{getUavName(item.id)}</td>
                    <td>
                        <button
                            className="btn btn-outline-danger"
                            onClick={() => deleteEffectiveUavById(item.id)}
                        >
                            Delete
                        </button>
                    </td>
                    <td>
                        <button
                            className="btn btn-outline-info"
                            onClick={() => updateEffectiveUav(item.id)}
                        >
                            Update
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

export default ListEffectiveUavComponent;
