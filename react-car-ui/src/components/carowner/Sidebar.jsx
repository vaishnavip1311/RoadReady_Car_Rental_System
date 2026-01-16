import { Link } from "react-router-dom";

function Sidebar() {
    return (
        <nav className="navbar navbar-inverse fixed-top" id="sidebar-wrapper" role="navigation">
            <ul className="nav sidebar-nav">
                <div className="sidebar-header">
                    <div className="sidebar-brand">
                        <a href="#">CarRental Owner</a>
                    </div>
                </div>
                <li><Link to="/carowner">Dashboard</Link></li>
                <li><Link to="/carowner/cars">My Cars</Link></li>
                <li><Link to="/carowner/bookings">View Bookings</Link></li>
                <li><Link to="/carowner/profile">Profile</Link></li>
            </ul>
        </nav>
    );
}

export default Sidebar;
