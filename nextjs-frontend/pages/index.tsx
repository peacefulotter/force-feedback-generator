import { useEffect, useState } from 'react'

// import EventSource from 'eventsource'
// import { SERVER_URL } from '../config'

// async function getData(path: string) {
// 	const res = await fetch(`${SERVER_URL}${path}`);
// 	console.log(res);
// 	if (!res.ok) {
// 	  	throw new Error('Failed to fetch data');
// 	}
// 	return res.json();
// }

// useEffect( () => {
//  const sse = EventSource(`${SERVER_URL}/stream`)
// 	sse.onopen = (e) => { console.log('listen to api-sse endpoint', e)};
// 	sse.onmessage = (e) => {
// 		const { name, data, value } = JSON.parse(e.data)
// 	};
// 	sse.onerror = (e) => { console.log('error', e )};
// 	return () => {
// 		console.log('Closing SSE');
// 		sse.close()
// 	}
// }, [])

type GamepadCollection = {[i: number]: Gamepad}

export default function Home() {

	const [controllers, setControllers] = useState<GamepadCollection>({})

	useEffect( () => {
		const loop = () => {
			const gamepads = navigator.getGamepads();
			let temp: GamepadCollection = {} 
			for (const gamepad of gamepads) {
				// Can be null if disconnected during the session
				if (gamepad == null) 
					continue;
				temp[gamepad.index] = gamepad
			}
			setControllers( temp )
			window.requestAnimationFrame(loop);
		}
		window.requestAnimationFrame(loop);
	}, [] )

	return (
		<main className="">
			{ Object.values(controllers).map( (c, i) =>
				<div className="flex flex-wrap flex-col">
					<div className="rounded-[1000px] relative m-24 border-8 p-1 bg-slate-600 w-96 h-96" key={`controller-${i}`}>
						<div className="absolute flex items-center justify-center font-bold text-black border-2 border-black rounded-[100px] bg-red-400 w-8 h-8 -translate-x-1/2 -translate-y-1/2" style={{left: `${(c.axes[0] / 2 + 0.5) * 100}%`, top: `${(c.axes[1] / 2 + 0.5) * 100}%`}}>L</div>
						<div className="absolute flex items-center justify-center font-bold text-black border-2  border-black rounded-[100px] bg-green-400 w-8 h-8 -translate-x-1/2 -translate-y-1/2" style={{left: `${(c.axes[2]  / 2 + 0.5) * 100}%`, top: `${(c.axes[3]  / 2 + 0.5) * 100}%`}}>R</div>
					</div>
					<div className="flex flex-wrap">
						{ c.buttons.map( (b, j) => 
							<div key={`btn-${i}-${j}`} className={`w-min h-min ${b.pressed ? 'bg-blue-700' : 'bg-blue-400'} px-10 py-3 h-10`}>{b.value}</div>
						)}
					</div>
				</div> 
				
			) }
		</main>
	)
}
