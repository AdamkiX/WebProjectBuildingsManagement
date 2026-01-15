"use client";
import ApartmentView from "@/components/apartments/ApartmentView";
import Topbar from "@/components/topbar/topbar";
import { AdminAddApartment } from "@/forms/AdminAddApartmentForm";

import useApartments from "@/queries/ApartmentsQuery";

export default function ApartmentsView() {
	const apartmentsQuery = useApartments();
	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			{/* TOPBAR */}
			<Topbar
				title="Apartments"
				filterConfig={{}}
				addElement={true}
				instanceConfig={AdminAddApartment}
			/>

			{apartmentsQuery.isPending && <div>Loading...</div>}
			{apartmentsQuery.isError && <div>Error</div>}
			{apartmentsQuery.isSuccess && (
				<div className="grid grid-cols-3 gap-4">
					{apartmentsQuery.data.trapartments
						.toSorted(
							(a, b) =>
								a.apartment.apt_number - b.apartment.apt_number
						)
						.map((apartment) => (
							<ApartmentView
								key={apartment.apartment.id}
								apartment={apartment}
							/>
						))}
				</div>
			)}
		</div>
	);
}
