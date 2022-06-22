%dw 2.0
output application/json
---
{
	code: "401",
	message: "Security",
	moreInformation: {
		description: error.description  as String default ""
	}
}