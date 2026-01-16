
import { useNavigate } from "react-router-dom";

function CarCard({ car }) {
  const navigate = useNavigate();

  return (
    <div className="col-md-4 mb-4">
      <div className="card car-card shadow-sm">
        <img
          className="card-img-top"
          src={`/images/${car.pic}`}
          alt={`${car.brand} ${car.model}`}
          style={{ height: "220px", objectFit: "cover", padding: "20px" }}
        />
        <div className="card-body">
          <h5 className="card-title text-fanta">
            {car.brand} {car.model} ({car.year})
          </h5>
          <p className="card-text">
            <strong>Color:</strong> {car.colour} <br />
            <strong>Fuel:</strong> {car.fuelType} <br />
            <strong>Seats:</strong> {car.seats} <br />
            <strong>Status:</strong>{" "}
            <span
              className={
                (car.availabilityStatus || "").toLowerCase() === "available"
                  ? "text-success"
                  : "text-danger"
              }
            >
              {car.availabilityStatus}
            </span>
            <br />
            <strong>Price/Day:</strong> ₹{car.pricePerDay}
          </p>

          <div className="d-flex gap-2">
            <button
              className="btn btn-outline-warning flex-grow-1"
              onClick={() => navigate(`/customer/car-reviews/${car.id}`)}
            >
              Reviews
            </button>

            <button
              className="btn btn-fanta flex-grow-1"
              disabled={(car.availabilityStatus || "").toLowerCase() !== "available"}
              onClick={() => navigate(`/customer/book/${car.id}`)}
            >
              Book Now
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CarCard;
