%dw 2.0
output application/json
---
{
	code: "405",
	message: "Method Not Allowed",
	moreInformation: {
		description: error.description  as String default ""	
	}
}