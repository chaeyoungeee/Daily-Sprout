import getMondayOfWeekContainingFirstDay from "functions/getMondayOfWeekContainingFirstDay";
import React from "react";
import DayLabels from "./DayLabels";
import WeekTiles from "./WeekTiles";

export default function MonthTiles({ year, month, ...props }) {
  // 주어진 년도와 월로 월요일 날짜를 구합니다.
  const mondayOfWeek = getMondayOfWeekContainingFirstDay(year, month);
  console.log(mondayOfWeek);

  // 날짜를 배열로 생성합니다. 5일치 날짜를 생성합니다.
  const days = Array.from({ length: 5 }, (_, i) => {
    const date = new Date(mondayOfWeek);
    date.setDate(date.getDate() + i * 7);
    return date;
  });

  return (
    <div className="flex gap-1 md:flex-row">
      <DayLabels className="w-[50%]" />
      {days.map((date, index) => (
        <WeekTiles key={index} date={date} className="w-[50%]" />
      ))}
    </div>
  );
}
