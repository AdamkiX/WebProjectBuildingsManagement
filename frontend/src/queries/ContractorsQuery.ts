import axios from "@/providers/AxiosInstance";
import { useQuery } from "@tanstack/react-query";
import { Contractor, ContractorResponse } from "@/types/Contractors";

async function fetchContractors(): Promise<Contractor[]> {
	const request = await axios.get<{ body: ContractorResponse[] }>(
		"/manager/executor"
	);
	const contractors = request.data.body.map((contractor) => {
		return {
			id_executor: contractor.executor.id_executor,
			name: contractor.executor.name,
			surname: contractor.executor.surname,
			phone: contractor.executor.phone,
			birthdate: contractor.executor.birthdate,
			company_name: contractor.executor.company_name,
			address: contractor.executor.address,
			nip: contractor.executor.nip,
			regon: contractor.executor.regon,
			work_type: contractor.workType.work_type,
		};
	});
	return contractors;
}

export default function useContractors() {
	return useQuery({ queryKey: ["contractors"], queryFn: fetchContractors });
}
