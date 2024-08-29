import { BasicButton } from "components/Button/BasicButton";
import { CheckBox } from "components/CheckBox/CheckBox";
import DayLabels from "components/DayLabels";
import WeekTiles from "components/WeekTiles";
import { Heading } from "components/Heading";
import getMondayOfWeekContainingFirstDay from "functions/getMondayOfWeekContainingFirstDay";
import React, { useState } from "react";
import MonthTiles from "components/MonthTiles";
import HabitBox from "components/HabitBox";
import NewHabitModal from "components/Modal/NewHabitModal";
import DeleteHabitModal from "components/Modal/DeleteHabitModal";


const Home = () => {
  const year = new Date().getFullYear();
  const month = new Date().getMonth() + 1;

  const [isNewModalOpen, setIsNewModalOpen] = useState(false);
  const openNewModal = () => setIsNewModalOpen(true);
  const closeNewModal = () => setIsNewModalOpen(false);


  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false); // 모달 상태를 false로 설정
  const openDeleteModal = () => setIsDeleteModalOpen(true);
  const closeDeleteModal = () => setIsDeleteModalOpen(false);


  return (
    <>
      <NewHabitModal isOpen={isNewModalOpen} closeModal={closeNewModal} />
      <DeleteHabitModal isOpen={isDeleteModalOpen} closeModal={closeDeleteModal} />
      <div className="bg-black-900 min-h-screen min-w-[280px] flex flex-col items-center justify-center md:flex-row md:items-center md:justify-center sm:items-start">
        <div className="w-full max-w-[700px] flex w-full flex-col items-center justify-center md:p-10 gap-[20px]">
          <div className="flex items-center justify-start w-full">
            <Heading
              size="headinglg"
              as="h1"
              className="!text-[30px] !text-white-a700"
            >
              {year}년 {month}월의 새싹🔥
            </Heading>
          </div>

          <div className="flex items-start justify-center gap-[20px] w-full sm:flex-col sm:items-center">
            <MonthTiles year={year} month={month} />
            <HabitBox />
          </div>
          <div className="flex w-full justify-end gap-2.5">
            <BasicButton onClick={openNewModal}>추가</BasicButton>
            <BasicButton onClick={openDeleteModal}>삭제</BasicButton>
          </div>
        </div>
      </div>
    </>
  );
};

export default Home;