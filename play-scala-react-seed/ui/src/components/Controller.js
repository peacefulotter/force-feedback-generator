
import { useEffect } from 'react'
import Client from '../Client'

export default function Controller() {
    useEffect( () => {
		Client.getController( (controller) => {
			console.log(controller);
		});
	}, [] )

    return (
        <div>Controller</div>
    )
}