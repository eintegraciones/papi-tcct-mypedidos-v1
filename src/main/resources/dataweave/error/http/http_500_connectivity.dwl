%dw 2.0
output application/json
---
{
	code: "500",
	message: "Connectivity",
	moreInformation: {
		description: error.description  as String default ""
	}
}