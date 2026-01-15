"use client";
import { instanceConfig } from "@/interfaces/instanceConfig";
import { XMarkIcon } from "@heroicons/react/16/solid";
import { Modal } from "@mui/material";
import { FormEvent, useState } from "react";

export default function AddInstance({
	instance,
}: {
	instance: instanceConfig;
}) {
	const [open, setOpen] = useState(false);
	const handleOpen = () => setOpen(true);
	const handleClose = () => setOpen(false);

	return (
		<>
			<div
				className="h-fit w-fit cursor-pointer rounded-xl border-2 border-[#D3DAE9] p-2 shadow-md"
				onClick={handleOpen}
			>
				Add {instance.name}
			</div>
			<Modal
				disableEscapeKeyDown
				open={open}
				onClose={handleClose}
				aria-labelledby="modal-modal-title"
				aria-describedby="modal-modal-description"
			>
				<div className="modal absolute left-1/2 top-1/2 block max-h-[90vh] -translate-x-1/2 -translate-y-1/2 transform overflow-auto rounded-xl bg-[#F7F7F7] p-4 shadow-md shadow-black outline-none">
					<XMarkIcon
						className="absolute right-2 top-2 size-10 cursor-pointer"
						onClick={handleClose}
					/>
					{instance.contentFunc()}
				</div>
			</Modal>
		</>
	);
}
