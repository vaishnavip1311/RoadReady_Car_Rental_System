import { useParams, useNavigate,Link } from "react-router-dom";
import { useState } from "react";
import axios from "axios";

function AddReview() {
  const { carId } = useParams();
  const navigate = useNavigate();
  const [comment, setComment] = useState("");
  const [rating, setRating] = useState(5);
  const [msg, setMsg] = useState("");

 const handleSubmit = async (e) => {
  e.preventDefault();
  const token = localStorage.getItem("token");

  try {
    await axios.post(
      `http://localhost:8080/api/review/add/${carId}`, 
      {
        comment,
        rating
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );
    setMsg("Review added successfully!");
   
  } catch (err) {
    console.error("Failed to add review", err);
    setMsg("Failed to submit review.");
  }
};


  return (
    <div className="container mt-5 col-md-6 offset-md-3">
       <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item">
                <Link to="/customer/cars">Available Cars</Link>
              </li>
              <li className="breadcrumb-item">
                <Link to={`/customer/car-reviews/${carId}`}>Reviews</Link>
              </li>
              <li className="breadcrumb-item active" aria-current="page">
               Add Review
              </li>
            </ol>
          </nav>
      <h3 className="text-center text-fanta mb-4">Add Review for Car ID {carId}</h3>

      {msg && (
        <div className={`alert ${msg.includes("success") ? "alert-success" : "alert-danger"}`}>
          {msg}
        </div>
      )}

      <form onSubmit={handleSubmit} className="card p-4 shadow-sm">
        <div className="mb-3">
          <label className="form-label">Comment</label>
          <textarea
            className="form-control"
            rows="3"
            value={comment}
            onChange={(e) => setComment(e.target.value)}
            required
          ></textarea>
        </div>

        <div className="mb-3">
          <label className="form-label">Rating</label>
          <select
            className="form-control"
            value={rating}
            onChange={(e) => setRating(parseInt(e.target.value))}
          >
            {[1, 2, 3, 4, 5].map((r) => (
              <option key={r} value={r}>
                {r}
              </option>
            ))}
          </select>
        </div>

        <button type="submit" className="btn btn-fanta w-100">Submit Review</button>
      </form>
    </div>
  );
}

export default AddReview;
