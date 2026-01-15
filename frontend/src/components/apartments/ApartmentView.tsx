import { useState } from "react";
import { ArrowsPointingOutIcon, UserIcon } from "@heroicons/react/24/outline";
import type { ApartmentsGet } from "@/types/apartment";
import { Modal } from "@mui/material";
import ApartmentDetails from "./ApartmentDetails";
import { XMarkIcon } from "@heroicons/react/16/solid";

export default function ApartmentView({
	apartment,
}: {
	apartment: ApartmentsGet;
}) {
	const [open, setOpen] = useState(false);

	return (
		<div className="grid grid-rows-2 overflow-hidden rounded-xl bg-white shadow-md shadow-black/15">
			<div className="bg-[#D9D9D9]"></div>
			<div className="m-2 grid grid-cols-[70%_30%] gap-y-2">
				{/* Apartment header */}
				<div>
					<h2 className="text-xl font-bold">
						{" "}
						Apartment {apartment.apartment.apt_number}
					</h2>
					<p className="text-sm text-main-green">
						{/* TODO Zmienić typ  */}
						{/* {apartment.rooms
							.map(
								(r) =>
									`${r.count}\u00A0${r.room}${
										r.count > 1 ? "s" : ""
									}`
							)
							.join(" | ")} */}
					</p>
				</div>
				{/* Owner */}
				<div className="grid grid-rows-2 items-center justify-center justify-items-center text-center">
					{apartment.apartment.idTenant ? (
						<>
							<UserIcon className="size-6 h-8 w-8 rounded-full bg-[#D9D9D9] p-1" />
							<p>
								{apartment.apartment.idTenant
									? `${apartment.tenant.name} ${apartment.tenant.surname}`
									: "No owner"}
							</p>
						</>
					) : (
						<>
							<p className="row-span-2 font-bold text-main-orange">
								No owner
							</p>
						</>
					)}
				</div>
				{/* Area */}
				<div className="m-1 flex items-end text-sm text-[#6B7280]">
					<ArrowsPointingOutIcon className="mr-2 size-5" />
					{apartment.apartment.apt_area} m^2
				</div>
				{/* Details button */}
				<div
					className="m-1 h-fit cursor-pointer self-end rounded-md border-2 border-[#D3DAE9] p-1 text-center text-sm text-[#6B7280]"
					onClick={() => setOpen(true)}
				>
					View Details
				</div>
				<Modal
					open={open}
					onClose={() => setOpen(false)}
					aria-labelledby="modal-modal-title"
					aria-describedby="modal-modal-description"
				>
					<div className="modal absolute left-1/2 top-1/2 block max-h-[90vh] min-w-[30vw] -translate-x-1/2 -translate-y-1/2 transform overflow-auto rounded-xl bg-[#F7F7F7] p-4 shadow-md shadow-black outline-none">
						<XMarkIcon
							className="absolute right-2 top-2 size-10 cursor-pointer"
							onClick={() => setOpen(false)}
						/>
						<ApartmentDetails apartment={apartment} />
					</div>
				</Modal>
			</div>
		</div>
	);
}
