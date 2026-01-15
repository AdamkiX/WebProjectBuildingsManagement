"use client";
import Filter from "./filter";
import SelectedBuilding from "./selectedBuilding";
import SelectedApartment from "./selectedApartment";
import AddInstance from "./addInstance";
import { instanceConfig } from "@/interfaces/instanceConfig";

export default function Topbar({
	title,
	filterConfig,
	addElement,
	instanceConfig,
	displayApartment,
}: {
	title: string;
	filterConfig: Object;
	addElement: boolean;
	instanceConfig?: instanceConfig;
	displayApartment?: boolean;
}) {
	if (addElement && !instanceConfig)
		throw new Error("instanceConfig is required when addElement is true");

	return (
		<>
			<div className="grid h-full w-full grid-cols-[3fr_1fr] grid-rows-3">
				<h1 className="row-span-2 text-5xl font-bold">{title}</h1>
				{(displayApartment === undefined ||
					displayApartment === false) && (
					<SelectedBuilding className="row-span-3 self-end" />
				)}
				{displayApartment === true && (
					<SelectedApartment className="row-span-3 self-end" />
				)}
				<div className="grid grid-cols-5 gap-2">
					{addElement && <AddInstance instance={instanceConfig!} />}
					<Filter
						filterConfig={filterConfig}
						addElement={addElement}
						dataSet={[]}
					/>
				</div>
			</div>
		</>
	);
}
