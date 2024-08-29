import Button from "components/Button/Button";
import BasicCheckBox from "components/CheckBox/BasicCheckBox";
import { Heading } from "components/Heading";
import { Input } from "components/Input/Input";
import Modal from "react-modal";
import React from "react";
import { BasicButton } from "components/Button/BasicButton";
import { IoClose } from "react-icons/io5";

export default function NewHabitModal({ isOpen, closeModal }) {
  const days = [
    "월요일",
    "화요일",
    "수요일",
    "목요일",
    "금요일",
    "토요일",
    "일요일",
  ];

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={closeModal}
      appElement={document.getElementById("root")}
      className="w-full flex items-center justify-center h-full"
      overlayClassName="overlay"
    >
      <div className="flex w-[370px] sm:w-[300px] flex-col rounded-[16px] bg-gray-900 px-[25px] py-[25px]">
        <div className="flex flex-col gap-[20px]">
          <div className="flex items-start justify-between">
            <Heading
              size="headinglg"
              as="h1"
              className="!text-[25px] !text-white-a700"
            >
              습관 추가🔥
            </Heading>
            <button onClick={closeModal}>
              <IoClose size="20" color="white" />
            </button>
          </div>
          <div className="flex flex-col items-start gap-3 self-stretch">
            <Heading as="h2">어떤 습관을 추가할까요?</Heading>
            <Input
              name="edittext"
              className="self-stretch flex align-center h-[40px] rounded-lg border-2 border-solid border-gray-900_01 bg-gray-900_01 px-3 text-white"
              style={{ color: "white" }}
            />
          </div>
          <div className="flex flex-col items-start gap-3">
            <Heading as="h3">요일을 선택해주세요.</Heading>
            <div className="flex flex-col items-start gap-[10px] self-stretch">
              {days.map((day) => (
                <BasicCheckBox key={day} label={day} name={day} />
              ))}
            </div>
          </div>
          <Button className="h-[40px] self-stretch rounded-lg bg-green-700 text-center text-[16px] font-semibold text-white-a700 sm:px-5">
            추가
          </Button>
        </div>
      </div>
    </Modal>
  );
}
