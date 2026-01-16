// import axios from "axios";
// import { useEffect, useState } from "react";

// function CarsList() {
//   const [cars, setCars] = useState([]);

//   useEffect(() => {
//     const getAllCars = async () => {
//       const response = await axios.get("http://localhost:8080/api/car/all");
//       setCars(response.data);
//     };
//     getAllCars();
//   }, []);

//   return (
//     <div className="container py-5">
//       <div className="text-center mb-5">
//         <h2 className="fw-bold text-primary">Available Cars</h2>
//         <hr className="w-25 mx-auto border-3 border-primary" />
//       </div>

//       <div className="row g-4">
//         {cars.map((c, index) => (
//           <div className="col-md-4" key={index}>
//             <div className="card shadow-sm border-0 rounded-4 h-100">
//           <div className="card-body">
//                 <h5 className="card-title fw-bold text-secondary">
//                   {c.brand} {c.model} ({c.year})
//                 </h5>
//           <ul className="list-unstyled mb-3">
//                   <li><strong>Color:</strong> {c.colour}</li>
//                   <li><strong>Fuel:</strong> {c.fuelType}</li>
//           <li><strong>Seats:</strong> {c.seats}</li>
//                   <li><strong>Status:</strong> {c.availabilityStatus}</li>
//                   <li><strong>Price/Day:</strong> ₹{c.pricePerDay}</li>
//                 </ul>
//            <div className="d-flex justify-content-between">
//                   <a href="#" className="btn btn-outline-primary btn-sm">View Details</a>
//                <a href="#" className="btn btn-primary">Book Now</a>
//                 </div>
//          </div>
//             </div>
//                     </div>
//         ))}
//       </div>
//     </div>
//   );
// }

// export default CarsList;
