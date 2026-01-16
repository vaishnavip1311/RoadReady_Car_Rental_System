import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "../css/manager/Profile.css";

function Profile() {
  const [manager, setManager] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchManager = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/manager/view", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setManager(response.data);
      } catch (err) {
        console.error("Error fetching manager:", err);
      }
    };
    fetchManager();
  }, []);

  const handleDelete = async () => {
    if (window.confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
      try {
        await axios.delete("http://localhost:8080/api/manager/delete", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        localStorage.removeItem("token");
        alert("Account deleted successfully.");
        navigate("/"); // Redirect to home or login page after deletion
      } catch (err) {
        console.error("Failed to delete account", err);
        alert("Failed to delete account. Please try again.");
      }
    }
  };

  return (
    <div className="container mt-5">
      <div className="row mb-3">
        <div className="col-lg-12">
          <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item">
                <Link to="/manager">Manager Dashboard</Link>
              </li>
              <li className="breadcrumb-item active" aria-current="page">
                Profile
              </li>
            </ol>
          </nav>
        </div>
      </div>

      <h2 className="text-center fanta-heading mb-4">Profile</h2>

      {manager && (
        <div className="card p-4 shadow rounded-4">
          <div className="row align-items-start">
            <div className="col-md-8">
              <h5><strong>ID:</strong> {manager.id}</h5>
              <h5><strong>Name:</strong> {manager.name}</h5>
              <h5><strong>Username:</strong> {manager.username}</h5>
              <h5><strong>Contact:</strong> {manager.contact}</h5>
              <h5><strong>Email:</strong> {manager.email}</h5>

              <div className="mt-4 d-flex gap-3">
                <Link to="/manager/edit-profile" className="btn btn-fanta px-4">
                  Edit Profile
                </Link>
                <button className="btn btn-danger px-4" onClick={handleDelete}>
                  Delete Account
                </button>
              </div>
            </div>

            <div className="col-md-4 text-center" style={{ marginTop: "1px" }}>
              <img
                src={`../images/${manager?.profilePic}`}
                alt="Manager"
                className="img-fluid rounded-circle shadow"
                style={{ width: "150px", height: "150px", objectFit: "cover" }}
              />
            </div>
          </div>

          <div style={{ marginTop: "40px" }}></div>
        </div>
      )}
    </div>
  );
}

export default Profile;
