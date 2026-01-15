"use client";
import { AdjustmentsHorizontalIcon } from "@heroicons/react/16/solid";
import { Modal } from "@mui/material";
import clsx from "clsx";
import { useState } from "react";

export default function Filter({
	filterConfig,
	addElement,
	dataSet,
}: {
	filterConfig: Object;
	addElement: boolean;
	dataSet: Object[];
}) {
	let filter = filterConfig;
	const [filtersActive, setFiltersActive] = useState<number>(0);
	const [modalOpen, setModalOpen] = useState<boolean>(false);

	return (
		<>
			<input
				type="text"
				className={clsx(
					"rounded-xl border-2 border-[#D3DAE9] bg-[#F7F7F7] p-2",
					{
						"col-span-3": !addElement,
					},
					{ "col-span-2": addElement }
				)}
				placeholder="Search"
			/>
			<div className="flex items-center">
				<AdjustmentsHorizontalIcon
					className="size-8 cursor-pointer self-center"
					onClick={() => setModalOpen(true)}
				/>
				{filtersActive > 0 && (
					<div className="ml-1 flex h-8 w-8 items-center justify-center rounded-full bg-main-orange text-xl font-bold text-white">
						{filtersActive}
					</div>
				)}
			</div>
			<Modal open={modalOpen} onClose={() => setModalOpen(false)}>
				<h1>Filter</h1>
			</Modal>
		</>
	);
}
