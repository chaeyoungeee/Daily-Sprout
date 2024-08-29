import { CheckBox } from "./CheckBox";

export default function BasicCheckBox({ ...props }) {
    return (
      <CheckBox
        name={props.name}
        label={props.label}
        className="flex gap-2 items-center text-[15px] font-semibold text-white-a700"
      />
    );
}