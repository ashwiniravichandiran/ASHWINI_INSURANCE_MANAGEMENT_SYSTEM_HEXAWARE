import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import proposalService from "../services/ProposalService";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

function Invoice() {
  const { proposalId } = useParams();
  const navigate = useNavigate();

  const [invoice, setInvoice] = useState(null);

  useEffect(() => {
    proposalService.getProposalById(proposalId)
      .then(res => {
        console.log(res.data); 
        setInvoice(res.data);
      })
      .catch(err => console.error(err));
  }, [proposalId]);

  const downloadPDF = () => {
    const input = document.getElementById("invoice");

    setTimeout(() => {
      html2canvas(input, { scale: 2, backgroundColor: "#ffffff" })
        .then((canvas) => {
          const imgData = canvas.toDataURL("image/png");

          const pdf = new jsPDF("p", "mm", "a4");

          const imgWidth = 190;
          const imgHeight = (canvas.height * imgWidth) / canvas.width;

          pdf.addImage(imgData, "PNG", 10, 10, imgWidth, imgHeight);
          pdf.save(`Invoice-${proposalId}.pdf`);
        });
    }, 300); 
  };

  if (!invoice) return <p>Loading...</p>;

  return (
    <div className="container my-5">
      
      <div
        id="invoice"
        className="card p-4 shadow-sm"
        style={{ maxWidth: "600px", margin: "auto", background: "#fff" }}
      >
<div style={{ display: "flex", justifyContent: "space-between" }}>
    <div>
      <h2 style={{ margin: 0 }}>InsureDrive Pvt Ltd</h2>
      <p style={{ margin: 0 }}>Chennai, India</p>
      <p style={{ margin: 0 }}>support@insuretech.com</p>
    </div>

    <div style={{ textAlign: "right" }}>
      <h1 style={{ margin: 0 }}>INVOICE</h1>
      
    </div>
  </div>
  <br/>

        <div className="d-flex justify-content-between">
          <div>
            <p><strong>Invoice ID:</strong> INV-{invoice.proposalId}</p>
            <p><strong>Date:</strong> {new Date(invoice.createdDate).toLocaleDateString()}</p>
          </div>

          <div className="text-end">
            <p><strong>Customer:</strong> {invoice.userName}</p>
            <p><strong>Vehicle:</strong> {invoice.vehicleModel || "-"}</p>
          </div>
        </div>

        <hr />

        <table className="table">
          <thead>
            <tr>
              <th>Description</th>
              <th className="text-end">Amount</th>
            </tr>
          </thead>

          <tbody>

  <tr>
    <td>Base Premium</td>
    <td className="text-end">
      ₹ {invoice.basePremium.toFixed(2)}
    </td>
  </tr>

  {invoice.zeroDep && (
    <tr>
      <td>Zero Depreciation</td>
      <td className="text-end">₹ 42.00</td>
    </tr>
  )}

  {invoice.rsa && (
    <tr>
      <td>Roadside Assistance</td>
      <td className="text-end">₹ 18.00</td>
    </tr>
  )}

  {invoice.paCover && (
    <tr>
      <td>Personal Accident Cover</td>
      <td className="text-end">₹ 24.00</td>
    </tr>
  )}

  <tr>
    <td>GST (18%)</td>
    <td className="text-end">
      ₹ {invoice.gstAmount.toFixed(2)}
    </td>
  </tr>

  <tr>
    <td><strong>Total</strong></td>
    <td className="text-end">
      <strong>₹ {invoice.premiumAmount.toFixed(2)}</strong>
    </td>
  </tr>

</tbody>
        </table>

      </div>

      <div className="mt-3 d-flex gap-2 justify-content-center">

        <button className="btn btn-primary" onClick={downloadPDF}>
          Download Invoice
        </button>

        {invoice.status === "QUOTE_GENERATED" && (
          <button
            className="btn btn-dark"
            onClick={() => navigate(`/payment/${proposalId}`)}
          >
            Pay Now
          </button>
        )}

      </div>

    </div>
  );
}

export default Invoice;