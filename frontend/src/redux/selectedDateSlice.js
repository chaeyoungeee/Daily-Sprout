import { createSlice } from "@reduxjs/toolkit";
import formatDateToISO from "functions/formatDateToISO";

const initialState = {
  selectedDate: formatDateToISO(new Date()),
};

const selectedDateSlice = createSlice({
  name: "selectedDate",
  initialState,
  reducers: {
    setSelectedDate: (state, action) => {
      state.selectedDate = action.payload
    },
  },
});

export const { setSelectedDate } = selectedDateSlice.actions;

export default selectedDateSlice.reducer;
