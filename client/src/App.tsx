
import ForceFeedback from './Components/ForceFeedback'
import WheelStatus from './Components/WheelStatus'

import './App.css';

function App() {
  return (
    <div className="App">
      
      <div className="ff-title">
          <div className="ff-main-title">ForceFeedback</div>
          <div className="ff-sub-title">Controller</div>
      </div>

      <div className="controller-wrapper">
        <WheelStatus />
        <ForceFeedback />
      </div>
      
    </div>
  );
}

export default App;
