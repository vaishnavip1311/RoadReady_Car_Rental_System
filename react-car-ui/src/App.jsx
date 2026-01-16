
import { BrowserRouter, Routes ,Route} from "react-router-dom";
import HomePage from "./components/HomePage";
import Signup from "./components/Signup";
import Login from "./components/Login";
import ManagerDashboard from "./components/manager/ManagerDashboard";
import Cars from "./components/manager/Cars";
import CarOwners from "./components/manager/CarOwners";
import Customers from "./components/manager/Customers";
import Maintenance from "./components/manager/Maintenance";
import Returns from "./components/manager/Returns";
import Profile from "./components/manager/Profile";
import Bookings from "./components/manager/Bookings";
import Stats from "./components/manager/Stats";
import CarDetailsEdit from "./components/manager/CarDetailsEdit";
import CustomerDetails from "./components/manager/CustomerDetails";
import OwnerCars from "./components/manager/OwnerCars";
import EditProfile from "./components/manager/EditProfile";
import MakePayments from "./components/manager/MakePayments";
import AddReturnLog from "./components/manager/AddReturnLog";
import AddMaintenance from "./components/manager/AddMaintenance";


import CustomerHome from "./components/customer/CustomerHome";
import AvailableCars from "./components/customer/AvailableCars";
import CustomerDashboard from "./components/customer/CustomerDashboard";
import CustomerBookings from "./components/customer/CustomerBookings";
import CustomerProfile from "./components/customer/CustomerProfile";
import EditCustomerProfile from "./components/customer/EditCustomerProfile";
import BookCarForm from "./components/customer/BookCarForm";
import BookingSummary from "./components/customer/BookingSummary";
import PaymentPage from "./components/customer/PaymentPage";
import Review from "./components/customer/Review";
import AddReview from "./components/customer/AddReview";
import UpdateBooking from "./components/customer/UpdateBooking";


import AddCar from "./components/carowner/AddCar";
import Reviews from "./components/carowner/Reviews";
import OwnerEditProfile from "./components/carowner/OwnerEditProfile";
import OwnerProfile from "./components/carowner/OwnerProfile";
import CarOwnerHome from "./components/carowner/CarOwnerHome";
import MyCars from "./components/carowner/MyCars";
import EditCarDetails from "./components/carowner/EditCarDetails";
import ViewBookings from "./components/carowner/ViewBookings";
import CarBookings from "./components/carowner/CarBookings";
import CarOwnerDashboard from "./components/carowner/CarOwnerDashboard";
function App() {
  

  return (
    <BrowserRouter>
    <Routes>
       <Route path="/" element={<HomePage />} />
       <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/manager" element={<ManagerDashboard />}>
          <Route index element={<Stats />} />
          <Route path="cars" element={<Cars />} />
          <Route path="car-details/:id" element={<CarDetailsEdit />} />
          <Route path="customers" element={<Customers />} />
          <Route path="customer-details/:customerId" element={<CustomerDetails />} />
          <Route path="carowners" element={<CarOwners />} />
          <Route path="owner-cars/:ownerId" element={<OwnerCars />} />
          <Route path="bookings" element={<Bookings />} />
          <Route path="payments" element={<MakePayments/>} />
          <Route path="returns" element={<Returns />} />
          <Route path="add-return-log" element={<AddReturnLog />} />
          <Route path="maintenance" element={<Maintenance />} />
          <Route path="add-maintenance" element={<AddMaintenance />} />
          <Route path="profile" element={<Profile />} />
          <Route path="edit-profile" element={<EditProfile />} />

        </Route>
        
         <Route path="/customer" element={<CustomerDashboard />}>
           <Route index element={<CustomerHome/>} />
   <Route path="book/:carId" element={<BookCarForm />} />
    <Route path="bookings" element={<CustomerBookings />} />
    <Route path="booking-summary/:bookingId" element={<BookingSummary />} />
    <Route path="update-booking/:bookingId" element={<UpdateBooking/>} />
    <Route path="cars" element={<AvailableCars />} />
    <Route path="payment/:bookingId" element={<PaymentPage />} />
<Route path="car-reviews/:carId" element={<Review />} />
         <Route path="car-review/add/:carId" element={<AddReview />}/>
 <Route path="edit-profile" element={<EditCustomerProfile />} />
    <Route path="profile" element={<CustomerProfile />} />
    </Route>


    <Route path="/carowner" element={<CarOwnerDashboard />}>
      <Route index element={<CarOwnerHome />} /> 
      <Route path="cars" element={<MyCars />} />
      <Route path="add-car" element={<AddCar />} />
      <Route path="car-details/:carId" element={<EditCarDetails />} />
        <Route path="car-reviews/:carId" element={<Reviews />} />
      <Route path="bookings" element={<ViewBookings />} />
      <Route path="view-bookings/:carId" element={<CarBookings />} />
      <Route path="profile" element={<OwnerProfile />} />
       <Route path="edit-profile" element={<OwnerEditProfile />} />
    </Route>   
    </Routes>
    </BrowserRouter>
  )
}

export default App
