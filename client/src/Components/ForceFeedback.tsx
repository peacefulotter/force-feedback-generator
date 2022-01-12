import { useState, useRef } from 'react';
import useDirections from './useDirections'
import Checkbox from './Checkbox';

import FFSliders from './FFSliders';
import { FFType } from '../assets/models';
import { postRequest } from '../assets/request';

import '../css/ff.css';
import '../App.css';


const ForceFeedback = () => {

    const [liveUpload, setLiveUpload] = useState<boolean>(false)
    const [type, setType] = useState<string>("constant");
    const [direction, Directions] = useDirections();
    const slidersRef = useRef({})

    
   const updateType = (t: string) => (isChecked: boolean) => {
        setType(t);
    }

    const liveUploadUpdate = (isChecked: boolean) => {
        setLiveUpload(isChecked)
    }

    const launchFF = () => {
        console.log( slidersRef.current );
        
        const FF_SETTINGS = {
            type, direction, ...slidersRef.current
        }

        console.log("Sending POST request with FF settings: ", FF_SETTINGS);
        postRequest( "/params", FF_SETTINGS )
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
            <FFSliders sref={slidersRef} type={type as FFType}/>
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
