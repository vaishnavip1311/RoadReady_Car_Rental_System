import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function BookingSummary() {
  const { bookingId } = useParams();
  const navigate = useNavigate();

  const [booking, setBooking] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!bookingId) return;

    axios.get(`http://localhost:8080/api/customer/view/${bookingId}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })
    .then((res) => {
      setBooking(res.data);
      setLoading(false);
    })
    .catch((err) => {
      console.error("Error fetching booking summary:", err);
      alert("Failed to load booking summary");
      setLoading(false);
    });
  }, [bookingId]);

  if (loading) return <div className="text-center mt-5">Loading booking summary...</div>;
  if (!booking) return <div className="text-center mt-5">No booking found</div>;

  return (
    <div className="container mt-5 mb-5">
      <div className="card shadow-lg p-4 border-0 rounded-4" style={{ backgroundColor: "#fffefb" }}>
        <h2 className="text-center mb-4" style={{ color: "#ff6f00" }}>Booking Summary</h2>

        <div className="mb-3"><strong>Car:</strong> {booking.carBrand} {booking.carModel}</div>
        <div className="mb-3"><strong>Pickup Date:</strong> {booking.pickupDate}</div>
        <div className="mb-3"><strong>Drop-off Date:</strong> {booking.dropoffDate}</div>
        <div className="mb-3"><strong>Location:</strong> {booking.location}</div>
        <div className="mb-3"><strong>Status:</strong> {booking.bookingStatus}</div>
        <div className="mb-4"><strong>Total Amount:</strong> <span className="text-success fw-bold">₹{booking.totalAmount}</span></div>

        <div className="text-center mb-3">
          <button
  onClick={() => navigate(`/customer/payment/${bookingId}`)}
  style={{
    display: "block",
    width: "100%",
    maxWidth: "300px",
    margin: "30px auto 0",
    padding: "15px",
    fontSize: "18px",
    backgroundColor: "#ff9800",
    color: "white",
    border: "none",
    borderRadius: "12px",
    fontWeight: "bold",
    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.2)",
    textAlign: "center",
  }}
>
  Proceed to Payment
</button>

        </div>
      </div>
    </div>
  );
}

export default BookingSummary;
