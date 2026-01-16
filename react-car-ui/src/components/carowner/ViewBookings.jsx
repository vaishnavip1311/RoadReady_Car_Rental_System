import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function ViewBookings() {
  const [cars, setCars] = useState([]);
  const [page, setPage] = useState(0);
  const size = 6;
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchCars();
  }, [page]);

  const fetchCars = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/car/by-owner?page=${page}&size=${size}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setCars(response.data);
    } catch (error) {
      console.error("Error fetching cars:", error);
    }
  };

  const handleViewBookings = (carId) => {
    navigate(`/carowner/view-bookings/${carId}`);
  };

  return (
    <div className="container mt-4">
      <h3 className="text-center text-fanta mb-4">My Cars & Bookings</h3>

      <div className="row">
        {cars.length === 0 ? (
          <p className="text-center">No cars found.</p>
        ) : (
          cars.map((car) => (
            <div className="col-md-4 mb-4" key={car.id}>
              <div className="card shadow-sm">
                <img
                  src={`/images/${car.pic}`}
                  alt={car.model}
                  className="card-img-top"
                  style={{ height: "220px", objectFit: "cover", padding: "20px" }}
                />
                <div className="card-body">
                  <h5 className="card-title">{car.brand} {car.model}</h5>
                  <p className="card-text">
                    <strong>Year:</strong> {car.year}<br />
                    <strong>Color:</strong> {car.colour}<br />
                    <strong>Price/Day:</strong> ₹{car.pricePerDay}
                  </p>
                  <button
                    className="btn btn-outline-fanta w-100"
                    onClick={() => handleViewBookings(car.id)}
                  >
                    View Bookings
                  </button>
                </div>
              </div>
            </div>
          ))
        )}
      </div>

     <div
  className="d-flex justify-content-center gap-4 mt-4"
  style={{ alignItems: "center", lineHeight: "1" }}
>
  <span
    onClick={() => page > 0 && setPage(page - 1)}
    style={{
      cursor: page === 0 ? "default" : "pointer",
      fontSize: "1rem",
      userSelect: "none",
    }}
  >
    &lt;
  </span>

  <span style={{ fontSize: "1rem", marginTop: "2px" }}>Page {page + 1}</span>

  <span
    onClick={() => setPage(page + 1)}
    style={{
      cursor: "pointer",
      fontSize: "1rem",
      userSelect: "none",
    }}
  >
    &gt;
  </span>
</div>

    </div>
  );
}

export default ViewBookings;
