import carImage from "../assets/Car.jpg";
import { ArrowRight, ShieldCheck, Clock, HeartHandshake } from "lucide-react";
import { Link } from "react-router-dom";

export function Hero() {
  return (
    <div className="vh-100 d-flex justify-content-center align-items-center">
    <section style={{
            borderRadius: "12px",
            padding: "180px 250px",
            backgroundImage: `linear-gradient(rgba(29, 24, 24, 0.6), rgba(0, 0, 0, 0.6)), url(${carImage})`,
            backgroundSize: "cover",
            backgroundPosition: "center"
          }}>
      <div className="container-fluid text-center px-5">

        <div className="d-inline-flex align-items-center gap-2 px-3 py-1 rounded-pill bg-light bg-opacity-10 text-light mb-3">
          <ShieldCheck size={16} className="text-primary"/>
          Trusted by 12,000+ vehicle owners
        </div>

        <h1 className="text-light fw-bold display-5 mb-3">
          Secure Your Vehicle Insurance Easily
        </h1>

        <p className="text-light mx-auto mb-4" style={{ maxWidth: "600px" }}>
          Compare policies, get instant quotes, and manage your coverage — all in one place.
          Simple, transparent, and built for peace of mind.
        </p>

        <div className="d-flex flex-column flex-sm-row justify-content-center gap-3 mb-4">
          <Link to="/login" className="btn btn-light d-flex align-items-center gap-2">
            Get a Quote
            <ArrowRight size={16} />
          </Link>

          <Link to="/#policies" className="btn btn-outline-light">
            View Policies
          </Link>
        </div>

        <div className="d-flex flex-wrap justify-content-center gap-4">
          <div className="d-flex align-items-center gap-2 text-light small">
            <ShieldCheck size={16} className="text-primary" />
            100% Secure
          </div>

          <div className="d-flex align-items-center gap-2 text-light small">
            <Clock size={16} className="text-primary" />
            Instant Quotes
          </div>

          <div className="d-flex align-items-center gap-2 text-light small">
            <HeartHandshake size={16} className="text-primary" />
            24/7 Support
          </div>
        </div>

      </div>
    </section>
    </div>
  );
}

export default Hero;