"use client";
import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import { getUserImages } from "@/utils/api";
import UploadImage from '@/components/UploadImage';

export default function Profile() {
  const { user } = useAuth();
  const [images, setImages] = useState<string[]>([]);

  useEffect(() => {
    fetchImages();
  }, []);

  // Function to fetch images
  const fetchImages = async () => {
    try {
      const data = await getUserImages();
      const imageUrls = data.map((image: { imageUrl: string }) => image.imageUrl);
      setImages(imageUrls);
    } catch {
      alert("Failed to load user images");
    }
  };

  return (
    <div className="container mx-auto p-8 bg-white rounded-lg shadow-lg">
      <h1 className="text-4xl font-extrabold text-gray-900 mb-6 text-center">
        {user}&apos;s Gallery
      </h1>

      <div className="flex justify-center mb-6">
        {/* Pass the fetchImages function to update state after upload */}
        <UploadImage onUploadSuccess={fetchImages} />
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        {images.length === 0 ? (
          <p className="text-gray-500 text-center col-span-full">
            No images uploaded yet. Start by adding some!
          </p>
        ) : (
          images.map((imageUrl, index) => (
            <div
              key={index}
              className="relative overflow-hidden rounded-xl shadow-md transform transition duration-300 hover:scale-105"
            >
              <img
                src={imageUrl}
                alt="Uploaded"
                className="w-full h-56 object-cover rounded-lg"
                onError={(e) => (e.currentTarget.src = "/placeholder.png")}
              />
            </div>
          ))
        )}
      </div>
    </div>
  );
}
