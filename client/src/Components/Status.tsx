import { FC, useEffect, useState } from 'react';
import { Status } from '../assets/models';

import { getRequest } from '../assets/request';

import '../css/status.css';
import '../App.css';



const WheelStatus = () => {
    const [status, setStatus] = useState<Status>()

    useEffect( () => {
        console.log("Sending GET request for Wheel status");
        
        getRequest("/status", (data) => {
            console.log(data);
            setStatus(data)
        })
    }, [])

    return (
        <div className="status-wrapper">
            <div className="wheel-name">{status?.name}</div>
            <div className="wheel-active">{status?.active}</div>
            <div className="wheel-axis">{status?.axisAngle}</div>
        </div>
    )
}

export default WheelStatus;
