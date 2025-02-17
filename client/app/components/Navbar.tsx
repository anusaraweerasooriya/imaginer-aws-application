"use client";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { useAuth } from "../context/AuthContext";

export default function Navbar() {
  const pathname = usePathname();
  const { user, logout } = useAuth();

  return (
    <nav className="flex justify-between items-center bg-blue-600 p-4 text-white shadow-lg">
      <Link href="/" className="text-xl font-bold">
        Image Gallery
      </Link>
      <div className="flex space-x-4">
        <Link href="/" className={pathname === "/" ? "underline" : ""}>Home</Link>
        {user ? (
          <>
            <Link href="/profile" className={pathname === "/profile" ? "underline" : ""}>Profile</Link>
            <button onClick={logout} className="bg-red-500 px-3 py-1 rounded">Logout</button>
          </>
        ) : (
          <>
            <Link href="/auth/login" className={pathname === "/auth/login" ? "underline" : ""}>Login</Link>
            <Link href="/auth/register" className={pathname === "/auth/register" ? "underline" : ""}>Register</Link>
          </>
        )}
      </div>
    </nav>
  );
}
