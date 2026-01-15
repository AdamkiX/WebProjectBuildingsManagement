import { Sidebar } from "@/components/sidebar/Sidebar";

export default function Layout({
	children,
}: Readonly<{ children: React.ReactNode }>) {
	return (
		<main className="grid h-screen w-screen grid-cols-[15vw_1fr]">
			<Sidebar />
			<main className="overflow-y-scroll">{children}</main>
		</main>
	);
}
