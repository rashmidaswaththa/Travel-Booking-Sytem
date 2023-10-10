import  AddTraveler  from "./components/AddTraveler";
import  EditTraveler from "./components/EditTraveler";
import  Home  from "./components/Home";
import TravelersList from "./components/TravelersList";
import UsersList from "./components/UsersList";
import AddUser from "./components/AddUser"

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
  },
  {
    path: '/userList',
    element: <UsersList/>
  },
  {
    path: '/addUser',
    element: <AddUser/>
  }
];

export default AppRoutes;
