import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import axios from "axios";

function PaymentPage() {
  const { bookingId } = useParams();
  const navigate = useNavigate();
  const [paymentSuccess, setPaymentSuccess] = useState(false);

  const handlePayment = () => {
    axios.post(`http://localhost:8080/api/customer/payment/${bookingId}`, {}, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      }
    })
    .then(() => {
      setPaymentSuccess(true);
    })
    .catch((err) => {
      console.error("Payment failed", err);
      alert("Payment failed. Try again.");
    });
  };

  return (
    <div className="container text-center mt-5">
      {!paymentSuccess ? (
        <>
          <h2 className="mb-4">Confirm Your Payment</h2>
          <button
            onClick={handlePayment}
            style={{
              width: "300px",
              padding: "15px",
              fontSize: "18px",
              backgroundColor: "#ff9800",
              color: "white",
              border: "none",
              borderRadius: "12px",
              fontWeight: "bold",
              boxShadow: "0 4px 8px rgba(0,0,0,0.2)"
            }}
          >
            Pay Now
          </button>
        </>
      ) : (
        <>
          <h1 style={{ color: "#ff9800", marginBottom: "30px" }}>Payment Done Successfully!</h1>
          <button
            onClick={() => navigate("/customer")}
            style={{
              width: "300px",
              padding: "15px",
              fontSize: "18px",
              backgroundColor: "#ff9800",
              color: "white",
              border: "none",
              borderRadius: "12px",
              fontWeight: "bold",
              boxShadow: "0 4px 8px rgba(0,0,0,0.2)"
            }}
          >
            Back to Home
          </button>
        </>
      )}
    </div>
  );
}

export default PaymentPage;
