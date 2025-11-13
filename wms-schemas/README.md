# WMS Schemas

## Zweck
Definiert die JSON-basierten Ereignisse für den Austausch zwischen Warehouse Management System (WMS) und Order Management Service (OMS) in Programm A2.

## Envelope
Jedes Event ist in `envelope.v1` verpackt und enthält:
- `type`: Event-Key (z. B. `wms.order.packed.v1`).
- `schemaVersion`: SemVer, aktuell `1.0.0`.
- `messageId`: UUID zur Idempotenz.
- `correlationId`: entspricht der `orderId`.
- `occurredAt`: ISO-8601 Timestamp.
- `source`: immer `wms`.
- `payload`: Ereignisspezifische Daten.

## Events
- `wms.order.arrived.v1`
- `wms.order.items_picked.v1`
- `wms.order.packed.v1`
- `wms.order.shipped.v1`

## Routing & Queues
Empfohlen wird ein RabbitMQ Topic Exchange `wms.exchange`.
- Queue `wms.status` mit Binding `wms.status.update.*`.
- Optional feinere Queues mit Bindings `wms.events.*`.
- Für Fehler eine dedizierte DLQ (z. B. `wms.status.dlq`).

## Versionierung
Nur additive Änderungen innerhalb der Version; Breaking Changes erfordern einen neuen `type` (z. B. `.v2`).

## Validierung
OMS validiert jede eingehende Nachricht gegen das passende Schema (siehe `WmsSchemaValidator`). Fehlgeschlagene Nachrichten werden verworfen bzw. in die DLQ verschoben und im Central Log erfasst.

## Idempotenz
Consumer deduplizieren anhand `messageId`.
