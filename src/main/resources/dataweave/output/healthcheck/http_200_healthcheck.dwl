%dw 2.0
output application/json
---
{
	status: "200",
	message: "Healthcheck is running",
	href: "https://developer.mozilla.org/es/docs/Web/HTTP/Status/200",
	data: {
		description: "API check for correct operation",
		href:"https://help.mulesoft.com/s/article/How-to-implement-health-check-monitor-for-your-apis"
		
	}
}