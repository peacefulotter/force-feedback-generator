

import { FC, useState, useImperativeHandle } from 'react';
import { ThemeProvider } from '@mui/styles';
import ParamSlider, { SliderStyle, useSliderStyles } from './ParamSlider'

import '../css/ff.css';
import '../App.css';
import { FFType } from '../assets/models';


export const lengthSliderStyle: SliderStyle = {
    a: "var(--14)", b: "var(--12)", c: "var(--12)", d: "var(--6)"
}

export const levelSliderStyle: SliderStyle = {
    a: "var(--10)", b: "var(--11)", c: "var(--11)", d: "var(--8)"
}

const getSliders = (b: boolean, c: boolean, d: boolean, e: boolean, isWave: boolean) => {
    return {
        effectLength: true, 
        attackLength: b, 
        fadeLength: c, 
        previousLevel: d, 
        nextLevel: e, 
        level: true,
        period: isWave,
        magnitude: isWave,
        offset: isWave,
        phase: isWave
    }
}

const TYPES = {
    "constant": getSliders( false, false, false, false, false),
    "sine":     getSliders( true, true, true, true,     true),
    "ramp":     getSliders( true, true, true, true,     false),
}

interface Props {
    sref: React.MutableRefObject<{}>;
    type: FFType;
}

const FFSliders: FC<Props> = ( { sref, type } ) => {

    const [ffParams, setFFParams] = useState<any>({
        attackLength: 0,
        effectLength: 0,
        fadeLength: 0,
        previousLevel: 0,
        level: 0,
        nextLevel: 0,
        period: 0,
        magnitude: 0,
        offset: 0,
        phase: 0,
    })

    useImperativeHandle(sref, () => ffParams, [ffParams] )

    const classes = useSliderStyles(levelSliderStyle);
    const allowedSliders = TYPES[type];

    const updateParam = ( key: string ) => ( value: number ) => {
        const copy = {...ffParams}
        copy[key] = value;
        setFFParams(copy)
    }

    const genLengthSlider = (key: string, name: string) => {
        return <ParamSlider 
            name={name}
            style={lengthSliderStyle} 
            min={0} max={1000} step={10} unit="ms" 
            callback={updateParam(key)} />
    }

    const genLevelSlider = (key: string, name: string) => {
        return <ParamSlider 
            name={name}
            style={levelSliderStyle} 
            min={-1} max={1} step={0.02} allowMark={true} 
            callback={updateParam(key)}/>
    }
    
    return (
        <div className="slider-wrapper">
            <ThemeProvider theme={classes}>
                { allowedSliders.attackLength ? genLengthSlider("attackLength", "Attack Length") : null }
                { genLengthSlider("effectLength", "Effect Length") }
                { allowedSliders.fadeLength ? genLengthSlider("fadeLength", "Fade Length") : null }
                { allowedSliders.period ? genLengthSlider("period", "Period") : null }
                { allowedSliders.previousLevel ? genLevelSlider("previousLevel", "Previous Level") : null }
                { genLevelSlider("level", "Level") }
                { allowedSliders.nextLevel ? genLevelSlider("nextLevel", "Next Level") : null }
                { allowedSliders.magnitude ? genLevelSlider("magnitude", "Magnitude") : null }
                { allowedSliders.offset ? genLevelSlider("offset", "Offset") : null }
                { allowedSliders.phase ? genLevelSlider("phase", "Phase") : null }
            </ThemeProvider>
        </div>
    )
}

export default FFSliders;
