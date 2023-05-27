'use client'
import { useEffect, useState } from 'react';
import EventSource from 'eventsource';

import { sse, SERVER_URL } from '../config';

const normalize = (x: number, y: number) => {
	const len = Math.sqrt(x * x + y * y)
	return len > 0.5 ? {x: x / len, y: y / len} : {x, y}
}

async function getData() {
	const res = await fetch(`${SERVER_URL}/controller`);
	if (!res.ok) {
	  	throw new Error('Failed to fetch data');
	}
	return res.json();
  }

export default async function Home() {

	const [pos, setPos] = useState({x: 0, y: 0})
	const [_pos, _setPos] = useState({x: 0, y: 0})

	const controller = await getData()
	console.log(controller);

	useEffect( () => {
		// Axios.get('/token').then(({data}) => {
		// 	console.log(data);
		// })
		console.log(sse);
		
		sse.onopen = (e) => { console.log('listen to api-sse endpoint', e)};
		sse.onmessage = (e) => {
			const { name, data, value } = JSON.parse(e.data)
			if ( name == 'rx') {
				setPos(({y}) => normalize(value, y))
				_setPos(({y}) => normalize(data, y))
			}
			else if ( name == 'ry') {
				setPos(({x}) => normalize(x, value))
				_setPos(({x}) => normalize(x, data))
			}
		};
		sse.onerror = (e) => { console.log('error', e )};
		return () => {
			console.log('Closing SSE');
			sse.close()
		}
	}, [])


	return (
		<main className="">
			<button onClick={onClick} className='bg-gray-800 px-6 py-2 rounded'>toggle</button>
			<div className="relative m-24 border-2 bg-slate-600 w-96 h-96">
				<div className="absolute rounded bg-red-400 w-5 h-5" style={{left: `${(pos.x / 2 + 0.5) * 100}%`, top: `${(pos.y / 2 + 0.5) * 100}%`}}></div>
				<div className="absolute rounded bg-green-400 w-5 h-5" style={{left: `${(_pos.x / 2 + 0.5) * 100}%`, top: `${(_pos.y / 2 + 0.5) * 100}%`}}></div>
			</div>
		</main>
	)
}