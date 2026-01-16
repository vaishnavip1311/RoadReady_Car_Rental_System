import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../css/manager/bookings.css";

function MakePayments() {
  const [bookings, setBookings] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchUnpaidBookings = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/booking/un-paid", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setBookings(response.data);
      } catch (error) {
        console.error("Error fetching unpaid bookings:", error);
      }
    };
    fetchUnpaidBookings();
  }, []);

  const handleMakePayout = async (bookingId) => {
    try {
      await axios.post(
        `http://localhost:8080/api/ownerpayout/create/${bookingId}`,
        {},
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        }
      );
      setMessage(`Payout successful for booking ID ${bookingId}`);
    
      setBookings(prev => prev.filter(b => b.id !== bookingId));
    } catch (error) {
      console.error("Payout error:", error);
      setMessage("Payout failed. Please try again.");
    }
  };

  return (
    <div className="container mt-4">
     <nav aria-label="breadcrumb">
  <ol className="breadcrumb fanta-breadcrumb">
    <li className="breadcrumb-item">
      <Link to="/manager">Manager Dashboard</Link>
    </li>
    <li className="breadcrumb-item active" aria-current="page">
      Make Payments
    </li>
  </ol>
</nav>

      <h2 className="text-center fanta-heading mt-3">Unpaid Bookings</h2>

      {message && (
        <div className={`alert ${message.includes("successful") ? "alert-success" : "alert-danger"} mt-3`}>
          {message}
        </div>
      )}

      <div className="row mt-3">
  {bookings.map((b, index) => (
    <div className="col-md-4 mb-4" key={index}>
      <div className="card booking-card shadow-sm">
        <div className="card-body">
          <h5 className="card-title text-fanta">Booking ID: {b.id}</h5>
          <p className="card-text">
            <strong>Customer:</strong> {b.customerName} <br />
            <strong>Car:</strong> {b.carBrand} {b.carModel} <br />
            <strong>Status:</strong> {b.bookingStatus} <br />
            <strong>Total:</strong> ₹{b.totalAmount} <br />
            <strong>Pickup:</strong> {b.pickupDate} <br />
            <strong>Dropoff:</strong> {b.dropoffDate}
          </p>

          <button
            className="btn btn-fanta w-100"
            onClick={() => handleMakePayout(b.id)}
          >
            Make Payout
          </button>
        </div>
      </div>
    </div>
  ))}
</div>

    </div>
  );
}

export default MakePayments;
