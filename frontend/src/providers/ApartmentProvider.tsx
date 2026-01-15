"use client";
import { useQueryClient } from "@tanstack/react-query";
import {
	createContext,
	useEffect,
	useState,
	useLayoutEffect,
	useContext,
} from "react";
import { useRouter } from "next/navigation";

export type ApartmentContextProps = {
	apartmentId: number | null;
	setApartmentId: (id: number | null) => void;
};

export const ApartmentContext = createContext<ApartmentContextProps>({
	apartmentId: null,
	setApartmentId: () => {},
});

export default function ApartmentProvider({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	const router = useRouter();
	const [apartmentId, setApartmentId] = useState<number | null>(null);
	const queryClient = useQueryClient();

	useLayoutEffect(() => {
		const id = localStorage.getItem("apartmentId");
		setApartmentId(id === null ? null : parseInt(id));
		if (id === null) {
			router.replace("/dashboard");
		}
	}, [router]);

	useEffect(() => {
		// queryClient.invalidateQueries({ queryKey: ["building"] });
		if (apartmentId !== null)
			localStorage.setItem("apartmentId", apartmentId.toString());
	}, [apartmentId, queryClient]);

	return (
		<ApartmentContext.Provider value={{ apartmentId, setApartmentId }}>
			{children}
		</ApartmentContext.Provider>
	);
}
