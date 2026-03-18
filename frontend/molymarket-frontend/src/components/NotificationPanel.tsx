import React, { useEffect, useState, useCallback } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";
import axios from "axios";

export interface Notification {
  id: number;
  senderId: number;
  reciepientId: number;
  message: string;
  type: string;
  createdAt: string | Date;
  isRead: boolean;
}

const NotificationPanel: React.FC = () => {
  const user = useSelector((state: RootState) => state.user);
  const [isOpen, setIsOpen] = useState(false);

  const [notifications, setNotifications] = useState<Notification[]>([]);

  const fetchNotifications = useCallback(async () => {
    if (!user?.id || !user?.token) return;

    const config = {
      headers: {
        Authorization: `Bearer ${user.token}`,
        "Content-Type": "application/json",
      },
    };

    try {
      const response = await axios.get(
        `${import.meta.env.VITE_NOTIFICATION_SERVICE_URL!}/notif/getNotifications?userId=${
          user?.id
        }`,
        config
      );
      setNotifications(response.data);
      console.log("Notifi", response.data);
    } catch (error) {
      console.error("Error fetching notifications:", error);
    }
  }, [user?.id, user?.token]);

  useEffect(() => {
    fetchNotifications();
  }, [fetchNotifications]);

  return (
    <>
      {/* Notification Button */}
      <button
        onClick={() => setIsOpen(true)}
        className="fixed bottom-6 right-6 z-50 bg-blue-600 text-white p-3 rounded-full shadow-lg hover:bg-blue-700 transition"
      >
        🔔
      </button>

      {/* Side Panel */}
      {isOpen && (
        <div className="fixed top-0 right-0 h-full w-80 bg-white shadow-lg z-40 transition-transform duration-300 ease-in-out">
          <div className="flex justify-between items-center p-4 border-b">
            <h2 className="text-lg font-semibold">Notifications</h2>
            <button
              onClick={() => setIsOpen(false)}
              className="text-gray-500 hover:text-gray-700"
            >
              ✖
            </button>
          </div>
          <ul className="p-4 space-y-3">
            {notifications.map(
              (notification: Notification ) => (
                <div key={notification.id} className="p-4 border-b">
                  <p>
                    <strong>Message:</strong> {notification.message}
                  </p>
                  <p>
                    <strong>Type:</strong> {notification.type}
                  </p>
                  <p>
                    <strong>From:</strong> User ID {notification.senderId}
                  </p>
                  <p>
                    <strong>Received:</strong>{" "}
                    {new Date(notification.createdAt).toLocaleString()}
                  </p>
                </div>
              )
            )}
          </ul>
        </div>
      )}

      {/* Backdrop */}
      {isOpen && (
        <div
          className="fixed inset-0 bg-black bg-opacity-25 z-30"
          onClick={() => setIsOpen(false)}
        />
      )}
    </>
  );
};

export default NotificationPanel;
