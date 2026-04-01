import Navbar from "../components/Navbar";
import Hero from '../components/Hero';
import Stats from '../components/Stats';
import Policies from "../components/Policies";
import Steps from "../components/Steps";
import Reviews from "../components/Reviews";
import Footer from "../components/Footer";
function Home(){
    return (
        <> 
        <Navbar/>
        <Hero/>
        <Stats/>
        <Policies/>
        <Steps/>
        <Reviews/>
        <Footer/>
        </>
    );
}

export default Home;