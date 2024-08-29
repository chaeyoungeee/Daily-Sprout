import React from "react";

export default function DayLabels({ ...props }) {
  const days = ["월", "화", "수", "목", "금", "토", "일"];
  return (
    <div
      {...props}
      className={`${props.className} flex flex-col items-center gap-1`}
    >
      {days.map((day) => (
        <div className="flex items-center justify-start h-[30px] w-[20px] text-gray-500 text-[15px] font-bold">
          {day}
        </div>
      ))}
    </div>
  );
}
