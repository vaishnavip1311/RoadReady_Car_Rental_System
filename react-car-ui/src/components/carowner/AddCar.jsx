import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";

function AddCar() {
  const navigate = useNavigate();

  const [car, setCar] = useState({
    brand: "",
    model: "",
    year: "",
    colour: "",
    fuelType: "",
    seats: "",
    pricePerDay: "",
    availabilityStatus: "AVAILABLE",
    carOwner: {
      id: ""
    }
  });

  const [msg, setMsg] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "carOwnerId") {
      setCar((prev) => ({
        ...prev,
        carOwner: { id: parseInt(value) }
      }));
    } else {
      setCar((prev) => ({
        ...prev,
        [name]: value
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/car/add", car, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });

      setMsg("Car added successfully!");
     
    } catch (error) {
      console.error("Error adding car:", error);
      setMsg("Failed to add car. Try again.");
    }
  };

  return (
    <div className="container mt-4">
      <nav aria-label="breadcrumb">
        <ol className="breadcrumb fanta-breadcrumb">
          <li className="breadcrumb-item"><Link to="/carowner">Car Owner Dashboard</Link></li>
          <li className="breadcrumb-item"><Link to="/carowner/cars">Cars</Link></li>
          <li className="breadcrumb-item active" aria-current="page">Add Car</li>
        </ol>
      </nav>

      <h2 className="text-center text-fanta mb-4">Add New Car</h2>
      {msg && <div className="alert alert-info">{msg}</div>}

      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Brand</label>
          <input type="text" name="brand" value={car.brand} onChange={handleChange} className="form-control" required />
        </div>

        <div className="mb-3">
          <label className="form-label">Model</label>
          <input type="text" name="model" value={car.model} onChange={handleChange} className="form-control" required />
        </div>

        <div className="mb-3">
          <label className="form-label">Year</label>
          <input type="number" name="year" value={car.year} onChange={handleChange} className="form-control" required />
        </div>

        <div className="mb-3">
          <label className="form-label">Color</label>
          <input type="text" name="colour" value={car.colour} onChange={handleChange} className="form-control" required />
        </div>

        <div className="mb-3">
          <label className="form-label">Fuel Type</label>
          <input type="text" name="fuelType" value={car.fuelType} onChange={handleChange} className="form-control" required />
        </div>

        <div className="mb-3">
          <label className="form-label">Seats</label>
          <input type="number" name="seats" value={car.seats} onChange={handleChange} className="form-control" required />
        </div>

        <div className="mb-3">
          <label className="form-label">Price Per Day (₹)</label>
          <input type="number" name="pricePerDay" value={car.pricePerDay} onChange={handleChange} className="form-control" required />
        </div>

        <div className="mb-3">
          <label className="form-label">Availability Status</label>
          <select name="availabilityStatus" value={car.availabilityStatus} onChange={handleChange} className="form-control" required>
            <option value="">Select</option>
            <option value="AVAILABLE">Available</option>
            <option value="NOT AVAILABLE">Not Available</option>
          </select>
        </div>

        <div className="mb-3">
          <label className="form-label">Car Owner ID</label>
          <input
            type="number"
            name="carOwnerId"
            value={car.carOwner?.id || ""}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>

        <div className="text-center">
          <button type="submit" className="btn btn-fanta px-4">Add Car</button>
        </div>
      </form>
    </div>
  );
}

export default AddCar;
