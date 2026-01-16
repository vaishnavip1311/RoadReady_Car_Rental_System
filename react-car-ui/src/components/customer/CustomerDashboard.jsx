import { useRef, useState, useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import CustomerSidebar from "../../components/customer/CustomerSidebar";
import Navbar from "../../components/customer/Navbar";
import '../css/manager/dash.css';

function CustomerDashboard() {
  const navigate = useNavigate();

  const [isClosed, setIsClosed] = useState(true);
  const overlayRef = useRef(null);
  const wrapperRef = useRef(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login");
    }
  }, [navigate]);

  const handleHamburgerClick = () => {
    const newState = !isClosed;
    setIsClosed(newState);

    if (overlayRef.current) {
      overlayRef.current.style.display = newState ? "none" : "block";
    }
    if (wrapperRef.current) {
      wrapperRef.current.classList.toggle("toggled");
    }
  };

  return (
    <div id="wrapper" ref={wrapperRef}>
      <div
        className="overlay"
        ref={overlayRef}
        onClick={handleHamburgerClick}
      ></div>

      <CustomerSidebar />

      <div id="page-content-wrapper">
        <Navbar />

        <button
          type="button"
          className={`hamburger animated fadeInLeft ${isClosed ? "is-closed" : "is-open"}`}
          data-toggle="offcanvas"
          onClick={handleHamburgerClick}
          aria-label="Toggle sidebar"
        >
          <span className="hamb-top"></span>
          <span className="hamb-middle"></span>
          <span className="hamb-bottom"></span>
        </button>

        <div className="container-fluid mt-4 px-4" style={{ paddingTop: "0px" }}>
          {/* Render nested route components here */}
          <Outlet />
        </div>
      </div>
    </div>
  );
}

export default CustomerDashboard;

