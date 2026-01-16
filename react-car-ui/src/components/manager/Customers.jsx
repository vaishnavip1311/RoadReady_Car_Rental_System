import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../css/manager/Cars.css";

function Customers() {
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    const fetchCustomers = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/customer/get-all", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setCustomers(response.data);
      } catch (error) {
        console.error("Error fetching customers:", error);
      }
    };
    fetchCustomers();
  }, []);

  return (
    <>
      <div className="container-fluid">
        <div className="row">
          <div className="col-lg-12">
            <nav aria-label="breadcrumb">
              <ol className="breadcrumb fanta-breadcrumb">
                <li className="breadcrumb-item">
                  <Link to="/manager">Manager Dashboard</Link>
                </li>
                <li className="breadcrumb-item active" aria-current="page">Customers</li>
              </ol>
            </nav>
          </div>
        </div>

        <h1 className="text-center fanta-heading mt-2">Customer List</h1>

        <div className="container mt-3">
          <div className="row">
            {customers.map((c, index) => (
              <div className="col-md-4 mb-4" key={index}>
                <div className="card car-card shadow-sm">
                  <div className="card-body">
                    <h5 className="card-title text-fanta">
                      {c.name} (ID: {c.id})
                    </h5>
                    <p className="card-text">
                      <strong>Username:</strong> {c.username} <br />
                      <strong>Email:</strong> {c.email} <br />
                      <strong>Phone:</strong> {c.contact}
                    </p>
                    <Link className="btn btn-fanta mt-2 w-100" to={`/manager/customer-details/${c.id}`}>
                      View Details
                    </Link>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
}

export default Customers;
