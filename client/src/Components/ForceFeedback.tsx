import { FC, useState } from 'react';
import { ThemeProvider } from '@mui/styles';
import useSlider, { useSliderStyles } from './useSlider'
import useDirections from './useDirections'
import Checkbox from './Checkbox';

import { postRequest } from '../assets/request';

import '../css/ff.css';
import '../App.css';


// FFEffect(direction, effectLength, previousLevel, nextLevel, level )

const ForceFeedback = () => {
    const [liveUpload, setLiveUpload] = useState<boolean>(false)
    const [type, setType] = useState<string>("constant");
    const [previousLevel, setPreviousLevel] = useState<number>(0);

    const [direction, Directions] = useDirections();
    const [effectLength, EffectLengthSlider] = useSlider("Effect Length");
    const [nextLevel, NextLevelSlider] = useSlider("Next Level");
    const [level, LevelSlider] = useSlider("Level",  () => {
        if ( liveUpload ) launchFF();
    } );

    const classes = useSliderStyles()

    const updateType = (t: string) => (isChecked: boolean) => {
        setType(t);
    }

    const liveUploadUpdate = (isChecked: boolean) => {
        setLiveUpload(isChecked)
    }

    const launchFF = () => {
        const FF_SETTINGS = {
            type, direction, effectLength: effectLength * 1000, previousLevel, nextLevel, level
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
                    { EffectLengthSlider }
                    { LevelSlider }
                    { NextLevelSlider }
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
