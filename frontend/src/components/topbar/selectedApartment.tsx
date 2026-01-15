import { useContext } from "react";
import useTenantsApartments from "@/queries/TenantsApartmentsQuery";
import {
	ApartmentContext,
	ApartmentContextProps,
} from "@/providers/ApartmentProvider";

export default function SelectedApartment({
	className,
}: {
	className?: string;
}) {
	const tenantApartmentsQuery = useTenantsApartments();
	const apartmentContext = useContext(
		ApartmentContext
	) as ApartmentContextProps;

	const apartment = tenantApartmentsQuery.data?.find(
		(trapartment) =>
			trapartment.apartment.id === apartmentContext.apartmentId
	);

	if (
		tenantApartmentsQuery.isError ||
		apartmentContext.apartmentId === null
	) {
		// TODO może jakiś redirect
		return (
			<div className={className}>
				<div className="grid grid-cols-1 rounded-xl bg-[#F7F7F7] px-4 py-3 text-right shadow-[0px_2px_10px_2px_rgba(0,0,0,0.25)]">
					<p className="text-2xl font-bold">
						Please select valid apartment
					</p>
				</div>
			</div>
		);
	}
	if (tenantApartmentsQuery.isPending) {
		return (
			<div className={className}>
				<div className="grid grid-cols-1 rounded-xl bg-[#F7F7F7] px-4 py-3 text-right shadow-[0px_2px_10px_2px_rgba(0,0,0,0.25)]">
					<p className="text-2xl font-bold">Loading...</p>
					<p className="text-xl font-bold text-[#6B7280]">
						Loading...
					</p>
				</div>
			</div>
		);
	}

	return (
		<div className={className}>
			<div className="grid grid-rows-2 rounded-xl bg-[#F7F7F7] px-4 py-3 text-right shadow-[0px_2px_10px_2px_rgba(0,0,0,0.25)]">
				<p className="text-2xl font-bold">
					Apartment {apartment?.apartment.apt_number}
				</p>
				<p className="text-xl font-bold text-[#6B7280]">
					Building ID {apartment?.apartment.id_building}
				</p>
			</div>
		</div>
	);
}
