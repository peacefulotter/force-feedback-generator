import { FC, useState } from 'react';
import { ThemeProvider } from '@mui/styles';
import ParamSlider, { SliderStyle, useSliderStyles } from './ParamSlider'
import useDirections from './useDirections'
import Checkbox from './Checkbox';

import { postRequest } from '../assets/request';

import '../css/ff.css';
import '../App.css';


const lengthSliderStyle: SliderStyle = {
    a: "var(--14)", b: "var(--12)", c: "var(--12)", d: "var(--6)"
}

const levelSliderStyle: SliderStyle = {
    a: "var(--10)", b: "var(--11)", c: "var(--11)", d: "var(--8)"
}

const ForceFeedback = () => {
    const [liveUpload, setLiveUpload] = useState<boolean>(false)
    const [type, setType] = useState<string>("constant");
    const [previousLevel, setPreviousLevel] = useState<number>(0);

    const [direction, Directions] = useDirections();

    const classes = useSliderStyles(levelSliderStyle);
    
    let effectLength = 0;
    let nextLevel = 0;
    let level = 0;

    const updateType = (t: string) => (isChecked: boolean) => {
        setType(t);
    }

    const updateLength = (v: number) => { effectLength = v; }
    const updateNextLevel = (v: number) => { nextLevel = v; }
    const updateLevel = (v: number) => { level = v; if ( liveUpload ) launchFF() }

    const liveUploadUpdate = (isChecked: boolean) => {
        setLiveUpload(isChecked)
    }

    const launchFF = () => {
        const FF_SETTINGS = {
            type, direction, effectLength, previousLevel, nextLevel, level
        }

        console.log("Sending POST request with FF settings: ", FF_SETTINGS);
        
        postRequest("/params", FF_SETTINGS, (data) => {
            console.log(data);
            // setPreviousLevel(level);
        })
    }

    return (
        <div className="ff-wrapper">
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
            { Directions }
            <div className="slider-wrapper">
                <ThemeProvider theme={classes}>
                    <ParamSlider name="Effect Length" style={lengthSliderStyle} min={0} max={1000} step={10} unit="ms" callback={updateLength}/>
                    <ParamSlider name="Next Level" style={levelSliderStyle} min={-1} max={1} step={0.02} allowMark={true} callback={updateNextLevel}/>
                    <ParamSlider name="Level" style={levelSliderStyle} min={-1} max={1} step={0.02} allowMark={true} callback={updateLevel}/>
                </ThemeProvider>
            </div>
            <div className="ff-launch-container">
                <Checkbox 
                    className="btn btn-orange ff-live" 
                    checkedClassName='btn-orange-checked' 
                    html={<div>Live</div>}
                    onClick={liveUploadUpdate} />
                <button className="btn btn-success ff-launch" onClick={launchFF}>Upload</button>
            </div>
        </div>
    )
}

export default ForceFeedback;
