import { useRef, useEffect, useState } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import '../css/manager/dash.css';
import Sidebar from './SideBar';
import Navbar from './Navbar';

function ManagerDashboard() {
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/');
        }
    }, [navigate]);

    const [isClosed, setIsClosed] = useState(true);
    const overlayRef = useRef(null);
    const wrapperRef = useRef(null);

    const handleHamburgerClick = () => {
        const newState = !isClosed;
        setIsClosed(newState);

        if (overlayRef.current) {
            overlayRef.current.style.display = newState ? 'none' : 'block';
        }

        if (wrapperRef.current) {
            wrapperRef.current.classList.toggle('toggled');
        }
    };

    return (
        <div id="wrapper" ref={wrapperRef}>
            <div className="overlay" ref={overlayRef}></div>

            <Sidebar />

            <div id="page-content-wrapper">
                <Navbar />

                <button
                    type="button"
                    className={`hamburger animated fadeInLeft ${isClosed ? 'is-closed' : 'is-open'}`}
                    data-toggle="offcanvas"
                    onClick={handleHamburgerClick}
                >
                    <span className="hamb-top"></span>
                    <span className="hamb-middle"></span>
                    <span className="hamb-bottom"></span>
                </button>

                <div className="container-fluid mt-4 px-4">
                    <Outlet />
                </div>
            </div>
        </div>
    );
}

export default ManagerDashboard;
