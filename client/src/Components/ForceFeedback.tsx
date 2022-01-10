import { FC, useState } from 'react';
import { ThemeProvider } from '@mui/styles';
import useSlider, { useSliderStyles } from './useSlider'
import Checkbox from './Checkbox';

import { postRequest } from '../assets/request';

import '../css/ff.css';
import '../App.css';


// FFEffect(direction, effectLength, previousLevel, nextLevel, level )

const ForceFeedback = () => {
    const [type, setType] = useState<string>("constant");
    const [previousLevel, setPreviousLevel] = useState<number>(0);

    const [direction, DirectionSlider] = useSlider("Direction");
    const [nextLevel, NextLevelSlider] = useSlider("Next level");
    const [level, LevelSlider] = useSlider("Level");

    const classes = useSliderStyles()

    const updateType = (t: string) => (isChecked: boolean) => {
        setType(t);
    }

    const updateParams = () => {
        const FF_PARAMS = {
            direction, previousLevel, level, nextLevel
        }
        postRequest("/params", {name: "bob", number: 30}, (data) => {
            console.log(data);
            setPreviousLevel(level);
        })
    }

    return (
        <div className="ff-wrapper">
            <div className="ff-title">
                <div className="ff-main-title">ForceFeedback</div>
                <div className="ff-sub-title">Controller</div>
            </div>
            <div className="ff-types">
                <Checkbox 
                    className='btn-purple ff-type'
                    checkedClassName='btn-purple-checked'
                    html={<div>Constant</div>}
                    forceState={type === "constant"}
                    onClick={updateType("constant")} />
                <Checkbox 
                    className='btn-purple ff-type'
                    checkedClassName='btn-purple-checked'
                    html={<div>Sine</div>}
                    forceState={type === "sine"}
                    onClick={updateType("sine")} />
            </div>
            <div className="ff-sliders">
                <ThemeProvider theme={classes}>
                    { DirectionSlider }
                    { LevelSlider }
                    { NextLevelSlider }
                </ThemeProvider>
                
            </div>
            <button className="btn btn-success ff-launch" onClick={updateParams}>Upload</button>
        </div>
    )
}

export default ForceFeedback;
