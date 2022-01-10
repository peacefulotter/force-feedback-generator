import { FC, useState } from 'react';

import '../css/ff.css';
import '../App.css';
import { postRequest } from '../assets/request';

const ForceFeedback = () => {
    const [params, setParams] = useState<any>();

    const updateParams = () => {
        postRequest("/params", {name: "bob", number: 30}, (data) => {
            console.log(data);
            setParams(params);
        })
    }

    return (
        <div className="ff-wrapper">
            <div className="ff-title">ForceFeedback Controller</div>
            <div className="ff-settings">
                <div className="ff-types">
                    <button className="btn ff-type">Constant</button>
                    <button className="btn ff-type">Sine</button>
                </div>
            </div>
            <button className="btn btn-success ff-launch" onClick={updateParams}>Upload</button>
        </div>
    )
}

export default ForceFeedback;
