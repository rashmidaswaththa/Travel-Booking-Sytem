import  AddTraveler  from "./components/AddTraveler";
import  EditTraveler from "./components/EditTraveler";
import  Home  from "./components/Home";

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
  }
];

export default AppRoutes;
