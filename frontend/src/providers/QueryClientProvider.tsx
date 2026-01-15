"use client";

import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { useState } from "react";

export default function QueryClientProviderComponent({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	const [queryClient] = useState(
		new QueryClient({
			defaultOptions: { queries: { refetchOnWindowFocus: false } },
		})
	);
	return (
		<QueryClientProvider client={queryClient}>
			{children}
		</QueryClientProvider>
	);
}
