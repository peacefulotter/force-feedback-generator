// NORTH( CartesianDirection.NORTH ),
//     NORTHEAST( CartesianDirection.NORTHEAST ),
//     EAST( CartesianDirection.EAST ),
//     SOUTHEAST( CartesianDirection.SOUTHEAST ),
//     SOUTH( CartesianDirection.SOUTH ),
//     SOUTHWEST( CartesianDirection.SOUTHWEST ),
//     WEST( CartesianDirection.WEST ),
//     NORTHWEST

// <div className="slider-name">{name}</div>

import { useState } from 'react';
import Checkbox from './Checkbox';

import '../css/ff.css';
import '../App.css';

const DIRECTIONS = [
    "NORTH", "NORTHEAST", "EAST", "SOUTHEAST",
    "SOUTH", "SOUTHWEST", "WEST", "NORTHWEST"   
]

const useDirections = (): [ number, any ] => {
    const [type, setType] = useState<number>(0);

    const checkType = (t: number) => {
        setType(t)
    }

    return [
        type, 
        <div className="directions-container">
            <div className='directions-name'>Directions</div>
            <div className="directions-btns">
                { DIRECTIONS.map( (dir, i) => {
                    return <Checkbox 
                        key={Math.random()}
                        className='btn btn-blue direction-btn'
                        checkedClassName='btn-blue-checked'
                        html={<div>{dir}</div>}
                        forceState={type === i}
                        onClick={() => checkType(i)}
                    />
            })}
            </div>
        </div>
        
    ]
}

export default useDirections;
