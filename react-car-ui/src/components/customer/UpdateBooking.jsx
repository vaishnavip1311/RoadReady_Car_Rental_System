import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function UpdateBooking() {
  const { bookingId } = useParams();
  const navigate = useNavigate();

  const [booking, setBooking] = useState({
    pickupDate: "",
    dropoffDate: ""
  });

  const [msg, setMsg] = useState("");

  useEffect(() => {
    const fetchBooking = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/api/customer/view/${bookingId}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
          }
        });
        setBooking({
          pickupDate: res.data.pickupDate,
          dropoffDate: res.data.dropoffDate
        });
      } catch (err) {
        console.error("Failed to fetch booking", err);
        setMsg("Could not fetch booking.");
      }
    };
    fetchBooking();
  }, [bookingId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBooking((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(
        `http://localhost:8080/api/customer/booking/update/${bookingId}`,
        booking,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
            "Content-Type": "application/json"
          }
        }
      );
      setMsg("Booking updated successfully!");
     
    } catch (err) {
      console.error("Failed to update booking", err);
      setMsg("Update failed.");
    }
  };

  return (
    <div className="container mt-5 col-md-6 offset-md-3">
      <h3 className="text-center text-fanta mb-4">Update Booking #{bookingId}</h3>

      {msg && (
        <div className={`alert ${msg.includes("success") ? "alert-success" : "alert-danger"}`}>
          {msg}
        </div>
      )}

      <form onSubmit={handleSubmit} className="card p-4 shadow-sm">
        <div className="mb-3">
          <label className="form-label">Pickup Date</label>
          <input
            type="date"
            name="pickupDate"
            value={booking.pickupDate}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Dropoff Date</label>
          <input
            type="date"
            name="dropoffDate"
            value={booking.dropoffDate}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>

        <button type="submit" className="btn btn-fanta w-100">
          Update Booking
        </button>
      </form>
    </div>
  );
}

export default UpdateBooking;
