import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { setSelectedDate } from "../redux/selectedDateSlice";
import formatDateToISO from "functions/formatDateToISO";

export default function DayTile({ date, ...props }) {
  const dispatch = useDispatch();
  const status = [
    "border-zinc-800 bg-zinc-900", // 0
    "border-green-700 bg-green-900", // 0~20
    "border-green-600 bg-green-800", // 20~40
    "border-green-500 bg-green-700", // 40~60
    "border-green-400 bg-green-600", // 60~80
    "border-green-300 bg-green-500", // 80~100
    "border-green-200 bg-green-400", // 100
  ];

  const handleDayTileClick = () => {
    const dateString = formatDateToISO(date);
    dispatch(setSelectedDate(dateString));
  };

  return (
    <div
      onClick={handleDayTileClick}
      className="h-[30px] w-[30px] rounded-md border-[2px] border-solid border-green-700 bg-green-900"
    />
  );
}
