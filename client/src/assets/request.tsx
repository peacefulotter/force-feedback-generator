
const req = ( path: string, credentials: any, callback: (data: any) => void ) => {
    fetch(path, credentials)
        .then(res => res.json())
        .then(data => callback(data));
}

export const postRequest = ( path: string, params: object, callback: (data: any) => void ) => {
    const CREDENTIALS = {
        method: 'POST',
        body: JSON.stringify(params),
        headers: new Headers({
            "Content-Type": "application/json"
        })
    }
    req(path, CREDENTIALS, callback);
}

export const getRequest = ( path: string, callback: (data: any) => void ) => {
    const CREDENTIALS = {
        method: 'GET',
        headers: new Headers({
            "Content-Type": "application/json"
        })
    }
    req(path, CREDENTIALS, callback);
}