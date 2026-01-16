import React from "react";
import { useNavigate } from "react-router-dom";
import "../css/HomePage.css";

function CustomerHome() {
  const navigate = useNavigate();

  return (
    <div className="d-flex vh-100 w-100">
     
      <div className="left-pane d-flex flex-column justify-content-center align-items-center text-white text-center px-4">
        <p className="fs-4 fst-italic mb-4">"Travel Better, Drive Smarter"</p>

      <div className="quote-section text-center">
  <p className="main-quote">Ready to hit the road with confidence ??</p>
  <p className="sub-quote">Unlock freedom, rent your perfect ride!</p>
  <p className="sub-quote">Drive into adventures waiting for you.</p>
  <p className="sub-quote">Your journey starts here with RoadReady.</p>
</div>

      </div>

     
      <div className="right-pane position-relative d-flex justify-content-center align-items-center">
      

       <h1 className="roadready-text">
  RoadReady !!!
  <span className="sparkle sparkle1"></span>
  <span className="sparkle sparkle2"></span>
  <span className="sparkle sparkle3"></span>
  <span className="sparkle sparkle4"></span>
  <span className="sparkle sparkle5"></span>
</h1>

      </div>
    </div>
  );
}

export default CustomerHome;
