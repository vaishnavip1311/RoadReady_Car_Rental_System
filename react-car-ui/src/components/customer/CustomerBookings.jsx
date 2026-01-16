import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function CustomerBookings() {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchBookings() {
      try {
        const response = await axios.get("http://localhost:8080/api/by-customer", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setBookings(response.data);
      } catch (error) {
        console.error("Error fetching bookings:", error);
        setBookings([]);
      } finally {
        setLoading(false);
      }
    }

    fetchBookings();
  }, []);

  const handleCancel = async (bookingId) => {
    try {
      await axios.delete(`http://localhost:8080/api/customer/booking/cancel/${bookingId}`, {
  headers: {
    Authorization: "Bearer " + localStorage.getItem("token"),
  },
});

      setBookings((prev) =>
        prev.map((b) =>
          b.id === bookingId ? { ...b, bookingStatus: "Cancelled" } : b
        )
      );
    } catch (err) {
      console.error("Failed to cancel booking:", err);
    }
  };

  const handleUpdate = (bookingId) => {
    navigate(`/customer/update-booking/${bookingId}`);
  };

  if (loading) {
    return (
      <div className="container mt-5 text-center">
        <p>Loading your bookings...</p>
      </div>
    );
  }

  if (bookings.length === 0) {
    return (
      <div className="container mt-5 text-center">
        <h4 className="text-muted">No bookings found.</h4>
      </div>
    );
  }

  return (
    <div className="container mt-5">
      <h2 className="text-center text-fanta mb-4 display-6">Your Bookings</h2>
      <div className="row">
        {bookings.map((booking) => (
          <div key={booking.id} className="col-md-6 mb-4">
            <div className="card shadow p-4 rounded-4">
              <h5
                className="mb-3"
                style={{ color: "#ff6f00", fontWeight: "700", fontSize: "1.5rem" }}
              >
                <i className="bi bi-receipt me-2"></i> Booking ID: {booking.id}
              </h5>
              <p><strong>Car:</strong> {booking.carName}</p>
              <p><strong>Pickup Date:</strong> {booking.pickupDate}</p>
              <p><strong>Dropoff Date:</strong> {booking.dropoffDate}</p>
              <p><strong>Total Amount:</strong> ₹{booking.totalAmount}</p>
              <p>
                <strong>Status:</strong>
                <span
                  className="ms-2 fw-bold text-white rounded px-3 py-1"
                  style={{
                    backgroundColor: booking.bookingStatus === "Cancelled" ? "#dc3545" : "#ff6f00",
                    fontSize: "1.1rem",
                    display: "inline-block",
                    minWidth: "90px",
                    textAlign: "center",
                    boxShadow: "0 0 5px rgba(0,0,0,0.15)"
                  }}
                >
                  {booking.bookingStatus}
                </span>
              </p>

              <div className="d-flex justify-content-end gap-2 mt-3">
                <button
                  className="btn btn-outline-warning"
                  onClick={() => handleUpdate(booking.id)}
                  disabled={booking.bookingStatus === "Cancelled"}
                >
                  Update
                </button>
                <button
                  className="btn btn-outline-danger"
                  onClick={() => handleCancel(booking.id)}
                  disabled={booking.bookingStatus === "Cancelled"}
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default CustomerBookings;
