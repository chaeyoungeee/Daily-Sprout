import React from "react";
import DayTile from "./DayTile";

export default function WeekTiles({ date, ...props }) {
    const generateWeekDates = (startDate, numDays) => {
      return Array.from({ length: numDays }, (_, index) => {
        const newDate = new Date(startDate);
        newDate.setDate(newDate.getDate() + index);
        return newDate;
      });
    };

  const weekDates = generateWeekDates(date, 7);
  
  return (
    <div
      {...props}
      className={`${props.className} flex flex-col items-center gap-1`}
    >
      {weekDates.map((date, index) => (
        <DayTile key={index} date={date} />
      ))}
    </div>
  );
}
