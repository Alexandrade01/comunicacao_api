## Endpoints da Aplicação
- **POST /comunicacao/agendar**: Agenda uma nova comunicação para o usuário.
- **GET /comunicacao/**: Busca o status de uma comunicação específica do usuário.
- **PATCH /comunicacao/cancelar**: Cancela uma comunicação específica do usuário.
- **POST /comunicacao/enviar-email**: Envia um email para o destinatário especificado.

## Execução com Docker
- A aplicação escuta na porta **8090** conforme `application.yaml`.
- O `Dockerfile` usa build multi-stage com Maven.
