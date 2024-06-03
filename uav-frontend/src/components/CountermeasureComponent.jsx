import ButtonLink from "./ButtonLink";
import useCountermeasureComponentHook from "../hooks/useCountermeasureComponentHook";

const CountermeasureComponent = () => {
  const {
    countermeasureName,
    setCountermeasureName,
    cmType,
    setCmType,
    hmin,
    setHmin,
    hmax,
    setHmax,
    distance,
    setDistance,
    nchannels,
    setNchannels,
    damageProbability,
    setDamageProbability,
    title,
    saveOrUpdateCountermeasure,
  } = useCountermeasureComponentHook();

  return (
    <div className="container mt-5">
      <ButtonLink text="Go Back" toAction="/countermeasures" />
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          <h2 className="text-center">{title}</h2>
          <div className="card-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">Назва: </label>
                <input
                    type="text"
                    name="countermeasureName"
                    placeholder="Введіть назву"
                    className="form-control"
                    value={countermeasureName}
                    onChange={(e) => setCountermeasureName(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Тип: </label>
                <select
                    name="cmType"
                    className="form-control"
                    value={cmType}
                    onChange={(e) => setCmType(e.target.value)}
                >
                  <option value="" disabled selected>Виберіть тип</option>
                  <option value="ППО">ППО</option>
                  <option value="РЕБ">РЕБ</option>
                  <option value="РЛС">РЛС</option>
                </select>
              </div>

              <div className="form-group mb-2">
                <label className="form-label">Мінімальна висота (м): </label>
                <input
                    type="number"
                    name="countermeasureHmin"
                    placeholder="Мінімальна висота"
                    className="form-control"
                    value={hmin}
                    onChange={(e) => setHmin(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Максимальна висота (м): </label>
                <input
                    type="number"
                    name="countermeasureHmax"
                    placeholder="Максимальна висота"
                    className="form-control"
                    value={hmax}
                    onChange={(e) => setHmax(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Дальність дії (м): </label>
                <input
                    type="number"
                    name="countermeasureDistance"
                    placeholder="Дальність дії"
                    className="form-control"
                    value={distance}
                    onChange={(e) => setDistance(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Максимальна кількість цілей: </label>
                <input
                    type="number"
                    name="countermeasureNchannels"
                    placeholder="Максимальна кількість цілей"
                    className="form-control"
                    value={nchannels}
                    onChange={(e) => setNchannels(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Вірогідність збиття, виявлення, придушення (0.01 - 1): </label>
               <input
                    type="number" min="0.01" max="1" step="0.01"
                    name="countermeasureDamageProbability"
                    placeholder="0.01 - 1"
                    className="form-control"
                    value={damageProbability}
                    onChange={(e) => setDamageProbability(e.target.value)}
                />
              </div>
              <button className="btn btn-outline-success" onClick={saveOrUpdateCountermeasure}>
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CountermeasureComponent;
