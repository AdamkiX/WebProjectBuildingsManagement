"use client";
import type { Column } from "@/types/Table";
import clsx from "clsx";

export default function TableRow<Type>(props: {
	columnConfig: Column<Type>[];
	value: Type & { onClick?: () => void };
}) {
	return (
		<>
			{props.columnConfig.map((column, index) => {
				if ("objectKey" in column) {
					return (
						<div
							key={index}
							className={clsx({
								" bg-gray-300 p-2": true,
								"rounded-l-md": index === 0,
								"rounded-r-md":
									index === props.columnConfig.length - 1,
								"text-left": column.textAlign === "left",
								"text-center": column.textAlign === "center",
								"text-right": column.textAlign === "right",
								"mr-1": column.divider === "right",
								"ml-1": column.divider === "left",
							})}
						>
							{/* @ts-ignore */}
							{typeof props.value[column.objectKey] === "boolean"
								? /* @ts-ignore */
									props.value[column.objectKey]
									? "Yes"
									: "No"
								: props.value[column.objectKey]}
						</div>
					);
				} else if ("text" in column) {
					return (
						<div
							key={index}
							className={clsx({
								" ml-1 w-fit cursor-pointer bg-gray-300 p-2 text-center underline":
									true,
								"rounded-l-md": index === 0,
								"rounded-r-md":
									index === props.columnConfig.length - 1,
							})}
							onClick={() => {
								if (props.value.onClick !== undefined)
									props.value.onClick();
							}}
						>
							{column.text}
						</div>
					);
				}
			})}
		</>
	);
}
