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
import { AuthContext, AuthContextType } from "./AuthProvider";

export type BuildingContextProps = {
	buildingId: number | null;
	setBuildingId: (id: number) => void;
};

export const BuildingContext = createContext<BuildingContextProps>({
	buildingId: null,
	setBuildingId: () => {},
});

export default function BuildingProvider({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	const router = useRouter();
	const [buildingId, setBuildingId] = useState<number | null>(null);
	const queryClient = useQueryClient();
	const authContext = useContext(AuthContext) as AuthContextType;

	useLayoutEffect(() => {
		if (authContext.user?.accountType === "admin") {
			const id = localStorage.getItem("buildingId");
			setBuildingId(id === null ? null : parseInt(id));
			if (id === null) {
				router.replace("/admin/buildings");
			}
		}
	}, [router, authContext.user?.accountType]);

	useEffect(() => {
		if (authContext.user?.accountType === "admin") {
			queryClient.invalidateQueries({ queryKey: ["building"] });
			if (buildingId !== null)
				localStorage.setItem("buildingId", buildingId.toString());
		}
	}, [buildingId, queryClient, authContext.user?.accountType]);

	return (
		<BuildingContext.Provider value={{ buildingId, setBuildingId }}>
			{children}
		</BuildingContext.Provider>
	);
}
