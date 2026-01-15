/** @type {import('next').NextConfig} */
const nextConfig = {
	reactStrictMode: false,
	redirects: async () => {
		return [
			{
				source: "/admin",
				destination: "/admin/buildings",
				permanent: true,
			},
		];
	},
};

export default nextConfig;
