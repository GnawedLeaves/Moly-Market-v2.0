import React from "react";
import { Tabs, TabsProps } from "antd";
import { useDesignToken } from "../../../DesignToken";

const CustomTabs: React.FC<TabsProps> = ({
  children,
  style,
  ...rest
}) => {
  const token = useDesignToken();

  return (
    <Tabs
      {...rest}
      style={{
        width: "100%",
        paddingBottom: "16px",
        color: token.colorTextBase,
        fontFamily: token.fontFamily,
        borderRadius: token.borderRadiusMed,
        ...style,
      }}
    >
      {children}
    </Tabs>
  );
};

export default CustomTabs;
