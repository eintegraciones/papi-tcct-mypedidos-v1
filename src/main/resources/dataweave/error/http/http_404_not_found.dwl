%dw 2.0
output application/json
---
{
	code: "404",
	message: "Not Found",
	moreInformation: {
		description: error.description  as String default ""	
	}
}