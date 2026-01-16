import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

function CustomerBookingDetails() {
  const { customerId } = useParams();
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/by-customer/${customerId}`,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        );
        setBookings(response.data);
      } catch (error) {
        console.error("Error fetching bookings:", error);
      }
    };
    fetchBookings();
  }, [customerId]);

  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-lg-12">
          <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item">
                <a href="/manager">Manager Dashboard</a>
              </li>
              <li className="breadcrumb-item">
                <a href="/manager/customers">Customers</a>
              </li>
              <li className="breadcrumb-item active" aria-current="page">
                Booking Details
              </li>
            </ol>
          </nav>
        </div>
      </div>

      <h3 className="text-center fanta-heading mt-2">
        Bookings of Customer ID: {customerId}
      </h3>

      <div className="col-lg-12 mt-4">
        {bookings.length === 0 ? (
          <div className="alert alert-warning">No bookings found.</div>
        ) : (
          bookings.map((b, index) => (
            <div className="card mt-3 shadow-sm" key={index}>
              <div className="card-body">
                <h5 className="card-title text-fanta">
                  Booking ID: {b.id}
                </h5>
                <p className="card-text">
                  <strong>Car:</strong> {b.carBrand} {b.carModel} <br />
                  <strong>Pickup Date:</strong> {b.pickupDate} <br />
                  <strong>Drop Date:</strong> {b.dropoffDate} <br />
                  <strong>Total Amount:</strong> ₹{b.totalAmount} <br />
                  <strong>Status:</strong> {b.bookingStatus}
                </p>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default CustomerBookingDetails;
