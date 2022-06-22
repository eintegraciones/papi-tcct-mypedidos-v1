%dw 2.0
output application/json
---
{
	code: "403",
	message: "Unauthorized",
	moreInformation: {
		description: error.description  as String default ""
	}
}