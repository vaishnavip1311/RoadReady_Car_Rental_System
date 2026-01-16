import { useParams, useNavigate ,Link} from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import "../css/customer/Review.css";

function Review() {
  const { carId } = useParams();
  const navigate = useNavigate();
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [editId, setEditId] = useState(null);
  const [editForm, setEditForm] = useState({ comment: "", rating: 5 });

  useEffect(() => {
    fetchReviews();
  }, [carId]);

  const fetchReviews = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/review/car/${carId}`);
      setReviews(response.data);
    } catch (error) {
      console.error("Failed to fetch reviews", error);
      setReviews([]);
    } finally {
      setLoading(false);
    }
  };

  const handleEditClick = (review) => {
    setEditId(review.id);
    setEditForm({ comment: review.comment, rating: review.rating });
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setEditForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleUpdate = async (id) => {
    try {
      await axios.put(
        `http://localhost:8080/api/review/update/${id}`,
        editForm,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
            "Content-Type": "application/json",
          },
        }
      );
      setEditId(null);
      fetchReviews();
    } catch (err) {
      console.error("Failed to update review", err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/review/delete/${id}`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
      fetchReviews();
    } catch (err) {
      console.error("Failed to delete review", err);
    }
  };

  const handleCancel = () => {
    setEditId(null);
    setEditForm({ comment: "", rating: 5 });
  };

  return (
    <div className="container mt-5">
       <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item">
                <Link to="/customer/cars">Available Cars</Link>
              </li>
              <li className="breadcrumb-item active" aria-current="page">
                Review
              </li>
            </ol>
          </nav>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="text-fanta display-6">Reviews for Car ID {carId}</h2>
        <button
          className="btn btn-fanta"
          onClick={() => navigate(`/customer/car-review/add/${carId}`)}
        >
          Add Review
        </button>
      </div>

      {loading ? (
        <p className="text-center">Loading reviews...</p>
      ) : reviews.length === 0 ? (
        <div className="text-center text-muted">No reviews found.</div>
      ) : (
        <ul className="list-group shadow rounded-4">
          {reviews.map((review) => (
            <li
              key={review.id}
              className="list-group-item mb-3 p-4 rounded-4 shadow-sm bg-light"
            >
              <h5 className="mb-2" style={{ color: "#ff6f00" }}>
                <i className="bi bi-person-circle me-2"></i> {review.customerName}
              </h5>

              {editId === review.id ? (
                <>
                  <div className="mb-2">
                    <label className="form-label">Comment</label>
                    <textarea
                      name="comment"
                      className="form-control"
                      value={editForm.comment}
                      onChange={handleEditChange}
                    />
                  </div>
                  <div className="mb-2">
                    <label className="form-label">Rating</label>
                    <select
                      name="rating"
                      className="form-control"
                      value={editForm.rating}
                      onChange={handleEditChange}
                    >
                      {[1, 2, 3, 4, 5].map((r) => (
                        <option key={r} value={r}>
                          {r}
                        </option>
                      ))}
                    </select>
                  </div>

                  <div className="d-flex justify-content-end gap-2">
                    <button
                      className="btn btn-sm text-white"
                      style={{ backgroundColor: "#ff6f00", border: "none" }}
                      onClick={() => handleUpdate(review.id)}
                    >
                      Save
                    </button>
                    <button
                      className="btn btn-sm text-white"
                      style={{ backgroundColor: "#ff6f00", border: "none" }}
                      onClick={handleCancel}
                    >
                      Cancel
                    </button>
                  </div>
                </>
              ) : (
                <>
                  <p className="fs-5 mb-2">{review.comment}</p>
                  <p className="fs-6 mb-3">
                    <strong>Rating:</strong> ⭐ {review.rating} / 5
                  </p>

                  <div className="d-flex justify-content-end gap-2">
                    <button
                      className="btn btn-sm text-white"
                      style={{ backgroundColor: "#ff6f00", border: "none" }}
                      onClick={() => handleEditClick(review)}
                    >
                      Update
                    </button>
                    <button
                      className="btn btn-sm text-white"
                      style={{ backgroundColor: "#ff6f00", border: "none" }}
                      onClick={() => handleDelete(review.id)}
                    >
                      Delete
                    </button>
                  </div>
                </>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default Review;
