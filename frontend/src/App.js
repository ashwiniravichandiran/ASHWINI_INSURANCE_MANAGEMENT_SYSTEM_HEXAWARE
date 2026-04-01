import Home from '../src/pages/Home'
import Login from './pages/Login';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Register from './pages/Register';
import DashBoard from './pages/DashBoard';
import OfficerDashboard from './pages/OfficerDashBoard';
import Payment from './pages/Payment';
import ProposalForm from './pages/ProposalForm';
import UserClaim from './pages/UserClaim';
import AdminClaim from './pages/AdminClaim';
import ClaimTracking from './pages/ClaimTracking';
import Policy from './pages/Policy';
import UserDashboard from './pages/UserDashboard';
import { useSelector } from 'react-redux';
import Invoice from './pages/Invoice';

function App() {
const user = useSelector((state) => state.auth.user);

  return (
    <BrowserRouter>
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register/>}/>
       <Route path="/dashboard" element={<DashBoard />}>
  
          {/* default dashboard */}
          <Route
            index
            element={
              user?.role === "ADMIN"
                ? <OfficerDashboard />
                : <UserDashboard />
            }
          />

          <Route path="policy" element={<Policy />} />
          <Route path="proposalform" element={<ProposalForm />} />
          <Route path="claimtracking" element={<ClaimTracking/>}/>
          <Route path="AdminClaim" element={<AdminClaim/>}/>

        </Route>
      
      <Route path="/officerdashboard" element={<OfficerDashboard/>}/>
      <Route path="/payment" element={<Payment/>}/>
      <Route path='/proposalform' element={<ProposalForm/>}/>
      <Route path='/userclaim' element={<UserClaim/>}/>
      <Route path='/policy' element={<Policy/>}/>
      <Route path='*' element={<Home/>}/>
      <Route path="/invoice/:proposalId" element={<Invoice />} />
      <Route path="/payment/:proposalId" element={<Payment />} />
      <Route path="/claim/:userPolicyId" element={<UserClaim />} />
    </Routes>
    </BrowserRouter>
  );
}

export default App;
