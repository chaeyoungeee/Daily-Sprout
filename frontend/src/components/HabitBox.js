
import React from "react";
import { Heading } from "./Heading";
import { useSelector } from "react-redux";
import BasicCheckBox from "./CheckBox/BasicCheckBox";
import HabitProgressBar from "./HabitProgressBar";

export default function HabitBox({ ...props }) {   
    const selectedDate = useSelector(
        (state) => state.selectedDate.selectedDate
    );

    const habits = ["물 마시기", "아침 먹기", "운동 30분"]
    return (
      <div className="flex flex-1 flex-col gap-4 rounded-[10px] bg-gray-900 p-3 sm:self-stretch">
        <div className="flex flex-col gap-1.5">
          <div className="flex flex-col items-start gap-0.5">
            <Heading
              size="headingmd"
              as="h3"
              className="!text-[22.24px] !text-white-a700"
            >
              {new Date(selectedDate).getMonth() + 1}/
              {new Date(selectedDate).getDate()}
            </Heading>
            <Heading size="headingxs" as="h2" className="!text-[12.35px]">
              습관을 얼마나 달성하셨나요?
            </Heading>
          </div>
          <HabitProgressBar percent={30} />
        </div>
        <div className="flex flex-col items-start gap-2">
          {habits.map((habit) => (
            <BasicCheckBox name={habit} label={habit} />
          ))}
        </div>
      </div>
    );
}
