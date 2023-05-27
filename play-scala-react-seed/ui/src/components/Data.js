
import { useState, useEffect } from 'react'

import { sse } from '../config';
import Client from '../Client'

const pos = { x: 0, y: 0.5 }
const _pos = { x: 0.5, y: 0.5 }

export default function Controller() {
    useEffect( () => {
		console.log(sse);
		sse.onopen = (e) => { console.log('listen to api-sse endpoint', e)};
		sse.onmessage = (e) => {
			const { name, data, value } = JSON.parse(e.data)
			console.log(name, data, value);
			// if ( name == 'rx') {
			// 	setPos(({y}) => normalize(value, y))
			// 	_setPos(({y}) => normalize(data, y))
			// }
			// else if ( name == 'ry') {
			// 	setPos(({x}) => normalize(x, value))
			// 	_setPos(({x}) => normalize(x, data))
			// }
		};
		sse.onerror = (e) => { console.log('error', e )};
		return () => {
			console.log('Closing SSE');
			sse.close()
		}
	}, [])

    const onClick = () => Client.toggle((res) => console.log(res))

    return (
        <div>
            Data
            <button onClick={onClick} className='bg-gray-800 px-6 py-2 rounded'>toggle</button>
			<div className="relative m-24 border-2 bg-slate-600 w-96 h-96">
				<div className="absolute rounded bg-red-400 w-5 h-5" style={{left: `${(pos.x / 2 + 0.5) * 100}%`, top: `${(pos.y / 2 + 0.5) * 100}%`}}></div>
				<div className="absolute rounded bg-green-400 w-5 h-5" style={{left: `${(_pos.x / 2 + 0.5) * 100}%`, top: `${(_pos.y / 2 + 0.5) * 100}%`}}></div>
			</div>    
        </div>
    )
}