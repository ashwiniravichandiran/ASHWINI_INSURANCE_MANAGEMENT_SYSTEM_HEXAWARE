import React from "react";

function Steps() {
  const steps = [
    "Submit Proposal",
    "Officer Review",
    "Get Quote",
    "Make Payment",
  ];

  return (
    <div className="container py-5">
      <h2 className="text-center mb-4">How It Works</h2>

      <div className="row text-center">
        {steps.map((step, index) => (
          <div key={index} className="col-md-3">
            <div className="p-3">
              <h4 className="text-secondary">{index + 1}</h4>
              <p>{step}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Steps;