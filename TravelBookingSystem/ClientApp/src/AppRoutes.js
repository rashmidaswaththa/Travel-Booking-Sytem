import  Home  from "./components/Home";

//Import for Traveler
import  AddTraveler  from "./components/Traveler/AddTraveler";
import  EditTraveler from "./components/Traveler/EditTraveler";
import TravelersList from "./components/Traveler/TravelersList"

//Import for User
import UsersList from "./components/User/UsersList";
import AddUser from "./components/User/AddUser";
import EditUser from "./components/User/EditUser";

//Imports for Train
import AddTrain from "./components/Train/AddTrain";
import TrainList from "./components/Train/TrainList";
import EditTrain from "./components/Train/EditTrain";

//Imports for Schedule
import AddSchedule from "./components/Schedule/AddSchedule";

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

    path: '/addTrain',
    element: <AddTrain />
  },
  {
    path: '/trainList',
    element: <TrainList />
  },
  {
    path: '/editTrain/:id',
    element: <EditTrain />
  },
  {
    path: '/addSchedule',
    element: <AddSchedule />
  },
  {
    path: '/userList',
    element: <UsersList/>
  },
  {
    path: '/addUser',
    element: <AddUser/>
  },
  {
    path: '/editUser',
    element: <EditUser/>
  }
];

export default AppRoutes;
