import { useState } from "react";
import { useNavigate, Link } from "react-router-dom"; 
import axios from "axios";

function AddReturnLog() {
  const [fuelLevel, setFuelLevel] = useState("");
  const [returnDate, setReturnDate] = useState("");
  const [bookingId, setBookingId] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        `http://localhost:8080/api/returnlog/add?bookingId=${bookingId}`,
        { fuelLevel, returnDate },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        }
      );
      setMessage("Return Log added successfully.");
      setFuelLevel("");
      setReturnDate("");
      setBookingId("");
    } catch (err) {
      console.error("Failed to add return log:", err);
      setMessage("Failed to add return log. Please try again.");
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="fanta-heading text-center mb-4">Add Return Log</h2>

    
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
                <Link to="/manager/returns" className="text-decoration-none text-fanta">
                  Return Logs
                </Link>
              </li>
              <li className="breadcrumb-item active" aria-current="page">
                Add Return Log
              </li>
            </ol>
          </nav>
        </div>
      </div>

      <div className="card shadow p-4 rounded-4">
        {message && (
          <div className="alert alert-info text-center" role="alert">
            {message}
          </div>
        )}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Fuel Level</label>
            <input
              type="text"
              className="form-control"
              value={fuelLevel}
              onChange={(e) => setFuelLevel(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Return Date</label>
            <input
              type="date"
              className="form-control"
              value={returnDate}
              onChange={(e) => setReturnDate(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Booking ID</label>
            <input
              type="text"
              className="form-control"
              value={bookingId}
              onChange={(e) => setBookingId(e.target.value)}
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

export default AddReturnLog;
