import { FC, useEffect, useState } from 'react';
import SockJS from "sockjs-client";
import { Stomp, Client } from '@stomp/stompjs'

import io from 'socket.io-client'


import { Status } from '../assets/models';

import '../css/status.css';
import '../App.css';

const url = "ws://127.0.0.1:8888/ws"

const WheelStatus = () => {
    const [status, setStatus] = useState<Status>()


    useEffect(() => {
        console.log("here");

        const socket = new SockJS(`http://localhost:8888/ws`);
        const client = Stomp.over(socket);
        // const client = new Client(); 
        
        // client.configure({
        //     brokerURL: 'ws://127.0.0.1:8888/ws',
        //     onConnect: () => {
        //         console.log('onConnect');
      
        //         client.subscribe('/topic/greetings', message => {
        //             console.log(message);
        //         });
        //     },
        //     // Helps during debugging, remove in production
        //     debug: (str) => {
        //       console.log(new Date(), str);
        //     }
        // });
        // client.activate();

        client.connect( {}, 
            () => {
                client.subscribe('/topic/greetings', (data: any) => {
                    console.log(data);
                    // const msg = JSON.parse(data);
                    // console.log(msg);
                    
                });
            }, () => {
                console.log('failed');
            }
        );
    }, [])


    return (
        <div className="status-wrapper">
            <div>
                <div className="wheel-name">Name: {status?.name}</div>
                <div className="wheel-active">Active: {status?.active}</div>
                <div className="wheel-axis">Axis: {status?.axisAngle}</div>
            </div>
        </div>
    )
}

export default WheelStatus;
