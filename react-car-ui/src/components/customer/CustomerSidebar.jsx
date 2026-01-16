import { Link } from "react-router-dom";

function CustomerSidebar() {
  return (
    <nav className="navbar navbar-inverse fixed-top" id="sidebar-wrapper" role="navigation">
      <ul className="nav sidebar-nav">
        <div className="sidebar-header">
          <div className="sidebar-brand">
            <a href="#">CarRental Customer</a>
          </div>
        </div>
        <li><Link to="/customer">Dashboard</Link></li>
        <li><Link to="/customer/cars">Available Cars</Link></li>        
        <li><Link to="/customer/bookings">My Bookings</Link></li>
        <li><Link to="/customer/profile">Profile</Link></li>
      </ul>
    </nav>
  );
}

export default CustomerSidebar;
