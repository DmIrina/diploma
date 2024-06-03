import ButtonLink from "./ButtonLink";
import useModelParamComponentHook from "../hooks/useModelParamComponentHook";

const ModelParamComponent = () => {
    const {
        modelParamName,
        setModelParamName,
        nuav,
        setNuav,
        nuavingroups,
        setNuavingroups,
        uavId,
        setUavId,
        uavs,
        timeBetweenGroups,
        setTimeBetweenGroups,
        timeRecharge,
        setTimeRecharge,
        title,
        saveOrUpdateModelParam,
    } = useModelParamComponentHook();


    return (
        <div className="container mt-5">
            <ButtonLink text="Назад" toAction="/modelparams"/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h2 className="text-center">{title}</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="form-label">Назва моделі: </label>
                                <input
                                    type="text"
                                    name="modelParamName"
                                    placeholder="Введіть назву"
                                    className="form-control"
                                    value={modelParamName}
                                    onChange={(e) => setModelParamName(e.target.value)}
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Кількість БПЛА: </label>
                                <input
                                    type="number"
                                    name="nuav"
                                    placeholder="Введіть кількість БПЛА, що беруть участь в експерименті"
                                    className="form-control"
                                    value={nuav}
                                    onChange={(e) => setNuav(e.target.value)}
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Кількість БПЛА у кожній групі: </label>
                                <input
                                    type="number"
                                    name="nuavingroups"
                                    placeholder="Введіть кількість БПЛА в одній групі"
                                    className="form-control"
                                    value={nuavingroups}
                                    onChange={(e) => setNuavingroups(e.target.value)}
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Оберіть засіб:</label>
                                <select
                                    className="form-select"
                                    value={uavId}
                                    onChange={(e) => setUavId(e.target.value)}
                                >
                                    <option value="Select UAV">Оберіть БПЛА</option>
                                    {uavs.map((item) => {
                                        return (
                                            <option key={item.id} value={item.id}>
                                                {item.uavName}
                                            </option>
                                        );
                                    })}
                                </select>
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Час між запуском груп</label>
                                <input
                                    type="number"
                                    name="timeBetweenGroups"
                                    placeholder="Час між запуском груп, сек"
                                    className="form-control"
                                    value={timeBetweenGroups}
                                    onChange={(e) => setTimeBetweenGroups(e.target.value)}
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Час відновлення ППО</label>
                                <input
                                    type="number"
                                    name="timeRecharge"
                                    placeholder="Час відновлення ППО, сек"
                                    className="form-control"
                                    value={timeRecharge}
                                    onChange={(e) => setTimeRecharge(e.target.value)}
                                />
                            </div>

                            <button className="btn btn-outline-success" onClick={saveOrUpdateModelParam}>
                                Submit
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ModelParamComponent;
