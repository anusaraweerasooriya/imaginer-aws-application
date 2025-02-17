/* eslint-disable @typescript-eslint/no-unused-vars */
"use client";
import { createContext, useContext, useEffect, useState } from "react";
import { loginUser, registerUser } from "@/utils/api";
import { useRouter } from "next/navigation";

interface AuthContextType {
  user: string | null;
  login: (username: string, password: string) => Promise<void>;
  register: (username: string, password: string) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    const token = localStorage.getItem("token");
    if (storedUser && token) {
      setUser(storedUser);
    }
  }, []);

  const login = async (username: string, password: string) => {
    try {
      const data = await loginUser(username, password);
      console.log("Login response:", data); // Debug token
      localStorage.setItem("token", data.message); // Save token
      localStorage.setItem("user", username);
      setUser(username);
      router.push("/profile");
    } catch (error) {
      alert("Login failed");
    }
  };

  const register = async (username: string, password: string) => {
    try {
      await registerUser(username, password);
      alert("Registration successful! Please login.");
      router.push("/auth/login");
    } catch (error) {
      alert("Registration failed");
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUser(null);
    router.push("/");
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
