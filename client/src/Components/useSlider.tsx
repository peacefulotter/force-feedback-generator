import { FC, useState } from 'react';
import Slider from '@mui/material/Slider';
import { makeStyles } from '@mui/styles';

import '../css/ff.css';
import '../App.css';

export const useSliderStyles = makeStyles(theme => ({
    root: {
        width: "350px !important",
        height: "8px !important"
    },
    thumb: {
        background: 'var(--2) !important'
    },
    mark: {},
    rail: {
        background: "linear-gradient(to right, var(--7), var(--6) 50%, var(--8) 50%, var(--9));",
        opacity: "0.9 !important"
    },
    track: {
      background: "transparent !important",
      border: "2px solid var(--2) !important"
    },
    valueLabel: {}
}));


const useSlider = (name: String): [ number, any ] => {
    const [value, setValue] = useState<number>(0);
    const classes = useSliderStyles();

    const updateSlider = (event: any) => {
        setValue(event.target.value)
    }

    return [
        value, 
        <div className="slider-container">
            <div className="slider-name">{name}</div>
            <Slider value={value} onChange={updateSlider} 
                min={-1} max={1}
                valueLabelDisplay="on"
                step={0.1}
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
