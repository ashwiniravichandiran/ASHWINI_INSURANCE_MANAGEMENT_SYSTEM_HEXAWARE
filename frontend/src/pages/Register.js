import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  registerStart,
  registerFailure,
  loginSuccess
} from "../redux/authSlice";
import AuthService from "../services/auth_service";
import { useNavigate } from "react-router-dom";
import bgImage from "../assets/blackcar.jpg";

function Register() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { loading, error } = useSelector((state) => state.auth);

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    address: "",
    aadhar: "",
    pan: "",
    dob: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

const handleSubmit = async (e) => {
  e.preventDefault();

  try {
    dispatch(registerStart());

    const response = await AuthService.register(formData);
    const data = response.data;

    console.log(data);

    dispatch(
      loginSuccess({
        user: {
          email: formData.email,
          role: "USER", 
        },
        token: data.token,
      })
    );

    alert("Registration Successful ✅");

    navigate("/dashboard");

  } catch (err) {
    dispatch(registerFailure(err.response?.data || "Error"));
  }
};


return (
  <div
    style={{
      minHeight: "100vh",
      backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.4), rgba(28, 16, 16, 0.4)), url(${bgImage})`,
      backgroundSize: "cover",
      backgroundPosition: "center",
      backgroundRepeat: "no-repeat",
    }}
  >
    <div className="container pt-5">
      
      <h2 className="text-center mb-4 text-white">Register</h2>

      <p className="text-center text-light mb-1">
      Secure your journey with reliable vehicle insurance
    </p>
    <p className="text-center text-info mb-4">
      Fast registration, instant protection, complete peace of mind
    </p>

      <form onSubmit={handleSubmit} className="card p-4 shadow w-50 mx-auto">

        <input className="form-control mb-2" name="name" placeholder="Name" onChange={handleChange} required />
        <input className="form-control mb-2" name="email" type="email" placeholder="Email" onChange={handleChange} required />
        <input className="form-control mb-2" name="password" type="password" placeholder="Password" onChange={handleChange} required />
        <input className="form-control mb-2" name="address" placeholder="Address" onChange={handleChange} />
        <input className="form-control mb-2" name="aadhar" placeholder="Aadhar Number" onChange={handleChange} />
        <input className="form-control mb-2" name="pan" placeholder="PAN Number" onChange={handleChange} />
        <input className="form-control mb-3" name="dob" type="date" onChange={handleChange} />

        <button className="btn btn-dark w-100" disabled={loading}>
          {loading ? "Registering..." : "Register"}
        </button>

        {error && <p className="text-danger mt-2">{error}</p>}
      </form>

    </div>
  </div>
);


}

export default Register;