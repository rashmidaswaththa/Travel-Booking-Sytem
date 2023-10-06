import  AddTraveler  from "./components/AddTraveler";
import  EditTraveler from "./components/EditTraveler";
import  Home  from "./components/Home";
import TravelersList from "./components/TravelersList"

const AppRoutes = [
  {
    index: true,
    element: <Home />
  },
  {
    path: '/addTraveler',
    element: <AddTraveler />
  },
  {
    path: '/editTraveler',
    element: <EditTraveler />
  },
  {
    path: '/travelerList',
    element: <TravelersList />
  }
];

export default AppRoutes;
