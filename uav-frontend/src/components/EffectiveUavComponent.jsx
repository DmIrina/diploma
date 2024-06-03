import ButtonLink from "./ButtonLink";
import useEffectiveUavComponentHook from "../hooks/useEffectiveUavComponentHook";

const EffectiveUavComponent = () => {
  const {
    x,
    setX,
    y,
    setY,
    status,
    setStatus,
    uavId,
    setUavId,
    uavs,
    saveOrUpdateEffectiveUav,
    title,
  } = useEffectiveUavComponentHook();

  return (
    <div className="container mt-5">
      <ButtonLink text="Назад" toAction="/" />
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          <h2 className="text-center">{title}</h2>
          <div className="card-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">X: </label>
                <input
                    className="form-control"
                    type="number"
                    placeholder="x"
                    name="x"
                    value={x}
                    onChange={(e) => setX(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Y: </label>
                <input
                    className="form-control"
                    type="number"
                    placeholder="y"
                    name="y"
                    value={y}
                    onChange={(e) => setY(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Статус: </label>
                <input
                    className="form-check-input"
                    type="checkbox"
                    name="status"
                    checked={status}
                    onChange={(e) => setStatus(e.target.checked)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Оберіть засіб:</label>
                <select
                    className="form-select"
                    value={uavId}
                    onChange={(e) => setUavId(e.target.value)}
                >
                  <option value="Select Uav">Оберіть засіб протидії</option>
                  {uavs.map((item) => {
                    return (
                        <option key={item.id} value={item.id}>
                          {item.uavName}
                        </option>
                    );
                  })}
                </select>
              </div>
              <button
                  className="btn btn-outline-success"
                  onClick={saveOrUpdateEffectiveUav}
              >
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EffectiveUavComponent;
