import ButtonLink from "./ButtonLink";
import useRouteParamComponentHook from "../hooks/useRouteParamComponentHook";

const RouteParamComponent = () => {
    const {
        dimX,
        setDimX,
        dimY,
        setDimY,
        layers,
        setLayers,
        sourceX,
        setSourceX,
        sourceY,
        setSourceY,
        sourceZ,
        setSourceZ,
        targetX,
        setTargetX,
        targetY,
        setTargetY,
        targetZ,
        setTargetZ,
        algorithmType,
        setAlgorithmType,
        routeParamName,
        setRouteParamName,
        title,
        saveOrUpdateRouteParam,
    } = useRouteParamComponentHook();

    return (
        <div className="container mt-5">
            <ButtonLink text="Go Back" toAction="/routeparams"/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h2 className="text-center">{title}</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="form-label">Назва маршруту: </label>
                                <input
                                    type="text"
                                    name="routeParamName"
                                    placeholder="Введіть назву"
                                    className="form-control"
                                    value={routeParamName}
                                    onChange={(e) => setRouteParamName(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label className="form-label">Мапа X: </label>
                                <input
                                    type="number"
                                    name="dimX"
                                    placeholder="Введіть розмір мапи - Х"
                                    className="form-control"
                                    value={dimX}
                                    onChange={(e) => setDimX(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label className="form-label">Мапа Y: </label>
                                <input
                                    type="number"
                                    name="dimY"
                                    placeholder="Введіть розмір мапи - Y"
                                    className="form-control"
                                    value={dimY}
                                    onChange={(e) => setDimY(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label className="form-label">Шари прольоту: </label>
                                <input
                                    type="text"
                                    name="layers"
                                    placeholder="Введіть максимальні висоти шарів прольоту (10, 1000, 5000, 20000)"
                                    className="form-control"
                                    value={layers}
                                    onChange={(e) => setLayers(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label className="form-label">Старт X: </label>
                                <input
                                    type="number"
                                    name="sourceX"
                                    placeholder="Координата точки старту - X"
                                    className="form-control"
                                    value={sourceX}
                                    onChange={(e) => setSourceX(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label className="form-label">Старт Y: </label>
                                <input
                                    type="number"
                                    name="sourceY"
                                    placeholder="Координата точки старту - Y"
                                    className="form-control"
                                    value={sourceY}
                                    onChange={(e) => setSourceY(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label className="form-label">Старт Z: </label>
                                <input
                                    type="number"
                                    name="sourceZ"
                                    placeholder="Номер шару точки старту"
                                    className="form-control"
                                    value={sourceZ}
                                    onChange={(e) => setSourceZ(e.target.value)}
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Ціль X: </label>
                                <input
                                    type="number"
                                    name="targetX"
                                    placeholder="Координата цілі - X"
                                    className="form-control"
                                    value={targetX}
                                    onChange={(e) => setTargetX(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label className="form-label">Ціль Y: </label>
                                <input
                                    type="number"
                                    name="targetY"
                                    placeholder="Координата цілі - Y"
                                    className="form-control"
                                    value={targetY}
                                    onChange={(e) => setTargetY(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label className="form-label">Ціль шар: </label>
                                <input
                                    type="number"
                                    name="targetZ"
                                    placeholder="Номер шару цілі"
                                    className="form-control"
                                    value={targetZ}
                                    onChange={(e) => setTargetZ(e.target.value)}
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Оберіть тип алгоритму для вибору маршруту:</label>
                                <div>
                                    <input type="radio" name="algorithmType" value="astar" id="astar"
                                           checked={algorithmType === "astar"}
                                           onChange={(e) => setAlgorithmType(e.target.value)}/>
                                    <label htmlFor="astar">A*</label>
                                </div>
                                <div>
                                    <input type="radio" name="algorithmType" value="dijkstra" id="dijkstra"
                                           checked={algorithmType === "dijkstra"}
                                           onChange={(e) => setAlgorithmType(e.target.value)}/>
                                    <label htmlFor="dijkstra">Дейкстри</label>
                                </div>
                            </div>

                            <button className="btn btn-outline-success" onClick={saveOrUpdateRouteParam}>
                                Submit
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RouteParamComponent;
