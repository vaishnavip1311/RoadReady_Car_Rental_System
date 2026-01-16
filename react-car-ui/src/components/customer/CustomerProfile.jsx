import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "../css/manager/Profile.css";

function CustomerProfile() {
  const [customer, setCustomer] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCustomer = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/customer/get-one", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setCustomer(response.data);
      } catch (err) {
        console.error("Error fetching customer:", err);
      }
    };
    fetchCustomer();
  }, []);

  const handleDelete = async () => {
    if (window.confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
      try {
        await axios.delete("http://localhost:8080/api/customer/delete", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });

        localStorage.removeItem("token");
        alert("Account deleted successfully.");
        navigate("/"); 
      } catch (err) {
        console.error("Failed to delete account", err);
        alert("Failed to delete account. Please try again.");
      }
    }
  };

  return (
    <div className="container mt-4">
      <div className="row mb-2">
        <div className="col-lg-12">
          <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item">
                <Link to="/customer">Customer Dashboard</Link>
              </li>
              <li className="breadcrumb-item active" aria-current="page">
                Profile
              </li>
            </ol>
          </nav>
        </div>
      </div>

      <h2 className="text-center fanta-heading mb-4">Customer Profile</h2>

      {customer && (
        <div className="card p-4 shadow rounded-4">
          <div className="row align-items-start">
            <div className="col-md-8">
              <h5><strong>ID:</strong> {customer.id}</h5>
              <h5><strong>Name:</strong> {customer.name}</h5>
              <h5><strong>Contact:</strong> {customer.contact}</h5>
              <h5><strong>Email:</strong> {customer.email}</h5>

              <div className="mt-4 d-flex gap-3">
                <Link to="/customer/edit-profile" className="btn btn-fanta px-4">
                  Edit Profile
                </Link>
                <button className="btn btn-danger px-4" onClick={handleDelete}>
                  Delete Account
                </button>
              </div>
            </div>

            <div className="col-md-4 text-center">
              <img
                src={`/images/${customer.profilePic}`}
                alt="Customer"
                className="img-fluid rounded-circle shadow"
                style={{ width: "150px", height: "150px", objectFit: "cover" }}
              />
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default CustomerProfile;
