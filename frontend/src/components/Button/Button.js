import React from "react";
import PropTypes from "prop-types";

const Button = ({
  children,
  className = "",
  leftIcon,
  rightIcon,
  onClick,
  ...restProps
}) => {
  return (
    <button onClick={onClick} className={`${className} `} {...restProps}>
      {!!leftIcon && leftIcon}
      {children}
      {!!rightIcon && rightIcon}
    </button>
  );
};

Button.propTypes = {
  className: PropTypes.string,
  children: PropTypes.node,
  leftIcon: PropTypes.node,
  rightIcon: PropTypes.node,
};

export default Button;
