import { FC, useState } from 'react';
import Slider from '@mui/material/Slider';
import { makeStyles } from '@mui/styles';

import '../css/ff.css';
import '../App.css';

export const useSliderStyles = makeStyles(theme => {
    console.log("here");
    return ({
        root: {
            width: "350px !important",
            height: "1em !important"
        },
        thumb: {
            background: 'var(--2) !important',
            width: "2em !important",
            height: "2em !important",
        },
        mark: {},
        rail: {
            background: "linear-gradient(to right, var(--6), var(--7) 50%, var(--9) 50%, var(--8));",
            opacity: "0.9 !important"
        },
        track: {
        background: "transparent !important",
        border: "2px solid var(--2) !important"
        },
        valueLabel: {}
    })
});


const useSlider = (name: String, callback?: () => void): [ number, any ] => {
    const [value, setValue] = useState<number>(0);
    const classes = useSliderStyles();

    const updateSlider = (event: any) => {
        setValue(event.target.value)
        if ( callback ) callback()
    }

    return [
        value, 
        <div className="slider-container">
            <div className="slider-name">{name}</div>
            <Slider value={value} onChange={updateSlider} 
                min={-1} max={1}
                valueLabelDisplay="on"
                step={0.02}
                classes={{
                    root: classes.root,
                    thumb: classes.thumb,
                    rail: classes.rail,
                    track: classes.track,
                    valueLabel: classes.valueLabel,
                    mark: classes.mark
                }}/>
        </div>
        
    ]
}

export default useSlider;
