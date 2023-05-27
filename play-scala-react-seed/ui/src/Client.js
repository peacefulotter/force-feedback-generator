/* eslint-disable no-undef */
const fetchApi = (path) => (cb) => fetch(path, {
    accept: "application/json",
  } )
    .then(checkStatus)
    .then(res => res.json())
    .then(cb);
    
    
function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }
  const error = new Error(`HTTP Error ${response.statusText}`);
  error.status = response.statusText;
  error.response = response;
  console.log(error); // eslint-disable-line no-console
  throw error;
}

const Client = { 
  getSummary: fetchApi('/api/summary'), 
  getController: fetchApi('/api/controller'),
  toggle: fetchApi('/api/toggle')
}

export default Client;
