"use client";

import Link from "next/link";
import React from "react";
import { usePathname } from "next/navigation";
import clsx from "clsx";
import { useContext } from "react";
import { AuthContext, AuthContextType } from "@/providers/AuthProvider";
import { useRouter } from "next/navigation";
// icon imports
import {
	HomeIcon,
	CurrencyDollarIcon,
	WrenchIcon,
	ExclamationTriangleIcon,
	DocumentCheckIcon,
	CogIcon,
	ArrowLeftStartOnRectangleIcon,
	MapIcon,
	UsersIcon,
} from "@heroicons/react/24/outline";

type menuItem = {
	name: string;
	icon: JSX.Element;
	link: string;
};

// UPPER MENU ITEMS
const userMenuItems: menuItem[] = [
	{ name: "Dashboard", icon: <HomeIcon />, link: "/dashboard" },
	{ name: "Payments", icon: <CurrencyDollarIcon />, link: "/payments" },
	{ name: "Repairs", icon: <WrenchIcon />, link: "/repairs" },
	{
		name: "Complaints",
		icon: <ExclamationTriangleIcon />,
		link: "/complaints",
	},
];

const adminMenuItems: menuItem[] = [
	{ name: "Buildings", icon: <MapIcon />, link: "/admin/buildings" },
	{ name: "Apartments", icon: <HomeIcon />, link: "/admin/apartments" },
	{ name: "Rents", icon: <CurrencyDollarIcon />, link: "/admin/rents" },
	{ name: "Faults", icon: <WrenchIcon />, link: "/admin/faults" },
	{ name: "Repairs", icon: <WrenchIcon />, link: "/admin/repairs" },
	{ name: "Contractors", icon: <UsersIcon />, link: "/admin/contractors" },
	{ name: "Complaints", icon: <MapIcon />, link: "/admin/complaints" },
];

const lowerMenuItems: menuItem[] = [
	{ name: "Contracts", icon: <DocumentCheckIcon />, link: "./contracts" },
	{ name: "Settings", icon: <CogIcon />, link: "/settings" },
];

export const Sidebar = () => {
	const { user, axios } = useContext(AuthContext) as AuthContextType;

	// console.log(user);

	const isAdmin = user?.accountType === "admin";
	const upperMenuItems = isAdmin ? adminMenuItems : userMenuItems;

	const pathname = usePathname();
	const router = useRouter();

	const logout = () => {
		axios
			.post("/auth/logout")
			.then((res) => {
				router.push("/");
			})
			.catch((err) => {
				console.error(err);
			});
	};

	return (
		<div className="grid h-screen grid-rows-[min-content_min-content_min-content_1fr_min-content] gap-2 rounded-r-lg bg-[#EFEFEF] p-4 shadow-[4px_4px_4px_0px_rgba(90,90,90,0.25)]">
			{/* USER INFO */}
			<div className="grid grid-cols-[min-content_1fr] gap-x-3 gap-y-1">
				{/* USER IMG */}
				<div className="h-[8vh] w-[8vh] rounded-md bg-main-orange"></div>
				{/* USER NAME */}
				<div className="self-center text-2xl">
					<p>{user?.name}</p>
					<p>{user?.surname}</p>
				</div>
				{/* USER ADDRESS */}
				<div className="col-span-2 text-sm text-main-green">
					{user?.residence}
				</div>
			</div>
			<div className="mb-2 h-0 rounded-sm border-2 border-main-orange"></div>
			{/* UPPER MENU */}
			<div className="flex flex-col gap-1 text-[#0F172A]">
				{upperMenuItems.map((item, index) => (
					<Link
						key={index}
						href={item.link}
						className={clsx(
							"grid grid-cols-[min-content_1fr] gap-3 rounded-md p-1",
							{
								"bg-[#5A5A5A] text-white":
									pathname === item.link,
							},
							{ "hover:bg-[#D2D2D2]": pathname !== item.link }
						)}
					>
						{<item.icon.type className="size-10" />}
						<span className="flex items-center text-xl">
							{item.name}
						</span>
					</Link>
				))}
			</div>
			{/* SPACER */}
			<div></div>
			{/* LOWER MENU */}
			<div>
				<div className="flex flex-col gap-1 text-[#0F172A]">
					{lowerMenuItems.map((item, index) => (
						<Link
							key={index}
							href={item.link}
							className={clsx(
								"grid grid-cols-[min-content_1fr] gap-3 rounded-md p-1 hover:bg-[#D2D2D2] ",
								{
									"bg-[#5A5A5A] text-white":
										pathname === item.link,
								}
							)}
						>
							{<item.icon.type className="size-10" />}
							<span className="flex items-center text-xl">
								{item.name}
							</span>
						</Link>
					))}
					{/* LOGOUT */}
					<div
						onClick={logout}
						className="grid grid-cols-[min-content_1fr] gap-3 rounded-md p-1 hover:bg-[#D2D2D2] "
					>
						<ArrowLeftStartOnRectangleIcon className="size-10" />
						<span className="flex items-center text-xl">
							Logout
						</span>
					</div>
				</div>
			</div>
		</div>
	);
};
