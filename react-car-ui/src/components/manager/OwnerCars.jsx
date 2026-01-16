import axios from "axios";
import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

function OwnerCars() {
  const { ownerId } = useParams();
  const [cars, setCars] = useState([]);

  useEffect(() => {
    const fetchCars = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/car/by-owner/${ownerId}`,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        );
        setCars(response.data);
      } catch (error) {
        console.error("Error fetching cars:", error);
      }
    };

    fetchCars();
  }, [ownerId]);

  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-lg-12">
          <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item">
                <Link to="/manager">Manager Dashboard</Link>
              </li>
              <li className="breadcrumb-item">
                <Link to="/manager/carowners">Car Owners</Link>
              </li>
              <li className="breadcrumb-item active" aria-current="page">
                Owner's Cars
              </li>
            </ol>
          </nav>
        </div>
      </div>

      <h3 className="text-center fanta-heading mt-2">Cars of Owner ID: {ownerId}</h3>

      <div className="row mt-4">
        {cars.length === 0 ? (
          <div className="alert alert-warning text-center">No cars found for this owner.</div>
        ) : (
            cars.map((car, index) => (
        <div className="col-md-4 mb-4" key={index}>
            <div className="card car-card shadow-sm">
                    <img
            className="card-img-top"
            src={`/images/${car.pic}`}  
            alt={`${car.brand} ${car.model}`}
            style={{ height: "220px", objectFit: "cover", padding: "20px" }}
            />


            <div className="card-body">
                <h5 className="card-title text-fanta">
                {car.brand} {car.model}
                </h5>
                <p className="card-text">
                <strong>Fuel Type:</strong> {car.fuelType} <br />
                <strong>Seating Capacity:</strong> {car.seats} <br />
                <strong>Price/Day:</strong> ₹{car.pricePerDay} <br />
                <strong>Available:</strong>{" "}
                {car.availabilityStatus?.toUpperCase() === "AVAILABLE" ? "Yes" : "No"}
                </p>
            </div>
            </div>
        </div>
        ))

        )}
      </div>
    </div>
  );
}

export default OwnerCars;
