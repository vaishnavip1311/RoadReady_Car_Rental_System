import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import "../css/customer/Review.css";

function Review() {
  const { carId } = useParams();
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchReviews() {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/review/car/${carId}`
        );
        setReviews(response.data);
      } catch (error) {
        console.error("Failed to fetch reviews", error);
        setReviews([]);
      } finally {
        setLoading(false);
      }
    }
    fetchReviews();
  }, [carId]);

  if (loading) return <p className="text-center mt-5">Loading reviews...</p>;

  if (reviews.length === 0)
    return (
      <div className="container mt-5 text-center">
        <h4 className="text-muted">No reviews found for Car ID {carId}.</h4>
      </div>
    );

  return (
    <div className="container mt-5">
      <h2 className="text-center text-fanta mb-4 display-5">Reviews for Car ID {carId}</h2>

      <div className="row justify-content-center">
        <div className="col-md-8">
          <ul className="list-group shadow rounded-4">
            {reviews.map((review) => (
              <li key={review.id} className="list-group-item mb-3 p-4 rounded-4 shadow-sm bg-light">
                <h5 className="mb-2" style={{ color: "#ff6f00" }}>
                  <i className="bi bi-person-circle me-2"></i> {review.customerName}
                </h5>
                <p className="fs-5 mb-2">{review.comment}</p>
                <p className="fs-6 mb-0">
                  <strong>Rating:</strong> ⭐ {review.rating} / 5
                </p>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}

export default Review;
