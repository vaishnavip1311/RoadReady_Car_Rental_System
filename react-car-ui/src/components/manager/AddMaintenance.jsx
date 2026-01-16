import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";

function AddMaintenance() {
  const [type, setType] = useState("");
  const [remarks, setRemarks] = useState("");
  const [date, setDate] = useState("");
  const [carId, setCarId] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        `http://localhost:8080/api/maintenance/add?carId=${carId}`,
        { type, remarks, date },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        }
      );
      setMessage("Car maintenance added successfully!");
    } catch (err) {
      console.error("Failed to add car maintenance:", err);
      setMessage("Failed to add maintenance log.");
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="fanta-heading text-center mb-4">Add Car Maintenance</h2>

    
      <div className="row mb-3">
        <div className="col-lg-12">
          <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item">
                <Link to="/manager" className="text-decoration-none text-fanta">
                  Manager Dashboard
                </Link>
              </li>
              <li className="breadcrumb-item">
                <Link to="/manager/maintenance" className="text-decoration-none text-fanta">
                  Maintenance List
                </Link>
              </li>
              <li className="breadcrumb-item active" aria-current="page">
                Add Maintenance
              </li>
            </ol>
          </nav>
        </div>
      </div>

      <div className="card shadow p-4 rounded-4">
        {message && (
          <div
            className={`alert ${
              message.includes("successfully") ? "alert-success" : "alert-danger"
            } text-center`}
            role="alert"
          >
            {message}
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Car ID</label>
            <input
              type="number"
              className="form-control"
              value={carId}
              onChange={(e) => setCarId(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Maintenance Type</label>
            <input
              type="text"
              className="form-control"
              value={type}
              onChange={(e) => setType(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Remarks</label>
            <textarea
              className="form-control"
              value={remarks}
              onChange={(e) => setRemarks(e.target.value)}
              rows="3"
              required
            ></textarea>
          </div>

          <div className="mb-3">
            <label className="form-label">Maintenance Date</label>
            <input
              type="date"
              className="form-control"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              required
            />
          </div>

          <button type="submit" className="btn btn-fanta w-100">
            Submit
          </button>
        </form>
      </div>
    </div>
  );
}

export default AddMaintenance;
