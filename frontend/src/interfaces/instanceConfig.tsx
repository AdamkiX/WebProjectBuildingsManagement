"use client";

import { FormEvent } from "react";

export interface instanceConfig {
	name: string;
	config?: {};
	contentFunc: () => JSX.Element;
}
