import { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { RootState } from "../../redux/store";
import { logout, updateBalance } from "../../redux/userSlice";
import { BUYER } from "../../constants/constants";
import {
  Avatar,
  Divider,
  Flex,
  Layout,
  Space,
  Typography,
  Row,
  Col,
  Statistic,
  Modal,
  message,
  InputNumber,
} from "antd";
import {
  DollarOutlined,
  LogoutOutlined,
  EditOutlined,
  UserOutlined,
  ShoppingOutlined,
  ShopOutlined,
  PlusOutlined,
  BankOutlined,
  CreditCardOutlined,
} from "@ant-design/icons";
import { Content } from "antd/es/layout/layout";
import { useDesignToken } from "../../DesignToken";
import CustomButton from "../../components/custom/CustomButton/CustomButton";
import CustomCard from "../../components/custom/CustomCard/CustomCard";

function Profile() {
  const user = useSelector((state: RootState) => state.user);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const token = useDesignToken();

  const [isTopUpModalVisible, setIsTopUpModalVisible] = useState(false);
  const [topUpAmount, setTopUpAmount] = useState<number>(100);

  const onLogout = () => {
    dispatch(logout(user));
    navigate("/");
  };

  const onEditProfile = () => {
    navigate("/editProfile");
  };

  const onViewOrders = () => {
    navigate("/orders");
  };

  const onViewProducts = () => {
    if (user.role !== BUYER) {
      navigate("/sellerProducts");
    }
  };

  const showTopUpModal = () => {
    setIsTopUpModalVisible(true);
  };

  const handleTopUp = async (method: string) => {
    if (!user.id || user.id === -1) {
      message.error("User not found!");
      return;
    }

    if (!topUpAmount || topUpAmount <= 0) {
      message.error("Please enter a valid amount!");
      return;
    }

    try {
      const response = await axios.post(
        `${
          import.meta.env.VITE_UAM_SERVICE_URL!
        }/api/uam/auth/updateBalanceById`,
        null,
        {
          params: {
            userId: user.id,
            amount: topUpAmount,
            operation: "ADD",
          },
        }
      );

      if (response.status === 200) {
        const newBalance = (user.balance || 0) + topUpAmount;
        dispatch(updateBalance(newBalance));
        message.success(
          `Successfully topped up $${topUpAmount.toFixed(2)} via ${method}!`
        );
        setIsTopUpModalVisible(false);
      }
    } catch (error) {
      console.error("Top-up failed:", error);
      message.error("Failed to top up. Please try again later.");
    }
  };

  const { Title, Text, Paragraph } = Typography;

  // Generate user initials for avatar
  const getInitials = () => {
    return user.username ? user.username.charAt(0).toUpperCase() : "U";
  };

  // Convert role to readable format with icon
  const getRoleDisplay = () => {
    if (user.role === BUYER) {
      return (
        <Space>
          <ShoppingOutlined style={{ color: token.colorPrimary }} />
          <Text>Buyer</Text>
        </Space>
      );
    }
    return (
      <Space>
        <ShopOutlined style={{ color: token.colorOrange }} />
        <Text>Seller</Text>
      </Space>
    );
  };

  return (
    <Layout style={{ minHeight: "100vh", background: token.colorBgBase }}>
      <Content
        style={{
          padding: "40px 24px",
          maxWidth: 1200,
          margin: "0 auto",
          width: "100%",
        }}
      >
        <Row gutter={[24, 24]} justify="center">
          <Col xs={24} md={22} lg={20} xl={18}>
            <CustomCard>
              {/* Header Section */}
              <Flex
                align="center"
                justify="space-between"
                style={{ marginBottom: 24 }}
              >
                <Title level={2} style={{ margin: 0 }}>
                  My Profile
                </Title>
                <Flex gap={12}>
                  <CustomButton
                    danger
                    onClick={onLogout}
                    icon={<LogoutOutlined />}
                  >
                    Log Out
                  </CustomButton>
                  <CustomButton
                    type="primary"
                    onClick={onEditProfile}
                    icon={<EditOutlined />}
                    style={{ background: token.colorPrimary }}
                  >
                    Edit Profile
                  </CustomButton>
                </Flex>
              </Flex>

              <Divider style={{ margin: "12px 0 32px" }} />

              {/* User Info Section */}
              <Row gutter={[32, 32]}>
                {/* Left Column - Avatar and Basic Info */}
                <Col xs={24} md={8}>
                  <Flex vertical align="center" gap={16}>
                    <Avatar
                      size={120}
                      style={{
                        backgroundColor: token.colorPrimary,
                        fontSize: 48,
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                      }}
                    >
                      {getInitials()}
                    </Avatar>

                    <div style={{ textAlign: "center" }}>
                      <Title level={3} style={{ marginBottom: 4 }}>
                        {user.username}
                      </Title>
                      {getRoleDisplay()}
                    </div>

                    {/* Stats */}
                    <CustomCard
                      style={{
                        marginTop: 16,
                      }}
                    >
                      <Statistic
                        title="Balance"
                        value={user.balance}
                        precision={2}
                        prefix={<DollarOutlined />}
                        suffix="SGD"
                        style={{ textAlign: "center" }}
                        valueStyle={{ color: token.colorPrimary }}
                      />
                      {user.role === BUYER && (
                        <CustomButton
                          type="primary"
                          icon={<PlusOutlined />}
                          onClick={showTopUpModal}
                          style={{
                            width: "100%",
                            marginTop: 12,
                            background: token.colorPrimary,
                          }}
                        >
                          Top Up
                        </CustomButton>
                      )}
                    </CustomCard>

                    {/* Action Buttons */}
                    <Flex
                      vertical
                      style={{ width: "100%", gap: 12, marginTop: 8 }}
                    >
                      <CustomButton
                        icon={<ShoppingOutlined />}
                        onClick={onViewOrders}
                      >
                        My Orders
                      </CustomButton>

                      {user.role !== BUYER && (
                        <CustomButton
                          icon={<ShopOutlined />}
                          onClick={onViewProducts}
                        >
                          My Products
                        </CustomButton>
                      )}
                    </Flex>
                  </Flex>
                </Col>

                {/* Right Column - Details */}
                <Col xs={24} md={16}>
                  <CustomCard
                    title={
                      <Flex align="center" gap={8}>
                        <UserOutlined />
                        <span>Account Details</span>
                      </Flex>
                    }
                    style={{
                      borderRadius: token.borderRadiusMed,
                      background: token.colorBgWhite,
                    }}
                  >
                    <CustomCard
                      title="Username"
                      style={{
                        borderRadius: token.borderRadiusSmall,
                      }}
                      size="small"
                    >
                      <Paragraph>
                        {user.username || "No shipping address provided."}
                      </Paragraph>
                    </CustomCard>
                    <CustomCard
                      title="Role"
                      style={{
                        marginTop: 24,
                        borderRadius: token.borderRadiusSmall,
                      }}
                      size="small"
                    >
                      <Paragraph>
                        {user.role === BUYER ? "Buyer" : "Seller"}
                      </Paragraph>
                    </CustomCard>{" "}
                    {user.role === BUYER ? (
                      <CustomCard
                        title="Shipping Information"
                        style={{
                          marginTop: 24,
                          borderRadius: token.borderRadiusSmall,
                        }}
                        size="small"
                      >
                        <Paragraph>
                          {user.address || "No shipping address provided."}
                        </Paragraph>
                      </CustomCard>
                    ) : (
                      <CustomCard
                        title="Seller Information"
                        style={{
                          marginTop: 24,
                          borderRadius: token.borderRadiusSmall,
                        }}
                        size="small"
                      >
                        <Paragraph>
                          <strong>UEN:</strong> {user.uen || "Not provided"}
                        </Paragraph>
                      </CustomCard>
                    )}
                  </CustomCard>

                  {/* Account Privacy & Security */}
                  <CustomCard
                    title="Account Security"
                    style={{
                      marginTop: 24,
                      borderRadius: token.borderRadiusMed,
                    }}
                    extra={
                      <CustomButton type="link" onClick={onEditProfile}>
                        Update
                      </CustomButton>
                    }
                  >
                    <Flex align="center" justify="space-between">
                      <Text>Password</Text>
                      <Text type="secondary">••••••••</Text>
                    </Flex>
                  </CustomCard>
                </Col>
              </Row>
            </CustomCard>
          </Col>
        </Row>
      </Content>

      <Modal
        title="Top Up Balance"
        open={isTopUpModalVisible}
        onCancel={() => setIsTopUpModalVisible(false)}
        footer={null}
        centered
      >
        <Paragraph>
          Enter the amount you would like to top up and select your preferred
          method.
        </Paragraph>

        <InputNumber
          min={1}
          max={10000}
          value={topUpAmount}
          style={{ width: "100%", marginBottom: 20 }}
          formatter={(value) =>
            value ? `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ",") : ""
          }
          parser={(value) => Number(value!.replace(/\$\s?|(,*)/g, ""))}
          onChange={(value) => setTopUpAmount(value || 0)}
          size="large"
        />

        <Flex vertical gap={12}>
          <CustomButton
            icon={<BankOutlined />}
            size="large"
            onClick={() => handleTopUp("Bank Transfer")}
            style={{
              height: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "flex-start",
              padding: "0 24px",
            }}
          >
            Bank Transfer
          </CustomButton>
          <CustomButton
            icon={<CreditCardOutlined />}
            size="large"
            onClick={() => handleTopUp("Credit Card")}
            style={{
              height: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "flex-start",
              padding: "0 24px",
            }}
          >
            Credit Card Top Up
          </CustomButton>
        </Flex>
      </Modal>
    </Layout>
  );
}

export default Profile;
