"use client";
import type { instanceConfig } from "@/interfaces/instanceConfig";
import {
	useState,
	ChangeEvent,
	useRef,
	useEffect,
	useContext,
	useCallback,
} from "react";
import {
	BuildingContext,
	BuildingContextProps,
} from "@/providers/BuildingProvider";
import { getFormField } from "@/helpers/formHelpers";
import { RoomPost } from "@/types/Rooms";

import useCreateApartment from "@/mutations/ApartmentMutation";
import { ApartmentPost } from "@/types/apartment";
import useRoomTypes from "@/queries/RoomsQuery";

export const AdminAddApartment: instanceConfig = {
	name: "Apartment",
	contentFunc: function Content() {
		const [roomCount, setRoomCount] = useState<number>(0);
		const roomTypesQuery = useRoomTypes();
		const [usedRoomTypes, setUsedRoomTypes] = useState<Set<number>>(
			new Set()
		);

		const selectParentRef = useRef<HTMLDivElement>(null);
		const apartmentMutation = useCreateApartment((e) => console.log(e));
		const buildingContext = useContext(
			BuildingContext
		) as BuildingContextProps;

		let submitHandler = (event: React.FormEvent<HTMLFormElement>) => {
			// TODO dodać listę wybraych pokoi
			event.preventDefault();
			const roomTypes = handleRoomTypeSubmit();
			try {
				const data = new FormData(event.target as HTMLFormElement);
				const newApartment: ApartmentPost = {
					apt_area: getFormField(data, "area"),
					apt_number: getFormField(data, "apNum"),
					details: getFormField(data, "details"),
					id_building: buildingContext.buildingId!.toString(),
					level: getFormField(data, "floor"),
				};
				apartmentMutation.mutate({
					apartment: newApartment,
					rooms: roomTypes,
				});
			} catch (error) {
				alert("All fields must be filled!");
				return;
			}
		};

		const updateSelectedElements = useCallback(() => {
			if (selectParentRef === null || roomTypesQuery.data === undefined)
				return;
			const usedTypes = new Set<number>();
			selectParentRef.current?.childNodes.forEach((child) => {
				child.childNodes.forEach((child) => {
					if (child.nodeName !== "SELECT") return;
					const select = child as HTMLSelectElement;
					const selectedIndex = select.options.selectedIndex;
					roomTypesQuery.data.forEach((k, i) => {
						if (i === selectedIndex) usedTypes.add(k.id);
					});
				});
				setUsedRoomTypes(usedTypes);
			});
		}, [roomTypesQuery.data]);

		const handleRoomTypeSubmit = (): RoomPost[] => {
			if (selectParentRef === null || roomTypesQuery.data === undefined)
				return [];
			const roomTypes: RoomPost[] = [];
			selectParentRef.current?.childNodes.forEach((child) => {
				const roomType = { id: { idRoom: 0 }, roomCount: 0 };
				child.childNodes.forEach((child, i) => {
					if (child.nodeName === "SELECT") {
						const select = child as HTMLSelectElement;
						roomType.id.idRoom = parseInt(
							select.selectedOptions[0].value
						);
					} else if (child.nodeName === "INPUT") {
						const input = child as HTMLInputElement;
						roomType.roomCount = parseInt(input.value);
					}
				});
				if (roomType.id.idRoom !== 0 && roomType.roomCount !== 0)
					roomTypes.push(roomType);
			});
			return roomTypes;
		};

		useEffect(() => {
			updateSelectedElements();
		}, [roomCount, updateSelectedElements]);

		const handleSelectChange = (event: ChangeEvent<HTMLSelectElement>) => {
			updateSelectedElements();
		};

		if (roomTypesQuery.isPending) return <div>Loading...</div>;
		if (roomTypesQuery.isError) return <div>Error...</div>;
		return (
			<>
				<h1 className="mb-4 text-2xl font-bold">Add Apartment</h1>
				<form
					id="addForm"
					className="m-auto grid h-min w-min items-center justify-center gap-3 text-lg"
					onSubmit={submitHandler}
				>
					<div className="w-[30vw]">
						<div className="grid grid-cols-[2fr_3fr] items-center gap-y-1">
							{/* Apartment number */}
							<label
								className="block h-fit items-center"
								htmlFor="apNum"
							>
								Apartment Number
							</label>
							<input
								type="text"
								name="apNum"
								id="apNum"
								placeholder="Number"
								className="block rounded-xl border-2 border-[#D3DAE9] px-4 py-2 text-base placeholder-main-orange/40"
							/>
							{/* FLOOR */}
							<label
								className="block h-fit items-center"
								htmlFor="floor"
							>
								Floor
							</label>
							<input
								type="text"
								name="floor"
								id="floor"
								placeholder="Floor"
								className="block rounded-xl border-2 border-[#D3DAE9] px-4 py-2 text-base placeholder-main-orange/40"
							/>
							{/* Metric Area */}
							<label
								className="block h-fit items-center"
								htmlFor="area"
							>
								Area
							</label>
							<input
								type="text"
								name="area"
								id="area"
								placeholder="Area"
								className="block rounded-xl border-2 border-[#D3DAE9] px-4 py-2 text-base placeholder-main-orange/40"
							/>
						</div>
					</div>
					{/* ROOMS */}
					<h2 className="mt-4 text-xl font-bold">Rooms</h2>
					<div ref={selectParentRef}>
						{/* <span className="col-span-2">Some crazy shit here</span> */}
						{Array.from({ length: roomCount }, (v, i) => i).map(
							(k) => {
								return (
									<div
										key={k}
										className="grid grid-cols-[7fr_1fr] gap-x-10 gap-y-2"
									>
										<select
											key={k}
											id={k + "s"}
											className="rounded-xl border-2 border-[#D3DAE9] px-4 py-2 text-base"
											onChange={handleSelectChange}
										>
											{roomTypesQuery.data.map((k, i) => {
												return (
													<option
														key={i}
														value={k.id}
														disabled={usedRoomTypes.has(
															k.id
														)}
													>
														{k.roomName}
													</option>
												);
											})}
										</select>
										<input
											type="number"
											className="rounded-xl border-2 border-[#D3DAE9] px-4 py-2 text-base [&::-webkit-inner-spin-button]:opacity-100 [&::-webkit-outer-spin-button]:opacity-100"
										/>
									</div>
								);
							}
						)}
						{
							// TODO: Styling needed here!
						}
						{roomCount !== roomTypesQuery.data.length && (
							<div
								onClick={() => {
									if (roomCount < roomTypesQuery.data.length)
										setRoomCount(roomCount + 1);
								}}
							>
								+ Add Room
							</div>
						)}
					</div>
					{/* Details */}
					<h2 className="mt-4 text-xl font-bold">Details</h2>
					<textarea
						name="details"
						id="details"
						className="h-[20vh] w-full resize-none rounded-lg border-2 border-[#D3DAE9] px-2 py-1 text-base placeholder-main-orange/40"
						placeholder="Details"
					/>
					<input
						type="submit"
						value={`+ Add Apartment`}
						// onClick={submitHandler}
						className="mt-2 w-min justify-self-center rounded-xl bg-main-green px-8 py-4 text-xl font-bold text-white"
					/>
				</form>
			</>
		);
	},
};
