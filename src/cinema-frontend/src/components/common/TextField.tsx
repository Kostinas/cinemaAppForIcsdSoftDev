import React from "react";

interface TextFieldProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  name: string;
}

const TextField: React.FC<TextFieldProps> = ({ label, name, ...rest }) => {
  return (
    <div className="form-field">
      <label htmlFor={name} className="form-label">
        {label}
      </label>
      <input id={name} name={name} className="form-input" {...rest} />
    </div>
  );
};

export default TextField;
