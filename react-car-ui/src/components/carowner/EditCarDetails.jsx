import { useEffect, useRef, useState } from "react";
import { useParams, Link } from "react-router-dom";
import axios from "axios";
import "../css/manager/Cars.css";

function EditCarDetails() {
  const { carId } = useParams();
  const fileInputRef = useRef(null);

  const [msg, setMsg] = useState("");
  const [imgMsg, setImgMsg] = useState("");
  const [car, setCar] = useState({
    brand: "",
    model: "",
    year: "",
    colour: "",
    fuelType: "",
    seats: "",
    pricePerDay: "",
    availabilityStatus: ""
  });

  useEffect(() => {
    const fetchCar = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/car/${carId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
          }
        });
        setCar(response.data);
      } catch (err) {
        console.error("Failed to fetch car details", err);
      }
    };
    fetchCar();
  }, [carId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCar((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    e.stopPropagation();

    try {
      await axios.put(`http://localhost:8080/api/car/update/${carId}`, car, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "application/json"
        }
      });
      setMsg("Car details updated successfully!");
    } catch (err) {
      console.error("Update failed", err);
      setMsg("Update failed. Try again.");
    }
  };

  const handleImageUpload = async () => {
    const file = fileInputRef.current.files[0];
    if (!file) {
      setImgMsg("Please select a car image.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      await axios.post(`http://localhost:8080/api/car/upload/car-pic?carId=${carId}`, formData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`
        }
      });

      setImgMsg("Car image uploaded successfully!");
    } catch (err) {
      console.error("Image upload failed", err);
      setImgMsg("Failed to upload car image.");
    }
  };

  return (
    <div className="container mt-4">
      <div className="row">
        <div className="col-lg-12">
          <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item"><Link to="/carowner">Car Owner Dashboard</Link></li>
              <li className="breadcrumb-item"><Link to="/carowner/cars">Cars</Link></li>
              <li className="breadcrumb-item active" aria-current="page">Edit Car</li>
            </ol>
          </nav>
        </div>
      </div>

      <div className="card shadow-sm col-md-8 offset-md-2">
        <div className="card-header text-center text-fanta">Edit Car Details</div>
        <div className="card-body">
          {msg && <div className="alert alert-success">{msg}</div>}

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
              <label className="form-label">Upload Car Image</label>
              <input type="file" ref={fileInputRef} className="form-control" accept="image/*" />
              <button type="button" className="btn btn-fanta mt-2" onClick={handleImageUpload}>
                Upload Image
              </button>
              {imgMsg && <div className="alert alert-info mt-2">{imgMsg}</div>}
            </div>

            <div className="d-grid mt-3">
              <button type="submit" className="btn btn-fanta">Update Car</button>
            </div>
          </form>
        </div>
        <div className="card-footer text-center text-muted">Manager Panel</div>
      </div>
    </div>
  );
}

export default EditCarDetails;
