
export type FFType = "constant" | "sine" | "ramp";

export interface Status {
    name: string;
    active: true;
    description: string;
    ffDescription: string;
    gain: number;
}

export interface Poll {
    axisAngle: number;
}