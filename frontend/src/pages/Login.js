import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "../services/auth_service";
import { useDispatch } from "react-redux";
import { loginSuccess } from "../redux/authSlice";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleLogin = async () => {
    if (!email || !password) {
      alert("Please enter email and password");
      return;
    }

    setLoading(true);

    try {
      const response = await AuthService.login({
        email,
        password,
      });

      const data = response.data;
      console.log(data); 

      localStorage.setItem("token", data.token);

      const tokenFromBackend = data.token;
      const role = data.role;
      
        dispatch(
          loginSuccess({
            user: { email: email , role: role},
            token: tokenFromBackend,
          })
        );

      alert("Login successful ✅");

      console.log("Token:", data.token);
      
        navigate("/dashboard");
      

    } catch (error) {
      console.error(error);

      if (error.response) {
        alert(error.response.data.message || "Invalid login");
      } else {
        alert("Server error");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container-fluid vh-100">
      <div className="row h-100">

        <div className="col-md-6 d-flex justify-content-center align-items-center">
          <div style={{ width: "100%", maxWidth: "380px" }}>

            <h2 className="fw-bold mb-3">Welcome Back</h2>
            <p className="text-muted mb-4">
              Login to manage your vehicle insurance
            </p>

            <input
              type="email"
              className="form-control mb-3 py-2"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />

            <input
              type="password"
              className="form-control mb-3 py-2"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />

            <button
              className="btn btn-dark w-100 mb-3"
              onClick={handleLogin}
              disabled={loading}
            >
              {loading ? "Logging in..." : "Login"}
            </button>

            <p className="text-center">
              Don’t have an account? <a href="/register">Register</a>
            </p>

          </div>
        </div>

        <div
          className="col-md-6 d-none d-md-block"
          style={{
            backgroundImage: `linear-gradient(rgba(29, 24, 24, 0.6), rgba(0, 0, 0, 0.6)), url('https://images.unsplash.com/photo-1550355291-bbee04a92027')`,
            backgroundSize: "cover",
            backgroundPosition: "center"
          }}
        />

      </div>
    </div>
  );
}

export default Login;