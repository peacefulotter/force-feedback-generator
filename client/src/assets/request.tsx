
export const postRequest = ( path: string, params: object, callback: (data: any) => void ) => {
    const CREDENTIALS = {
        method: 'POST',
        body: JSON.stringify(params),
        headers: new Headers({
            "Content-Type": "application/json"
        })
    }
    fetch(path, CREDENTIALS)
        .then(res => res.json())
        .then(data => callback(data));
}