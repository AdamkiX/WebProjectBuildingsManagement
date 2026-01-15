import axios from "@/providers/AxiosInstance";
import { ComplaintGet } from "@/types/Complaints";
import { useQuery } from "@tanstack/react-query";

async function getComplaints() {
	const request = await axios.get<ComplaintGet>("/tenant/complaint");
	return request.data.body.map((complaint) => {
		return {
			...complaint,
			date: new Date(complaint.date).toISOString().split("T")[0],
		};
	});
}

export default function useComplaints() {
	return useQuery({ queryKey: ["complaints"], queryFn: getComplaints });
}
