# ECOVOLT DATA MANAGEMENT
Este projeto foi desenvolvido como parte de uma solução tecnológica para comunidades remotas, com foco na previsão e otimização de geração e consumo de energia. Ele utiliza uma API RESTful construída com Spring Boot para gerenciar dados de geração e consummo de energia, fontes renováveis e etc. Essas comunidades frequentemente enfrentam dificuldades no acesso à energia confiável devido à variabilidade climática e à imprevisibilidade da demanda. A solução proposta busca mitigar esses desafios, facilitando a transição energética para sistemas renováveis, permitindo um planejamento mais eficiente e sustentável.

Este projeto tem potencial para ser implementado em soluções reais de monitoramento e gestão de energia, contribuindo para um futuro mais sustentável e seguro para as comunidades que dependem de energia renovável.

## Pré-requisitos

- Java 17 ou superior
- Gradle
- IDE de sua escolha (recomendado: IntelliJ IDEA)

## Instalação

1. Clone o repositório:
    ```bash
    git clone https://github.com/laLuna01/EcoVoltJava.git
    ```
2. Navegue até o diretório do projeto:
    ```bash
    cd EcoVoltJava
    ```
3. Compile o projeto usando Gradle:
    ```bash
    ./gradlew build
    ```
4. Execute a aplicação:
    ```bash
    ./gradlew bootRun
    ```

## Uso

A aplicação estará disponível em `http://localhost:8080`. Você pode usar ferramentas como Postman ou cURL para interagir com a API.

## Endpoints

### Fonte de energia

- **POST /fonteenergia**: Cria uma nova fonte de energia.
- **GET /fonteenergia**: Retorna todas as fontes de energia.
- **GET /fonteenergia/{id}**: Retorna uma fonte de energia pelo ID.
- **PUT /fonteenergia/{id}**: Atualiza uma fonte de energia existente.
- **DELETE /fonteenergia/{id}**: Deleta uma fonte de energia.

### Consumidor

- **POST /consumidor**: Cria um novo consumidor.
- **GET /consumidor**: Retorna todos os consumidores.
- **GET /consumidor/{id}**: Retorna um consumidor pelo ID.
- **PUT /consumidor/{id}**: Atualiza um consumidor existente.
- **DELETE /consumidor/{id}**: Deleta um consumidor.

### Dispositivo

- **POST /dispositivo**: Cria um novo dispositivo.
- **GET /dispositivo**: Retorna todos os dispositivos.
- **GET /dispositivo/{id}**: Retorna um dispositivo pelo ID.
- **PUT /dispositivo/{id}**: Atualiza um dispositivo existente.
- **DELETE /dispositivo/{id}**: Deleta um dispositivo.

### Tipo de consumidor

- **POST /tipoconsumidor**: Cria um novo tipo de consumidor.
- **GET /tipoconsumidor**: Retorna todos os tipos de consumidor.
- **GET /tipoconsumidor/{id}**: Retorna um tipo de consumidor pelo ID.
- **PUT /tipoconsumidor/{id}**: Atualiza um tipo de consumidor existente.
- **DELETE /tipoconsumidor/{id}**: Deleta um tipo de consumidor.

### Tipo de fonte

- **POST /tipofonte**: Cria um novo tipo de fonte.
- **GET /tipofonte**: Retorna todos os tipos de fonte.
- **GET /tipofonte/{id}**: Retorna um tipo de fonte pelo ID.
- **PUT /tipofonte/{id}**: Atualiza um tipo de fonte existente.
- **DELETE /tipofonte/{id}**: Deleta um tipo de fonte.

### Tipo de dispositivo

- **POST /tipodispositivo**: Cria um novo tipo de dispositivo.
- **GET /tipodispositivo**: Retorna todos os tipos de dispositivo.
- **GET /tipodispositivo/{id}**: Retorna um tipo de dispositivo pelo ID.
- **PUT /tipodispositivo/{id}**: Atualiza um tipo de dispositivo existente.
- **DELETE /tipodispositivo/{id}**: Deleta um tipo de dispositivo.

### Consumo de energia

- **POST /consumoenergia**: Cria um novo consumo de energia.
- **GET /consumoenergia**: Retorna todos os consumos de energia.
- **GET /consumoenergia/{id}**: Retorna um consumo de energia pelo ID.
- **PUT /consumoenergia/{id}**: Atualiza um consumo de energia existente.
- **DELETE /consumoenergia/{id}**: Deleta um consumo de energia.

### Geração de energia

- **POST /geracaoenergia**: Cria uma nova geração de energia.
- **GET /geracaoenergia**: Retorna todas as gerações de energia.
- **GET /geracaoenergia/{id}**: Retorna uma geração de energia pelo ID.
- **PUT /geracaoenergia/{id}**: Atualiza uma geração de energia existente.
- **DELETE /geracaoenergia/{id}**: Deleta uma geração de energia.

### País

- **POST /pais**: Cria um novo país.
- **GET /pais**: Retorna todos os países.
- **GET /pais/{id}**: Retorna um país pelo ID.
- **PUT /pais/{id}**: Atualiza um país existente.
- **DELETE /pais/{id}**: Deleta um país.

### Estado

- **POST /estado**: Cria um novo estado.
- **GET /estado**: Retorna todos os estados.
- **GET /estado/{id}**: Retorna um estado pelo ID.
- **PUT /estado/{id}**: Atualiza um estado existente.
- **DELETE /estado/{id}**: Deleta um estado.

### Cidade

- **POST /cidade**: Cria uma nova cidade.
- **GET /cidade**: Retorna todas as cidades.
- **GET /cidade/{id}**: Retorna uma cidade pelo ID.
- **PUT /cidade/{id}**: Atualiza uma cidade existente.
- **DELETE /cidade/{id}**: Deleta uma cidade.

### Bairro

- **POST /bairro**: Cria um novo bairro.
- **GET /bairro**: Retorna todos os bairros.
- **GET /bairro/{id}**: Retorna um bairro pelo ID.
- **PUT /bairro/{id}**: Atualiza um bairro existente.
- **DELETE /bairro/{id}**: Deleta um bairro.

### Localização

- **POST /localizacao**: Cria uma nova localização.
- **GET /localizacao**: Retorna todas as localizações.
- **GET /localizacao/{id}**: Retorna uma localização pelo ID.
- **PUT /localizacao/{id}**: Atualiza uma localização existente.
- **DELETE /localizacao/{id}**: Deleta uma localização.
  
## Swagger

`http://localhost:8080/swagger-ui.html`

## Testes
- Vá até collections no postman e importe o arquivo .json

[https://EcoVoltJava/blob/master/documentos/testes_api_postman.json](https://github.com/laLuna01/EcoVoltJava/blob/master/docs/EcoVolt%20GS%20API.postman_collection.json)

## Diagrama de Entidade e Relacionamento
![alt text](Relacional.png)

## Diagramas de Classes de Entidade

### Controllers
![alt text](controllers.png)

### Dtos
![alt text](dtos.png)

### Exceptions
![alt text](exceptions.png)

### Mappers
![alt text](mappers.png)

### Repositories
![alt text](repositories.png)

### Models
![alt text](models.png)

## Alunos
<b>Luana Sousa Matos</b> RM552621
<b>Nicolas Martins</b> RM553478
