import { useState } from 'react';
import { Layer, Circle, Text, Stage, Group } from 'react-konva';
import Konva from 'konva';
import useDrawComponentHook from "../hooks/useDrawComponentHook.jsx";
import ButtonLink from "./ButtonLink.jsx";

function DrawComponent() {
    const { effectiveCountermeasures, countermeasures, getCountermeasure, x, y, route } = useDrawComponentHook();
    const [colors, setColors] = useState(Array.from({ length: 5 }, () => Konva.Util.getRandomColor()));
    const routeColors = ['#8B4513', '#FF0000', '#FFA500', '#0900ff', '#0900FFFF'];

    const handleClick = (index) => {
        const newColors = [...colors];
        newColors[index] = Konva.Util.getRandomColor();
        setColors(newColors);
    };

    if (effectiveCountermeasures.length === 0 || countermeasures.length === 0 || route.length === 0) {
        // Поки дані завантажуються - спінер або щось інше
        return <div>Loading...</div>;
    }

    return (
        <div style={{ position: 'relative', margin: '20px', textAlign: 'center' }}>
            <Stage width={x} height={y}>
                <Layer>
                    {effectiveCountermeasures
                        .sort((a, b) => getCountermeasure(b.countermeasureId).distance - getCountermeasure(a.countermeasureId).distance)
                        .map((item) => {
                            const countermeasure = getCountermeasure(item.countermeasureId);
                            const radius = countermeasure.distance / 1000;
                            const shouldDisplayText = radius > 10;
                            return (
                                <Group key={item.id}>
                                    <Circle
                                        x={item.x}
                                        y={item.y}
                                        radius={radius}
                                        fill={Konva.Util.getRandomColor()}
                                        shadowBlur={5}
                                        onClick={() => handleClick(item.id)}
                                    />
                                    {shouldDisplayText && (
                                        <Text
                                            text={countermeasure.countermeasureName}
                                            x={item.x - 25}
                                            y={item.y - 7.5}
                                            fontSize={10}
                                            fill="black"
                                        />
                                    )}
                                </Group>
                            );
                        })}
                    {route.map((point, index) => (
                        <Circle
                            key={index}
                            x={point.x}
                            y={point.y}
                            radius={1}
                            fill={routeColors[point.z]}
                        />
                    ))}
                </Layer>
            </Stage>
            <ButtonLink text="Назад" toAction="/routeparams"/>

        </div>
    );
}

export default DrawComponent;
