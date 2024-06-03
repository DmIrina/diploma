import ButtonLink from "./ButtonLink";
import useEffectiveCountermeasureComponentHook from "../hooks/useEffectiveCountermeasureComponentHook";

const EffectiveCountermeasureComponent = () => {
  const {
    x,
    setX,
    y,
    setY,
    credibility,
    setCredibility,
    countermeasureId,
    setCountermeasureId,
    countermeasures,
    saveOrUpdateEffectiveCountermeasure,
    title,
  } = useEffectiveCountermeasureComponentHook();

  return (
    <div className="container mt-5">
      <ButtonLink text="Назад" toAction="/effectivecountermeasures" />
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
                <label className="form-label">Достовірність даних: </label>
                <input
                  className="form-control"
                  type="number" min="0.01" max="1" step="0.01"
                  placeholder="Достовірність даних про засіб протидії"
                  name="credibility"
                  value={credibility}
                  onChange={(e) => setCredibility(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Оберіть засіб:</label>
                <select
                  className="form-select"
                  value={countermeasureId}
                  onChange={(e) => setCountermeasureId(e.target.value)}
                >
                  <option value="Select Countermeasure">Оберіть засіб протидії</option>
                  {countermeasures.map((item) => {
                    return (
                      <option key={item.id} value={item.id}>
                        {item.countermeasureName}
                      </option>
                    );
                  })}
                </select>
              </div>
              <button
                className="btn btn-outline-success"
                onClick={saveOrUpdateEffectiveCountermeasure}
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

export default EffectiveCountermeasureComponent;
