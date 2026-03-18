import { InputNumber, InputNumberProps } from "antd";
import { useDesignToken } from "../../../DesignToken";

type CustomInputProps = InputNumberProps;

const CustomInputNumber = (props: CustomInputProps) => {
  const token = useDesignToken();
  const { ...restProps } = props;

  const componentStyles = {
    background: token.colorBgWhite,
    ...props.style,
  };

  return <InputNumber style={componentStyles} {...restProps} />;
};

export default CustomInputNumber;
