import { FC, useState } from 'react';
import Slider from '@mui/material/Slider';
import { makeStyles } from '@mui/styles';

import '../css/ff.css';
import '../App.css';

export interface SliderStyle {
    a: string;
    b: string;
    c: string;
    d: string;
}

export const useSliderStyles = makeStyles(theme => ({
    root: {
        width: "290px !important",
        height: "10px !important",
    },
    thumb: {
        background: 'var(--2) !important',
        width: "25px !important",
        height: "25px !important",
    },
    rail: (props: SliderStyle) => ({
        background: `linear-gradient(to right, ${props.a}, ${props.b} 50%, ${props.c} 50%, ${props.d})`,
        opacity: "0.9 !important",
        overflow: 'hidden !important'
    }),
    mark: {
        width: "5px !important",
        height: "1em !important",
        borderRadius: "2px !important",
        background: "white !important"
    },
    track: {
        background: "transparent !important",
        border: "2px solid var(--2) !important"
    },
    valueLabel: {
        transform: "translate(0%,30%) scale(1) !important",
        left: "40px !important",
        "&::before": {
            left: "0px !important",
            transform: "translate(-50%, -120%) rotate(135deg) !important"
        }
    }
}));

interface Props {
    name: String;
    style: SliderStyle;
    min: number;
    max: number;
    step: number;
    allowMark?: boolean;
    unit?: string;
    callback?: (v: number) => void;
}

const ParamSlider: FC<Props> = ( { name, style, min, max, step, allowMark, unit, callback } ) => {
    const [value, setValue] = useState<number>(0);
    const classes = useSliderStyles(style);

    const updateSlider = (event: any) => {
        const v = event.target.value;
        setValue(v)
        if ( callback ) callback(v)
    }

    const sliderText = (value: number) => {
        return value + " " + (unit || '')
    }

    return ( 
        <div className="slider-container">
            <div className="slider-name">{name}</div>
            <Slider value={value} onChange={updateSlider} 
                min={min} max={max}
                valueLabelDisplay="on"
                step={step}
                marks={allowMark ? [{value: 0, label: ""}] : []}
                valueLabelFormat={sliderText}
                classes={{
                    root: classes.root,
                    thumb: classes.thumb,
                    rail: classes.rail,
                    track: classes.track,
                    valueLabel: classes.valueLabel,
                    mark: classes.mark
                }}/>
        </div>
    )
}

export default ParamSlider;
