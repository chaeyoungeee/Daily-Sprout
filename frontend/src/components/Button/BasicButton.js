import Button from "./Button";


const BasicButton = ({ onClick, children }) => {
  return (
    <Button onClick={onClick} className="flex py-[8px] px-[10px] flex-row items-center justify-center rounded-md border-[1.5px] border-solid border-green-a700 text-center text-[15px] font-semibold text-white-a700">
      {children}
    </Button>
  );
};

export { BasicButton }