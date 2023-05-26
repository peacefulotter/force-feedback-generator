'use client'
import { useEffect } from 'react';
import EventSource from 'eventsource';

import { sse, Axios } from '../config';

export default function Home() {
	useEffect( () => {
		console.log("here")
		// Axios.get('/token').then(({data}) => {
		// 	console.log(data);
		// })
		sse.onopen = (e) => { console.log('listen to api-sse endpoint', e)};
		sse.onmessage = (e) => {
			const json = JSON.parse(e.data)
			console.log('Received:', json);
		};
		sse.onerror = (e) => { console.log('error', e )};
		return () => sse.close()
	}, [])

	const onClick = () => {
		Axios.get('/toggle')
	}

	return (
		<main className="">
			<button onClick={onClick} className='bg-gray-800 px-6 py-2 rounded'>toggle</button>
		</main>
	)
}
