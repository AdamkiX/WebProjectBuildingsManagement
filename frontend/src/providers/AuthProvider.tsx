"use client";
import { UserProfile } from "@/types/userProfile";
import { createContext, useLayoutEffect, useState } from "react";
import { AxiosInstance } from "axios";
import { usePathname, useRouter } from "next/navigation";
import axiosInstance from "@/providers/AxiosInstance";

export type AuthContextType = {
	user: UserProfile | null;
	setUserProfile: (user: UserProfile) => void;
	axios: AxiosInstance;
};

export const AuthContext = createContext<AuthContextType | null>(null);

export default function AuthProvider({
	children,
}: {
	children: React.ReactNode;
}) {
	const [userProfile, setUserProfile] = useState<UserProfile | null>(null);
	const pathname = usePathname();
	const router = useRouter();

	useLayoutEffect(() => {
		const publicPaths = ["/"];

		if (!userProfile && !publicPaths.includes(pathname)) {
			axiosInstance
				.get("/auth/loggedUser", { withCredentials: true })
				.then((res) => {
					if (res.status !== 200) {
						throw new Error("Bad response");
					}
					let userProfile: UserProfile;
					if (res.data.manager) {
						userProfile = res.data.manager as UserProfile;
						userProfile.accountType = "admin";
					} else if (res.data.tenant) {
						userProfile = res.data.tenant as UserProfile;
						userProfile.accountType = "user";
					} else {
						throw new Error("Bad response");
					}
					setUserProfile(userProfile);
				})
				.catch((err) => {
					router.push("/");
					console.error(err);
				});
		} else if (!publicPaths.includes(pathname)) {
			if (
				userProfile?.accountType === "admin" &&
				!pathname.startsWith("/admin")
			) {
				router.push("/admin");
			}
			if (
				userProfile?.accountType === "user" &&
				pathname.startsWith("/admin")
			) {
				router.push("/dashboard");
			}
		}
	}, [setUserProfile, userProfile, pathname, router]);

	return (
		<AuthContext.Provider
			value={{
				user: userProfile,
				setUserProfile,
				axios: axiosInstance,
			}}
		>
			{children}
		</AuthContext.Provider>
	);
}
