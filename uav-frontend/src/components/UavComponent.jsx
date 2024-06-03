import ButtonLink from "./ButtonLink";
import useUavComponentHook from "../hooks/useUavComponentHook";

const UavComponent = () => {
  const {
    uavName,
    setUavName,
    hmin,
    setHmin,
    hmax,
    setHmax,
    distance,
    setDistance,
    speed,
    setSpeed,
    resistance,
    setResistance,
    noiseimmunity,
    setNoiseimmunity,
    stealth,
    setStealth,
    // eag,
    // setEag,
    title,
    saveOrUpdateUav,
  } = useUavComponentHook();

  // React.useEffect(() => {
  //   setCmType(''); // значення за замовчуванням
  // }, []);

  return (
    <div className="container mt-5">
      <ButtonLink text="Go Back" toAction="/uavs" />
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          <h2 className="text-center">{title}</h2>
          <div className="card-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">Назва: </label>
                <input
                    type="text"
                    name="uavName"
                    placeholder="Введіть назву"
                    className="form-control"
                    value={uavName}
                    onChange={(e) => setUavName(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Мінімальна висота (м): </label>
                <input
                    type="number"
                    name="uavhmin"
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
                    name="uavhmax"
                    placeholder="Максимальна висота"
                    className="form-control"
                    value={hmax}
                    onChange={(e) => setHmax(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Дальність дії (км): </label>
                <input
                    type="number"
                    name="uavdistance"
                    placeholder="км"
                    className="form-control"
                    value={distance}
                    onChange={(e) => setDistance(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Максимальна швидкість км/год: </label>
                <input
                    type="number"
                    name="uavspeed"
                    placeholder="км/год"
                    className="form-control"
                    value={speed}
                    onChange={(e) => setSpeed(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Стійкість до ураження засобами ППО (1-100): </label>
                <input
                    type="number" min='1' max='100'
                    name="uavResistance"
                    placeholder="1-100"
                    className="form-control"
                    value={resistance}
                    onChange={(e) => setResistance(e.target.value)}
                />
              </div>

              <div className="form-group mb-2">
                <label className="form-label">Завадостійкість засобам РЕБ (1-100): </label>
                <input
                    type="number" min="0.01" max="1" step="0.01"
                    name="uavNoiseimmunity"
                    placeholder="1-100"
                    className="form-control"
                    value={noiseimmunity}
                    onChange={(e) => setNoiseimmunity(e.target.value)}
                />
              </div>

              <div className="form-group mb-2">
                <label className="form-label">Невидимість (1-100): </label>
                <input
                    type="number" min="0.01" max="1" step="0.01"
                    name="uavStealth"
                    placeholder="1-100"
                    className="form-control"
                    value={stealth}
                    onChange={(e) => setStealth(e.target.value)}
                />
              </div>

              {/*<div className="form-group mb-2">*/}
              {/*  <label className="form-label">Ефективність дії в групі (1-100): </label>*/}
              {/*  <input*/}
              {/*      type="number" min="0.01" max="1" step="0.01"*/}
              {/*      name="uavEag"*/}
              {/*      placeholder="1-100"*/}
              {/*      className="form-control"*/}
              {/*      value={eag}*/}
              {/*      onChange={(e) => setEag(e.target.value)}*/}
              {/*  />*/}
              {/*</div>*/}

              <button className="btn btn-outline-success" onClick={saveOrUpdateUav}>
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UavComponent;
