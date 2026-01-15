"use client";
import ApartmentProvider from "@/providers/ApartmentProvider";
export default function Layout({ children }: { children: React.ReactNode }) {
	return <ApartmentProvider>{children}</ApartmentProvider>;
}
