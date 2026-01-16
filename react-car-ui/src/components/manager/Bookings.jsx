import axios from "axios";
import { useEffect, useState } from "react";
import "../css/manager/Bookings.css";

function Bookings() {
  const [bookings, setBookings] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const size = 6;

  const fetchBookings = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/manager/view-all-bookings?page=${page}&size=${size}`,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        }
      );

      const data = response.data;

      setBookings(data.content || []);
      setTotalPages(data.totalPages || 1);
    } catch (error) {
      console.error("Error fetching bookings:", error);
      setBookings([]);
    }
  };

  useEffect(() => {
    fetchBookings();
  }, [page]);

  const handlePrev = () => {
    if (page > 0) setPage(page - 1);
  };

  const handleNext = () => {
    if (page + 1 < totalPages) setPage(page + 1);
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center fanta-heading mb-4">All Bookings</h2>

      <div className="row">
        {bookings.length === 0 ? (
          <p className="text-center">No bookings found.</p>
        ) : (
          bookings.map((b, index) => (
            <div className="col-md-4 mb-4" key={index}>
              <div className="card booking-card shadow-sm">
                <div className="card-body">
                  <h5 className="card-title text-fanta">Booking Id: {b.id}</h5>
                  <p className="card-text">
                    <strong>Customer:</strong> {b.customerName} <br />
                    <strong>Car:</strong> {b.carBrand} {b.carModel} <br />
                    <strong>Status:</strong> {b.bookingStatus} <br />
                    <strong>Total:</strong> ₹{b.totalAmount} <br />
                    <strong>Pickup:</strong> {b.pickupDate} <br />
                    <strong>Dropoff:</strong> {b.dropoffDate}
                  </p>
                </div>
              </div>
            </div>
          ))
        )}
      </div>

          {totalPages > 1 && (
        <div className="d-flex justify-content-center align-items-center gap-3 mt-4">
          <span
            style={{
              fontSize: "2rem",
              color: "#ff6f00",
              cursor: page === 0 ? "not-allowed" : "pointer",
              userSelect: "none",
              opacity: page === 0 ? 0.4 : 1,
            }}
            onClick={handlePrev}
          >
            {"<"}
          </span>
          <span style={{ fontSize: "1.5rem" }}>
            {page + 1}
          </span>
          <span
            style={{
              fontSize: "2rem",
              color: "#ff6f00",
              cursor: page + 1 === totalPages ? "not-allowed" : "pointer",
              userSelect: "none",
              opacity: page + 1 === totalPages ? 0.4 : 1,
            }}
            onClick={handleNext}
          >
            {">"}
          </span>
        </div>
      )}
    </div>
  );
}

export default Bookings;
