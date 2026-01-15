import BuildingProvider from "@/providers/BuildingProvider";

export default function Layout({
	children,
}: Readonly<{ children: React.ReactNode }>) {
	return <BuildingProvider>{children}</BuildingProvider>;
}
