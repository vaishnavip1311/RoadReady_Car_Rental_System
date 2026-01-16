import { Link } from "react-router-dom";

function Sidebar() {
    return (
        <>
          
            <nav className="navbar navbar-inverse fixed-top" id="sidebar-wrapper" role="navigation">
                <ul className="nav sidebar-nav">
                    <div className="sidebar-header">
                        <div className="sidebar-brand">
                            <a href="#">CarRental Manager</a>
                        </div>
                    </div>
                    <li><Link to="/manager">Dashboard</Link></li>
                    <li><Link to="/manager/cars">Manage Cars</Link></li>
                    <li><Link to="/manager/bookings">Bookings</Link></li>
            
                    <li><Link to="/manager/customers">Customers</Link></li>
                    <li><Link to="/manager/carowners">CarOwners</Link></li>
                    <li><Link to="/manager/payments">Make Payments</Link></li>
                  
                      <li><Link to="/manager/returns">Returns</Link></li>
                    <li><Link to="/manager/maintenance">Maintenance</Link></li>
                    <li><Link to="/manager/profile">Profile</Link></li>

                </ul>
            </nav>
        </>
    );
}

export default Sidebar;
