Changelog de la integración integración 556 y 618: PAPI-TCCT-MYPEDIDOS-V1
===============
- Versión inicial de la PAPI
- El endpoint POST /dataverse-to-snowflake pasa a llamarse POST /mypedidos-to-snowflake
- Se añade la integración 618 (movimiento de datos directo entre dataverse y snowflake) a la PAPI.
- Se añade endpoint POST /mypedidos/dataverse/snowflake
- El endpoint POST /mypedidos-to-snowflake pasa a llamarse POST /mypedidos/dataverse/datalake/snowflake
- La PAPI pasa de llamarse PAPI-TCCT-DATAVERSE-TO-SNOWFLAKE-V1 a PAPI-TCCT-MYPEDIDOS-V1
- Añadido envío de notificaciones a Cloudhub
- Se añade capacidad de multiples origenes de datos
- Se añade SQL Server como origen de datos
- Refactorizado de endpoints:
    - POST: /dataverse-sqlserver-to-snowflake --> Integración 556
    - POST: /dataverse-to-snowflake --> Integración 618
- 618 ahora también se ejecuta a través de scheduler con la misma periodicidad que la 556. De momento queda el endpoint de pruebas que más adelante será eliminado.