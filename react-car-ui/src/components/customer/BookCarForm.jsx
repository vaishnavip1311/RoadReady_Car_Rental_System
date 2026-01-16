import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

function BookCarForm() {
  const { carId } = useParams();
  const navigate = useNavigate();

  const [pickupDate, setPickupDate] = useState("");
  const [dropoffDate, setDropoffDate] = useState("");
  const [location, setLocation] = useState("");

  const handleSubmit = (e) => {
  e.preventDefault();

  const bookingData = { pickupDate, dropoffDate, location };

  axios.post(`http://localhost:8080/api/customer/book/car/${carId}`, bookingData, {
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
  })
  .then((res) => {
    const bookingId = res.data.id;
     console.log("Booking ID received:", bookingId); 
    navigate(`/customer/booking-summary/${bookingId}`);  // Navigate to summary page
  })
  .catch((err) => {
    alert("Booking failed: " + (err.response?.data?.message || err.message));
  });
};

  return (
    <div className="container mt-4">
      <h2 className="text-center fanta-heading">Book This Car</h2>

      <form className="card p-4 shadow-sm mt-4" onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Pickup Date</label>
          <input
            type="date"
            className="form-control"
            required
            value={pickupDate}
            onChange={(e) => setPickupDate(e.target.value)}
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Drop-off Date</label>
          <input
            type="date"
            className="form-control"
            required
            value={dropoffDate}
            onChange={(e) => setDropoffDate(e.target.value)}
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Location</label>
          <select
            className="form-select"
            required
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          >
            <option value="">-- Select Location --</option>
            <option value="Chennai">Chennai</option>
            <option value="Coimbatore">Coimbatore</option>
            <option value="Madurai">Madurai</option>
          </select>
        </div>

        <button type="submit" className="btn btn-fanta">Submit</button>
      </form>
    </div>
  );
}

export default BookCarForm;
