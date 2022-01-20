import { useState } from 'react';
import moment from 'moment'


import { postRequest } from '../assets/request';

import '../css/status.css';
import '../App.css';



const formatTime = (time: number) => {
    const d = moment.duration(time * 100, 'milliseconds');
    return moment(d.asMinutes(),'mm').format('mm:ss');
}


const Recording = () => {
    const [time, setTime] = useState<number>(0);
    const [isRecording, setIsRecording] = useState<boolean>(false)
    const [stopwatch, setStopwatch] = useState<any>(null)

    const toggleRecording = () => {
        if ( isRecording )
        {
            clearInterval( stopwatch )
            setTime(0)
            postRequest( '/stop', {} )
        }
        else
        {
            postRequest( '/start', {} )

            const sw = setInterval( () => {
                setTime(time => time + 1)
            }, 100 )
            setStopwatch( sw )
        }

        setIsRecording( ir => !ir )
    }

    return (
        <div className="recording-wrapper">
            <div 
                className={`btn recording-btn ${isRecording ? 'btn-error' : 'btn-success'}`} 
                onClick={toggleRecording}>
                {isRecording ? "Stop" : "Start"}
            </div>
            <div className="recording-sample">{time} Samples</div>
            <div className="recording-separation"></div>
            <div className="recording-timer">{formatTime(time)}</div>
        </div>
    )
}

export default Recording;
