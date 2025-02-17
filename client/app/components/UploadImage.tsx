"use client";
import { useState } from "react";
import { uploadImage } from "@/utils/api";
import { UploadCloud } from "lucide-react";

interface UploadImageProps {
  onUploadSuccess: () => void; // Callback function to update images
}

export default function UploadImage({ onUploadSuccess }: UploadImageProps) {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [uploading, setUploading] = useState(false);
  const [message, setMessage] = useState("");

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      setSelectedFile(event.target.files[0]);
    }
  };

  const handleUpload = async () => {
    if (!selectedFile) {
      setMessage("Please select an image.");
      return;
    }

    setUploading(true);
    setMessage("");

    try {
      const response = await uploadImage(selectedFile);
      setMessage("Image uploaded successfully!");
      console.log("Uploaded Image URL:", response);

      // Trigger the callback to fetch updated images
      onUploadSuccess();
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      setMessage("Upload failed. Please try again.");
    } finally {
      setUploading(false);
      setSelectedFile(null);
    }
  };

  return (
    <div className="p-6 border border-gray-200 rounded-lg shadow-lg max-w-md mx-auto bg-white">
      <h2 className="text-2xl font-bold mb-4 text-gray-800 flex items-center gap-2">
        <UploadCloud className="w-6 h-6 text-blue-500" /> Upload an Image
      </h2>

      <div className="flex flex-col items-center justify-center border-2 border-dashed border-gray-300 p-5 rounded-lg bg-gray-50 hover:bg-gray-100 transition">
        <input
          type="file"
          accept="image/*"
          onChange={handleFileChange}
          className="hidden"
          id="file-upload"
        />
        <label
          htmlFor="file-upload"
          className="cursor-pointer text-gray-700 hover:text-blue-600 transition"
        >
          {selectedFile ? (
            <span className="text-green-600 font-medium">{selectedFile.name}</span>
          ) : (
            "Click to select an image"
          )}
        </label>
      </div>

      <button
        onClick={handleUpload}
        disabled={!selectedFile || uploading}
        className="mt-4 w-full px-5 py-2.5 bg-blue-600 text-white text-lg font-medium rounded-lg hover:bg-blue-700 transition disabled:bg-gray-400 disabled:cursor-not-allowed"
      >
        {uploading ? "Uploading..." : "Upload"}
      </button>

      {message && (
        <p className="mt-3 text-center text-sm font-semibold text-gray-700">{message}</p>
      )}
    </div>
  );
}
