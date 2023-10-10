import  Home  from "./components/Home";

//Import for Traveler
import  AddTraveler  from "./components/AddTraveler";
import  EditTraveler from "./components/EditTraveler";

import TravelersList from "./components/TravelersList"

import  Home  from "./components/Home";
import TravelersList from "./components/TravelersList";
import UsersList from "./components/UsersList";
import AddUser from "./components/AddUser"

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
    path: '/userList',
    element: <UsersList/>
  },
  {
    path: '/addUser',
    element: <AddUser/>
  }
];

export default AppRoutes;
