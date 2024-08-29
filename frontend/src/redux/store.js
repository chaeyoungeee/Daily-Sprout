import { configureStore } from "@reduxjs/toolkit";
import selectedDateSlice from "./selectedDateSlice";
const store = configureStore({
  reducer: {
    selectedDate: selectedDateSlice,
  },
});

export default store;
