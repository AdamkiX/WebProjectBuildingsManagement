import useRoomTypes from "@/queries/RoomsQuery";
import type { ApartmentsGet } from "@/types/apartment";
import { ArrowsPointingOutIcon } from "@heroicons/react/24/outline";
import clsx from "clsx";

export default function ApartmentDetails({
	apartment,
}: {
	apartment: ApartmentsGet;
}) {
	const roomTypesQuery = useRoomTypes();

	const getRoomTypeName = (id: number): string => {
		const roomType = roomTypesQuery.data?.find((type) => type.id === id);
		return roomType ? roomType.roomName : "Unknown";
	};

	return (
		<>
			{roomTypesQuery.isError && <div>Error</div>}
			{roomTypesQuery.isPending && <div>Loading...</div>}
			{roomTypesQuery.isSuccess && (
				<>
					<h1 className="mb-2 text-3xl font-bold">
						Apartment {apartment.apartment.apt_number} details
					</h1>
					{/* area */}
					<div className="m-1 flex items-end text-sm text-[#6B7280]">
						<ArrowsPointingOutIcon className="mr-1 size-5" />
						{apartment.apartment.apt_area} m^2
					</div>
					{/* floor */}
					<p className="m-1 flex items-end text-sm text-[#6B7280]">
						Floor {apartment.apartment.level}
					</p>
					<div className="mt-2 grid grid-cols-2 justify-between">
						{/* ROOMS */}
						<div className="text-left">
							<h2 className="text-2xl font-bold">Rooms</h2>
							<ul className="text-[#6B7280]">
								{apartment.rooms.map((room) => (
									<li key={room.id.idRoom}>
										{getRoomTypeName(room.id.idRoom)}
										{" - "}
										{room.roomCount}
									</li>
								))}
							</ul>
						</div>
						{/* OWNER */}
						<div className="text-right">
							<h2 className="text-2xl font-bold">Owner</h2>
							<p
								className={clsx(
									"text-xl",
									{ "": apartment.apartment.idTenant },
									{
										"font-bold text-main-orange":
											!apartment.apartment.idTenant,
									}
								)}
							>
								{apartment.apartment.idTenant
									? `${apartment.tenant.name} ${apartment.tenant.surname}`
									: "None"}
							</p>
						</div>
						{/* DETAILS */}
					</div>
					<div className="mt-2 text-left">
						<h2 className="text-2xl font-bold">Details</h2>
						<p className="text-justify text-[#6B7280]">
							{apartment.apartment.details}
						</p>
					</div>
				</>
			)}
		</>
	);
}
