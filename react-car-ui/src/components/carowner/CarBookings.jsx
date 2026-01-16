import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

function CarBookings() {
  const { carId } = useParams();
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [msg, setMsg] = useState("");

  useEffect(() => {
    fetchBookings();
  }, [carId]);

  const fetchBookings = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/book/get-car/${carId}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      setBookings(response.data);
    } catch (error) {
      console.error("Failed to fetch bookings", error);
      setMsg("Could not load bookings.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-4">
      <h3 className="text-center mb-4" style={{ color: "#ff6f00" }}>
        Bookings for Car ID {carId}
      </h3>

      {msg && <div className="alert alert-danger">{msg}</div>}

      {loading ? (
        <p className="text-center" style={{ color: "#ff6f00" }}>
          Loading bookings...
        </p>
      ) : bookings.length === 0 ? (
        <p className="text-center text-muted">No bookings found for this car.</p>
      ) : (
        <div className="table-responsive">
          <table className="table table-bordered shadow-sm">
            <thead style={{ backgroundColor: "#ff6f00", color: "#fff" }}>
              <tr>
                <th>#</th>
                <th>Customer</th>
                <th>Car</th>
                <th>Status</th>
                <th>Start</th>
                <th>End</th>
                <th>Amount</th>
              </tr>
            </thead>
            <tbody>
              {bookings.map((b, index) => (
                <tr key={b.id}>
                  <td style={{ color: "#ff6f00" }}>{index + 1}</td>
                  <td style={{ color: "#ff6f00" }}>{b.customerName}</td>
                  <td style={{ color: "#ff6f00" }}>{b.carBrand} {b.carModel}</td>
                  <td style={{ color: "#ff6f00" }}>{b.bookingStatus}</td>
                  <td style={{ color: "#ff6f00" }}>{b.pickupDate}</td>
                  <td style={{ color: "#ff6f00" }}>{b.dropoffDate}</td>
                  <td style={{ color: "#ff6f00" }}>₹{b.totalAmount}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default CarBookings;
