import React from "react";

const sizes = {
  headingxs: "text-[12px] font-semibold",
  headings: "text-[15px] font-bold",
  headingmd: "text-[22px] font-extrabold",
  headinglg: "text-[30px] font-semibold md:text-[28px] sm:text-[26px]",
};

const Heading = ({ children, className = "", size = "headings", as, ...restProps }) => {
  const Component = as || "h6";

  return (
    <Component className={`text-gray-500 ${className} ${sizes[size]}`} {...restProps}>
      {children}
    </Component>
  );
};

export { Heading };
