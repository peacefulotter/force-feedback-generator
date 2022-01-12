import { FC } from 'react';
import Slider from '@mui/material/Slider';
import { useSliderStyles, SliderStyle } from './ParamSlider';

import '../css/ff.css';
import '../App.css';

interface Props {
    axis: number
}

const sliderStyle: SliderStyle = {
    a: "red",
    b: "black",
    c: "black",
    d: "green"
}

const Axis: FC<Props> = ( { axis } ) => {
    const classes = useSliderStyles(sliderStyle)

    return ( 
        <Slider value={axis}
            min={-1} max={1}
            valueLabelDisplay="on"
            step={0.01}
            classes={{
                root: classes.root,
                thumb: classes.thumb,
                rail: classes.rail,
                track: classes.track,
                valueLabel: classes.valueLabel,
                mark: classes.mark
            }}/>
    )
}

export default Axis;
