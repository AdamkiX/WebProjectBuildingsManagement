"use client";
import { LoginInputField } from "@/components/LoginInputField";
import { useContext, useLayoutEffect, useState } from "react";
// import axios from "axios";
import { useRouter } from "next/navigation";
import { UserProfile } from "@/types/userProfile";
import { AuthContext, AuthContextType } from "@/providers/AuthProvider";

export default function LoginPane() {
	const router = useRouter();

	const [error, setError] = useState<string | null>(null);
	const [username, setUsername] = useState<string>("");
	const [password, setPassword] = useState<string>("");
	const [lock, setLock] = useState<boolean>(false);

	const auth = useContext(AuthContext) as AuthContextType;

	const { user, setUserProfile, axios } = auth;

	let onSubmit = (e: React.FormEvent) => {
		setLock(true);
		e.preventDefault();
		axios
			.post(
				"/auth/signin",
				{
					username,
					password,
				},
				{
					withCredentials: true,
				}
			)
			.then((res) => {
				if (res.status != 200) {
					setError("Bad credentials");
					throw new Error("Bad credentials");
				}

				let userProfile: UserProfile;

				if (res.data.body.manager) {
					userProfile = res.data.body.manager as UserProfile;
					userProfile.accountType = "admin";
				} else if (res.data.body.tenant) {
					userProfile = res.data.body.tenant as UserProfile;
					userProfile.accountType = "user";
				} else {
					setError("Bad response");
					throw new Error("Bad response");
				}

				setUserProfile(userProfile);

				if (userProfile.accountType === "admin")
					axios.put("/agreements");

				router.push(
					userProfile.accountType === "admin"
						? "/admin/buildings"
						: "/dashboard"
				);
			})
			.catch((err) => {
				if (err.response) setError(err.response.message);
				console.error(err);
			})
			.then(() => setLock(false)); // finally
	};

	// LOAD SPINNER
	useLayoutEffect(() => {
		async function getLoader() {
			const { bouncy } = await import("ldrs");
			bouncy.register();
		}
		getLoader();
	}, []);

	return (
		//   <main className="loginBG dark:loginBG-dark grid min-h-screen grid-cols-1 grid-rows-[20%_80%] items-center justify-items-center">
		<main className="loginBG flex h-screen w-screen items-center justify-center">
			<div className="block h-full w-full bg-white/60 p-6 outline-white backdrop-blur-sm md:h-auto md:w-3/5 md:rounded-xl md:outline md:outline-2 lg:w-1/3">
				<h1 className="text-center text-3xl font-bold">Login</h1>
				<form className="md-8" method="POST" onSubmit={onSubmit}>
					{error && (
						<div className="mt-2 text-center font-bold text-red-500 drop-shadow-sm">
							{error}
						</div>
					)}
					<div className="mx-auto max-w-lg">
						<LoginInputField
							label="Username"
							type="text"
							name="username"
							value={username}
							onChange={setUsername}
							lock={lock}
						/>
						<LoginInputField
							label="Password"
							type="password"
							name="password"
							value={password}
							onChange={setPassword}
							lock={lock}
						/>
						<div
							className="mt-3 flex h-14 w-full items-center justify-center rounded-lg bg-main-green px-6 py-3 text-center text-lg font-semibold text-white shadow-xl transition-colors hover:bg-black"
							onClick={!lock ? onSubmit : () => {}}
						>
							{!lock ? (
								<span>LOGIN</span>
							) : (
								<l-bouncy speed={1} color={"white"}></l-bouncy>
							)}
						</div>
						<input type="submit" className="hidden" />
					</div>
				</form>
			</div>
		</main>
	);
}
